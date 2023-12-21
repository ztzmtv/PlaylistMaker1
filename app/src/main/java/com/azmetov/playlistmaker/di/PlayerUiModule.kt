package com.azmetov.playlistmaker.di

import com.azmetov.playlistmaker.core.ui.recycler.BaseVHBinder
import com.azmetov.playlistmaker.core.ui.recycler.ListItem
import com.azmetov.playlistmaker.core.ui.recycler.ViewHolderFactory
import com.azmetov.playlistmaker.features.player.ui.viewholders.AttributeBinder
import com.azmetov.playlistmaker.features.player.ui.viewholders.AttributeVH
import com.azmetov.playlistmaker.features.player.ui.viewholders.AttributeVHFactory
import com.azmetov.playlistmaker.features.player.ui.viewholders.ImageBinder
import com.azmetov.playlistmaker.features.player.ui.viewholders.ImageVH
import com.azmetov.playlistmaker.features.player.ui.viewholders.ImageVHFactory
import com.azmetov.playlistmaker.features.player.ui.viewholders.PlayerBinder
import com.azmetov.playlistmaker.features.player.ui.viewholders.PlayerVH
import com.azmetov.playlistmaker.features.player.ui.viewholders.PlayerVHFactory
import com.azmetov.playlistmaker.features.player.ui.viewholders.TimerBinder
import com.azmetov.playlistmaker.features.player.ui.viewholders.TimerVH
import com.azmetov.playlistmaker.features.player.ui.viewholders.TimerVHFactory
import com.azmetov.playlistmaker.features.player.ui.viewholders.TitleBinder
import com.azmetov.playlistmaker.features.player.ui.viewholders.TitleVH
import com.azmetov.playlistmaker.features.player.ui.viewholders.TitleVHFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

val playerUiModule = module {
    factory { PlayerBinder() }
    factory { TimerBinder() }

    factory<ViewHolderFactory<ImageVH>>(named(NAME_PLAYER_IMAGE_VH_FACTORY)) { ImageVHFactory() }
    factory<ViewHolderFactory<TitleVH>>(named(NAME_PLAYER_TITLE_VH_FACTORY)) { TitleVHFactory() }
    factory<ViewHolderFactory<PlayerVH>>(named(NAME_PLAYER_PLAYER_VH_FACTORY)) { PlayerVHFactory() }
    factory<ViewHolderFactory<AttributeVH>>(named(NAME_PLAYER_ATTRIBUTE_VH_FACTORY)) { AttributeVHFactory() }
    factory<ViewHolderFactory<TimerVH>>(named(NAME_PLAYER_TIMER_VH_FACTORY)) { TimerVHFactory() }

    factory<BaseVHBinder<ImageVH, String?>>(named(NAME_PLAYER_IMAGE_BINDER)) { ImageBinder() }
    factory<BaseVHBinder<TitleVH, Pair<String, String>>>(named(NAME_PLAYER_TITLE_BINDER)) { TitleBinder() }
    factory<BaseVHBinder<PlayerVH, Unit>>(named(NAME_PLAYER_PLAYER_BINDER)) { get<PlayerBinder>() }
    factory<BaseVHBinder<AttributeVH, Pair<String, String>>>(named(NAME_PLAYER_ATTRIBUTE_BINDER)) { AttributeBinder() }
    factory<BaseVHBinder<TimerVH, Int>>(named(NAME_PLAYER_TIMER_BINDER)) { get<TimerBinder>() }

    factory(named(NAME_PLAYER_IMAGE)) { param ->
        ListItem<ImageVH, String?>(
            get(named(NAME_PLAYER_IMAGE_VH_FACTORY)),
            get(named(NAME_PLAYER_IMAGE_BINDER)),
            param.get()
        )
    }
    factory(named(NAME_PLAYER_TITLE)) { param ->
        ListItem<TitleVH, Pair<String, String>>(
            get(named(NAME_PLAYER_TITLE_VH_FACTORY)),
            get(named(NAME_PLAYER_TITLE_BINDER)),
            param.get()
        )
    }
    factory(named(NAME_PLAYER_PLAYER)) { paramPlayerBinder ->
        ListItem<PlayerVH, Unit>(
            get(named(NAME_PLAYER_PLAYER_VH_FACTORY)),
            paramPlayerBinder.get(),
            Unit
        )
    }
    factory(named(NAME_PLAYER_ATTRIBUTE)) { param ->
        ListItem<AttributeVH, Pair<String, String>>(
            get(named(NAME_PLAYER_ATTRIBUTE_VH_FACTORY)),
            get(named(NAME_PLAYER_ATTRIBUTE_BINDER)),
            param.get()
        )
    }
    factory(named(NAME_PLAYER_TIMER)) { paramTimerBinder ->
        ListItem<TimerVH, Int>(
            get(named(NAME_PLAYER_TIMER_VH_FACTORY)),
            paramTimerBinder.get(),
            0
        )
    }
}

const val NAME_PLAYER_IMAGE = "NAME_PLAYER_IMAGE"
const val NAME_PLAYER_TITLE = "NAME_PLAYER_TITLE"
const val NAME_PLAYER_PLAYER = "NAME_PLAYER_PLAYER"
const val NAME_PLAYER_ATTRIBUTE = "NAME_PLAYER_ATTRIBUTE"
const val NAME_PLAYER_TIMER = "NAME_PLAYER_TIMER"

const val NAME_PLAYER_IMAGE_VH_FACTORY = "NAME_IMAGE_VH_FACTORY"
const val NAME_PLAYER_TITLE_VH_FACTORY = "NAME_PLAYER_TITLE_VH_FACTORY"
const val NAME_PLAYER_ATTRIBUTE_VH_FACTORY = "NAME_PLAYER_ATTRIBUTE_VH_FACTORY"
const val NAME_PLAYER_PLAYER_VH_FACTORY = "NAME_PLAYER_PLAYER_VH_FACTORY"
const val NAME_PLAYER_TIMER_VH_FACTORY = "NAME_PLAYER_TIMER_VH_FACTORY"

const val NAME_PLAYER_IMAGE_BINDER = "NAME_IMAGE_BINDER"
const val NAME_PLAYER_TITLE_BINDER = "NAME_PLAYER_TITLE_BINDER"
const val NAME_PLAYER_ATTRIBUTE_BINDER = "NAME_PLAYER_ATTRIBUTE_BINDER"
const val NAME_PLAYER_PLAYER_BINDER = "NAME_PLAYER_PLAYER_BINDER"
const val NAME_PLAYER_TIMER_BINDER = "NAME_PLAYER_TIMER_BINDER"

