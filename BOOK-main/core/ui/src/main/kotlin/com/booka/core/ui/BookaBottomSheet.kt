package com.booka.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.booka.core.designsystem.BookaSpacing

/** 공통 바텀시트(지시서 PART1 필수 화면). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookaBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        androidx.compose.foundation.layout.Column(modifier = Modifier.padding(BookaSpacing.md)) {
            content()
        }
    }
}
