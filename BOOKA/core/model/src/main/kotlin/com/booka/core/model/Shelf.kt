package com.booka.core.model

data class Shelf(
    val id: String,
    val name: String,
    val workIds: List<String>,
    val sortOrder: Int,
)
