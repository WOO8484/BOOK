package com.booka.core.model

data class ReadingProgress(
    val workId: String,
    val novelCharOffset: Long?,
    val comicPageIndex: Int?,
    val updatedAtEpochMillis: Long,
)
