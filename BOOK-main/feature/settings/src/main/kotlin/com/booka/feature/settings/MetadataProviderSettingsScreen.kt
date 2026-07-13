package com.booka.feature.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.booka.core.ui.NotYetImplementedDialog

/** 메타정보 설정 화면(지시서 7, 8.2 필수 화면). API 키가 없어도 로컬 기능은 정상 동작해야 한다. */
@Composable
fun MetadataProviderSettingsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("메타정보 설정") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로") }
                },
            )
        },
    ) { padding ->
        androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(padding)) {
            ListItem(
                headlineContent = { Text("Naver") },
                supportingContent = { Text("Client ID/Secret 등록 시 활성화 (PART 2)") },
                trailingContent = {
                    Switch(
                        checked = uiState.providerToggles.naverEnabled,
                        onCheckedChange = { viewModel.setNaverEnabled(it) },
                    )
                },
            )
            ListItem(
                headlineContent = { Text("Google Books") },
                supportingContent = { Text("키 없이도 제한적으로 사용 가능") },
                trailingContent = {
                    Switch(
                        checked = uiState.providerToggles.googleBooksEnabled,
                        onCheckedChange = { viewModel.setGoogleBooksEnabled(it) },
                    )
                },
            )
            ListItem(
                headlineContent = { Text("Open Library") },
                supportingContent = { Text("기본 무키 Provider") },
                trailingContent = {
                    Switch(
                        checked = uiState.providerToggles.openLibraryEnabled,
                        onCheckedChange = { viewModel.setOpenLibraryEnabled(it) },
                    )
                },
            )
        }
    }

    uiState.notYetImplementedFeature?.let { (feature, part) ->
        NotYetImplementedDialog(feature, part, onDismiss = { viewModel.dismissNotYetImplemented() })
    }
}
