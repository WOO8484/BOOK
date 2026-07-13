package com.booka.domain.usecase

import com.booka.core.common.BookaResult
import com.booka.core.model.ReadingProgress
import com.booka.domain.repository.ReaderRepository
import javax.inject.Inject

class SaveReadingProgressUseCase @Inject constructor(
    private val repository: ReaderRepository,
) {
    suspend operator fun invoke(progress: ReadingProgress): BookaResult<Unit> = repository.saveProgress(progress)
}
