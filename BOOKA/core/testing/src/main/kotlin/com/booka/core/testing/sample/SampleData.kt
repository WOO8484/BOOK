package com.booka.core.testing.sample

import com.booka.core.model.Bookmark
import com.booka.core.model.MetadataCandidate
import com.booka.core.model.MetadataSource
import com.booka.core.model.ScoreComponent
import com.booka.core.model.Shelf
import com.booka.core.model.Work
import com.booka.core.model.WorkStatus
import com.booka.core.model.WorkType

/**
 * PART 1 전용 결정론적 샘플 데이터(지시서 7.1). 실제 저장소가 아니며 화면에는
 * "샘플 데이터"임을 표시한다.
 */
object SampleData {

    val shelves: List<Shelf> = listOf(
        Shelf(id = "shelf-all", name = "전체", workIds = emptyList(), sortOrder = 0),
        Shelf(id = "shelf-novel", name = "소설", workIds = listOf("work-1", "work-3", "work-5"), sortOrder = 1),
        Shelf(id = "shelf-comic", name = "만화", workIds = listOf("work-2", "work-4", "work-6"), sortOrder = 2),
        Shelf(id = "shelf-favorite", name = "즐겨찾기", workIds = listOf("work-1", "work-4"), sortOrder = 3),
    )

    val works: List<Work> = listOf(
        Work(
            id = "work-1", title = "달빛 아래 도서관", author = "한서연", type = WorkType.NOVEL,
            status = WorkStatus.READING, coverUrl = null,
            genres = listOf("판타지", "드라마"), tags = listOf("힐링", "성장"),
            synopsis = "폐관 직전의 도서관을 지키게 된 사서의 이야기(샘플 데이터).",
            publishedYear = 2023, addedAtEpochMillis = 1_700_000_000_000, lastReadAtEpochMillis = 1_701_000_000_000,
            shelfIds = listOf("shelf-novel", "shelf-favorite"), fileCount = 12, progressPercent = 42, isFavorite = true,
        ),
        Work(
            id = "work-2", title = "철갑기병 크로니클", author = "오태준", type = WorkType.COMIC,
            status = WorkStatus.UNREAD, coverUrl = null,
            genres = listOf("액션", "SF"), tags = listOf("전쟁", "로봇"),
            synopsis = "붕괴한 제국을 무대로 한 기계병기 전쟁 만화(샘플 데이터).",
            publishedYear = 2021, addedAtEpochMillis = 1_699_000_000_000, lastReadAtEpochMillis = null,
            shelfIds = listOf("shelf-comic"), fileCount = 8, progressPercent = 0, isFavorite = false,
        ),
        Work(
            id = "work-3", title = "이토록 조용한 겨울", author = "박세아", type = WorkType.NOVEL,
            status = WorkStatus.COMPLETED, coverUrl = null,
            genres = listOf("로맨스"), tags = listOf("잔잔함"),
            synopsis = "작은 마을에서 벌어지는 두 사람의 겨울 이야기(샘플 데이터).",
            publishedYear = 2019, addedAtEpochMillis = 1_690_000_000_000, lastReadAtEpochMillis = 1_695_000_000_000,
            shelfIds = listOf("shelf-novel"), fileCount = 1, progressPercent = 100, isFavorite = false,
        ),
        Work(
            id = "work-4", title = "언더시티 탐정록", author = "정민호", type = WorkType.COMIC,
            status = WorkStatus.READING, coverUrl = null,
            genres = listOf("미스터리"), tags = listOf("느와르"),
            synopsis = "지하도시를 배경으로 한 탐정 추리물(샘플 데이터).",
            publishedYear = 2022, addedAtEpochMillis = 1_705_000_000_000, lastReadAtEpochMillis = 1_706_500_000_000,
            shelfIds = listOf("shelf-comic", "shelf-favorite"), fileCount = 20, progressPercent = 65, isFavorite = true,
        ),
        Work(
            id = "work-5", title = "붉은 항성계", author = "김도윤", type = WorkType.NOVEL,
            status = WorkStatus.ON_HOLD, coverUrl = null,
            genres = listOf("SF"), tags = listOf("우주", "장편"),
            synopsis = "항성 간 이주선단을 둘러싼 장편 SF(샘플 데이터).",
            publishedYear = 2024, addedAtEpochMillis = 1_708_000_000_000, lastReadAtEpochMillis = 1_708_200_000_000,
            shelfIds = listOf("shelf-novel"), fileCount = 30, progressPercent = 8, isFavorite = false,
        ),
        Work(
            id = "work-6", title = "마을버스 정류장", author = "이하늘", type = WorkType.COMIC,
            status = WorkStatus.UNREAD, coverUrl = null,
            genres = listOf("일상"), tags = listOf("옴니버스"),
            synopsis = "동네 정류장에서 벌어지는 소소한 일상 옴니버스(샘플 데이터).",
            publishedYear = 2020, addedAtEpochMillis = 1_685_000_000_000, lastReadAtEpochMillis = null,
            shelfIds = listOf("shelf-comic"), fileCount = 5, progressPercent = 0, isFavorite = false,
        ),
    )

    val bookmarksByWork: Map<String, List<Bookmark>> = mapOf(
        "work-1" to listOf(
            Bookmark("bm-1", "work-1", "3장 시작", 12_400, null, 1_701_000_000_000),
            Bookmark("bm-2", "work-1", "인상적인 대사", 25_800, null, 1_701_100_000_000),
        ),
        "work-4" to listOf(
            Bookmark("bm-3", "work-4", "12화 절단신공 직전", null, 14, 1_706_500_000_000),
        ),
    )

    fun metadataCandidatesFor(workDraftId: String): List<MetadataCandidate> = listOf(
        MetadataCandidate(
            id = "cand-$workDraftId-1", workId = workDraftId, source = MetadataSource.NAVER,
            title = "달빛 아래 도서관", author = "한서연", coverUrl = null, isbn = "9791100000012",
            publishedYear = 2023, synopsis = "네이버 검색 결과(샘플).",
            score = 92, scoreBreakdown = listOf(
                ScoreComponent("정규화 제목 완전 일치", 45),
                ScoreComponent("작가 완전 일치", 25),
                ScoreComponent("ISBN 일치", 10),
                ScoreComponent("출간연도 일치", 5),
                ScoreComponent("표지 존재", 5),
            ),
            mergedFromSources = listOf(MetadataSource.NAVER, MetadataSource.GOOGLE_BOOKS),
        ),
        MetadataCandidate(
            id = "cand-$workDraftId-2", workId = workDraftId, source = MetadataSource.OPEN_LIBRARY,
            title = "달빛 아래의 도서관", author = null, coverUrl = null, isbn = null,
            publishedYear = 2023, synopsis = "Open Library 검색 결과(샘플).",
            score = 55, scoreBreakdown = listOf(
                ScoreComponent("제목 부분·유사 일치", 25),
                ScoreComponent("출간연도 일치", 5),
            ),
            mergedFromSources = listOf(MetadataSource.OPEN_LIBRARY),
        ),
        MetadataCandidate(
            id = "cand-$workDraftId-3", workId = workDraftId, source = MetadataSource.LOCAL_ANALYSIS,
            title = "달빛아래도서관_최종본", author = null, coverUrl = null, isbn = null,
            publishedYear = null, synopsis = "파일명 기반 로컬 분석 결과(샘플).",
            score = 20, scoreBreakdown = listOf(ScoreComponent("제목 부분·유사 일치", 20)),
            mergedFromSources = listOf(MetadataSource.LOCAL_ANALYSIS),
        ),
    )

    val sampleChapterTitles = listOf(
        "1장 서문", "2장 도서관의 열쇠", "3장 낡은 목록", "4장 사서의 비밀", "5장 마지막 손님",
    )

    val sampleComicPageCount = 18
}
