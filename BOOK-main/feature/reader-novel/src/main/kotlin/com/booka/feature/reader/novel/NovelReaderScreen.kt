package com.booka.feature.reader.novel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.booka.core.designsystem.BookaSpacing
import com.booka.core.ui.BookaBottomSheet
import com.booka.core.ui.BookaErrorState
import com.booka.core.ui.BookaLoadingState
import com.booka.core.ui.SampleDataBadge

/** 소설 뷰어 UI(지시서 7 필수 화면). 실제 TXT 렌더링·인코딩·분할 처리 엔진은 PART 3에서 구현된다.
 * 이 화면은 하단 메뉴를 숨기는 대상이며(지시서 3.1), 그 처리는 앱 최상위 NavHost에서 담당한다. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovelReaderScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NovelReaderViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            if (!uiState.isImmersive) {
                TopAppBar(
                    title = { Text(uiState.chapters.getOrNull(uiState.currentChapterIndex)?.title ?: "소설 뷰어") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로")
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.openToc() }) {
                            Icon(Icons.Filled.List, contentDescription = "목차")
                        }
                    },
                )
            }
        },
    ) { padding ->
        when {
            uiState.isLoading -> BookaLoadingState(modifier = Modifier.fillMaxSize().padding(padding))
            uiState.errorMessage != null -> BookaErrorState(
                message = uiState.errorMessage,
                modifier = Modifier.fillMaxSize().padding(padding),
            )
            else -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = { viewModel.toggleImmersive() })
                    },
            ) {
                if (!uiState.isImmersive) {
                    Row(modifier = Modifier.padding(BookaSpacing.sm)) { SampleDataBadge() }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(BookaSpacing.lg),
                ) {
                    Text(uiState.chapterText, style = MaterialTheme.typography.bodyLarge)
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = BookaSpacing.lg),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        TextButton(onClick = { viewModel.onPrevChapter() }) { Text("이전 화") }
                        TextButton(onClick = { viewModel.onNextChapter() }) { Text("다음 화") }
                    }
                }
            }
        }
    }

    if (uiState.isTocVisible) {
        val sheetState = rememberModalBottomSheetState()
        BookaBottomSheet(sheetState = sheetState, onDismiss = { viewModel.dismissToc() }) {
            Text("목차", style = MaterialTheme.typography.titleMedium)
            LazyColumn(contentPadding = PaddingValues(vertical = BookaSpacing.sm)) {
                items(uiState.chapters, key = { it.id }) { chapter ->
                    ListItem(
                        headlineContent = { Text(chapter.title) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.onSelectChapter(chapter.index) },
                    )
                }
            }
        }
    }
}
