package com.u1tramarinet.infolistapp.domain

import com.u1tramarinet.infolistapp.di.DefaultDispatcher
import com.u1tramarinet.infolistapp.repository.RandomValueRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChangeUpdateIntervalUseCase @Inject constructor(
    private val repository: RandomValueRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(timeMills: Long) {
        withContext(dispatcher) {
            repository.setUpdateInterval(timeMills)
        }
    }
}