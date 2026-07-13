package com.booka.core.common

import org.junit.Assert.assertEquals
import org.junit.Test

class NaturalSortComparatorTest {

    @Test
    fun `숫자가 포함된 파일명은 자릿수가 아니라 값으로 정렬된다`() {
        val input = listOf("page10.jpg", "page2.jpg", "page1.jpg")
        val expected = listOf("page1.jpg", "page2.jpg", "page10.jpg")

        val actual = input.sortedWith(NaturalSortComparator)

        assertEquals(expected, actual)
    }

    @Test
    fun `숫자가 없는 문자열은 사전순으로 정렬된다`() {
        val input = listOf("banana", "apple", "cherry")
        val expected = listOf("apple", "banana", "cherry")

        assertEquals(expected, input.sortedWith(NaturalSortComparator))
    }

    @Test
    fun `접두사가 같고 자리수가 다른 숫자도 올바르게 정렬된다`() {
        val input = listOf("ch2_p9.jpg", "ch2_p10.jpg", "ch10_p1.jpg", "ch2_p1.jpg")
        val expected = listOf("ch2_p1.jpg", "ch2_p9.jpg", "ch2_p10.jpg", "ch10_p1.jpg")

        assertEquals(expected, input.sortedWith(NaturalSortComparator))
    }
}
