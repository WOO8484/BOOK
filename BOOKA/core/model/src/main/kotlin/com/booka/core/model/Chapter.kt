package com.booka.core.model

data class Chapter(
    val id: String,
    val workId: String,
    val index: Int,
    val title: String,
    val charOffsetStart: Long,
    val charOffsetEnd: Long,
)
