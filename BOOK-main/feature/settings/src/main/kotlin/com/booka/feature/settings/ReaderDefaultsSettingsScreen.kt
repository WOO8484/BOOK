package com.booka.feature.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.booka.core.designsystem.BookaSpacing

/** 뷰어 설정 화면(지시서 7, 9 필수 화면). 실제 렌더링 반영은 PART 3에서 연결된다. */
@Composable
fun ReaderDefaultsSettingsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("뷰어 설정") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로") }
                },
            )
        },
    ) { padding ->
        androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(padding).padding(BookaSpacing.md)) {
            Text("글자 크기 (${String.format("%.1fx", uiState.readerFontScale)})")
            Slider(
                value = uiState.readerFontScale,
                onValueChange = { viewModel.setReaderFontScale(it) },
                valueRange = 0.8f..2.0f,
            )
            Text("줄 간격 (${String.format("%.1fx", uiState.readerLineSpacing)})")
            Slider(
                value = uiState.readerLineSpacing,
                onValueChange = { viewModel.setReaderLineSpacing(it) },
                valueRange = 1.0f..2.0f,
            )
            Text("배경 테마", modifier = Modifier.padding(top = BookaSpacing.md))
            Row {
                ReaderTheme.entries.forEach { theme ->
                    FilterChip(
                        selected = uiState.readerTheme == theme,
                        onClick = { viewModel.setReaderTheme(theme) },
                        label = { Text(theme.label) },
                        modifier = Modifier.padding(end = BookaSpacing.xs),
                    )
                }
            }
        }
    }
}
