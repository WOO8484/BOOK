package com.booka.core.testing.fake

import com.booka.core.common.BookaError
import com.booka.core.common.BookaResult
import com.booka.core.model.Bookmark
import com.booka.core.model.ReadingProgress
import com.booka.core.model.Shelf
import com.booka.core.model.Work
import com.booka.core.testing.sample.SampleData
import com.booka.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * :domain의 LibraryRepository를 결정론적 샘플 데이터로 구현한 Fake.
 * release 빌드에는 포함하지 않고 PART 1 debug DI 모듈에서만 바인딩한다(지시서 7.1).
 */
@Singleton
class FakeLibraryRepository @Inject constructor() : LibraryRepository {

    private val worksState = MutableStateFlow(SampleData.works)
    private val shelvesState = MutableStateFlow(SampleData.shelves)

    override fun observeWorks(query: String, shelfId: String?) =
        worksState.asStateFlow().map { list ->
            list.filter { work ->
                (query.isBlank() || work.title.contains(query, ignoreCase = true) ||
                    (work.author?.contains(query, ignoreCase = true) == true)) &&
                    (shelfId == null || shelfId == "shelf-all" || work.shelfIds.contains(shelfId))
            }
        }

    override suspend fun getWork(workId: String): BookaResult<Work> {
        val work = worksState.value.find { it.id == workId }
            ?: return BookaResult.Failure(BookaError.NotFound("작품을 찾을 수 없습니다(샘플 데이터)."))
        return BookaResult.Success(work)
    }

    override fun observeShelves() = shelvesState.asStateFlow()

    override suspend fun toggleFavorite(workId: String): BookaResult<Unit> {
        worksState.value = worksState.value.map {
            if (it.id == workId) it.copy(isFavorite = !it.isFavorite) else it
        }
        return BookaResult.Success(Unit)
    }

    override suspend fun getReadingProgress(workId: String): BookaResult<ReadingProgress?> {
        val work = worksState.value.find { it.id == workId } ?: return BookaResult.Success(null)
        return BookaResult.Success(
            ReadingProgress(
                workId = workId,
                novelCharOffset = if (work.progressPercent > 0) work.progressPercent.toLong() * 100 else null,
                comicPageIndex = null,
                updatedAtEpochMillis = work.lastReadAtEpochMillis ?: 0L,
            ),
        )
    }

    override suspend fun getBookmarks(workId: String): BookaResult<List<Bookmark>> =
        BookaResult.Success(SampleData.bookmarksByWork[workId].orEmpty())
}
