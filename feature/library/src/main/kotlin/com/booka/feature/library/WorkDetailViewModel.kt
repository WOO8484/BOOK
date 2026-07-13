package com.booka.feature.library

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.booka.core.common.BookaResult
import com.booka.core.navigation.BookaDestination
import com.booka.domain.usecase.GetWorkDetailUseCase
import com.booka.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkDetailViewModel @Inject constructor(
    private val getWorkDetail: GetWorkDetailUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    // 화면 상태는 프로세스 재생성 시 복원 가능한 값(workId)만 SavedStateHandle에 유지한다(지시서 5).
    // Navigation Compose 타입-세이프 route(kotlinx.serialization)에서 직접 복원한다.
    private val workId: String = savedStateHandle.toRoute<BookaDestination.WorkDetail>().workId

    private val _uiState = MutableStateFlow(WorkDetailUiState())
    val uiState: StateFlow<WorkDetailUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = getWorkDetail(workId)) {
                is BookaResult.Success -> _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    work = result.data,
                )
                is BookaResult.Failure -> _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.error.message,
                )
                BookaResult.Loading -> Unit
            }
        }
    }

    fun onAction(action: WorkDetailUiAction) {
        when (action) {
            WorkDetailUiAction.ToggleFavorite -> viewModelScope.launch {
                toggleFavorite(workId)
                load()
            }
            WorkDetailUiAction.RequestDelete -> _uiState.value = _uiState.value.copy(isDeleteConfirmVisible = true)
            WorkDetailUiAction.DismissDelete -> _uiState.value = _uiState.value.copy(isDeleteConfirmVisible = false)
            WorkDetailUiAction.ConfirmDelete -> {
                // PART 1: 실제 삭제(Room)는 PART 2에서 연결된다. 여기서는 다이얼로그 흐름만 검증한다.
                _uiState.value = _uiState.value.copy(isDeleteConfirmVisible = false)
            }
            WorkDetailUiAction.Retry -> load()
        }
    }
}
