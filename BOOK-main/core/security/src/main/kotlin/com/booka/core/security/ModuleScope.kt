package com.booka.core.security

/**
 * :core:security 모듈 경계.
 *
 * Android Keystore 기반 API 키 암호화 저장, 민감 로그 차단을 담당한다(구현 PART 2).
 *
 * PART 1 범위: 지시서 7.1에 따라 이 모듈의 실제 구현체는 아직 만들지 않는다.
 * PART 1의 모든 화면은 :domain 인터페이스에 대한 Fake 구현(:core:testing)으로 동작하며,
 * 이 모듈이 비어 있다고 해서 화면 흐름이 끊기지 않는다.
 */
object ModuleScope {
    const val MODULE_NAME = "core:security"
}
