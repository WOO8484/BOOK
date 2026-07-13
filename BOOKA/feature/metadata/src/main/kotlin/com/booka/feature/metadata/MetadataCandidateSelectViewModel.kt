package com.booka.feature.metadata

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.booka.core.model.MetadataCandidate
import com.booka.core.navigation.BookaDestination
import com.booka.domain.usecase.ApplyMetadataCandidateUseCase
import com.booka.domain.usecase.ObserveMetadataCandidatesUseCase
import com.booka.domain.usecase.SearchMetadataCandidatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MetadataCandidateSelectViewModel @Inject constructor(
    observeCandidates: ObserveMetadataCandidatesUseCase,
    private val searchOnline: SearchMetadataCandidatesUseCase,
    private val applyCandidate: ApplyMetadataCandidateUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<BookaDestination.MetadataCandidateSelect>()
    val workDraftId: String get() = route.workDraftId

    val candidates: StateFlow<List<MetadataCandidate>> = observeCandidates(route.sessionId, route.workDraftId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch { searchOnline(route.sessionId, route.workDraftId, "") }
    }

    fun onSelect(candidate: MetadataCandidate, onApplied: () -> Unit) {
        viewModelScope.launch {
            applyCandidate(route.workDraftId, candidate.id)
            onApplied()
        }
    }
}
