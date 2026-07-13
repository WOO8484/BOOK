package com.booka.domain.usecase

import com.booka.core.common.BookaResult
import com.booka.domain.repository.ImportRepository
import javax.inject.Inject

class CancelImportUseCase @Inject constructor(
    private val repository: ImportRepository,
) {
    suspend operator fun invoke(sessionId: String): BookaResult<Unit> = repository.cancelImport(sessionId)
}
