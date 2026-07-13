package com.booka.core.model

/** 서재에 등록된 하나의 작품(소설 또는 만화)을 나타내는 불변 도메인 모델. */
data class Work(
    val id: String,
    val title: String,
    val author: String?,
    val type: WorkType,
    val status: WorkStatus,
    val coverUrl: String?,
    val genres: List<String>,
    val tags: List<String>,
    val synopsis: String?,
    val publishedYear: Int?,
    val addedAtEpochMillis: Long,
    val lastReadAtEpochMillis: Long?,
    val shelfIds: List<String>,
    val fileCount: Int,
    val progressPercent: Int,
    val isFavorite: Boolean,
)

enum class WorkType { NOVEL, COMIC }

enum class WorkStatus { UNREAD, READING, COMPLETED, ON_HOLD }
