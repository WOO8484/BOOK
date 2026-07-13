package com.booka.feature.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.booka.core.designsystem.BookaSpacing
import com.booka.core.ui.BookaBottomSheet
import com.booka.core.ui.BookaEmptyState
import com.booka.core.ui.BookaErrorState
import com.booka.core.ui.BookaLoadingState
import com.booka.core.ui.SampleDataBadge
import com.booka.core.ui.WorkCard

/** 서재 메인 화면(지시서 7 필수 화면). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    onSearchClick: () -> Unit,
    onWorkClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("서재") },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(Icons.Filled.Search, contentDescription = "검색")
                    }
                    IconButton(onClick = { viewModel.onAction(LibraryUiAction.OpenFilterSheet) }) {
                        Icon(Icons.Filled.FilterList, contentDescription = "필터 및 정렬")
                    }
                },
            )
        },
    ) { padding ->
        LibraryContent(
            uiState = uiState,
            onAction = viewModel::onAction,
            onWorkClick = onWorkClick,
            padding = padding,
        )
    }

    if (uiState.isFilterSheetVisible) {
        val sheetState = rememberModalBottomSheetState()
        BookaBottomSheet(
            sheetState = sheetState,
            onDismiss = { viewModel.onAction(LibraryUiAction.DismissFilterSheet) },
        ) {
            Text("정렬 기준", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
            LibrarySortOrder.entries.forEach { order ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                ) {
                    RadioButton(
                        selected = uiState.sortOrder == order,
                        onClick = { viewModel.onAction(LibraryUiAction.SelectSort(order)) },
                    )
                    Text(order.label)
                }
            }
        }
    }
}

@Composable
private fun LibraryContent(
    uiState: LibraryUiState,
    onAction: (LibraryUiAction) -> Unit,
    onWorkClick: (String) -> Unit,
    padding: PaddingValues,
) {
    Column(modifier = Modifier.fillMaxSize().padding(padding)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = BookaSpacing.md, vertical = BookaSpacing.sm),
            horizontalArrangement = Arrangement.spacedBy(BookaSpacing.sm),
        ) {
            SampleDataBadge()
        }

        androidx.compose.foundation.lazy.LazyRow(
            contentPadding = PaddingValues(horizontal = BookaSpacing.md),
            horizontalArrangement = Arrangement.spacedBy(BookaSpacing.sm),
        ) {
            // LazyGridScope.items와의 이름 충돌을 피하기 위해 LazyListScope 확장을 명시적으로 호출한다.
            androidx.compose.foundation.lazy.items(uiState.shelves) { shelf ->
                FilterChip(
                    selected = uiState.selectedShelfId == shelf.id,
                    onClick = { onAction(LibraryUiAction.SelectShelf(shelf.id)) },
                    label = { Text(shelf.name) },
                )
            }
        }

        when {
            uiState.isLoading -> BookaLoadingState(modifier = Modifier.fillMaxSize())
            uiState.errorMessage != null -> BookaErrorState(
                message = uiState.errorMessage,
                onRetry = { onAction(LibraryUiAction.Retry) },
            )
            uiState.works.isEmpty() -> BookaEmptyState(
                title = "등록된 작품이 없습니다",
                description = "가져오기 메뉴에서 소설이나 만화를 추가해 보세요.",
            )
            else -> LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 140.dp),
                contentPadding = PaddingValues(BookaSpacing.md),
                horizontalArrangement = Arrangement.spacedBy(BookaSpacing.sm),
                verticalArrangement = Arrangement.spacedBy(BookaSpacing.sm),
            ) {
                items(uiState.works, key = { it.id }) { work ->
                    WorkCard(
                        title = work.title,
                        author = work.author,
                        coverUrl = work.coverUrl,
                        progressLabel = if (work.progressPercent > 0) "${work.progressPercent}%" else null,
                        onClick = { onWorkClick(work.id) },
                    )
                }
            }
        }
    }
}
