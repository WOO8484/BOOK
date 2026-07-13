package com.booka.feature.metadata

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.booka.core.designsystem.BookaSpacing

/** 직접 수정 화면(지시서 7 필수 화면). 사용자 입력은 어떤 Provider 결과보다 우선한다(지시서 8.3). */
@Composable
fun MetadataManualEditScreen(
    onSaved: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MetadataManualEditViewModel = hiltViewModel(),
) {
    val form by viewModel.formState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("직접 수정") }) },
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(BookaSpacing.md)) {
            OutlinedTextField(
                value = form.title,
                onValueChange = viewModel::onTitleChange,
                label = { Text("제목") },
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = form.author,
                onValueChange = viewModel::onAuthorChange,
                label = { Text("작가") },
                modifier = Modifier.fillMaxWidth().padding(top = BookaSpacing.sm),
            )
            OutlinedTextField(
                value = form.synopsis,
                onValueChange = viewModel::onSynopsisChange,
                label = { Text("줄거리") },
                minLines = 4,
                modifier = Modifier.fillMaxWidth().padding(top = BookaSpacing.sm),
            )
            Button(
                onClick = { viewModel.save(onSaved) },
                enabled = form.title.isNotBlank() && !form.isSaving,
                modifier = Modifier.fillMaxWidth().padding(top = BookaSpacing.md),
            ) {
                Text(if (form.isSaving) "저장 중..." else "저장")
            }
        }
    }
}
