package com.booka.domain.usecase

import com.booka.core.common.BookaResult
import com.booka.domain.repository.MetadataRepository
import javax.inject.Inject

class SearchMetadataCandidatesUseCase @Inject constructor(
    private val repository: MetadataRepository,
) {
    suspend operator fun invoke(sessionId: String, workDraftId: String, query: String): BookaResult<Unit> =
        repository.searchOnline(sessionId, workDraftId, query)
}
