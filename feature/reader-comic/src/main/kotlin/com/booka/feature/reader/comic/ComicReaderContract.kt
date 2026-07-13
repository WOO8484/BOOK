package com.booka.feature.reader.comic

import com.booka.core.model.ComicPage

data class ComicReaderUiState(
    val isLoading: Boolean = true,
    val pages: List<ComicPage> = emptyList(),
    val currentPageIndex: Int = 0,
    val isImmersive: Boolean = false,
    val errorMessage: String? = null,
)
