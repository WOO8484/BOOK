package com.booka.core.navigation

import kotlinx.serialization.Serializable

/**
 * route 문자열 난립을 금지하기 위한(지시서 3.5) 타입-세이프 destination 계약.
 * 각 feature 모듈은 이 봉인 클래스의 하위 타입만 사용해 navigate 한다.
 */
sealed interface BookaDestination {

    // 하단 메뉴 3개(지시서 3.1)
    @Serializable data object Library : BookaDestination
    @Serializable data object Import : BookaDestination
    @Serializable data object Settings : BookaDestination

    @Serializable data class LibrarySearch(val initialQuery: String = "") : BookaDestination
    @Serializable data class WorkDetail(val workId: String) : BookaDestination

    @Serializable data class ImportProgress(val sessionId: String) : BookaDestination
    @Serializable data class MetadataAnalysis(val sessionId: String) : BookaDestination
    @Serializable data class MetadataCandidateSelect(val sessionId: String, val workDraftId: String) : BookaDestination
    @Serializable data class MetadataManualEdit(val sessionId: String, val workDraftId: String) : BookaDestination
    @Serializable data class RenamePreview(val sessionId: String) : BookaDestination

    // 뷰어: 하단 메뉴 숨김 대상(지시서 3.1)
    @Serializable data class NovelReader(val workId: String) : BookaDestination
    @Serializable data class ComicReader(val workId: String) : BookaDestination

    @Serializable data object SettingsShelfDisplay : BookaDestination
    @Serializable data object SettingsMetadataProviders : BookaDestination
    @Serializable data object SettingsReaderDefaults : BookaDestination
    @Serializable data object SettingsStorage : BookaDestination
    @Serializable data object SettingsAbout : BookaDestination
}

/** 하단 메뉴를 숨겨야 하는 destination인지 판단(지시서 3.1, 6.1 잔상 방지와 연결). */
fun BookaDestination.hidesBottomBar(): Boolean = when (this) {
    is BookaDestination.NovelReader, is BookaDestination.ComicReader -> true
    else -> false
}
