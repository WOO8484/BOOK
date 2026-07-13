package com.booka.domain.repository

import com.booka.core.common.BookaResult
import com.booka.core.model.Bookmark
import com.booka.core.model.ReadingProgress
import com.booka.core.model.Shelf
import com.booka.core.model.Work
import kotlinx.coroutines.flow.Flow

/**
 * 구현체는 :data:library에 둔다(PART 2). PART 1에서는 Fake 구현을 :core:testing에 두고
 * DI로 바인딩한다(지시서 7.1).
 */
interface LibraryRepository {
    fun observeWorks(query: String = "", shelfId: String? = null): Flow<List<Work>>
    suspend fun getWork(workId: String): BookaResult<Work>
    fun observeShelves(): Flow<List<Shelf>>
    suspend fun toggleFavorite(workId: String): BookaResult<Unit>
    suspend fun getReadingProgress(workId: String): BookaResult<ReadingProgress?>
    suspend fun getBookmarks(workId: String): BookaResult<List<Bookmark>>
}
