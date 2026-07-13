package com.booka.domain.repository

import com.booka.core.common.BookaResult
import com.booka.core.model.Chapter
import com.booka.core.model.ComicPage
import com.booka.core.model.ReadingProgress

/** 구현체는 :data:reader에 둔다(PART 3, 실제 파일 엔진 포함). */
interface ReaderRepository {
    suspend fun getChapters(workId: String): BookaResult<List<Chapter>>
    suspend fun getChapterText(workId: String, chapterId: String): BookaResult<String>
    suspend fun getComicPages(workId: String): BookaResult<List<ComicPage>>
    suspend fun saveProgress(progress: ReadingProgress): BookaResult<Unit>
}
