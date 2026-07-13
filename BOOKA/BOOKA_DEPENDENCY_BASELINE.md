# BOOKA 의존성 기준선 (BOOKA_DEPENDENCY_BASELINE.md)

지시서 v4.1 3.4 기준일 2026-07-12. PART 1에서 아래 버전으로 고정했으며,
이후 PART에서 공식 호환성 문제 없이는 임의 변경하지 않는다.

## 핵심 고정 버전 (지시서 3.4 그대로 적용)

| 항목 | 버전 |
|---|---|
| JDK (빌드 요구) | 17 |
| Gradle Wrapper | 9.4.1 |
| Android Gradle Plugin | 9.2.1 |
| Kotlin | 2.3.21 |
| KSP | 2.3.9 |
| compileSdk / targetSdk | 37 |
| minSdk | 26 |
| Compose BOM | 2026.06.01 |
| Room | 2.8.4 |
| Dagger/Hilt | 2.60.1 |
| AndroxX Hilt (hilt-navigation-compose) | 1.4.0 |
| Retrofit | 3.0.0 |
| Coil | 3.5.0 |

## PART 1에서 추가로 선정한 안정 버전 (지시서 3.4 마지막 항목에 따라 최초 1회 선정)

| 항목 | 버전 | 비고 |
|---|---|---|
| OkHttp | 4.13.0 | Retrofit 3.0.0 호환 |
| Kotlinx Coroutines | 1.10.1 | |
| Kotlinx Serialization | 1.8.0 | Navigation type-safe route에 사용 |
| Navigation Compose | 2.9.1 | kotlinx.serialization 기반 type-safe navigation |
| Lifecycle (runtime/viewmodel-compose/runtime-compose) | 2.9.0 | |
| DataStore Preferences | 1.1.2 | :core:datastore, PART 2~3에서 실사용 |
| core-ktx | 1.16.0 | |
| activity-compose | 1.10.1 | |
| appcompat | 1.7.0 | |
| security-crypto | 1.1.0 | :core:security, PART 2에서 실사용 |
| JUnit4 | 4.13.2 | |
| androidx.test.ext:junit | 1.2.1 | |
| espresso-core | 3.6.1 | |
| androidx.test:runner | 1.6.2 | Hilt 계측 테스트 러너용 |
| MockWebServer | 4.13.0 | PART 2 Provider mock 테스트용 |
| javax.inject | 1 | :domain의 @Inject 어노테이션 전용(JSR-330), Android 비의존 |

## 미검증 사실 고지

이 표의 버전 조합은 지시서 고정값과 흔히 함께 쓰이는 안정 버전을 근거로 선정했으나,
**이 환경에는 네트워크와 Gradle이 없어 실제로 `./gradlew` resolve를 통해 상호 호환성을
검증하지 못했다.** 네트워크가 있는 환경에서 최초 `./gradlew build` 실행 시
버전 충돌이 보고되면, 지시서 3.4 절차(동일 안정 채널 patch 버전 우선, 변경 전후 기록)에
따라 `gradle/libs.versions.toml`만 수정하면 된다.

## 동적 버전 금지 준수

`libs.versions.toml`과 모든 `build.gradle.kts`에서 `+`, `latest.release`, 버전 범위(`[1.0,2.0)`)를
사용하지 않았다. 모든 라이브러리는 정확한 버전 문자열로 고정되어 있다.
