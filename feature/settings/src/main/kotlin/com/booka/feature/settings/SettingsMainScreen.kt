package com.booka.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.booka.core.navigation.BookaDestination

/** 설정 메인 화면(지시서 7 필수 화면). */
@Composable
fun SettingsMainScreen(
    onNavigate: (BookaDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("설정") }) },
    ) { padding ->
        androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(padding)) {
            ListItem(
                headlineContent = { Text("책장 및 표시") },
                supportingContent = { Text("그리드/리스트, 다크 모드") },
                leadingContent = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null) },
                modifier = Modifier.clickable { onNavigate(BookaDestination.SettingsShelfDisplay) },
            )
            ListItem(
                headlineContent = { Text("메타정보 설정") },
                supportingContent = { Text("Naver / Google Books / Open Library") },
                leadingContent = { Icon(Icons.Filled.CloudDownload, contentDescription = null) },
                modifier = Modifier.clickable { onNavigate(BookaDestination.SettingsMetadataProviders) },
            )
            ListItem(
                headlineContent = { Text("뷰어 설정") },
                supportingContent = { Text("글자 크기, 줄 간격, 테마") },
                leadingContent = { Icon(Icons.Filled.Tune, contentDescription = null) },
                modifier = Modifier.clickable { onNavigate(BookaDestination.SettingsReaderDefaults) },
            )
            ListItem(
                headlineContent = { Text("저장 및 관리") },
                supportingContent = { Text("캐시, 백업, 복원, 초기화") },
                leadingContent = { Icon(Icons.Filled.Storage, contentDescription = null) },
                modifier = Modifier.clickable { onNavigate(BookaDestination.SettingsStorage) },
            )
            ListItem(
                headlineContent = { Text("기타") },
                supportingContent = { Text("버전, 라이선스, 개인정보") },
                leadingContent = { Icon(Icons.Filled.Info, contentDescription = null) },
                modifier = Modifier.clickable { onNavigate(BookaDestination.SettingsAbout) },
            )
        }
    }
}
