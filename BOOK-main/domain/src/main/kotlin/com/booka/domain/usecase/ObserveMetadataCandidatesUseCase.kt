package com.booka.domain.usecase

import com.booka.core.model.MetadataCandidate
import com.booka.domain.repository.MetadataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMetadataCandidatesUseCase @Inject constructor(
    private val repository: MetadataRepository,
) {
    operator fun invoke(sessionId: String, workDraftId: String): Flow<List<MetadataCandidate>> =
        repository.observeCandidates(sessionId, workDraftId)
}
