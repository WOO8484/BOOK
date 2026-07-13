package com.booka.core.common

/** 이미지/파일 자연 정렬(예: page2 < page10). :core:files의 실제 파일 나열에서 사용될 순수 로직. */
object NaturalSortComparator : Comparator<String> {
    override fun compare(a: String, b: String): Int {
        val ax = split(a)
        val bx = split(b)
        var i = 0
        while (i < ax.size && i < bx.size) {
            val x = ax[i]
            val y = bx[i]
            val bothNumeric = x.firstOrNull()?.isDigit() == true && y.firstOrNull()?.isDigit() == true
            val cmp = if (bothNumeric) {
                (x.toLongOrNull() ?: 0L).compareTo(y.toLongOrNull() ?: 0L)
            } else {
                x.compareTo(y)
            }
            if (cmp != 0) return cmp
            i++
        }
        return ax.size - bx.size
    }

    private fun split(s: String): List<String> =
        Regex("(\\d+|\\D+)").findAll(s).map { it.value }.toList()
}
