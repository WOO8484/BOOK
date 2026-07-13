package com.booka.domain.usecase

import com.booka.core.model.Work
import com.booka.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLibraryWorksUseCase @Inject constructor(
    private val repository: LibraryRepository,
) {
    operator fun invoke(query: String = "", shelfId: String? = null): Flow<List<Work>> =
        repository.observeWorks(query, shelfId)
}
