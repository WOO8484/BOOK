package com.booka.core.model

/** SAF로 가져온 원본 파일에 대한 메타(파일 URI 자체를 UI에 노출하지 않기 위한 값 객체). */
data class BookFile(
    val id: String,
    val workId: String,
    val displayName: String,
    val format: FileFormat,
    val sizeBytes: Long,
    val importedAtEpochMillis: Long,
    val encoding: TextEncoding?,
)

enum class FileFormat { TXT, ZIP, CBZ, IMAGE_FOLDER }

enum class TextEncoding { UTF_8, EUC_KR, CP949, UNKNOWN }
