package com.booka.feature.reader.comic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.booka.core.ui.BookaErrorState
import com.booka.core.ui.BookaLoadingState

/** 만화 뷰어 UI(지시서 7 필수 화면). 실제 ZIP/CBZ 이미지 디코딩·샘플링은 PART 3에서 구현된다.
 * PART 1은 결정론적 페이지 목록으로 페이지 넘김·몰입 모드 흐름만 검증한다. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicReaderScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ComicReaderViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            if (!uiState.isImmersive) {
                TopAppBar(
                    title = { Text("${uiState.currentPageIndex + 1} / ${uiState.pages.size}") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로")
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
            uiState.pages.isNotEmpty() -> {
                val pagerState = rememberPagerState(
                    initialPage = uiState.currentPageIndex,
                    pageCount = { uiState.pages.size },
                )
                LaunchedEffect(pagerState.currentPage) { viewModel.onPageChanged(pagerState.currentPage) }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize().padding(padding),
                ) { pageIndex ->
                    val page = uiState.pages[pageIndex]
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                            .clickable { viewModel.toggleImmersive() },
                        contentAlignment = Alignment.Center,
                    ) {
                        // 실제 이미지 디코딩(PART 3) 전까지는 페이지 경로만 표시하는 자리표시 콘텐츠.
                        Surface(color = Color(0xFF2A2A2A)) {
                            Text(
                                "샘플 페이지 ${pageIndex + 1}\n${page.entryPath}",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(24.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}
