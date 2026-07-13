package com.booka.feature.reader.novel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.booka.core.common.BookaResult
import com.booka.core.navigation.BookaDestination
import com.booka.domain.usecase.GetChapterTextUseCase
import com.booka.domain.usecase.GetChaptersUseCase
import com.booka.domain.usecase.SaveReadingProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NovelReaderViewModel @Inject constructor(
    private val getChapters: GetChaptersUseCase,
    private val getChapterText: GetChapterTextUseCase,
    private val saveProgress: SaveReadingProgressUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val workId: String = savedStateHandle.toRoute<BookaDestination.NovelReader>().workId

    private val _uiState = MutableStateFlow(NovelReaderUiState())
    val uiState: StateFlow<NovelReaderUiState> = _uiState.asStateFlow()

    init { loadChapters() }

    private fun loadChapters() {
        viewModelScope.launch {
            when (val result = getChapters(workId)) {
                is BookaResult.Success -> {
                    _uiState.value = _uiState.value.copy(chapters = result.data)
                    loadChapterText(0)
                }
                is BookaResult.Failure -> _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.error.message,
                )
                BookaResult.Loading -> Unit
            }
        }
    }

    private fun loadChapterText(index: Int) {
        val chapter = _uiState.value.chapters.getOrNull(index) ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = getChapterText(workId, chapter.id)) {
                is BookaResult.Success -> _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    currentChapterIndex = index,
                    chapterText = result.data,
                    isTocVisible = false,
                )
                is BookaResult.Failure -> _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.error.message,
                )
                BookaResult.Loading -> Unit
            }
            saveProgress(
                com.booka.core.model.ReadingProgress(
                    workId = workId,
                    novelCharOffset = chapter.charOffsetStart,
                    comicPageIndex = null,
                    updatedAtEpochMillis = System.currentTimeMillis(),
                ),
            )
        }
    }

    fun onSelectChapter(index: Int) = loadChapterText(index)
    fun onNextChapter() {
        val next = _uiState.value.currentChapterIndex + 1
        if (next < _uiState.value.chapters.size) loadChapterText(next)
    }
    fun onPrevChapter() {
        val prev = _uiState.value.currentChapterIndex - 1
        if (prev >= 0) loadChapterText(prev)
    }
    fun toggleImmersive() { _uiState.value = _uiState.value.copy(isImmersive = !_uiState.value.isImmersive) }
    fun openToc() { _uiState.value = _uiState.value.copy(isTocVisible = true) }
    fun dismissToc() { _uiState.value = _uiState.value.copy(isTocVisible = false) }
}
