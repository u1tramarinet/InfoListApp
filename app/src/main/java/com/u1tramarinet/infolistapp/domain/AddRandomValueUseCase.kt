package com.u1tramarinet.infolistapp.domain

import com.u1tramarinet.infolistapp.di.DefaultDispatcher
import com.u1tramarinet.infolistapp.repository.RandomValuesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddRandomValueUseCase @Inject constructor(
    private val repository: RandomValuesRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke() {
        withContext(dispatcher) {
            repository.addRandomValue()
        }
    }
}