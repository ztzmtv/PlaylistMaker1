package com.azmetov.playlistmaker.features.search.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.lifecycle.lifecycleScope
import com.azmetov.playlistmaker.R
import com.azmetov.playlistmaker.core.domain.models.Track
import com.azmetov.playlistmaker.core.ui.fragment.BindingFragment
import com.azmetov.playlistmaker.core.ui.recycler.BaseAdapter
import com.azmetov.playlistmaker.core.ui.recycler.ListItem
import com.azmetov.playlistmaker.core.ui.recycler.item.TrackBinder
import com.azmetov.playlistmaker.core.ui.recycler.item.TrackVH
import com.azmetov.playlistmaker.databinding.FragmentSearchBinding
import com.azmetov.playlistmaker.di.NAME_BASE_ADAPTER
import com.azmetov.playlistmaker.di.NAME_SEARCH_BUTTON
import com.azmetov.playlistmaker.di.NAME_SEARCH_IMAGE
import com.azmetov.playlistmaker.di.NAME_SEARCH_LABEL
import com.azmetov.playlistmaker.di.NAME_SEARCH_PROGRESSBAR
import com.azmetov.playlistmaker.di.NAME_SEARCH_TRACK
import com.azmetov.playlistmaker.features.player.ui.activity.PlayerActivity
import com.azmetov.playlistmaker.features.search.ui.items.ButtonBinder
import com.azmetov.playlistmaker.features.search.ui.items.ButtonVH
import com.azmetov.playlistmaker.features.search.ui.items.ImageVH
import com.azmetov.playlistmaker.features.search.ui.items.ProgressbarVH
import com.azmetov.playlistmaker.features.search.ui.state.SearchScreenState
import com.azmetov.playlistmaker.features.search.ui.viewmodel.SearchViewModel
import com.azmetov.playlistmaker.utils.debounce
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named


class SearchFragment : BindingFragment<FragmentSearchBinding>() {
    private val viewModel: SearchViewModel by viewModel()
    private val adapter: BaseAdapter by inject(named(NAME_BASE_ADAPTER))

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let { onRestoreInstanceState(it) }
        viewModel.screenStateLiveData().observe(viewLifecycleOwner) { state ->
            adapter.clear()
            when (state) {
                is SearchScreenState.Result -> {
                    addListItemsTracks(state.list)
                }

                is SearchScreenState.ConnectionError -> {
                    addListItemImage(R.drawable.ic_network_error)
                    addListItemLabel("Проблемы со связью")
                    addListItemLabel("Загрузка не удалась. Проверьте подключение к интернету")
                    addListItemButton("Обновить", viewModel::sendRequest)
                }

                is SearchScreenState.NotFound -> {
                    addListItemImage(R.drawable.ic_not_found)
                    addListItemLabel("Ничего не нашлось")
                }

                is SearchScreenState.History -> {
                    if (state.list.isNotEmpty()) {
                        addListItemLabel("Вы искали")
                        addListItemsTracks(state.list)
                        addListItemButton("Очистить историю", viewModel::clearListListener)
                    }
                }

                SearchScreenState.Loading -> {
                    addListItemProgressbar()
                }

                else -> {}
            }
            adapter.notifyDataSetChanged()
        }

        setupAdapters()
        setupListeners()
    }

    private fun setupAdapters() {
        binding.searchRv.adapter = adapter
    }

    private fun setupListeners() {
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            val text = binding.searchEditText.text.toString()
            if (actionId == EditorInfo.IME_ACTION_DONE && text.isNotEmpty()) {
                viewModel.searchDebounce(text, viewLifecycleOwner.lifecycleScope)
                true
            } else
                false
        }


        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {/*NO-OP*/
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchDebounce(text.toString(), viewLifecycleOwner.lifecycleScope)
            }

            override fun afterTextChanged(p0: Editable?) { /*NO-OP*/
            }

        })

        binding.searchEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val trackList = viewModel.getListFromStorage()
                trackList?.let { viewModel.updateScreen(SearchScreenState.History(it)) }
            }
        }
        binding.searchEditText.requestFocus()
        hideKeyboard(binding.searchEditText)

        binding.searchTextInputLayout.setEndIconOnClickListener {
            binding.searchEditText.text.clear()
            hideKeyboard(binding.searchEditText)
        }
    }

    private fun addListItemProgressbar() {
        val item: ListItem<ProgressbarVH, Any> by inject(named(NAME_SEARCH_PROGRESSBAR))
        adapter.addItem(item)
    }

    private fun addListItemImage(@DrawableRes resId: Int?) {
        val item: ListItem<ImageVH, Int?> by inject(named(NAME_SEARCH_IMAGE)) {
            parametersOf(resId)
        }
        adapter.addItem(item)
    }

    private fun addListItemButton(data: String, action: () -> Unit) {
        val buttonBinder: ButtonBinder by inject()
        buttonBinder.buttonClickListener = action
        val item: ListItem<ButtonVH, String?> by inject(named(NAME_SEARCH_BUTTON)) {
            parametersOf(buttonBinder, data)
        }
        adapter.addItem(item)
    }

    private fun addListItemLabel(data: String) {
        val item: ListItem<ImageVH, String?> by inject(named(NAME_SEARCH_LABEL)) {
            parametersOf(data)
        }
        adapter.addItem(item)
    }

    private val trackClickListener: ((Track) -> Unit) =
        debounce(SearchViewModel.CLICK_DEBOUNCE_DELAY, lifecycleScope, false) { track ->
            viewModel.addToStorage(track)
            val intent = PlayerActivity.getIntent(requireContext(), track)
            startActivity(intent)
        }


    private fun addListItemsTracks(trackList: List<Track>) {
        trackList.forEach { track ->
            val binder: TrackBinder by inject()
            binder.trackClickListener = trackClickListener
            val item: ListItem<TrackVH, Track> by inject(named(NAME_SEARCH_TRACK)) {
                parametersOf(binder, track)
            }
            adapter.addItem(item)
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val editTextString = binding.searchEditText.text.toString()
        outState.putString(EDIT_TEXT_KEY, editTextString)
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val editTextString = savedInstanceState.getString(EDIT_TEXT_KEY, "")
        binding.searchEditText.setText(editTextString, TextView.BufferType.EDITABLE)
    }

    companion object {
        private const val EDIT_TEXT_KEY = "EDIT_TEXT_KEY"
    }
}