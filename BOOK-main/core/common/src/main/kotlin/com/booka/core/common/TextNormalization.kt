package com.booka.core.common

/** 메타 후보 제목 비교를 위한 정규화(지시서 8.3: 공백/대소문자/구두점/권차 처리, 원문 보존). */
object TextNormalization {
    private val punctuationRegex = Regex("[\\p{Punct}]")
    // 영문(vol. 3, volume 3)과 한국어(3권, 3화) 어순이 서로 반대이므로 두 패턴을 모두 처리한다.
    private val volumeRegex = Regex("(?i)\\b(vol\\.?|volume)\\s*\\d+\\b|\\d+\\s*(권|화)\\b")
    private val whitespaceRegex = Regex("\\s+")

    fun normalizeTitle(raw: String): String {
        var s = raw.lowercase()
        s = volumeRegex.replace(s, " ")
        s = punctuationRegex.replace(s, " ")
        s = whitespaceRegex.replace(s, " ").trim()
        return s
    }
}
