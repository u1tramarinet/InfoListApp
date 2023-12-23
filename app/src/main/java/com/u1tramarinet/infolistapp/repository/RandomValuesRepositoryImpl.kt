package com.u1tramarinet.infolistapp.repository

import android.util.Log
import com.u1tramarinet.infolistapp.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RandomValuesRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : RandomValuesRepository {
    private val randomValues: MutableSet<RandomValue> = mutableSetOf()
    private var elapsedTime: Long = 0
    private val range = 0..100
    private val intervalRange = 100..1000 step 100
    override suspend fun addRandomValue() {
        Log.d(RandomValuesRepositoryImpl::class.java.simpleName, "addRandomValue()")
        withContext(dispatcher) {
            val newId = randomValues.size + 1
            randomValues.add(generateRandomValueLocked(newId))
        }
    }

    override fun getRandomValuesStream(): Flow<List<RandomValue>> = flow {
        elapsedTime = 0L
        while (true) {
            val delayTime = intervalRange.min().toLong()
            delay(delayTime)
            elapsedTime += delayTime
            updateValueLocked(elapsedTime = elapsedTime)
            emit(randomValues.toList())
        }
    }.flowOn(dispatcher)

    override suspend fun setRunningState(running: Boolean) {
        Log.d(RandomValuesRepositoryImpl::class.java.simpleName, "setRunningState($running)")
        withContext(dispatcher) {
            val values: MutableSet<RandomValue> = randomValues.map { value ->
                value.copy(isRunning = running)
            }.toMutableSet()
            randomValues.clear()
            randomValues.addAll(values)
        }
    }

    private fun updateValueLocked(elapsedTime: Long) {
        val intervalMatchedValues =
            randomValues.filter { v -> (elapsedTime % v.updateInterval) == 0L }
        val runningValues = intervalMatchedValues.filter { v -> v.isRunning }
        Log.d(
            RandomValuesRepositoryImpl::class.java.simpleName,
            "elapsed=$elapsedTime ms, total=${randomValues.size}, ${intervalMatchedValues.size} matches interval, ${runningValues.size} is running"
        )
        runningValues.forEach { origin ->
            randomValues.remove(origin)
            val target = origin.copy(value = range.random())
            randomValues.add(target)
            Log.d(
                RandomValuesRepositoryImpl::class.java.simpleName,
                "emitted new value: $target"
            )
        }
    }

    private fun generateRandomValueLocked(id: Int): RandomValue {
        val created = RandomValue(
            id = id,
            updateInterval = intervalRange.shuffled().first().toLong(),
        )
        Log.d(RandomValuesRepositoryImpl::class.java.simpleName, "created: $created")
        return created
    }
}