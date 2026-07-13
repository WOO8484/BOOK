package com.booka.core.model

data class Bookmark(
    val id: String,
    val workId: String,
    val label: String,
    val novelCharOffset: Long?,
    val comicPageIndex: Int?,
    val createdAtEpochMillis: Long,
)
