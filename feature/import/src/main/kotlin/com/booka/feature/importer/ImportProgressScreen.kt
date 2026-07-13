package com.booka.feature.importer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.booka.core.designsystem.BookaSpacing
import com.booka.domain.repository.ImportProgressState

/** 가져오기 진행 화면(지시서 7 필수 화면). */
@Composable
fun ImportProgressScreen(
    onDone: () -> Unit,
    onOpenRenamePreview: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ImportProgressViewModel = hiltViewModel(),
) {
    val state by viewModel.progress.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("가져오기 진행") }) },
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(BookaSpacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        ) {
            when (val s = state) {
                ImportProgressState.Idle -> {
                    Text("준비 중...", style = MaterialTheme.typography.bodyMedium)
                }
                is ImportProgressState.Scanning -> {
                    Text("파일 분석 중 (${s.processed}/${s.total})", style = MaterialTheme.typography.titleMedium)
                    LinearProgressIndicator(
                        progress = { s.processed / s.total.toFloat() },
                        modifier = Modifier.fillMaxWidth().padding(top = BookaSpacing.md),
                    )
                }
                is ImportProgressState.Completed -> {
                    Text("가져오기 완료", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "성공 ${s.importedCount}건 · 건너뜀 ${s.skippedCount}건 (샘플 데이터)",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = BookaSpacing.sm),
                    )
                    Button(onClick = onOpenRenamePreview, modifier = Modifier.padding(top = BookaSpacing.md)) {
                        Text("파일명 변경 미리보기")
                    }
                    Button(onClick = onDone, modifier = Modifier.padding(top = BookaSpacing.sm)) {
                        Text("서재로 이동")
                    }
                }
                is ImportProgressState.Failed -> {
                    Text("가져오기 실패", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.error)
                    Text(s.message, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = BookaSpacing.sm))
                    Button(onClick = { viewModel.onRetry() }, modifier = Modifier.padding(top = BookaSpacing.md)) {
                        Text("다시 시도")
                    }
                }
            }

            if (state is ImportProgressState.Scanning) {
                Button(onClick = { viewModel.onCancel() }, modifier = Modifier.padding(top = BookaSpacing.md)) {
                    Text("취소")
                }
            }
        }
    }
}
