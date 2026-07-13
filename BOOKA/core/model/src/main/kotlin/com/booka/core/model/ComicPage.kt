package com.booka.core.model

data class ComicPage(
    val id: String,
    val workId: String,
    val index: Int,
    val entryPath: String,
    val width: Int?,
    val height: Int?,
    val isCorrupted: Boolean,
)
