package com.booka.feature.import

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.booka.core.designsystem.BookaSpacing
import com.booka.core.ui.SampleDataBadge

private data class RenamePair(val before: String, val after: String)

/** 파일명 변경 미리보기 화면(지시서 7 필수 화면). 실제 파일명 변경(SAF)은 PART 2에서 연결된다. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenamePreviewScreen(
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val samplePairs = listOf(
        RenamePair("달빛_아래_도서관_최종본.txt", "달빛 아래 도서관 - 한서연.txt"),
        RenamePair("철갑기병_크로니클_01-08.cbz", "철갑기병 크로니클 001-008.cbz"),
        RenamePair("붉은_항성계.zip", "붉은 항성계.zip"),
    )

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("파일명 변경 미리보기") }) },
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Row(modifier = Modifier.padding(BookaSpacing.md)) {
                SampleDataBadge(label = "실제 이름 변경은 PART 2에서 적용")
            }
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = BookaSpacing.md),
                verticalArrangement = Arrangement.spacedBy(BookaSpacing.sm),
            ) {
                items(samplePairs) { pair ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(BookaSpacing.sm)) {
                            Text(pair.before, style = MaterialTheme.typography.bodyMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                                Text(
                                    pair.after,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(start = BookaSpacing.xs),
                                )
                            }
                        }
                    }
                }
            }
            Button(onClick = onConfirm, modifier = Modifier.fillMaxWidth().padding(BookaSpacing.md)) {
                Text("확인")
            }
        }
    }
}
