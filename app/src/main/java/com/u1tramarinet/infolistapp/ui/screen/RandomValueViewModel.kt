package com.u1tramarinet.infolistapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.u1tramarinet.infolistapp.domain.ChangeRunningStateUseCase
import com.u1tramarinet.infolistapp.domain.ChangeUpdateIntervalUseCase
import com.u1tramarinet.infolistapp.domain.GetIntervalConfigUseCase
import com.u1tramarinet.infolistapp.domain.IntervalConfig
import com.u1tramarinet.infolistapp.domain.ObserveRandomValueUseCase
import com.u1tramarinet.infolistapp.domain.ObserveRunningStateUseCase
import com.u1tramarinet.infolistapp.domain.ObserveUpdateIntervalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RandomValueUiState(
    val randomValue: Int,
    val interval: Long,
    val isRunning: Boolean = true,
    val intervalConfig: IntervalConfig,
    val sliderState: SliderState,
)

data class SliderState(
    val value: Float,
    val minValue: Float = 0f,
    val maxValue: Float,
    val steps: Int,
)

@HiltViewModel
class RandomValueViewModel @Inject constructor(
    observeRandomValue: ObserveRandomValueUseCase,
    observeInterval: ObserveUpdateIntervalUseCase,
    observeRunningState: ObserveRunningStateUseCase,
    getIntervalConfig: GetIntervalConfigUseCase,
    private val changeInterval: ChangeUpdateIntervalUseCase,
    private val changeRunningState: ChangeRunningStateUseCase,
) : ViewModel() {
    val uiState: StateFlow<ScreenState<RandomValueUiState>> =
        combine(
            observeRandomValue(),
            observeInterval(),
            observeRunningState(),
        ) { randomValue, interval, runningState ->
            ScreenState.Success(
                data = RandomValueUiState(
                    randomValue, interval, runningState,
                    intervalConfig,
                    generateSliderState(interval, intervalConfig),
                )
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ScreenState.Loading,
        )
    private val intervalConfig = getIntervalConfig()

    fun updateInterval(timeMills: Long) {
        viewModelScope.launch {
            changeInterval(timeMills)
        }
    }

    fun updateIntervalViaSlider(value: Float) {
        viewModelScope.launch {
            val modified = (intervalConfig.slowest - value)
            changeInterval(modified.toLong())
        }
    }

    fun updateRunningState(running: Boolean) {
        viewModelScope.launch {
            changeRunningState(running)
        }
    }

    private fun generateSliderState(interval: Long, intervalConfig: IntervalConfig): SliderState {
        val distance = intervalConfig.slowest - intervalConfig.fastest
        val steps = (distance / 10).toInt()
        return SliderState(
            value = (intervalConfig.slowest - interval).toFloat(),
            maxValue = distance.toFloat(),
            steps = steps,
        )
    }
}