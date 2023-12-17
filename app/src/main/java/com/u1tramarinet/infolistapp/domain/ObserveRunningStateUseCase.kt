package com.u1tramarinet.infolistapp.domain

import com.u1tramarinet.infolistapp.repository.InfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveRunningStateUseCase @Inject constructor(
    private val repository: InfoRepository,
) {
    operator fun invoke(): Flow<Boolean> = repository.getUpdateState()
}