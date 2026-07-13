package com.booka.domain.usecase

import com.booka.core.common.BookaResult
import com.booka.core.model.ComicPage
import com.booka.domain.repository.ReaderRepository
import javax.inject.Inject

class GetComicPagesUseCase @Inject constructor(
    private val repository: ReaderRepository,
) {
    suspend operator fun invoke(workId: String): BookaResult<List<ComicPage>> = repository.getComicPages(workId)
}
