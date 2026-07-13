package com.booka.feature.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/** 기타(버전·라이선스·개인정보) 화면(지시서 7, 11 필수 화면). */
@Composable
fun AboutScreen(onBack: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("기타") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로") }
                },
            )
        },
    ) { padding ->
        androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(padding)) {
            ListItem(headlineContent = { Text("앱 버전") }, supportingContent = { Text("BOOKA PART 1 (개발 중)") })
            ListItem(headlineContent = { Text("오픈소스 라이선스") }, supportingContent = { Text("PART 4에서 전체 목록 제공") })
            ListItem(headlineContent = { Text("개인정보 처리방침") }, supportingContent = { Text("PART 4에서 문서 제공") })
        }
    }
}
