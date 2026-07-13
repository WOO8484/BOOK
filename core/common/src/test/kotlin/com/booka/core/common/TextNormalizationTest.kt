package com.booka.core.common

import org.junit.Assert.assertEquals
import org.junit.Test

class TextNormalizationTest {

    @Test
    fun `공백과 대소문자 차이는 정규화 후 동일하게 취급된다`() {
        val a = TextNormalization.normalizeTitle("The Great   Library")
        val b = TextNormalization.normalizeTitle("the great library")

        assertEquals(a, b)
    }

    @Test
    fun `권차 표현은 정규화 시 제거된다`() {
        val withVolume = TextNormalization.normalizeTitle("철갑기병 크로니클 3권")
        val withoutVolume = TextNormalization.normalizeTitle("철갑기병 크로니클")

        assertEquals(withoutVolume.trim(), withVolume.trim())
    }

    @Test
    fun `구두점은 제거되지만 원문 문자는 유지된다`() {
        val result = TextNormalization.normalizeTitle("달빛, 아래: 도서관!")

        assertEquals("달빛 아래 도서관", result)
    }
}
