package com.booka.core.testing.fake

import com.booka.core.common.BookaResult
import com.booka.core.model.MetadataCandidate
import com.booka.core.testing.sample.SampleData
import com.booka.domain.repository.MetadataRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeMetadataRepository @Inject constructor() : MetadataRepository {

    private val candidatesByDraft = mutableMapOf<String, MutableStateFlow<List<MetadataCandidate>>>()

    override fun observeCandidates(sessionId: String, workDraftId: String) =
        candidatesByDraft.getOrPut(workDraftId) { MutableStateFlow(emptyList()) }.asStateFlow()

    override suspend fun analyzeLocal(sessionId: String, workDraftId: String): BookaResult<Unit> {
        delay(300)
        val flow = candidatesByDraft.getOrPut(workDraftId) { MutableStateFlow(emptyList()) }
        flow.value = SampleData.metadataCandidatesFor(workDraftId).filter {
            it.source == com.booka.core.model.MetadataSource.LOCAL_ANALYSIS
        }
        return BookaResult.Success(Unit)
    }

    override suspend fun searchOnline(sessionId: String, workDraftId: String, query: String): BookaResult<Unit> {
        delay(400)
        val flow = candidatesByDraft.getOrPut(workDraftId) { MutableStateFlow(emptyList()) }
        flow.value = SampleData.metadataCandidatesFor(workDraftId)
        return BookaResult.Success(Unit)
    }

    override suspend fun applyCandidate(workDraftId: String, candidateId: String): BookaResult<Unit> =
        BookaResult.Success(Unit)

    override suspend fun applyManualEdit(
        workDraftId: String,
        title: String,
        author: String?,
        synopsis: String?,
    ): BookaResult<Unit> = BookaResult.Success(Unit)
}
