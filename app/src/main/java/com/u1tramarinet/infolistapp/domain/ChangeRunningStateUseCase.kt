package com.u1tramarinet.infolistapp.domain

import com.u1tramarinet.infolistapp.di.DefaultDispatcher
import com.u1tramarinet.infolistapp.repository.InfoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChangeRunningStateUseCase @Inject constructor(
    private val repository: InfoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(running: Boolean) {
        withContext(dispatcher) {
            repository.setUpdateState(running)
        }
    }
}