package com.booka.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.booka.core.designsystem.BookaSpacing

/** 서재 그리드/리스트 공용 작품 카드. */
@Composable
fun WorkCard(
    title: String,
    author: String?,
    coverUrl: String?,
    progressLabel: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Column {
            AsyncImage(
                model = coverUrl,
                contentDescription = "$title 표지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
            )
            Column(modifier = Modifier.padding(BookaSpacing.sm)) {
                Text(
                    title,
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (author != null) {
                    Text(
                        author,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                if (progressLabel != null) {
                    Text(progressLabel, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}
