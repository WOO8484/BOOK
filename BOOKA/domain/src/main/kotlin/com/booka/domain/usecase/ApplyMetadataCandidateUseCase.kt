package com.booka.domain.usecase

import com.booka.core.common.BookaResult
import com.booka.domain.repository.MetadataRepository
import javax.inject.Inject

class ApplyMetadataCandidateUseCase @Inject constructor(
    private val repository: MetadataRepository,
) {
    suspend operator fun invoke(workDraftId: String, candidateId: String): BookaResult<Unit> =
        repository.applyCandidate(workDraftId, candidateId)
}
