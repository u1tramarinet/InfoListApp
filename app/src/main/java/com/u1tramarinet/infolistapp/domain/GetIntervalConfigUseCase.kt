package com.u1tramarinet.infolistapp.domain

import javax.inject.Inject

class GetIntervalConfigUseCase @Inject constructor() {
    operator fun invoke(): IntervalConfig {
        return IntervalConfig()
    }
}

data class IntervalConfig(
    val fastest: Long = 10,
    val middle: Long = 100,
    val slowest: Long = 500,
)