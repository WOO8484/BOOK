package com.booka.domain.repository

import com.booka.core.common.BookaResult
import kotlinx.coroutines.flow.Flow

data class ImportSession(
    val id: String,
    val fileNames: List<String>,
)

sealed interface ImportProgressState {
    data object Idle : ImportProgressState
    data class Scanning(val processed: Int, val total: Int) : ImportProgressState
    data class Completed(val importedCount: Int, val skippedCount: Int) : ImportProgressState
    data class Failed(val message: String) : ImportProgressState
}

/** 구현체는 :data:import에 둔다(PART 2, SAF 실제 처리 포함). */
interface ImportRepository {
    suspend fun startImport(displayNames: List<String>): BookaResult<ImportSession>
    fun observeProgress(sessionId: String): Flow<ImportProgressState>
    suspend fun cancelImport(sessionId: String): BookaResult<Unit>
    suspend fun retryImport(sessionId: String): BookaResult<Unit>
}
