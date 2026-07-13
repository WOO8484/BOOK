package com.booka.domain.repository

import com.booka.core.common.BookaResult
import com.booka.core.model.MetadataCandidate
import kotlinx.coroutines.flow.Flow

/** 구현체는 :data:metadata에 둔다(PART 2, Naver/Google Books/Open Library 포함). */
interface MetadataRepository {
    fun observeCandidates(sessionId: String, workDraftId: String): Flow<List<MetadataCandidate>>
    suspend fun analyzeLocal(sessionId: String, workDraftId: String): BookaResult<Unit>
    suspend fun searchOnline(sessionId: String, workDraftId: String, query: String): BookaResult<Unit>
    suspend fun applyCandidate(workDraftId: String, candidateId: String): BookaResult<Unit>
    suspend fun applyManualEdit(workDraftId: String, title: String, author: String?, synopsis: String?): BookaResult<Unit>
}
