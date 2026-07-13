package com.booka.feature.library

import com.booka.core.model.Bookmark
import com.booka.core.model.Work

data class WorkDetailUiState(
    val isLoading: Boolean = true,
    val work: Work? = null,
    val bookmarks: List<Bookmark> = emptyList(),
    val errorMessage: String? = null,
    val isDeleteConfirmVisible: Boolean = false,
)

sealed interface WorkDetailUiAction {
    data object ToggleFavorite : WorkDetailUiAction
    data object RequestDelete : WorkDetailUiAction
    data object ConfirmDelete : WorkDetailUiAction
    data object DismissDelete : WorkDetailUiAction
    data object Retry : WorkDetailUiAction
}
