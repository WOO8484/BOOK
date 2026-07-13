# Gradle Wrapper 파일 상태 안내 (최종 방식)

## 실제로 존재하는 파일
- `gradlew` — 실행 권한 부여됨(rwxr-xr-x), **표준 공식 Gradle Wrapper 스크립트** 형식
  (`org.gradle.wrapper.GradleWrapperMain`을 호출하는 통상적인 구조).
- `gradlew.bat` — 동일한 표준 방식의 Windows 배치 스크립트.
- `gradle/wrapper/gradle-wrapper.properties` — Gradle 9.4.1 distributionUrl 고정.
- `.github/workflows/bootstrap-wrapper.yml` — 아래 참조.
- `.github/workflows/booka-part1-build.yml` — 본 빌드 워크플로.

## 여전히 존재하지 않는 파일
- `gradle/wrapper/gradle-wrapper.jar` (공식 바이너리)

## 왜 아직 없는가 (이미 두 방법 다 확인함, 재확인해도 결과는 같음)
1. **다운로드 불가**: dl.google.com / repo.maven.apache.org / services.gradle.org /
   maven.google.com 전부 `host_not_allowed`로 차단.
2. **직접 컴파일도 불가**: 이 작업 환경에는 `javac`가 없다. `java`는 있지만
   `openjdk-21-jre-headless`(JRE)뿐이고 JDK가 아니다. `jar`, `jlink` 등 다른 JDK 도구도 없다.
   apt로 JDK 설치도 네트워크 차단으로 불가능. (이전 제출에서 이 문제를 우회하려고 직접
   Java 소스를 작성해 컴파일하려 했으나, 애초에 컴파일러가 없어 그 방법도 성립하지 않았다.
   그래서 이번에는 그 우회용 커스텀 소스를 걷어내고, 아래의 더 깔끔한 방식으로 바꿨다.)

이 환경에서 공식 `gradle-wrapper.jar` 바이너리를 만들 방법은 없다. 몇 번을 다시 시도해도
같은 결과다(네트워크·JDK가 이 대화 환경에 아예 없다는 사실 자체는 바뀌지 않는다).

## 최종 방식: GitHub Actions가 1회만 공식 바이너리를 만들어 저장소에 커밋

**`.github/workflows/bootstrap-wrapper.yml`** (workflow_dispatch로 수동 실행하는 1회성 워크플로)
1. 체크아웃
2. JDK 17 설치 (GitHub Actions 러너에는 실제 JDK가 설치됨 — javac 포함)
3. `gradle wrapper --gradle-version 9.4.1 --distribution-type bin` 실행
   (GitHub 호스트 러너에는 Gradle CLI가 기본 설치되어 있어 이 명령이 동작한다.
   이건 Claude 작업 환경 얘기가 아니라 GitHub Actions 러너 얘기다 — 거기엔 네트워크도
   JDK도 Gradle CLI도 다 있다.)
4. 결과로 생성된 `gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.jar`,
   `gradle/wrapper/gradle-wrapper.properties`를 저장소에 **커밋 & 푸시**

이 워크플로를 GitHub 저장소의 Actions 탭에서 **한 번만** 수동 실행하면, 그 순간부터
`gradle/wrapper/gradle-wrapper.jar`는 진짜 공식 바이너리로 저장소에 실제로 존재하게 된다.

그 다음부터 **`.github/workflows/booka-part1-build.yml`은 wrapper를 만드는 어떤 과정도
거치지 않는다.** 첫 단계에서 `gradle-wrapper.jar`가 있는지만 확인하고(없으면 위 bootstrap
워크플로를 먼저 실행하라는 안내와 함께 즉시 실패), 있으면 바로
`chmod +x ./gradlew` → `./gradlew clean` → `./gradlew test` → `./gradlew lint` →
`./gradlew assembleDebug` 순서로만 진행한다.

## 정리
- 이 zip 안에는 `gradlew`(표준 형식, 실행권한 O), `gradlew.bat`(표준 형식),
  `gradle-wrapper.properties`가 실제로 있다.
- `gradle-wrapper.jar` 바이너리 자체는 여전히 없다 — 이 환경엔 그걸 만들 수단이 전혀 없다.
- 대신 GitHub Actions 안에서 **한 번만** 수동으로 돌리면 공식 바이너리가 저장소에 커밋되고,
  그 이후로는 메인 빌드 워크플로가 wrapper 생성 없이 `./gradlew`만 사용하도록 이미 구성해
  두었다. 사용자가 할 일은 "Actions 탭 → Bootstrap Official Gradle Wrapper → Run workflow"
  한 번 누르는 것뿐이다.
