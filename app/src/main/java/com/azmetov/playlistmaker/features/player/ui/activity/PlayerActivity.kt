package com.azmetov.playlistmaker.features.player.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azmetov.playlistmaker.R
import com.azmetov.playlistmaker.core.domain.models.Track
import com.azmetov.playlistmaker.core.ui.recycler.BaseAdapter
import com.azmetov.playlistmaker.core.ui.recycler.ListItem
import com.azmetov.playlistmaker.di.NAME_BASE_ADAPTER
import com.azmetov.playlistmaker.di.NAME_PLAYER_ATTRIBUTE
import com.azmetov.playlistmaker.di.NAME_PLAYER_IMAGE
import com.azmetov.playlistmaker.di.NAME_PLAYER_PLAYER
import com.azmetov.playlistmaker.di.NAME_PLAYER_TIMER
import com.azmetov.playlistmaker.di.NAME_PLAYER_TITLE
import com.azmetov.playlistmaker.features.player.ui.viewholders.AttributeVH
import com.azmetov.playlistmaker.features.player.ui.viewholders.ImageVH
import com.azmetov.playlistmaker.features.player.ui.viewholders.PlayerBinder
import com.azmetov.playlistmaker.features.player.ui.viewholders.PlayerVH
import com.azmetov.playlistmaker.features.player.ui.viewholders.TimerBinder
import com.azmetov.playlistmaker.features.player.ui.viewholders.TimerVH
import com.azmetov.playlistmaker.features.player.ui.viewholders.TitleVH
import com.azmetov.playlistmaker.features.player.ui.viewmodel.PlayerViewModel
import com.azmetov.playlistmaker.utils.Converter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named


class PlayerActivity : AppCompatActivity() {
    private val viewModel: PlayerViewModel by viewModel()
    private val playerBinder: PlayerBinder by inject()
    private val timerBinder: TimerBinder by inject()
    private val adapter: BaseAdapter by inject(named(NAME_BASE_ADAPTER))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val track = getTrackFromExtras(intent.extras)
            ?: throw NullPointerException("Track can not be null")
        if (savedInstanceState == null) {
            viewModel.initTrack(track)
        }

        setupRecyclerView()
        setupToolbar()
        setupPlayerUI(track)
        setupListeners()
        collectStateFlow()
    }

    private fun collectStateFlow() {
        viewModel.isFavorite().observe(this) { isFavorite ->
            playerBinder.setFavoritesState(isFavorite)
        }

        viewModel.isPlaying().observe(this) { isPlaying ->
            playerBinder.setPlayState(isPlaying)
        }

        viewModel.trackPosition().observe(this) { time ->
            timerBinder.setTimer(time)
        }

        viewModel.isInPlaylist().observe(this) { isAdded ->
            playerBinder.setAddToPlaylistState(isAdded)
        }
    }

    private fun setupListeners() {
        playerBinder.playClickListener = viewModel.getPlayClickListener()
        playerBinder.favoriteClickListener = viewModel.getFavoriteClickListener()
        playerBinder.playlistClickListener = viewModel.getAppPlaylistClickListener()
    }


    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.player_toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    @Suppress("DEPRECATION")
    private fun getTrackFromExtras(extras: Bundle?): Track? =
        if (extras != null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                extras.getSerializable(EXTRA_TRACK, Track::class.java)
            else
                extras.getSerializable(EXTRA_TRACK) as Track
        else
            throw NullPointerException("Extra serializable in PlayerActivity can not be null!")


    private fun setupRecyclerView() {
        val rv = findViewById<RecyclerView>(R.id.list)
        val llm = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv.adapter = adapter
        rv.layoutManager = llm
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setupPlayerUI(track: Track) {
        val imageItem: ListItem<ImageVH, String?> by inject(
            qualifier = named(NAME_PLAYER_IMAGE)
        ) {
            val attr = Converter.getCoverArtwork(track.artworkUrl100)
            parametersOf(attr)
        }
        val titleItem: ListItem<TitleVH, Pair<String, String>> by inject(
            qualifier = named(NAME_PLAYER_TITLE)
        ) {
            val attr = Pair(track.trackName, track.artistName)
            parametersOf(attr)
        }
        val playerItem: ListItem<PlayerVH, Unit> by inject(
            qualifier = named(NAME_PLAYER_PLAYER)
        ) {
            parametersOf(playerBinder)
        }
        val timerItem: ListItem<TimerVH, Int> by inject(
            qualifier = named(NAME_PLAYER_TIMER)
        ) {
            parametersOf(timerBinder)
        }
        val attr1 = Pair("Длительность", Converter.convertTime(track.trackTime))
        val attr2 = Pair("Альбом", track.collectionName!!)
        val attr3 = Pair("Год", track.releaseDate!!)
        val attr4 = Pair("Жанр", track.genre!!)
        val attr5 = Pair("Страна", track.country!!)

        val attributeItem1: ListItem<AttributeVH, Pair<String, String>> by inject(
            qualifier = named(NAME_PLAYER_ATTRIBUTE)
        ) {
            parametersOf(attr1)
        }
        val attributeItem2: ListItem<AttributeVH, Pair<String, String>> by inject(
            qualifier = named(NAME_PLAYER_ATTRIBUTE)
        ) {
            parametersOf(attr2)
        }
        val attributeItem3: ListItem<AttributeVH, Pair<String, String>> by inject(
            qualifier = named(NAME_PLAYER_ATTRIBUTE)
        ) {
            parametersOf(attr3)
        }
        val attributeItem4: ListItem<AttributeVH, Pair<String, String>> by inject(
            qualifier = named(NAME_PLAYER_ATTRIBUTE)
        ) {
            parametersOf(attr4)
        }
        val attributeItem5: ListItem<AttributeVH, Pair<String, String>> by inject(
            qualifier = named(NAME_PLAYER_ATTRIBUTE)
        ) {
            parametersOf(attr5)
        }
        with(adapter) {
            addItem(imageItem)
            addItem(titleItem)
            addItem(playerItem)
            addItem(timerItem)
            addItem(attributeItem1)
            addItem(attributeItem2)
            addItem(attributeItem3)
            addItem(attributeItem4)
            addItem(attributeItem5)
            notifyDataSetChanged()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        playerBinder.playClickListener = null
        playerBinder.favoriteClickListener = null
        playerBinder.playlistClickListener = null
    }

    companion object {
        private const val EXTRA_TRACK = "EXTRA_TRACK"
        private fun log(s: String) {
            Log.d("${this::class.qualifiedName}TAG", s)
        }

        fun getIntent(context: Context, track: Track) =
            Intent(context, PlayerActivity::class.java).apply {
                putExtra(EXTRA_TRACK, track)
            }
    }

}