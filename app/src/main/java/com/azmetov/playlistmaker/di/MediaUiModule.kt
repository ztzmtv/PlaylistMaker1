package com.azmetov.playlistmaker.di

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
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mediaUiModule = module {

    factory<ViewHolderFactory<ButtonVH>>(named(NAME_PLAYLISTS_BUTTON_VH_FACTORY)) { ButtonVHFactory() }
    factory<ViewHolderFactory<ImageVH>>(named(NAME_PLAYLISTS_IMAGE_VH_FACTORY)) { ImageVHFactory() }
    factory<ViewHolderFactory<LabelVH>>(named(NAME_PLAYLISTS_LABEL_VH_FACTORY)) { LabelVHFactory() }

    factory<BaseVHBinder<ButtonVH, String?>>(named(NAME_PLAYLISTS_BUTTON_BINDER)) { ButtonBinder() }
    factory<BaseVHBinder<ImageVH, Int?>>(named(NAME_PLAYLISTS_IMAGE_BINDER)) { ImageBinder() }
    factory<BaseVHBinder<LabelVH, String?>>(named(NAME_PLAYLISTS_LABEL_BINDER)) { LabelBinder() }

    factory(named(NAME_PLAYLISTS_BUTTON)) { param ->
        ListItem<ButtonVH, String?>(
            get(named(NAME_PLAYLISTS_BUTTON_VH_FACTORY)),
            get(named(NAME_PLAYLISTS_BUTTON_BINDER)),
            param.get()
        )
    }

    factory(named(NAME_PLAYLISTS_IMAGE)) { param ->
        ListItem<ImageVH, Int?>(
            get(named(NAME_PLAYLISTS_IMAGE_VH_FACTORY)),
            get(named(NAME_PLAYLISTS_IMAGE_BINDER)),
            param.get()
        )
    }
    factory(named(NAME_PLAYLISTS_LABEL)) { param ->
        ListItem<LabelVH, String?>(
            get(named(NAME_PLAYLISTS_LABEL_VH_FACTORY)),
            get(named(NAME_PLAYLISTS_LABEL_BINDER)),
            param.get()
        )
    }
}

const val NAME_PLAYLISTS_BUTTON = "NAME_PLAYLISTS_BUTTON"
const val NAME_PLAYLISTS_IMAGE = "NAME_PLAYLISTS_IMAGE"
const val NAME_PLAYLISTS_LABEL = "NAME_PLAYLISTS_LABEL"

const val NAME_PLAYLISTS_BUTTON_VH_FACTORY = "NAME_PLAYLISTS_BUTTON_VH_FACTORY"
const val NAME_PLAYLISTS_IMAGE_VH_FACTORY = "NAME_PLAYLISTS_IMAGE_VH_FACTORY"
const val NAME_PLAYLISTS_LABEL_VH_FACTORY = "NAME_PLAYLISTS_LABEL_VH_FACTORY"

const val NAME_PLAYLISTS_BUTTON_BINDER = "NAME_PLAYLISTS_BUTTON_BINDER"
const val NAME_PLAYLISTS_IMAGE_BINDER = "NAME_PLAYLISTS_IMAGE_BINDER"
const val NAME_PLAYLISTS_LABEL_BINDER = "NAME_PLAYLISTS_LABEL_BINDER"
