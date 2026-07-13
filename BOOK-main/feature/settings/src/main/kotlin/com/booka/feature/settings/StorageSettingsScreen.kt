package com.booka.feature.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.booka.core.designsystem.BookaSpacing
import com.booka.core.ui.BookaConfirmDialog
import com.booka.core.ui.NotYetImplementedDialog

/** 저장 및 관리 화면(지시서 7, 11 필수 화면). 데이터 초기화는 이중 확인을 요구한다. */
@Composable
fun StorageSettingsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("저장 및 관리") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로") }
                },
            )
        },
    ) { padding ->
        androidx.compose.foundation.layout.Column(modifier = Modifier.fillMaxWidth().padding(padding)) {
            ListItem(
                headlineContent = { Text("캐시 용량") },
                supportingContent = { Text(uiState.cacheSizeLabel) },
                trailingContent = { TextButton(onClick = { viewModel.requestClearCache() }) { Text("삭제") } },
            )
            ListItem(
                headlineContent = { Text("데이터 백업") },
                trailingContent = { TextButton(onClick = { viewModel.requestBackup() }) { Text("백업") } },
            )
            ListItem(
                headlineContent = { Text("데이터 복원") },
                trailingContent = { TextButton(onClick = { viewModel.requestRestore() }) { Text("복원") } },
            )
            ListItem(
                headlineContent = { Text("오류 로그 내보내기") },
                trailingContent = { TextButton(onClick = { viewModel.requestExportErrorLog() }) { Text("내보내기") } },
            )
            Button(
                onClick = { viewModel.requestReset() },
                modifier = Modifier.fillMaxWidth().padding(BookaSpacing.md),
            ) {
                Text("데이터 초기화")
            }
        }
    }

    if (uiState.isResetConfirmVisible) {
        BookaConfirmDialog(
            title = "정말 초기화할까요?",
            message = "서재의 모든 작품과 설정이 삭제됩니다. 되돌릴 수 없습니다.",
            isDestructive = true,
            onConfirm = { viewModel.confirmResetFirstStep() },
            onDismiss = { viewModel.dismissResetFirstStep() },
        )
    }
    if (uiState.isResetSecondConfirmVisible) {
        BookaConfirmDialog(
            title = "마지막 확인",
            message = "이 작업은 취소할 수 없습니다. 정말 진행하시겠습니까? (PART 1에서는 흐름만 검증하며 실제 삭제는 수행하지 않습니다)",
            isDestructive = true,
            confirmLabel = "초기화",
            onConfirm = { viewModel.confirmResetFinal() },
            onDismiss = { viewModel.dismissResetSecondStep() },
        )
    }
    uiState.notYetImplementedFeature?.let { (feature, part) ->
        NotYetImplementedDialog(feature, part, onDismiss = { viewModel.dismissNotYetImplemented() })
    }
}
