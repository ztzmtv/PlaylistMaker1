package com.azmetov.playlistmaker.features.media.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azmetov.playlistmaker.R
import com.azmetov.playlistmaker.core.ui.fragment.BindingFragment
import com.azmetov.playlistmaker.core.ui.recycler.BaseAdapter
import com.azmetov.playlistmaker.core.ui.recycler.ListItem
import com.azmetov.playlistmaker.core.ui.recycler.SpaceItemDecoration
import com.azmetov.playlistmaker.databinding.FragmentPlaylistsBinding
import com.azmetov.playlistmaker.di.NAME_BASE_ADAPTER
import com.azmetov.playlistmaker.features.media.ui.viewmodel.PlaylistsViewModel
import com.azmetov.playlistmaker.features.search.ui.items.ButtonBinder
import com.azmetov.playlistmaker.features.search.ui.items.ButtonVHFactory
import com.azmetov.playlistmaker.features.search.ui.items.ImageBinder
import com.azmetov.playlistmaker.features.search.ui.items.ImageVHFactory
import com.azmetov.playlistmaker.features.search.ui.items.LabelBinder
import com.azmetov.playlistmaker.features.search.ui.items.LabelVHFactory
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class PlaylistsFragment : BindingFragment<FragmentPlaylistsBinding>() {

    private val viewModel: PlaylistsViewModel by viewModel()
    private val adapter: BaseAdapter by inject(named(NAME_BASE_ADAPTER))

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlaylistsBinding {
        return FragmentPlaylistsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonItemDecoration = SpaceItemDecoration(
            context = requireContext(),
            position = 0,
            spaceTopDp = 24,
        )
        val imageItemDecoration = SpaceItemDecoration(
            context = requireContext(),
            position = 1,
            spaceTopDp = 46,
            spaceBottomDp = 16
        )
        binding.playlistsRv.addItemDecoration(buttonItemDecoration)
        binding.playlistsRv.addItemDecoration(imageItemDecoration)
        binding.playlistsRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.playlistsRv.adapter = adapter
        adapter.clear()


        val stubButtonItem = ListItem(
            factory = ButtonVHFactory(),
            binder = ButtonBinder(),
            data = getString(R.string.media_playlists_button_title)
        )
        val itemDecoration = SpaceItemDecoration(
            context = requireContext(),
            position = FIRST_POSITION,
            spaceBottomDp = BOTTOM_SPACE
        )
        binding.playlistsRv.addItemDecoration(itemDecoration)
        val stubImageItem = ListItem(
            factory = ImageVHFactory(),
            binder = ImageBinder(),
            data = R.drawable.ic_not_found
        )
        val stubTextItem = ListItem(
            factory = LabelVHFactory(),
            binder = LabelBinder(),
            data = getString(R.string.media_playlists_empty_playlists)
        )
        adapter.addItem(stubButtonItem)
        adapter.addItem(stubImageItem)
        adapter.addItem(stubTextItem)
    }

    companion object {
        fun newInstance() = PlaylistsFragment()
        private const val BOTTOM_SPACE = 46
        private const val FIRST_POSITION = 0
    }
}