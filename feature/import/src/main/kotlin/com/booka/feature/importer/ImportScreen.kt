package com.booka.feature.importer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.booka.core.designsystem.BookaSpacing
import com.booka.core.ui.SampleDataBadge

/** 가져오기 메인 화면(지시서 7 필수 화면). SAF 실제 선택은 PART 2에서 연결되며,
 * PART 1은 결정론적 샘플 파일 목록으로 선택·시작 흐름을 검증한다. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportScreen(
    onNavigateToProgress: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ImportViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.navigateToProgress.collect { sessionId -> onNavigateToProgress(sessionId) }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onAction(ImportUiAction.DismissError)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("가져오기") }) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Row(modifier = Modifier.fillMaxWidth().padding(BookaSpacing.md)) {
                SampleDataBadge(label = "SAF 실제 선택은 PART 2에서 연결")
            }
            LazyColumn(
                modifier = Modifier.weight(1f, fill = true),
                contentPadding = PaddingValues(horizontal = BookaSpacing.md),
                verticalArrangement = Arrangement.spacedBy(BookaSpacing.sm),
            ) {
                items(uiState.availableFiles, key = { it.name }) { file ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(BookaSpacing.sm),
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = file.isSelected,
                                onCheckedChange = { viewModel.onAction(ImportUiAction.ToggleFile(file.name)) },
                            )
                            Column {
                                Text(file.name, style = MaterialTheme.typography.bodyMedium)
                                Text(file.sizeLabel, style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }
            Button(
                onClick = { viewModel.onAction(ImportUiAction.StartImport) },
                enabled = !uiState.isStarting && uiState.availableFiles.any { it.isSelected },
                modifier = Modifier.fillMaxWidth().padding(BookaSpacing.md),
            ) {
                Text(if (uiState.isStarting) "시작하는 중..." else "가져오기 시작")
            }
        }
    }
}
