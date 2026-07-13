package com.booka.feature.metadata

sealed interface MetadataAnalysisUiState {
    data object Analyzing : MetadataAnalysisUiState
    data object Done : MetadataAnalysisUiState
    data class Failed(val message: String) : MetadataAnalysisUiState
}
