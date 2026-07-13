package com.booka.domain.usecase

import com.booka.core.common.BookaResult
import com.booka.domain.repository.ReaderRepository
import javax.inject.Inject

class GetChapterTextUseCase @Inject constructor(
    private val repository: ReaderRepository,
) {
    suspend operator fun invoke(workId: String, chapterId: String): BookaResult<String> =
        repository.getChapterText(workId, chapterId)
}
