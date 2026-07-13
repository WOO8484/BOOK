package com.booka.feature.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.booka.core.designsystem.BookaSpacing
import com.booka.core.model.WorkType
import com.booka.core.ui.BookaConfirmDialog
import com.booka.core.ui.BookaErrorState
import com.booka.core.ui.BookaLoadingState
import com.booka.core.ui.SampleDataBadge

/** 작품 상세 화면(지시서 7 필수 화면). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkDetailScreen(
    onBack: () -> Unit,
    onReadNovel: (String) -> Unit,
    onReadComic: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(uiState.work?.title ?: "작품 상세") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.onAction(WorkDetailUiAction.ToggleFavorite) }) {
                        Icon(
                            if (uiState.work?.isFavorite == true) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "즐겨찾기",
                        )
                    }
                    IconButton(onClick = { viewModel.onAction(WorkDetailUiAction.RequestDelete) }) {
                        Icon(Icons.Filled.Delete, contentDescription = "삭제")
                    }
                },
            )
        },
    ) { padding ->
        when {
            uiState.isLoading -> BookaLoadingState(modifier = Modifier.fillMaxSize().padding(padding))
            uiState.errorMessage != null -> BookaErrorState(
                message = uiState.errorMessage,
                onRetry = { viewModel.onAction(WorkDetailUiAction.Retry) },
                modifier = Modifier.fillMaxSize().padding(padding),
            )
            uiState.work != null -> WorkDetailContent(
                work = uiState.work!!,
                onReadNovel = onReadNovel,
                onReadComic = onReadComic,
                modifier = Modifier.fillMaxSize().padding(padding),
            )
        }
    }

    if (uiState.isDeleteConfirmVisible) {
        BookaConfirmDialog(
            title = "작품을 삭제할까요?",
            message = "PART 1에서는 실제 삭제 대신 다이얼로그 흐름만 검증합니다(샘플 데이터).",
            isDestructive = true,
            onConfirm = { viewModel.onAction(WorkDetailUiAction.ConfirmDelete) },
            onDismiss = { viewModel.onAction(WorkDetailUiAction.DismissDelete) },
        )
    }
}

@Composable
private fun WorkDetailContent(
    work: com.booka.core.model.Work,
    onReadNovel: (String) -> Unit,
    onReadComic: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier, contentPadding = androidx.compose.foundation.layout.PaddingValues(BookaSpacing.md)) {
        item {
            SampleDataBadge()
            Text(work.title, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = BookaSpacing.sm))
            if (work.author != null) {
                Text(work.author, style = MaterialTheme.typography.bodyMedium)
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = BookaSpacing.sm),
                horizontalArrangement = Arrangement.spacedBy(BookaSpacing.xs),
            ) {
                work.genres.forEach { genre -> AssistChip(onClick = {}, label = { Text(genre) }) }
            }
            if (work.synopsis != null) {
                Text(work.synopsis, style = MaterialTheme.typography.bodyMedium, overflow = TextOverflow.Clip)
            }
            Button(
                onClick = { if (work.type == WorkType.NOVEL) onReadNovel(work.id) else onReadComic(work.id) },
                modifier = Modifier.fillMaxWidth().padding(top = BookaSpacing.md).height(BookaSpacing.minTouchTarget),
            ) {
                Icon(Icons.Filled.AutoStories, contentDescription = null)
                Text(if (work.progressPercent > 0) "이어 읽기 (${work.progressPercent}%)" else "읽기 시작", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}
