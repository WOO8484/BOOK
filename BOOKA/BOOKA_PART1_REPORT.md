# BOOKA_PART1_REPORT.md

## [PART 1 상태: 소스·CI 워크플로 준비 완료 / 최종 통과는 GitHub Actions 실행 후 확정]

이 보고서는 두 가지를 구분해서 말한다.
- **소스 준비**와 **GitHub Actions 워크플로 준비**는 이 시점 기준으로 완료됐다.
- **PART 1 최종 통과**는 GitHub Actions가 실제로 clean/test/lint/assembleDebug를 성공시키고
  APK·로그·SHA-256이 Artifacts로 실제 생성된 뒤에만 선언한다. 아직 그 실행이 없었으므로
  최종 통과라고 말하지 않는다.

## [기준 소스 판정]
인계 패키지에는 실행지시서 v4.1 문서 1개만 존재했고, 빌드 가능한 기존 소스·프로토타입은
제공되지 않았다. 지시서 3.3에 따라 **"기준 소스 없음 → 신규 구축"**으로 판정하고,
`com.booka.app` 패키지의 신규 23개 모듈 Android 프로젝트를 처음부터 작성했다.

## [실행 환경의 근본적 제약 — 먼저 밝힘]
이 작업을 수행한 컴퓨터 실행 도구에는 다음이 없다.
- Android SDK, Gradle, adb, aapt2 (전혀 설치되어 있지 않음)
- 외부 네트워크 접근 (dl.google.com, repo.maven.apache.org, services.gradle.org,
  maven.google.com 등 전부 `host_not_allowed`로 차단, apt도 차단)
- JDK 자체가 없음. `java` 명령은 되지만 실제로는 `openjdk-21-jre-headless`(JRE)만 설치되어
  있고 `javac`(컴파일러)는 없다(`update-alternatives --list javac` → "no alternatives for
  javac"). 지시서 고정값인 JDK 17도 아니고, JRE라서 애초에 아무것도 컴파일할 수 없다.

그 결과 `./gradlew clean/test/lint/assembleDebug/projects/dependencies`를
**단 한 번도 실제로 실행하지 못했다.** 이는 도구·인프라의 한계이며, 실행했다고 보고하지 않는다.

## [구현 기능]
- 23개 멀티모듈 Gradle 프로젝트 구조 전체 (settings.gradle.kts, 각 모듈 build.gradle.kts)
- `gradle/libs.versions.toml` 전체 버전 고정(지시서 3.4 값 그대로 + PART1 최초 선정 안정 버전)
- `:core:model` 전체 도메인 모델(Work/BookFile/Chapter/ComicPage/Metadata/MetadataCandidate/
  ReadingProgress/Shelf/Bookmark 등)
- `:core:common` (BookaResult, BookaError, DispatcherProvider, TextNormalization, NaturalSortComparator)
- `:core:designsystem` (색상, 타이포, Shape, Spacing, 라이트/다크 테마)
- `:core:ui` (WorkCard, 로딩/빈상태/오류상태, 확인 다이얼로그, 바텀시트, 샘플데이터 배지, 미구현 안내 다이얼로그)
- `:core:navigation` (kotlinx.serialization 기반 type-safe BookaDestination 20종)
- `:domain` (Repository interface 4종 + UseCase 20개)
- `:core:testing` (결정론적 샘플 데이터 + Fake Repository 4종, 지시서 7.1 요구사항)
- `:feature:library` (서재 메인/검색/필터·정렬 바텀시트/작품 상세)
- `:feature:import` (가져오기 메인/진행 화면/파일명 변경 미리보기)
- `:feature:metadata` (메타 분석/후보 선택(8.3 점수 규칙 반영)/직접 수정)
- `:feature:reader-novel`, `:feature:reader-comic` (소설·만화 뷰어 UI, 챕터/페이지 이동, 몰입 모드)
- `:feature:settings` (설정 메인 + 5개 하위 화면, 데이터 초기화 이중 확인 포함)
- `:app` (Application, MainActivity, 최상위 NavHost, 하단 메뉴 잔상·중복 방지 구현, 뷰어 화면 하단 메뉴 숨김)
- `:app/src/debug` 전용 Hilt Fake DI 바인딩(release 그래프에서 제외)
- `:core:database`, `:core:datastore`, `:core:files`, `:core:network`, `:core:security`,
  `:data:library`, `:data:import`, `:data:metadata`, `:data:reader`: 지시서 7.1에 따라
  PART 1에서는 실제 구현체를 만들지 않고, 모듈 경계와 gradle 설정만 갖춘 스캐폴드로 존재
  (빈 폴더 아님, PART 2·3에서 실제 구현 예정임을 KDoc으로 명시)
- 단위 테스트 8개(core:common 6개, domain 2개, core:model 2개) 및 계측 테스트 2개(app, 실행은 미실시)

## [미완료 기능]
- **실제 빌드 실행/검증** (도구 없음 — 위 제약 참조)
- **APK 생성** (Gradle 없이는 불가능)
- 실제 Room/SAF/API/파일 엔진 (PART 1 범위에서 의도적으로 제외, 지시서 7.1과 일치)
- 에뮬레이터/실기기 테스트 전체(잔상 반복 테스트 실제 실행, 회전, 접근성, 큰 글자) — 기기 없음

## [모듈 구조 검증]
`BOOKA_PART1_MODULE_CHECK.md` 참조. 23/23 모듈 선언·폴더·build.gradle.kts 일치를
스크립트로 전수 대조했다(단, `./gradlew projects` 실제 출력은 아님).

## [빌드 결과]
**실행 안 됨.** clean/test/lint/assembleDebug 전부 미실행. 사유는 상단 "실행 환경의 근본적
제약" 참조. 네트워크·Android SDK·Gradle이 있는 환경(로컬 PC, Android Studio, GitHub Actions 등)에서
`BOOKA_Android_PART1.zip`을 풀고 아래를 실행하면 된다.

```bash
gradle wrapper --gradle-version 9.4.1 --distribution-type bin   # wrapper jar 최초 1회 생성
./gradlew clean
./gradlew test
./gradlew lint
./gradlew assembleDebug
```

## [테스트 결과]
**실행 안 됨.** 소스에는 JUnit 단위 테스트 8개, Compose 계측 테스트 2개가 존재하지만
실제로 실행한 결과가 아니다("실행했다"고 보고하지 않는다는 지시서 원칙 준수).

## [실기 확인 항목]
전부 **미실시** (에뮬레이터/실기기 없음). 지시서 6.2에 따라 통과로 위조하지 않고 미실시로 기록한다.

## [산출물]
실제로 존재하는 파일만 기록한다(지시서 15).

| 파일 | 상태 |
|---|---|
| BOOKA_Android_PART1.zip | 존재 (전체 소스 트리) |
| BOOKA_PART1_REPORT.md | 존재 (본 문서) |
| BOOKA_PART1_MODULE_CHECK.md | 존재 |
| BOOKA_PART1_SCREEN_CHECKLIST.md | 존재 |
| BOOKA_DEPENDENCY_BASELINE.md | 존재 |
| MASTER_PROGRESS.md | 존재 |
| GRADLE_WRAPPER_JAR_안내.md | 존재 (최종 방식 반영) |
| BOOKA_PART1_SHA256SUMS.txt | 존재 (소스 ZIP 체크섬만 — APK 없음) |
| BOOKA_PART1-debug.apk | **없음** — Gradle/SDK 없이 생성 불가 |
| BOOKA_PART1_build_logs.zip | **없음** — 실행한 빌드가 없어 로그 자체가 존재하지 않음 |
| .github/workflows/booka-part1-build.yml | 존재 (wrapper 생성 과정 없이 처음부터 `./gradlew`만 사용하도록 수정) |
| .github/workflows/bootstrap-wrapper.yml | 존재 (신규 추가 — 아래 참조) |
| gradlew | 존재, 실행권한 O (rwxr-xr-x), 표준 형식(`org.gradle.wrapper.GradleWrapperMain` 참조) |
| gradlew.bat | 존재, 표준 형식 |
| gradle/wrapper/gradle-wrapper.properties | 존재 (Gradle 9.4.1 고정, 변경 없음) |
| gradle/wrapper/gradle-wrapper.jar | **여전히 없음** — 이유와 해결책은 아래 참조 |

## [gradle-wrapper.jar에 대한 최종 답]

세 번째로 같은 질문을 주셨는데, 매번 다시 확인해도 사실 자체는 같다: **이 작업 환경에는
공식 gradle-wrapper.jar를 만들 어떤 수단도 없다.**
- 다운로드: dl.google.com/repo.maven.apache.org/services.gradle.org/maven.google.com
  전부 재확인, 전부 차단.
- 직접 컴파일: 지난 제출에서 시도했던 "직접 Java 소스를 작성해 컴파일"도 애초에 성립하지
  않았다 — 이 환경에는 `javac`가 없다(JRE만 설치됨, JDK 아님). 그래서 이번에는 그 우회용
  커스텀 소스(`WrapperLauncher.java`)를 완전히 걷어냈다. 어설픈 자체 구현보다, 처음부터
  공식 바이너리를 쓰는 게 맞다고 판단했다.

**그래서 방식을 바꿨다.** `gradlew`/`gradlew.bat`을 이제 표준 Gradle Wrapper 형식
(`org.gradle.wrapper.GradleWrapperMain` 참조)으로 다시 썼고, 공식 `gradle-wrapper.jar`
바이너리 자체는 **GitHub Actions 안에서 한 번만** 만들어 저장소에 커밋하도록
`.github/workflows/bootstrap-wrapper.yml`을 새로 추가했다. GitHub Actions 러너에는
(이 환경과 달리) 네트워크도, JDK도, `gradle` CLI도 실제로 있기 때문에
`gradle wrapper --gradle-version 9.4.1 --distribution-type bin`이 거기서는 정상 동작한다.

**사용 순서**
1. 저장소를 GitHub에 올린다.
2. Actions 탭 → **Bootstrap Official Gradle Wrapper (1회성)** → Run workflow (한 번만).
   이 워크플로가 공식 `gradlew`, `gradlew.bat`, `gradle-wrapper.jar`를 만들어 저장소에
   자동으로 커밋·푸시한다.
3. 그 다음부터 `.github/workflows/booka-part1-build.yml`은 wrapper를 만드는 과정을
   전혀 거치지 않는다 — 첫 단계에서 `gradle-wrapper.jar`가 있는지만 확인하고(없으면 위
   1회성 워크플로를 먼저 실행하라는 안내와 함께 즉시 실패), 있으면 바로
   `chmod +x ./gradlew` → `./gradlew clean` → `test` → `lint` → `assembleDebug` 순서로만
   진행한다. 말씀하신 "Wrapper 생성 없이 바로 ./gradlew 실행"을 이 시점부터는 그대로
   충족한다.

이 zip 자체에는 여전히 `gradle-wrapper.jar` 바이너리가 없다는 사실은 그대로 남는다.
Actions 탭에서 버튼 한 번 누르는 것까지는 이 환경이 대신할 수 없다.

## [BOOKA_PART1_build_logs.zip 문제 — 원인과 수정]

지적하신 증상들(zip 안에 같은 이름 zip 중복, 로그 없음, test/lint 결과 빈 폴더, Gradle 버전
미기록, BUILD SUCCESSFUL 확인 불가)을 워크플로 코드를 다시 읽으며 원인을 찾았다. 실제로
이 환경에서 실행해 재현해 본 것은 아니고(GitHub Actions 자체가 없음), 코드 검토로 찾은
버그다. 이 시점부터는 그 버그를 고친 새 버전이다.

**버그 1 — zip 안에 같은 이름 zip이 중복되는 이유**
`actions/upload-artifact`는 업로드된 내용을 다운로드할 때 항상 zip으로 한 번 더 감싼다.
그런데 이전 워크플로는 로그들을 미리 직접 `BOOKA_PART1_build_logs.zip`으로 압축한 뒤 그
zip 파일 자체를 업로드했다. 그 결과 다운로드하면 "BOOKA_PART1_build_logs.zip"(GitHub가
감싼 바깥 zip) 안에 내가 만든 "BOOKA_PART1_build_logs.zip"(진짜 로그가 든 안쪽 zip)이
또 들어있는 이중 구조가 됐다. **고침**: 로그 파일들을 더 이상 미리 압축하지 않고, 개별
파일 그대로(`clean.log`, `test.log`, `lint.log`, `assembleDebug.log`,
`gradle-version.log`, `environment.log`, `SUMMARY.txt`) 업로드하도록 바꿨다.

**버그 2 — 로그가 비어 보이고 test/lint 결과가 없던 이유 (추정)**
`./gradlew clean 2>&1 | tee 로그파일` 처럼 파이프로 연결하면, 셸이 파이프의 마지막 명령
(`tee`)의 종료 코드만으로 그 스텝의 성공/실패를 판단한다. `tee`는 거의 항상 성공하므로,
`gradlew` 자체가 실패해도(예: 컴파일 오류, 의존성 해석 실패 등) 워크플로는 "성공"으로
착각하고 다음 단계(test → lint → assembleDebug)로 조용히 넘어간다. 그러면 test/lint가
제대로 실행되지 않았을 가능성이 높고, 그 결과 test-results·lint-results가 비었을
것이다. **고침**: 각 `./gradlew` 단계에서 `set -o pipefail` + `${PIPESTATUS[0]}`로
gradlew 자신의 실제 종료 코드를 확인해 실패 시 그 단계에서 바로 멈추도록 했다. 이제
실패하면 실패한 그 로그에 진짜 오류 메시지가 남고, 이후 무의미한 빈 결과물을 만들지 않는다.

**버그 3 — Gradle 버전 미기록**
`echo "Gradle: $(./gradlew --version | grep 'Gradle ' || true)"` 한 줄로 처리했는데,
`./gradlew --version` 자체가 실패하면 `|| true`가 오류를 조용히 삼켜서 아무 정보도 안
남았을 것이다. **고침**: `./gradlew --version`을 `clean`보다 먼저 실행하는 별도 단계로
분리했다(wrapper가 애초에 정상 기동하는지 가장 먼저 확인하는 용도 겸용). 실패하면 이
단계 자체가 실패하며 원인이 `gradle-version.log`에 그대로 남는다.

**버그 4 — 소스 ZIP 자기 자신 포함 가능성**
`zip -r artifacts/BOOKA_Android_PART1.zip . -x "artifacts/*" ...`는 저장소 전체를 훑으며
동시에 `artifacts/` 안에 결과물을 쓰는데, `zip`의 `-x` 패턴은 기본적으로 `*`가 `/`를
넘어 매칭되지 않아 `artifacts/test-results/...`처럼 한 단계 더 깊은 경로는 제외되지 않고
같이 담겼을 가능성이 있다. **고침**: 저장소를 직접 훑는 대신 `git archive --format=zip
--output=artifacts/BOOKA_Android_PART1.zip HEAD`로 바꿨다. git에 커밋된 파일만 정확히
담기므로 build 산출물, artifacts 폴더, .git 등이 애초에 대상이 되지 않는다.

**추가**: "전체 GitHub Actions 실행 로그"를 위해 `collect-full-run-log`라는 두 번째 job을
추가했다. `build` job이 끝난 뒤 `gh run view --log`로 이 실행 자체의 원본 콘솔 로그
전체를 받아 `BOOKA_PART1_full_actions_log` Artifact로 올린다. 이건 내가 직접 만든
`clean.log` 등과는 별개로, GitHub Actions가 기록한 그대로의 전체 로그다.

이 수정이 실제로 문제를 100% 없앤다고 장담하지는 않는다 — 여전히 이 환경에서 실행해
확인할 방법이 없기 때문이다. 다만 지적하신 증상 각각에 대해 코드상 명확한 원인을 찾아
고쳤고, 이제는 실패하더라도 "빈 결과물"이 아니라 "실패 원인이 담긴 로그"가 남도록
구조를 바꿨다는 점이 이전과 다르다.


1. **gradlew 실행 권한**: `-rwxr-xr-x` 확인됨(로컬 `chmod +x` 후 zip에 포함, 왕복 테스트로
   권한 보존 확인).
2. **Gradle Wrapper 버전과 AGP·Kotlin·JDK 17 호환성**: `gradle-wrapper.properties`는
   Gradle 9.4.1 유지. AGP 9.2.1 / Kotlin 2.3.21 / JDK 17 조합은
   `BOOKA_DEPENDENCY_BASELINE.md`에 지시서 3.4 고정값 그대로 기록. **이 환경에는 Gradle
   자체가 없어 실제 resolve 검증은 여전히 못 했다** — 이전과 동일한 한계, 달라진 것 없음.
3. **GitHub Actions가 처음부터 `./gradlew` 사용**: `bootstrap-wrapper.yml` 실행 이후로는
   충족. 메인 워크플로에서 `gradle wrapper`도, 이전의 `javac` 컴파일 단계도 모두 제거했다.
4. **23개 모듈 전체 포함**: 재검증 완료, 23/23 일치(누락 없음).
5. **settings.gradle.kts와 실제 모듈 폴더 일치**: 재검증 완료, 23개 전부 `build.gradle.kts`
   존재 확인.

## [GitHub Actions 워크플로 — 최종 구성 (2개 파일)]

### `.github/workflows/bootstrap-wrapper.yml` (1회성, workflow_dispatch만)
체크아웃 → JDK 17 설치 → `gradle wrapper --gradle-version 9.4.1 --distribution-type bin`
→ 생성 결과 확인 → `gradlew`/`gradlew.bat`/`gradle-wrapper.jar`/`gradle-wrapper.properties`
커밋·푸시.

### `.github/workflows/booka-part1-build.yml` (push/PR/workflow_dispatch, job 2개)

**`build` job**
1. 체크아웃(`fetch-depth: 0`, git archive용), JDK 17 설치, Android SDK 설치
2. `gradle-wrapper.jar` 존재 확인 (없으면 즉시 실패 + bootstrap 워크플로 실행 안내)
3. `chmod +x ./gradlew` 및 실행권한 확인
4. `./gradlew --version` 단독 실행 → `gradle-version.log` (wrapper 최초 기동 겸 최우선 검증)
5. `./gradlew clean` → `clean.log` (PIPESTATUS로 실패 시 즉시 중단)
6. `./gradlew test` → `test.log`
7. `./gradlew lint` → `lint.log`
8. `./gradlew assembleDebug` → `assembleDebug.log`
9. `environment.log` 기록 (JDK/OS/시각)
10. `app/build/outputs/apk/debug/app-debug.apk` 확인 후 `BOOKA_PART1-debug.apk`로 복사
    (없으면 조용히 넘어가지 않고 `apk-copy-notice.txt`에 원인 기록)
11. test/lint 결과 수집(파일 개수를 `SUMMARY.txt`에 기록해 빈 폴더인지 바로 확인 가능)
12. `git archive`로 `BOOKA_Android_PART1.zip` 재생성 (커밋된 파일만 정확히 포함)
13. `BOOKA_PART1_SHA256SUMS.txt` (APK가 없으면 그 사실을 명시하고 존재하는 파일만 계산)
14. 5종 Artifact 업로드 — **로그 파일은 더 이상 미리 zip으로 묶지 않고 원본 그대로 업로드**
    (`BOOKA_PART1_build_logs`에 clean/test/lint/assembleDebug/gradle-version/environment
    로그 + SUMMARY.txt 전부 개별 파일로 포함, `BOOKA_PART1-debug-apk`,
    `BOOKA_PART1_test_results`, `BOOKA_PART1_lint_results`, `BOOKA_PART1_SHA256SUMS`,
    `BOOKA_Android_PART1_source`)

**`collect-full-run-log` job** (build 이후, always 실행)
`gh run view --log`로 이 실행 자체의 GitHub Actions 원본 콘솔 로그 전체를 받아
`BOOKA_PART1_full_actions_log` Artifact로 업로드한다.

두 워크플로 파일 모두 `python3 -c "import yaml; yaml.safe_load(open('...'))"`로 파싱
검증했다(bootstrap 6 step / build 23 step + collect-full-run-log 2 step). **다만 GitHub
Actions 러너에서 실제로 실행해 성공한 적은 아직 없다.** 이 환경에는 GitHub Actions 자체가
없어 실행할 수 없기 때문이다.

## [지금 이 시점의 정확한 상태]
- 소스 준비: **완료** (23개 모듈, 24개 화면, 지시서 7.1 Fake 구현 전부 포함)
- Gradle Wrapper 스크립트(gradlew/gradlew.bat, 표준 형식): **완료** (실행권한 포함)
- gradle-wrapper.jar 바이너리: **여전히 없음** — `bootstrap-wrapper.yml`을 1회 수동
  실행해야 저장소에 생긴다(이 환경이 대신할 수 없는 유일한 수동 조작).
- CI 워크플로 준비: **완료** (2개 파일, 문법 검증 완료, 메인 워크플로는 wrapper 생성 없이
  `./gradlew`만 사용, 요청하신 6종 아티팩트 업로드 구성 반영)
- 실제 GitHub Actions 실행 결과(성공/실패, 실제 APK, 실제 로그, 실제 SHA-256): **아직 없음**
- 따라서 "클로드 환경에 APK가 없다"는 이유로 이 제출을 실패로 보고하지 않되,
  "GitHub Actions가 실제 성공했다"고도 보고하지 않는다. 그 확인은 실행 후에만 가능하다.

## [SHA-256]
이 환경이 만든 소스 ZIP 자체의 체크섬은 대화 메시지로 별도 안내한다(zip은 자기 자신의
체크섬을 담을 수 없음). **APK의 진짜 SHA-256은 `bootstrap-wrapper.yml` → 메인 워크플로
실행 후 `BOOKA_PART1_SHA256SUMS.txt` Artifact에서 확인해야 한다.**

## [알려진 문제]
1. 이 환경에서 빌드를 한 번도 실행하지 못해, 소스가 실제로 컴파일되는지 100% 보증할 수 없다.
   육안 검토와 정적 대조만 수행했다.
2. JDK 21 환경에서 작성했으나 지시서 고정값은 JDK 17이다. 빌드 시 JDK 17로 맞출 것을 권장한다.
3. `gradle-wrapper.jar` 바이너리가 없다(`GRADLE_WRAPPER_JAR_안내.md` 참조).
4. Naver/Google Books/Open Library, Room, SAF는 지시서 설계대로 PART 2에서 실제 연동된다.

## [해당 PART 통과 여부]
**최종 통과 아님 — 실행 대기 상태.** 지시서 7의 PART 1 통과 기준 중 "화면 이동/Placeholder 0/
미연결 버튼 0/멀티모듈 규칙"은 소스·정적 검토 기준으로 충족했다고 판단한다.
"`clean`,`test`,`lint`,`assembleDebug` 성공"과 "설치 가능한 PART 1 APK"는 소스와 CI 워크플로가
모두 준비됐다는 의미에서 **실행 준비 완료**로 보고하되, GitHub Actions 실행 결과가 아직 없으므로
**통과로 확정하지 않는다.** Claude 실행 환경에 APK가 없다는 사실 자체를 실패로 보고하지는
않지만, "성공했다"고도 말하지 않는다 — 그 판정은 워크플로 실행 결과로만 내려진다.

## [다음 PART 진입 가능 여부]
사용자가 네트워크·SDK가 있는 환경에서 위 4개 명령을 실행해 실제 통과를 확인한 뒤 승인하는 것을
권장한다. 그 확인 없이 PART 2로 진행하면, PART 2도 동일한 사유로 실제 빌드 검증 없이 진행된다.

사용자 승인 대기
