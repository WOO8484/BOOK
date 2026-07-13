package com.booka.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.booka.core.designsystem.BookaSpacing

@Composable
fun BookaErrorState(
    message: String,
    modifier: Modifier = Modifier,
    retryLabel: String = "다시 시도",
    onRetry: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(BookaSpacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(Icons.Filled.ErrorOutline, contentDescription = "오류", tint = MaterialTheme.colorScheme.error)
        Text(message, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = BookaSpacing.sm))
        if (onRetry != null) {
            Button(onClick = onRetry, modifier = Modifier.padding(top = BookaSpacing.md)) {
                Text(retryLabel)
            }
        }
    }
}
