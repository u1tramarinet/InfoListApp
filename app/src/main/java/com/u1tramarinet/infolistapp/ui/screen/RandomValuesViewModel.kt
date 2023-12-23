package com.u1tramarinet.infolistapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.u1tramarinet.infolistapp.domain.AddRandomValueUseCase
import com.u1tramarinet.infolistapp.domain.ChangeRunningStatesUseCase
import com.u1tramarinet.infolistapp.domain.ObserveRandomValuesUseCase
import com.u1tramarinet.infolistapp.repository.RandomValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RandomValuesUiState(
    val randomValues: List<RandomValueState> = listOf(),
    val isRunning: Boolean = true,
    val isRunnable: Boolean = true,
)

sealed class RandomValueState(open val id: Int) {
    data class Loading(override val id: Int) : RandomValueState(id = id)
    data class Success(override val id: Int, val value: Int, val running: Boolean) :
        RandomValueState(id = id)

    override fun equals(other: Any?): Boolean {
        if (other !is RandomValueState) {
            return false
        }
        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}

@HiltViewModel
class RandomValuesViewModel @Inject constructor(
    observeRandomValuesUseCase: ObserveRandomValuesUseCase,
    private val addRandomValueUseCase: AddRandomValueUseCase,
    private val changeRunningStatesUseCase: ChangeRunningStatesUseCase,
) : ViewModel() {
    private val comparator: Comparator<RandomValue> = compareBy { it.id }
    val uiState: StateFlow<RandomValuesUiState> =
        observeRandomValuesUseCase().map { list ->
            RandomValuesUiState(list
                .sortedWith(comparator)
                .map { value ->
                    if (value.value != null) {
                        RandomValueState.Success(
                            id = value.id,
                            value = value.value,
                            running = value.isRunning,
                        )
                    } else {
                        RandomValueState.Loading(id = value.id)
                    }
                }, isRunning = list.any { it.isRunning },
                isRunnable = list.isNotEmpty()
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = RandomValuesUiState()
        )

    fun addRandomValue() {
        viewModelScope.launch {
            addRandomValueUseCase()
        }
    }

    fun changeRunningState(running: Boolean) {
        viewModelScope.launch {
            changeRunningStatesUseCase(running)
        }
    }
}