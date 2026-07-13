package com.booka.feature.reader.comic

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.booka.core.common.BookaResult
import com.booka.core.model.ReadingProgress
import com.booka.core.navigation.BookaDestination
import com.booka.domain.usecase.GetComicPagesUseCase
import com.booka.domain.usecase.SaveReadingProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicReaderViewModel @Inject constructor(
    private val getComicPages: GetComicPagesUseCase,
    private val saveProgress: SaveReadingProgressUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val workId: String = savedStateHandle.toRoute<BookaDestination.ComicReader>().workId

    private val _uiState = MutableStateFlow(ComicReaderUiState())
    val uiState: StateFlow<ComicReaderUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            when (val result = getComicPages(workId)) {
                is BookaResult.Success -> _uiState.value = _uiState.value.copy(isLoading = false, pages = result.data)
                is BookaResult.Failure -> _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.error.message,
                )
                BookaResult.Loading -> Unit
            }
        }
    }

    fun onPageChanged(index: Int) {
        _uiState.value = _uiState.value.copy(currentPageIndex = index)
        viewModelScope.launch {
            saveProgress(
                ReadingProgress(
                    workId = workId,
                    novelCharOffset = null,
                    comicPageIndex = index,
                    updatedAtEpochMillis = System.currentTimeMillis(),
                ),
            )
        }
    }

    fun toggleImmersive() { _uiState.value = _uiState.value.copy(isImmersive = !_uiState.value.isImmersive) }
}
