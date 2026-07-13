# PART 1 import 예약어 namespace 수정

- `:data:import` 모듈 경로는 유지
- namespace/package: `com.booka.data.import` → `com.booka.data.importer`
- `:feature:import` 모듈 경로는 유지
- namespace/package: `com.booka.feature.import` → `com.booka.feature.importer`
- 관련 Kotlin import 문과 실제 소스 디렉터리 경로도 함께 변경

Java/Kotlin 예약어 `import`를 패키지 세그먼트로 사용해 발생하는 Android namespace 검증 오류를 방지한다.
