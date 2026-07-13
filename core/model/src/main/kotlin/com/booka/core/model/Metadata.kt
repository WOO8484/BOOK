package com.booka.core.model

data class Metadata(
    val workId: String,
    val title: String,
    val author: String?,
    val genres: List<String>,
    val tags: List<String>,
    val synopsis: String?,
    val status: WorkStatus,
    val publishedYear: Int?,
    val coverUrl: String?,
    val ratings: List<SourceRating>,
    val isAiAssisted: Boolean,
    val fieldSources: Map<String, MetadataSource>,
)

data class SourceRating(
    val provider: MetadataSource,
    val score: Double,
    val scaleMax: Double,
)

enum class MetadataSource { USER_INPUT, LOCAL_ANALYSIS, NAVER, GOOGLE_BOOKS, OPEN_LIBRARY, AI_ASSISTED }
