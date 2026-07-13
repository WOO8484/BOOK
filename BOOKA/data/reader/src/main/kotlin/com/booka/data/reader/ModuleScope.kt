package com.booka.data.reader

/**
 * :data:reader 모듈 경계.
 *
 * ReaderRepository 구현체. 챕터·페이지 로딩, 위치·책갈피 저장(구현 PART 3).
 *
 * PART 1 범위: :domain의 Repository interface 실제 구현체는 여기 두지만(지시서 4.1),
 * 실 데이터 소스 연결은 아직 하지 않는다. PART 1 화면은 :core:testing의 Fake 구현으로 동작한다.
 */
object ModuleScope {
    const val MODULE_NAME = "data:reader"
}
