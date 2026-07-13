package com.booka.feature.library

import com.booka.core.model.Shelf
import com.booka.core.model.Work

enum class LibrarySortOrder(val label: String) {
    RECENTLY_ADDED("최근 추가순"),
    TITLE_ASC("제목순"),
    LAST_READ("최근 읽은순"),
}

data class LibraryUiState(
    val isLoading: Boolean = true,
    val works: List<Work> = emptyList(),
    val shelves: List<Shelf> = emptyList(),
    val selectedShelfId: String = "shelf-all",
    val sortOrder: LibrarySortOrder = LibrarySortOrder.RECENTLY_ADDED,
    val isFilterSheetVisible: Boolean = false,
    val errorMessage: String? = null,
)

sealed interface LibraryUiAction {
    data class SelectShelf(val shelfId: String) : LibraryUiAction
    data class SelectSort(val sortOrder: LibrarySortOrder) : LibraryUiAction
    data object OpenFilterSheet : LibraryUiAction
    data object DismissFilterSheet : LibraryUiAction
    data class ToggleFavorite(val workId: String) : LibraryUiAction
    data object Retry : LibraryUiAction
}
