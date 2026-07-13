package com.booka.feature.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.booka.core.designsystem.BookaSpacing

/** 책장 및 표시 설정(지시서 7, 11 필수 화면). */
@Composable
fun ShelfDisplaySettingsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("책장 및 표시") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로") }
                },
            )
        },
    ) { padding ->
        androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(padding)) {
            ListItem(headlineContent = { Text("보기 방식") })
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = BookaSpacing.md)) {
                ShelfViewMode.entries.forEach { mode ->
                    Row {
                        RadioButton(
                            selected = uiState.shelfViewMode == mode,
                            onClick = { viewModel.setShelfViewMode(mode) },
                        )
                        Text(if (mode == ShelfViewMode.GRID) "격자형" else "목록형")
                    }
                }
            }
            ListItem(
                headlineContent = { Text("시스템 설정에 따라 다크 모드") },
                trailingContent = {
                    Switch(
                        checked = uiState.darkModeFollowSystem,
                        onCheckedChange = { viewModel.setDarkModeFollowSystem(it) },
                    )
                },
            )
            if (!uiState.darkModeFollowSystem) {
                ListItem(
                    headlineContent = { Text("다크 모드 강제 사용") },
                    trailingContent = {
                        Switch(checked = uiState.forceDarkMode, onCheckedChange = { viewModel.setForceDarkMode(it) })
                    },
                )
            }
        }
    }
}
