package com.booka.feature.metadata

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.booka.core.designsystem.BookaSpacing
import com.booka.core.ui.BookaErrorState
import com.booka.core.ui.BookaLoadingState

/** 메타 분석 화면(지시서 7 필수 화면). 로컬 파일명 기반 분석을 시뮬레이션한다. */
@Composable
fun MetadataAnalysisScreen(
    onAnalysisDone: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MetadataAnalysisViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState is MetadataAnalysisUiState.Done) {
            onAnalysisDone(viewModel.workDraftId)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("메타 분석") }) },
    ) { padding ->
        when (val state = uiState) {
            MetadataAnalysisUiState.Analyzing -> BookaLoadingState(
                modifier = Modifier.fillMaxSize().padding(padding),
                label = "파일명·본문 앞부분을 분석하는 중",
            )
            MetadataAnalysisUiState.Done -> Column(
                modifier = Modifier.fillMaxSize().padding(padding).padding(BookaSpacing.lg),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("분석 완료", style = MaterialTheme.typography.titleMedium)
            }
            is MetadataAnalysisUiState.Failed -> BookaErrorState(
                message = state.message,
                onRetry = { viewModel.retry() },
                modifier = Modifier.fillMaxSize().padding(padding),
            )
        }
    }
}
