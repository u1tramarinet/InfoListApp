package com.u1tramarinet.infolistapp.domain

import com.u1tramarinet.infolistapp.repository.RandomValue
import com.u1tramarinet.infolistapp.repository.RandomValuesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveRandomValuesUseCase @Inject constructor(private val repository: RandomValuesRepository) {
    operator fun invoke(): Flow<List<RandomValue>> =
        repository.getRandomValuesStream()
}