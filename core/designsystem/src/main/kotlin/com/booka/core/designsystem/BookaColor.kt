package com.booka.core.designsystem

import androidx.compose.ui.graphics.Color

// 흰색 모드 기본(지시서 3.1), 다크 모드 병행 지원
object BookaColor {
    val Primary = Color(0xFF2F6F4F)
    val PrimaryDark = Color(0xFF8FD9AE)
    val OnPrimary = Color(0xFFFFFFFF)

    val Background = Color(0xFFFFFFFF)
    val BackgroundDark = Color(0xFF121212)
    val Surface = Color(0xFFFFFFFF)
    val SurfaceDark = Color(0xFF1E1E1E)
    val SurfaceVariant = Color(0xFFF1F3F0)
    val SurfaceVariantDark = Color(0xFF2A2C2A)

    val OnBackground = Color(0xFF1B1C1B)
    val OnBackgroundDark = Color(0xFFE4E4E4)

    val Error = Color(0xFFBA1A1A)
    val ErrorDark = Color(0xFFFFB4AB)

    // 독서 화면 전용 배경(소설 뷰어 밝은/어두운/세피아, PART 3에서 실제 사용)
    val ReaderSepia = Color(0xFFF4ECD8)
}
