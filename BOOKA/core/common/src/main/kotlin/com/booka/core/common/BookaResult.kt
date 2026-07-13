package com.booka.core.common

/** 앱 전역 공통 결과 타입. 오류를 삼키지 않고 상태로 변환하기 위해 사용한다(지시서 5). */
sealed interface BookaResult<out T> {
    data class Success<T>(val data: T) : BookaResult<T>
    data class Failure(val error: BookaError) : BookaResult<Nothing>
    data object Loading : BookaResult<Nothing>
}

inline fun <T, R> BookaResult<T>.map(transform: (T) -> R): BookaResult<R> = when (this) {
    is BookaResult.Success -> BookaResult.Success(transform(data))
    is BookaResult.Failure -> this
    is BookaResult.Loading -> this
}

fun <T> BookaResult<T>.getOrNull(): T? = (this as? BookaResult.Success)?.data
