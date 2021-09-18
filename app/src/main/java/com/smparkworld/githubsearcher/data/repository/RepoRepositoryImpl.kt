package com.smparkworld.githubsearcher.data.repository

import com.smparkworld.githubsearcher.data.remote.RepoRemoteDataSource
import com.smparkworld.githubsearcher.di.AppModule.DispatcherIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val remoteDataSource: RepoRemoteDataSource,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher
) : RepoRepository {

    override suspend fun getRepoById(uid: String) = withContext(ioDispatcher) {
        remoteDataSource.getRepoById(uid)
    }
}