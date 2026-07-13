package com.booka.data.import

/**
 * :data:import 모듈 경계.
 *
 * ImportRepository 구현체. SAF와 파일 파서 조합, 가져오기 orchestration(구현 PART 2).
 *
 * PART 1 범위: :domain의 Repository interface 실제 구현체는 여기 두지만(지시서 4.1),
 * 실 데이터 소스 연결은 아직 하지 않는다. PART 1 화면은 :core:testing의 Fake 구현으로 동작한다.
 */
object ModuleScope {
    const val MODULE_NAME = "data:import"
}
