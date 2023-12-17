package com.u1tramarinet.infolistapp.repository

import com.u1tramarinet.infolistapp.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InfoRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : InfoRepository {
    private val delayTimeMills: MutableStateFlow<Long> = MutableStateFlow(100L)
    private val runningState: MutableStateFlow<Boolean> = MutableStateFlow(true)
    override fun getRandomValueStream(): Flow<Int> = flow {
        val range = (1..100)
        while (true) {
            delay(delayTimeMills.value)
            if (runningState.value) {
                emit(range.random())
            }
        }
    }.flowOn(dispatcher)

    override suspend fun setUpdateInterval(timeMills: Long) {
        withContext(dispatcher) {
            delayTimeMills.value = timeMills
        }
    }
    override fun getUpdateIntervalStream(): Flow<Long> = delayTimeMills

    override suspend fun setUpdateState(running: Boolean) {
        withContext(dispatcher) {
            runningState.value = running
        }
    }
    override fun getUpdateState(): Flow<Boolean> = runningState
}