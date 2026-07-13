package com.booka.domain.usecase

import com.booka.core.common.BookaResult
import com.booka.domain.repository.MetadataRepository
import javax.inject.Inject

class ApplyManualMetadataEditUseCase @Inject constructor(
    private val repository: MetadataRepository,
) {
    suspend operator fun invoke(
        workDraftId: String,
        title: String,
        author: String?,
        synopsis: String?,
    ): BookaResult<Unit> = repository.applyManualEdit(workDraftId, title, author, synopsis)
}
