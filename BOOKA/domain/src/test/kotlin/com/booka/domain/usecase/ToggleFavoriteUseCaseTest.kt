package com.booka.domain.usecase

import com.booka.core.common.BookaError
import com.booka.core.common.BookaResult
import com.booka.core.model.Bookmark
import com.booka.core.model.ReadingProgress
import com.booka.core.model.Shelf
import com.booka.core.model.Work
import com.booka.core.model.WorkStatus
import com.booka.core.model.WorkType
import com.booka.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

private class InMemoryLibraryRepository(initial: List<Work>) : LibraryRepository {
    private val state = MutableStateFlow(initial)

    override fun observeWorks(query: String, shelfId: String?): Flow<List<Work>> = state.asStateFlow()
    override suspend fun getWork(workId: String): BookaResult<Work> {
        val work = state.value.find { it.id == workId }
            ?: return BookaResult.Failure(BookaError.NotFound("not found"))
        return BookaResult.Success(work)
    }
    override fun observeShelves(): Flow<List<Shelf>> = MutableStateFlow(emptyList<Shelf>()).asStateFlow()
    override suspend fun toggleFavorite(workId: String): BookaResult<Unit> {
        state.value = state.value.map { if (it.id == workId) it.copy(isFavorite = !it.isFavorite) else it }
        return BookaResult.Success(Unit)
    }
    override suspend fun getReadingProgress(workId: String): BookaResult<ReadingProgress?> = BookaResult.Success(null)
    override suspend fun getBookmarks(workId: String): BookaResult<List<Bookmark>> = BookaResult.Success(emptyList())
}

private fun sampleWork(id: String, favorite: Boolean) = Work(
    id = id, title = "제목", author = "작가", type = WorkType.NOVEL, status = WorkStatus.READING,
    coverUrl = null, genres = emptyList(), tags = emptyList(), synopsis = null, publishedYear = null,
    addedAtEpochMillis = 0, lastReadAtEpochMillis = null, shelfIds = emptyList(), fileCount = 1,
    progressPercent = 0, isFavorite = favorite,
)

class ToggleFavoriteUseCaseTest {

    @Test
    fun `즐겨찾기를 토글하면 상태가 반전된다`() = runBlocking {
        val repository = InMemoryLibraryRepository(listOf(sampleWork("work-1", favorite = false)))
        val useCase = ToggleFavoriteUseCase(repository)

        val result = useCase("work-1")

        assertTrue(result is BookaResult.Success)
        val after = repository.getWork("work-1")
        assertTrue((after as BookaResult.Success).data.isFavorite)
    }

    @Test
    fun `존재하지 않는 작품 조회는 실패를 반환한다`() = runBlocking {
        val repository = InMemoryLibraryRepository(emptyList())
        val result = repository.getWork("missing")

        assertTrue(result is BookaResult.Failure)
        assertEquals("NOT_FOUND", (result as BookaResult.Failure).error.code)
    }
}
