package com.u1tramarinet.infolistapp.repository

import kotlinx.coroutines.flow.Flow

interface RandomValuesRepository {
    suspend fun addRandomValue()
    fun getRandomValuesStream(): Flow<List<RandomValue>>
    suspend fun setRunningState(running: Boolean)
}

data class RandomValue(
    val id: Int,
    val value: Int? = null,
    val isRunning: Boolean = true,
    val updateInterval: Long,
) {
    override fun equals(other: Any?): Boolean {
        if (other !is RandomValue) {
            return false
        }
        return other.id == id
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return "id=$id, " +
                "value=$value, " +
                "updateInterval=$updateInterval"
    }
}