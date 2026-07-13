package com.booka.domain.usecase

import com.booka.core.common.BookaResult
import com.booka.domain.repository.ImportRepository
import com.booka.domain.repository.ImportSession
import javax.inject.Inject

class StartImportUseCase @Inject constructor(
    private val repository: ImportRepository,
) {
    suspend operator fun invoke(displayNames: List<String>): BookaResult<ImportSession> =
        repository.startImport(displayNames)
}
