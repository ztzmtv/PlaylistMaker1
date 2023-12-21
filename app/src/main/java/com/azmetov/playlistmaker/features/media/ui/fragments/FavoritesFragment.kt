package com.azmetov.playlistmaker.features.media.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.azmetov.playlistmaker.R
import com.azmetov.playlistmaker.core.domain.models.Track
import com.azmetov.playlistmaker.core.ui.fragment.BindingFragment
import com.azmetov.playlistmaker.core.ui.recycler.BaseAdapter
import com.azmetov.playlistmaker.core.ui.recycler.ListItem
import com.azmetov.playlistmaker.core.ui.recycler.SpaceItemDecoration
import com.azmetov.playlistmaker.core.ui.recycler.item.TrackBinder
import com.azmetov.playlistmaker.core.ui.recycler.item.TrackVH
import com.azmetov.playlistmaker.databinding.FragmentFavoritesBinding
import com.azmetov.playlistmaker.di.NAME_BASE_ADAPTER
import com.azmetov.playlistmaker.di.NAME_SEARCH_TRACK
import com.azmetov.playlistmaker.features.media.ui.viewmodel.FavoritesViewModel
import com.azmetov.playlistmaker.features.player.ui.activity.PlayerActivity
import com.azmetov.playlistmaker.features.search.ui.items.ImageBinder
import com.azmetov.playlistmaker.features.search.ui.items.ImageVHFactory
import com.azmetov.playlistmaker.features.search.ui.items.LabelBinder
import com.azmetov.playlistmaker.features.search.ui.items.LabelVHFactory
import com.azmetov.playlistmaker.utils.debounce
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class FavoritesFragment : BindingFragment<FragmentFavoritesBinding>() {

    private val viewModel: FavoritesViewModel by viewModel()
    private val adapter: BaseAdapter by inject(named(NAME_BASE_ADAPTER))

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoritesBinding {
        return FragmentFavoritesBinding.inflate(inflater, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favoritesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.favoritesRv.adapter = adapter

        viewModel.favoritesList().observe(viewLifecycleOwner) { favorites ->
            adapter.clear()
            if (favorites.isEmpty()) {
                mediaIsEmpty()
            } else {
                showFavorites(favorites)
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun showFavorites(favorites: List<Track>) {
        binding.favoritesRv.invalidateItemDecorations()
        val itemDecoration = SpaceItemDecoration(
            context = requireContext(),
            position = RECYCLER_FIRST_POSITION,
            spaceTopDp = SPACE_TOP,
            spaceBottomDp = SPACE_BOTTOM
        )
        binding.favoritesRv.addItemDecoration(itemDecoration)
        favorites.forEach { track ->
            val binder: TrackBinder by inject()
            binder.trackClickListener = trackClickListener
            val item: ListItem<TrackVH, Track> by inject(named(NAME_SEARCH_TRACK)) {
                parametersOf(binder, track)
            }
            adapter.addItem(item)
        }
    }

    private fun mediaIsEmpty() {
        val itemDecoration = SpaceItemDecoration(
            context = requireContext(),
            position = 0,
            spaceTopDp = SPACE_TOP_WHEN_EMPTY,
            spaceBottomDp = SPACE_BOTTOM_WHEN_EMPTY
        )
        binding.favoritesRv.addItemDecoration(itemDecoration)
        val stubImageItem = ListItem(
            factory = ImageVHFactory(),
            binder = ImageBinder(),
            data = R.drawable.ic_not_found
        )
        val stubTextItem = ListItem(
            factory = LabelVHFactory(),
            binder = LabelBinder(),
            data = getString(R.string.media_favorites_empty_medialibrary)
        )
        adapter.addItem(stubImageItem)
        adapter.addItem(stubTextItem)
    }

    private val trackClickListener: ((Track) -> Unit) =
        debounce(CLICK_DEBOUNCE_DELAY, lifecycleScope, false) { track ->
            val intent = PlayerActivity.getIntent(requireContext(), track)
            startActivity(intent)
        }

    companion object {
        fun newInstance() = FavoritesFragment()
        private const val CLICK_DEBOUNCE_DELAY = 100L
        private const val RECYCLER_FIRST_POSITION = 0
        private const val SPACE_BOTTOM_WHEN_EMPTY = 16
        private const val SPACE_TOP_WHEN_EMPTY = 100
        private const val SPACE_BOTTOM = 0
        private const val SPACE_TOP = 16
    }
}