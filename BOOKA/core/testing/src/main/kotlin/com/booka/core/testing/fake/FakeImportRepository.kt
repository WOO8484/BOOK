package com.booka.core.testing.fake

import com.booka.core.common.BookaError
import com.booka.core.common.BookaResult
import com.booka.domain.repository.ImportProgressState
import com.booka.domain.repository.ImportRepository
import com.booka.domain.repository.ImportSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/** SAF/실제 파싱 없이 진행률·성공·실패 상태 전환만 시뮬레이션하는 Fake(지시서 7.1). */
@Singleton
class FakeImportRepository @Inject constructor() : ImportRepository {

    private val sessions = mutableMapOf<String, MutableStateFlow<ImportProgressState>>()

    override suspend fun startImport(displayNames: List<String>): BookaResult<ImportSession> {
        if (displayNames.isEmpty()) {
            return BookaResult.Failure(BookaError.Unexpected("가져올 파일을 선택하세요(샘플 시뮬레이션)."))
        }
        val sessionId = UUID.randomUUID().toString()
        sessions[sessionId] = MutableStateFlow(ImportProgressState.Idle)
        return BookaResult.Success(ImportSession(sessionId, displayNames))
    }

    override fun observeProgress(sessionId: String): StateFlow<ImportProgressState> =
        sessions.getOrPut(sessionId) { MutableStateFlow(ImportProgressState.Idle) }.asStateFlow()

    override suspend fun cancelImport(sessionId: String): BookaResult<Unit> {
        sessions[sessionId]?.value = ImportProgressState.Failed("사용자가 취소함(샘플 시뮬레이션).")
        return BookaResult.Success(Unit)
    }

    override suspend fun retryImport(sessionId: String): BookaResult<Unit> {
        val flow = sessions.getOrPut(sessionId) { MutableStateFlow(ImportProgressState.Idle) }
        val total = 6
        for (i in 1..total) {
            delay(150)
            flow.value = ImportProgressState.Scanning(i, total)
        }
        flow.value = ImportProgressState.Completed(importedCount = total - 1, skippedCount = 1)
        return BookaResult.Success(Unit)
    }
}
