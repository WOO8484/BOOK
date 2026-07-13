package com.booka.feature.importer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.booka.core.navigation.BookaDestination
import com.booka.domain.repository.ImportProgressState
import com.booka.domain.usecase.CancelImportUseCase
import com.booka.domain.usecase.ObserveImportProgressUseCase
import com.booka.domain.usecase.RetryImportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImportProgressViewModel @Inject constructor(
    observeImportProgress: ObserveImportProgressUseCase,
    private val cancelImport: CancelImportUseCase,
    private val retryImport: RetryImportUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val sessionId: String = savedStateHandle.toRoute<BookaDestination.ImportProgress>().sessionId

    val progress: StateFlow<ImportProgressState> = observeImportProgress(sessionId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ImportProgressState.Idle)

    init {
        // PART 1 시뮬레이션 시작(실제 SAF/파일 엔진 없이 결정론적 진행률만 재현, 지시서 7.1).
        viewModelScope.launch { retryImport(sessionId) }
    }

    fun onCancel() {
        viewModelScope.launch { cancelImport(sessionId) }
    }

    fun onRetry() {
        viewModelScope.launch { retryImport(sessionId) }
    }
}
