package com.booka.feature.importer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.booka.core.common.BookaResult
import com.booka.domain.usecase.StartImportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImportViewModel @Inject constructor(
    private val startImport: StartImportUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ImportUiState())
    val uiState: StateFlow<ImportUiState> = _uiState.asStateFlow()

    private val _navigateToProgress = MutableSharedFlow<String>()
    val navigateToProgress: SharedFlow<String> = _navigateToProgress

    fun onAction(action: ImportUiAction) {
        when (action) {
            is ImportUiAction.ToggleFile -> {
                _uiState.value = _uiState.value.copy(
                    availableFiles = _uiState.value.availableFiles.map {
                        if (it.name == action.name) it.copy(isSelected = !it.isSelected) else it
                    },
                )
            }
            ImportUiAction.StartImport -> {
                val selected = _uiState.value.availableFiles.filter { it.isSelected }.map { it.name }
                viewModelScope.launch {
                    _uiState.value = _uiState.value.copy(isStarting = true, errorMessage = null)
                    when (val result = startImport(selected)) {
                        is BookaResult.Success -> {
                            _uiState.value = _uiState.value.copy(isStarting = false)
                            _navigateToProgress.emit(result.data.id)
                        }
                        is BookaResult.Failure -> _uiState.value = _uiState.value.copy(
                            isStarting = false,
                            errorMessage = result.error.message,
                        )
                        BookaResult.Loading -> Unit
                    }
                }
            }
            ImportUiAction.DismissError -> _uiState.value = _uiState.value.copy(errorMessage = null)
        }
    }
}
