# Gradle Wrapper 처리 안내

이 프로젝트는 `gradlew`, `gradlew.bat`, `gradle-wrapper.properties`를 포함합니다.

GitHub 업로드 또는 iPhone 압축 해제 과정에서 `gradle-wrapper.jar` 바이너리가 빠질 수 있으므로,
`.github/workflows/booka-part1-build.yml`이 빌드 시작 전에 Gradle 공식 저장소에서
Gradle 9.4.1용 `gradle-wrapper.jar`를 자동 복구하고 공식 Wrapper 검증 Action으로 무결성을 확인합니다.

따라서 별도의 Bootstrap 실행, 저장소 쓰기 권한, 자동 커밋은 필요하지 않습니다.
