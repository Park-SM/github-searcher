package com.smparkworld.githubsearcher.data.repository

import androidx.paging.*
import com.smparkworld.githubsearcher.data.remote.EventRemoteDataSource
import com.smparkworld.githubsearcher.model.DetailUserUIModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
        private val remoteDataSource: EventRemoteDataSource
): EventRepository {

    override suspend fun getEventsById(uid: String, pageSize: Int) =
            Pager(PagingConfig(pageSize = pageSize)) {
                EventPagingSource(remoteDataSource, uid, pageSize)
            }.flow.map {
                it.map { item -> DetailUserUIModel.Item(item) as DetailUserUIModel}
                  .insertHeaderItem(item = DetailUserUIModel.Header)
                  .insertSeparators { before, after ->
                      if (before is DetailUserUIModel.Item && after is DetailUserUIModel.Item) {
                          DetailUserUIModel.Separator
                      } else null
                  }
            }
}