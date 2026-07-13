package com.booka.feature.metadata

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.booka.core.designsystem.BookaSpacing
import com.booka.core.model.MetadataCandidate
import com.booka.core.model.isAutoApplyEligible
import com.booka.core.ui.BookaEmptyState
import com.booka.core.ui.SampleDataBadge

/** 메타 후보 선택 화면(지시서 7 필수 화면, 8.3 점수 규칙 반영). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetadataCandidateSelectScreen(
    onCandidateApplied: () -> Unit,
    onManualEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MetadataCandidateSelectViewModel = hiltViewModel(),
) {
    val candidates by viewModel.candidates.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("메타 후보 선택") }) },
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Row(modifier = Modifier.padding(BookaSpacing.md)) {
                SampleDataBadge(label = "Naver/Google Books/Open Library는 PART 2에서 실연동")
            }
            if (candidates.isEmpty()) {
                BookaEmptyState(
                    title = "후보를 불러오는 중",
                    description = "잠시 후 후보가 표시됩니다.",
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = BookaSpacing.md),
                    verticalArrangement = Arrangement.spacedBy(BookaSpacing.sm),
                ) {
                    items(candidates, key = { it.id }) { candidate ->
                        CandidateCard(candidate) { viewModel.onSelect(candidate, onCandidateApplied) }
                    }
                }
            }
            OutlinedButton(
                onClick = onManualEdit,
                modifier = Modifier.fillMaxWidth().padding(BookaSpacing.md),
            ) {
                Text("직접 입력하기")
            }
        }
    }
}

@Composable
private fun CandidateCard(candidate: MetadataCandidate, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(BookaSpacing.sm)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(candidate.title, style = MaterialTheme.typography.titleMedium)
                AssistChip(
                    onClick = {},
                    label = { Text("${candidate.score}점" + if (candidate.isAutoApplyEligible) " · 자동적용 가능" else "") },
                )
            }
            candidate.author?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
            Text(candidate.source.name, style = MaterialTheme.typography.labelSmall)
            LinearProgressIndicator(
                progress = { candidate.score / 100f },
                modifier = Modifier.fillMaxWidth().padding(top = BookaSpacing.xs),
            )
            candidate.synopsis?.let {
                Text(it, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = BookaSpacing.xs))
            }
        }
    }
}
