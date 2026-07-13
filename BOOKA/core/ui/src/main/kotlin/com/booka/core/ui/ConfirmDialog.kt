package com.booka.core.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

/** 공통 확인 다이얼로그(지시서 PART1 필수 화면: 확인 다이얼로그). */
@Composable
fun BookaConfirmDialog(
    title: String,
    message: String,
    confirmLabel: String = "확인",
    dismissLabel: String = "취소",
    isDestructive: Boolean = false,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirmLabel)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissLabel)
            }
        },
    )
}
