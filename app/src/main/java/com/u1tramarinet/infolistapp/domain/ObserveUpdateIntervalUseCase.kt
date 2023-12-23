package com.u1tramarinet.infolistapp.domain

import com.u1tramarinet.infolistapp.repository.RandomValueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUpdateIntervalUseCase @Inject constructor(
    private val repository: RandomValueRepository,
) {
    operator fun invoke(): Flow<Long> = repository.getUpdateIntervalStream()
}