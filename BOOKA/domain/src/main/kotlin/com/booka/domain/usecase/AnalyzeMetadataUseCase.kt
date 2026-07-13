package com.booka.domain.usecase

import com.booka.core.common.BookaResult
import com.booka.domain.repository.MetadataRepository
import javax.inject.Inject

class AnalyzeMetadataUseCase @Inject constructor(
    private val repository: MetadataRepository,
) {
    suspend operator fun invoke(sessionId: String, workDraftId: String): BookaResult<Unit> =
        repository.analyzeLocal(sessionId, workDraftId)
}
