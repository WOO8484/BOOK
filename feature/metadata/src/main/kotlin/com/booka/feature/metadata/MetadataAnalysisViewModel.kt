package com.booka.feature.metadata

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.booka.core.common.BookaResult
import com.booka.core.navigation.BookaDestination
import com.booka.domain.usecase.AnalyzeMetadataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MetadataAnalysisViewModel @Inject constructor(
    private val analyzeMetadata: AnalyzeMetadataUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<BookaDestination.MetadataAnalysis>()

    private val _uiState = MutableStateFlow<MetadataAnalysisUiState>(MetadataAnalysisUiState.Analyzing)
    val uiState: StateFlow<MetadataAnalysisUiState> = _uiState.asStateFlow()

    // 임시 초안 ID. 실제 가져오기 세션과의 연결은 PART 2에서 Room으로 영속화된다.
    val workDraftId: String get() = "${route.sessionId}-draft-1"

    init { analyze() }

    private fun analyze() {
        viewModelScope.launch {
            _uiState.value = MetadataAnalysisUiState.Analyzing
            when (val result = analyzeMetadata(route.sessionId, workDraftId)) {
                is BookaResult.Success -> _uiState.value = MetadataAnalysisUiState.Done
                is BookaResult.Failure -> _uiState.value = MetadataAnalysisUiState.Failed(result.error.message)
                BookaResult.Loading -> Unit
            }
        }
    }

    fun retry() = analyze()
}
