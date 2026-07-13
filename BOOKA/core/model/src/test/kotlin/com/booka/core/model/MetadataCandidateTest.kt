package com.booka.core.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MetadataCandidateTest {

    private fun candidate(score: Int) = MetadataCandidate(
        id = "c1", workId = "w1", source = MetadataSource.NAVER, title = "제목", author = null,
        coverUrl = null, isbn = null, publishedYear = null, synopsis = null,
        score = score, scoreBreakdown = emptyList(), mergedFromSources = emptyList(),
    )

    @Test
    fun `80점 이상은 자동 적용 대상이다`() {
        assertTrue(candidate(80).isAutoApplyEligible)
        assertTrue(candidate(92).isAutoApplyEligible)
    }

    @Test
    fun `80점 미만은 자동 적용 대상이 아니다`() {
        assertFalse(candidate(79).isAutoApplyEligible)
        assertFalse(candidate(0).isAutoApplyEligible)
    }
}
