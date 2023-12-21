package com.azmetov.playlistmaker.features.player.data

import com.azmetov.playlistmaker.core.data.frameworks.storage.db.FavoritesDao
import com.azmetov.playlistmaker.core.domain.models.Track
import com.azmetov.playlistmaker.features.player.domain.FavoritesRepository
import com.azmetov.playlistmaker.utils.Converter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val dao: FavoritesDao
) : FavoritesRepository {
    override suspend fun insertTrack(track: Track) =
        dao.insertTrack(Converter.entityToFavoriteDbModel(track))

    override suspend fun deleteTrack(track: Track) =
        dao.deleteTrack(Converter.entityToFavoriteDbModel(track))

    override fun getFavoritesFlow(): Flow<List<Track>> {
        return dao.getListFlow().map { list ->
            list.map { Converter.favoriteDbModelToEntity(it) }
        }
    }

    override suspend fun getListId(): List<Int> {
        return dao.getListId()
    }

}