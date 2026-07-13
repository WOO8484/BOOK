package com.booka.domain.usecase

import com.booka.core.common.BookaResult
import com.booka.core.model.Work
import com.booka.domain.repository.LibraryRepository
import javax.inject.Inject

class GetWorkDetailUseCase @Inject constructor(
    private val repository: LibraryRepository,
) {
    suspend operator fun invoke(workId: String): BookaResult<Work> = repository.getWork(workId)
}
