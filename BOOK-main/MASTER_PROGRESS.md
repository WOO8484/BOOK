# MASTER_PROGRESS.md

## 현재 PART
PART 1 (프로젝트 기반·전체 UI·내비게이션)

## 기준 소스
기준 소스 없음 → 신규 구축 (지시서 3.3). 인계 패키지에는 실행지시서 v4.1 문서만 있었음.

## 이전 승인 상태
PART 1은 사용자가 "PART 1 시작"을 명시적으로 지시하여 착수함. 아직 사용자 승인 전.

## 완료·미완료
- 완료: 23개 모듈 구조, 전체 필수 화면 UI, Fake Repository/샘플 데이터, navigation,
  잔상 방지 하단 메뉴, 단위/계측 테스트 소스, 표준 형식 gradlew(실행권한 O)·gradlew.bat,
  GitHub Actions 워크플로 2종
  (bootstrap-wrapper.yml — 1회성, 공식 gradle-wrapper.jar 생성·커밋;
   booka-part1-build.yml — wrapper 생성 과정 없이 처음부터 ./gradlew clean/test/lint/
   assembleDebug만 실행 + APK/빌드 로그/test 결과/lint 결과/SHA256SUMS/소스 zip 업로드).
- 미완료:
  1. `gradle/wrapper/gradle-wrapper.jar` 바이너리 자체 — 네트워크도 JDK(javac)도 없어
     이 환경에서는 만들 수 없다. `bootstrap-wrapper.yml`을 GitHub Actions에서 1회
     수동 실행해야 저장소에 생긴다(유일하게 남은, 이 환경이 대신할 수 없는 수동 조작).
  2. GitHub Actions 실제 실행 및 성공 확인, 그로부터 나오는 실제 APK/로그/SHA-256.

## 최근 성공 빌드
없음. 이 환경에서 Gradle을 실행한 적이 없음.

## 최근 실패
빌드를 시도하지 않았으므로 "실패" 로그도 없음(시도 자체가 불가능했음).

## 주요 결정
- 패키지명 com.booka.app, minSdk 26, JVM target 17로 고정(지시서 3.1).
- 지시서 3.4 고정 버전표 그대로 적용 + PART1에서 최초 선정한 안정 버전은
  BOOKA_DEPENDENCY_BASELINE.md에 기록, 이후 임의 변경 금지.
- Fake Repository는 :core:testing에 두고, DI 바인딩은 :app/src/debug 전용 source set에 배치해
  release 그래프에서 완전히 제외되도록 설계(지시서 7.1, 3.6).
- Navigation은 route 문자열 대신 kotlinx.serialization 기반 type-safe BookaDestination 사용(지시서 3.5).

## 테스트 수
단위 테스트 8개(core:common 6, domain 2, core:model 2), 계측 테스트 2개(app, 미실행).

## APK 위치
없음 (생성되지 않음).

## ZIP 위치
BOOKA_Android_PART1.zip (대화에서 제공).

## SHA-256
소스 ZIP 자체의 체크섬은 대화 메시지로 안내한다(zip은 자기 자신의 체크섬을 담을 수 없어
별도 파일로 두지 않음). APK의 체크섬은 GitHub Actions 실행 후 Artifact로 생성된다.

## 다음 재개 지점
1. 저장소를 GitHub에 올린 뒤, Actions 탭에서 `bootstrap-wrapper.yml`을 **한 번만** 수동
   실행해 공식 gradle-wrapper.jar를 저장소에 커밋한다.
2. 그 다음 `booka-part1-build.yml`(push 또는 workflow_dispatch)이 wrapper 생성 없이
   `./gradlew clean/test/lint/assembleDebug`를 실제로 실행해 통과 여부를 확인한다.
3. 오류가 있으면 근본 원인을 수정하고 재실행한다(테스트 삭제·비활성화 금지).
4. 실제 통과가 확인되면 PART 1을 최종 승인하고, "PART 2 시작"을 지시한다.
5. 이 파일은 매 PART 종료 시 갱신하며, 새 세션은 보고서가 아니라 최근 승인된 ZIP과
   실제 소스를 기준으로 재개한다(지시서 14).
