package com.booka.feature.reader.novel

import com.booka.core.model.Chapter

data class NovelReaderUiState(
    val isLoading: Boolean = true,
    val chapters: List<Chapter> = emptyList(),
    val currentChapterIndex: Int = 0,
    val chapterText: String = "",
    val isImmersive: Boolean = false,
    val isTocVisible: Boolean = false,
    val errorMessage: String? = null,
)
