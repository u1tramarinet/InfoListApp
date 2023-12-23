package com.u1tramarinet.infolistapp.domain

import com.u1tramarinet.infolistapp.repository.RandomValueRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveRandomValueUseCase @Inject constructor(private val repository: RandomValueRepository) {
    operator fun invoke(): Flow<Int> = repository.getRandomValueStream()
}