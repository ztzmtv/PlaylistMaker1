package com.azmetov.playlistmaker.di

import com.azmetov.playlistmaker.core.domain.models.Track
import com.azmetov.playlistmaker.core.ui.recycler.BaseVHBinder
import com.azmetov.playlistmaker.core.ui.recycler.ListItem
import com.azmetov.playlistmaker.core.ui.recycler.ViewHolderFactory
import com.azmetov.playlistmaker.features.search.ui.items.ButtonBinder
import com.azmetov.playlistmaker.features.search.ui.items.ButtonVH
import com.azmetov.playlistmaker.features.search.ui.items.ButtonVHFactory
import com.azmetov.playlistmaker.features.search.ui.items.ImageBinder
import com.azmetov.playlistmaker.features.search.ui.items.ImageVH
import com.azmetov.playlistmaker.features.search.ui.items.ImageVHFactory
import com.azmetov.playlistmaker.features.search.ui.items.LabelBinder
import com.azmetov.playlistmaker.features.search.ui.items.LabelVH
import com.azmetov.playlistmaker.features.search.ui.items.LabelVHFactory
import com.azmetov.playlistmaker.features.search.ui.items.ProgressbarBinder
import com.azmetov.playlistmaker.features.search.ui.items.ProgressbarVH
import com.azmetov.playlistmaker.features.search.ui.items.ProgressbarVHFactory
import com.azmetov.playlistmaker.core.ui.recycler.item.TrackBinder
import com.azmetov.playlistmaker.core.ui.recycler.item.TrackVH
import com.azmetov.playlistmaker.core.ui.recycler.item.TrackVHFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

val searchUiModule = module {
    factory { ButtonBinder() }
    factory { TrackBinder() }

    factory<ViewHolderFactory<ProgressbarVH>>(named(NAME_SEARCH_PROGRESSBAR_VH_FACTORY)) { ProgressbarVHFactory() }
    factory<ViewHolderFactory<ImageVH>>(named(NAME_SEARCH_IMAGE_VH_FACTORY)) { ImageVHFactory() }
    factory<ViewHolderFactory<ButtonVH>>(named(NAME_SEARCH_BUTTON_VH_FACTORY)) { ButtonVHFactory() }
    factory<ViewHolderFactory<LabelVH>>(named(NAME_SEARCH_LABEL_VH_FACTORY)) { LabelVHFactory() }
    factory<ViewHolderFactory<TrackVH>>(named(NAME_SEARCH_TRACK_VH_FACTORY)) { TrackVHFactory() }

    factory<BaseVHBinder<ProgressbarVH, Any>>(named(NAME_SEARCH_PROGRESSBAR_BINDER)) { ProgressbarBinder() }
    factory<BaseVHBinder<ImageVH, Int?>>(named(NAME_SEARCH_IMAGE_BINDER)) { ImageBinder() }
    factory<BaseVHBinder<ButtonVH, String?>>(named(NAME_SEARCH_BUTTON_BINDER)) { get<ButtonBinder>() }
    factory<BaseVHBinder<LabelVH, String?>>(named(NAME_SEARCH_LABEL_BINDER)) { LabelBinder() }
    factory<BaseVHBinder<TrackVH, Track>>(named(NAME_SEARCH_TRACK_BINDER)) { get<TrackBinder>() }


    factory(named(NAME_SEARCH_LABEL)) { params ->
        ListItem<ImageVH, String?>(
            get(named(NAME_SEARCH_LABEL_VH_FACTORY)),
            get(named(NAME_SEARCH_LABEL_BINDER)),
            params.get()
        )
    }
    factory(named(NAME_SEARCH_BUTTON)) { params ->
        ListItem<ButtonVH, String?>(
            get(named(NAME_SEARCH_BUTTON_VH_FACTORY)),
            params.get(),
            params.get()
        )
    }

    factory(named(NAME_SEARCH_IMAGE)) { params ->
        ListItem<ImageVH, Int?>(
            get(named(NAME_SEARCH_IMAGE_VH_FACTORY)),
            get(named(NAME_SEARCH_IMAGE_BINDER)),
            params.get()
        )
    }
    factory(named(NAME_SEARCH_PROGRESSBAR)) {
        ListItem<ProgressbarVH, Any>(
            get(named(NAME_SEARCH_PROGRESSBAR_VH_FACTORY)),
            get(named(NAME_SEARCH_PROGRESSBAR_BINDER)),
            Any()
        )
    }

    factory(named(NAME_SEARCH_TRACK)) { params ->
        ListItem<TrackVH, Track>(
            get(named(NAME_SEARCH_TRACK_VH_FACTORY)),
            params.get(),
            params.get()
        )
    }

}
const val NAME_SEARCH_LABEL = "NAME_SEARCH_LABEL"
const val NAME_SEARCH_BUTTON = "NAME_SEARCH_BUTTON"
const val NAME_SEARCH_IMAGE = "NAME_SEARCH_IMAGE"
const val NAME_SEARCH_PROGRESSBAR = "NAME_SEARCH_PROGRESSBAR"
const val NAME_SEARCH_TRACK = "NAME_SEARCH_TRACK"

const val NAME_SEARCH_PROGRESSBAR_VH_FACTORY = "NAME_SEARCH_PROGRESSBAR_VH"
const val NAME_SEARCH_IMAGE_VH_FACTORY = "NAME_SEARCH_IMAGE_VH"
const val NAME_SEARCH_BUTTON_VH_FACTORY = "NAME_SEARCH_BUTTON_VH"
const val NAME_SEARCH_LABEL_VH_FACTORY = "NAME_SEARCH_LABEL_VH"
const val NAME_SEARCH_TRACK_VH_FACTORY = "NAME_SEARCH_TRACK_VH"

const val NAME_SEARCH_PROGRESSBAR_BINDER = "NAME_SEARCH_PROGRESSBAR_BINDER"
const val NAME_SEARCH_IMAGE_BINDER = "NAME_SEARCH_IMAGE_BINDER"
const val NAME_SEARCH_BUTTON_BINDER = "NAME_SEARCH_BUTTON_BINDER"
const val NAME_SEARCH_LABEL_BINDER = "NAME_SEARCH_LABEL_BINDER"
const val NAME_SEARCH_TRACK_BINDER = "NAME_SEARCH_TRACK_BINDER"