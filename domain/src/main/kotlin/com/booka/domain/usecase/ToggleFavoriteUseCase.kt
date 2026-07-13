package com.booka.domain.usecase

import com.booka.core.common.BookaResult
import com.booka.domain.repository.LibraryRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: LibraryRepository,
) {
    suspend operator fun invoke(workId: String): BookaResult<Unit> = repository.toggleFavorite(workId)
}
