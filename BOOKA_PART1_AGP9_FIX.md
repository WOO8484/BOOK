# BOOKA PART 1 AGP 9 수정 기록

- AGP 9.2.1의 내장 Kotlin 지원과 충돌하던 `org.jetbrains.kotlin.android` 플러그인을 루트와 23개 Android 모듈에서 제거했다.
- 기존 `kotlinOptions { jvmTarget = "17" }` 블록을 제거했다. Java 17 타깃은 각 모듈의 `compileOptions`에서 유지된다.
- 버전 카탈로그의 `kotlin-android` 플러그인 별칭도 제거했다.
- Room/Hilt용 KSP, Kotlin Serialization, Android Application/Library 플러그인은 유지했다.

실제 통과 여부는 GitHub Actions에서 `./gradlew --version`, `clean`, `test`, `lint`, `assembleDebug`를 실행해 확인한다.
