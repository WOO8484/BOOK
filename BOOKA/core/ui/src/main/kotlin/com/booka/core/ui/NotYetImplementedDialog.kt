package com.booka.core.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

/**
 * 아직 구현되지 않은 실제 기능(Room/SAF/API/뷰어 엔진 등)에 연결된 버튼을 빈 동작으로 두지 않기 위한
 * 안내 다이얼로그(지시서 7.1). 미연결 메뉴 취급을 피하기 위해 모든 버튼은 이 다이얼로그 또는
 * 실제 Fake 화면 전환 중 하나로 반드시 연결한다.
 */
@Composable
fun NotYetImplementedDialog(
    featureLabel: String,
    plannedPart: String,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("$featureLabel 는 $plannedPart 에서 제공됩니다") },
        text = { Text("PART 1은 화면 흐름과 UI 검증 단계입니다. 실제 기능은 해당 PART 승인 후 연결됩니다.") },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("확인") }
        },
    )
}
