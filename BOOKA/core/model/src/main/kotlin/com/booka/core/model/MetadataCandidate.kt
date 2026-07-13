package com.booka.core.model

/** 온라인/로컬 분석으로 생성된 메타 후보 하나. 점수는 100점 만점(지시서 8.3). */
data class MetadataCandidate(
    val id: String,
    val workId: String,
    val source: MetadataSource,
    val title: String,
    val author: String?,
    val coverUrl: String?,
    val isbn: String?,
    val publishedYear: Int?,
    val synopsis: String?,
    val score: Int,
    val scoreBreakdown: List<ScoreComponent>,
    val mergedFromSources: List<MetadataSource>,
)

data class ScoreComponent(val label: String, val points: Int)

val MetadataCandidate.isAutoApplyEligible: Boolean
    get() = score >= 80
