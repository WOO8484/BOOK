package com.booka.domain.usecase

import com.booka.domain.repository.ImportProgressState
import com.booka.domain.repository.ImportRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveImportProgressUseCase @Inject constructor(
    private val repository: ImportRepository,
) {
    operator fun invoke(sessionId: String): Flow<ImportProgressState> = repository.observeProgress(sessionId)
}
