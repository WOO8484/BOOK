package com.booka.feature.metadata

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.booka.core.navigation.BookaDestination
import com.booka.domain.usecase.ApplyManualMetadataEditUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ManualEditFormState(
    val title: String = "",
    val author: String = "",
    val synopsis: String = "",
    val isSaving: Boolean = false,
)

@HiltViewModel
class MetadataManualEditViewModel @Inject constructor(
    private val applyManualEdit: ApplyManualMetadataEditUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<BookaDestination.MetadataManualEdit>()

    private val _formState = MutableStateFlow(ManualEditFormState())
    val formState: StateFlow<ManualEditFormState> = _formState.asStateFlow()

    fun onTitleChange(value: String) { _formState.value = _formState.value.copy(title = value) }
    fun onAuthorChange(value: String) { _formState.value = _formState.value.copy(author = value) }
    fun onSynopsisChange(value: String) { _formState.value = _formState.value.copy(synopsis = value) }

    fun save(onSaved: () -> Unit) {
        viewModelScope.launch {
            _formState.value = _formState.value.copy(isSaving = true)
            applyManualEdit(
                route.workDraftId,
                _formState.value.title,
                _formState.value.author.ifBlank { null },
                _formState.value.synopsis.ifBlank { null },
            )
            _formState.value = _formState.value.copy(isSaving = false)
            onSaved()
        }
    }
}
