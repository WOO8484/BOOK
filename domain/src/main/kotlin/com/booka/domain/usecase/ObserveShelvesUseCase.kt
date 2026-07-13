package com.booka.domain.usecase

import com.booka.core.model.Shelf
import com.booka.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveShelvesUseCase @Inject constructor(
    private val repository: LibraryRepository,
) {
    operator fun invoke(): Flow<List<Shelf>> = repository.observeShelves()
}
