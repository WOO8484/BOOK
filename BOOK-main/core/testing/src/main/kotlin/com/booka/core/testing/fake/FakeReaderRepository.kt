package com.booka.core.testing.fake

import com.booka.core.common.BookaResult
import com.booka.core.model.Chapter
import com.booka.core.model.ComicPage
import com.booka.core.model.ReadingProgress
import com.booka.core.testing.sample.SampleData
import com.booka.domain.repository.ReaderRepository
import javax.inject.Inject
import javax.inject.Singleton

/** 실제 뷰어 엔진(PART 3) 대신 화면 흐름 검증용 고정 텍스트/페이지 목록만 제공. */
@Singleton
class FakeReaderRepository @Inject constructor() : ReaderRepository {

    override suspend fun getChapters(workId: String): BookaResult<List<Chapter>> {
        val chapters = SampleData.sampleChapterTitles.mapIndexed { index, title ->
            Chapter(
                id = "$workId-ch-$index", workId = workId, index = index, title = title,
                charOffsetStart = index * 1000L, charOffsetEnd = (index + 1) * 1000L,
            )
        }
        return BookaResult.Success(chapters)
    }

    override suspend fun getChapterText(workId: String, chapterId: String): BookaResult<String> =
        BookaResult.Success(
            "이 본문은 PART 1 샘플 데이터입니다. 실제 TXT 렌더링 엔진은 PART 3에서 구현됩니다.\n\n" +
                "챕터 ID: $chapterId\n\n" + "샘플 문단. ".repeat(40),
        )

    override suspend fun getComicPages(workId: String): BookaResult<List<ComicPage>> {
        val pages = (0 until SampleData.sampleComicPageCount).map { index ->
            ComicPage(
                id = "$workId-page-$index", workId = workId, index = index,
                entryPath = "sample/page_${index.toString().padStart(3, '0')}.jpg",
                width = 1080, height = 1536, isCorrupted = false,
            )
        }
        return BookaResult.Success(pages)
    }

    override suspend fun saveProgress(progress: ReadingProgress): BookaResult<Unit> = BookaResult.Success(Unit)
}
