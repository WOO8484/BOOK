package com.booka.feature.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.booka.core.designsystem.BookaSpacing
import com.booka.core.ui.BookaEmptyState

/** 서재 검색 화면(지시서 7 필수 화면). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrarySearchScreen(
    initialQuery: String,
    onBack: () -> Unit,
    onWorkClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = hiltViewModel(),
) {
    var query by remember { mutableStateOf(initialQuery) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    val filtered = remember(uiState.works, query) {
        if (query.isBlank()) emptyList()
        else uiState.works.filter {
            it.title.contains(query, ignoreCase = true) || (it.author?.contains(query, ignoreCase = true) == true)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("제목 또는 작가 검색") },
                        modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                        singleLine = true,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로")
                    }
                },
            )
        },
    ) { padding ->
        when {
            query.isBlank() -> BookaEmptyState(
                title = "검색어를 입력하세요",
                description = "작품 제목이나 작가 이름으로 찾을 수 있습니다.",
                modifier = Modifier.fillMaxSize().padding(padding),
            )
            filtered.isEmpty() -> BookaEmptyState(
                title = "검색 결과가 없습니다",
                description = "'$query'에 대한 결과를 찾지 못했습니다.",
                modifier = Modifier.fillMaxSize().padding(padding),
            )
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(BookaSpacing.sm),
            ) {
                items(filtered, key = { it.id }) { work ->
                    ListItem(
                        headlineContent = { Text(work.title) },
                        supportingContent = { work.author?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth().clickable { onWorkClick(work.id) },
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
