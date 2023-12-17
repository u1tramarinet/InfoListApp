package com.u1tramarinet.infolistapp.repository

import kotlinx.coroutines.flow.Flow

interface InfoRepository {
    fun getRandomValueStream(): Flow<Int>
    suspend fun setUpdateInterval(timeMills: Long)
    fun getUpdateIntervalStream(): Flow<Long>
    suspend fun setUpdateState(running: Boolean)
    fun getUpdateState(): Flow<Boolean>
}