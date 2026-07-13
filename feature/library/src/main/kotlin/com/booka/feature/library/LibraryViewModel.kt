package com.booka.feature.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booka.core.common.BookaError
import com.booka.domain.usecase.GetLibraryWorksUseCase
import com.booka.domain.usecase.ObserveShelvesUseCase
import com.booka.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getLibraryWorks: GetLibraryWorksUseCase,
    private val observeShelves: ObserveShelvesUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    init {
        loadLibrary()
    }

    private fun loadLibrary() {
        viewModelScope.launch {
            combine(
                getLibraryWorks(shelfId = null),
                observeShelves(),
            ) { works, shelves -> works to shelves }
                .catch { throwable ->
                    val message = (throwable as? BookaError)?.message ?: "서재를 불러오지 못했습니다."
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = message)
                }
                .onEach { (works, shelves) ->
                    val filtered = applyShelfFilter(works, _uiState.value.selectedShelfId)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        works = sortWorks(filtered, _uiState.value.sortOrder),
                        shelves = shelves,
                        errorMessage = null,
                    )
                }
                .collect { }
        }
    }

    fun onAction(action: LibraryUiAction) {
        when (action) {
            is LibraryUiAction.SelectShelf -> {
                _uiState.value = _uiState.value.copy(selectedShelfId = action.shelfId)
                loadLibrary()
            }
            is LibraryUiAction.SelectSort -> {
                _uiState.value = _uiState.value.copy(
                    sortOrder = action.sortOrder,
                    works = sortWorks(_uiState.value.works, action.sortOrder),
                    isFilterSheetVisible = false,
                )
            }
            LibraryUiAction.OpenFilterSheet -> _uiState.value = _uiState.value.copy(isFilterSheetVisible = true)
            LibraryUiAction.DismissFilterSheet -> _uiState.value = _uiState.value.copy(isFilterSheetVisible = false)
            is LibraryUiAction.ToggleFavorite -> viewModelScope.launch { toggleFavorite(action.workId) }
            LibraryUiAction.Retry -> {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                loadLibrary()
            }
        }
    }

    private fun applyShelfFilter(
        works: List<com.booka.core.model.Work>,
        shelfId: String,
    ) = if (shelfId == "shelf-all") works else works.filter { it.shelfIds.contains(shelfId) }

    private fun sortWorks(
        works: List<com.booka.core.model.Work>,
        order: LibrarySortOrder,
    ) = when (order) {
        LibrarySortOrder.RECENTLY_ADDED -> works.sortedByDescending { it.addedAtEpochMillis }
        LibrarySortOrder.TITLE_ASC -> works.sortedBy { it.title }
        LibrarySortOrder.LAST_READ -> works.sortedByDescending { it.lastReadAtEpochMillis ?: 0L }
    }
}
