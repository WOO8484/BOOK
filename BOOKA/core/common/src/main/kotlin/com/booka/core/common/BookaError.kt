package com.booka.core.common

/** 공통 예외 계층. 각 오류 코드는 사용자 메시지로 직접 매핑 가능해야 한다. */
sealed class BookaError(open val code: String, override val message: String) : Throwable(message) {
    data class NotFound(override val message: String) : BookaError("NOT_FOUND", message)
    data class Network(override val message: String) : BookaError("NETWORK", message)
    data class FileLimitExceeded(override val message: String) : BookaError("FILE_LIMIT", message)
    data class SecurityViolation(override val message: String) : BookaError("SECURITY", message)
    data class Unexpected(override val message: String) : BookaError("UNEXPECTED", message)
}
