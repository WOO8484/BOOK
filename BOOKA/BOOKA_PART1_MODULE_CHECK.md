# BOOKA_PART1_MODULE_CHECK.md

## 중요 고지

이 환경에는 Gradle이 설치되어 있지 않고 네트워크도 차단되어 있어
`./gradlew projects`를 실제로 실행할 수 없었다. 아래 내용은 **`./gradlew projects`의
실제 출력이 아니라, `settings.gradle.kts`와 실제 디렉터리 구조를 스크립트로 대조한
정적 검증 결과**다. 실제 명령 실행 결과는 네트워크·SDK가 있는 환경에서 별도로 확인해야 한다.

## settings.gradle.kts 선언 모듈 수

```
23 (grep -c "^include" settings.gradle.kts 결과)
```

## 선언 모듈 vs 실제 폴더 및 build.gradle.kts 존재 여부 (전수 대조)

| # | 모듈 | 폴더 존재 | build.gradle.kts | 비고 |
|---|---|---|---|---|
| 1 | :app | O | O | Application/MainActivity/최상위 NavHost |
| 2 | :core:common | O | O | Result/Dispatcher/유틸 |
| 3 | :core:model | O | O | Work/BookFile/Chapter/ComicPage/Metadata 등 |
| 4 | :core:designsystem | O | O | 색상/타이포/모양/테마 |
| 5 | :core:ui | O | O | 카드/로딩/빈상태/오류/다이얼로그/바텀시트 |
| 6 | :core:navigation | O | O | BookaDestination route 계약 |
| 7 | :core:database | O | O | PART 2 구현 예정 스캐폴드 |
| 8 | :core:datastore | O | O | PART 2~3 구현 예정 스캐폴드 |
| 9 | :core:files | O | O | PART 2 구현 예정 스캐폴드 |
| 10 | :core:network | O | O | PART 2 구현 예정 스캐폴드 |
| 11 | :core:security | O | O | PART 2 구현 예정 스캐폴드 |
| 12 | :core:testing | O | O | Fake Repository + 샘플 데이터 |
| 13 | :domain | O | O | Repository interface + UseCase 20개 |
| 14 | :data:library | O | O | PART 2 구현 예정 스캐폴드 |
| 15 | :data:import | O | O | PART 2 구현 예정 스캐폴드 |
| 16 | :data:metadata | O | O | PART 2 구현 예정 스캐폴드 |
| 17 | :data:reader | O | O | PART 3 구현 예정 스캐폴드 |
| 18 | :feature:library | O | O | 서재/검색/작품상세 |
| 19 | :feature:import | O | O | 가져오기/진행/파일명변경미리보기 |
| 20 | :feature:metadata | O | O | 메타분석/후보선택/직접수정 |
| 21 | :feature:reader-novel | O | O | 소설 뷰어 UI |
| 22 | :feature:reader-comic | O | O | 만화 뷰어 UI |
| 23 | :feature:settings | O | O | 설정 메인/책장/메타/뷰어/저장/기타 |

**결과: 23/23 일치. 중복 선언, 누락 폴더, 빈 모듈(build.gradle.kts 없는 이름뿐인 모듈) 없음.**

## 의존 방향 규칙 위반 검사 (지시서 4.2, 정적 grep 검사)

- `:domain/build.gradle.kts`에 `:data:*`, `:feature:*` 참조 없음 확인 (O)
- `:core:model/build.gradle.kts`에 Compose/Android UI 의존 없음 확인 (O)
- `feature:*` 6개 모듈의 build.gradle.kts에 서로 다른 `feature:*` 참조 없음 확인 (O)
- `:app`만 6개 feature 모듈 전체에 의존 (O, 최상위 조립 역할에 부합)
- Fake 구현(:core:testing)은 `:app`의 `debugImplementation`에만 연결, release 그래프에서 제외 (O)

## 전체 Kotlin 소스 파일 수

```
115개 (find . -name "*.kt" | wc -l)
```

## 한계

- `./gradlew dependencies`, `./gradlew projects` 실제 실행 결과 없음(도구 없음).
- 컴파일러가 없어 타입 오류·미해결 참조 등은 육안 검토로만 확인했으며, 100% 보증하지 않는다.
