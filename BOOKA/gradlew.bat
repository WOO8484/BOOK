@rem
@rem BOOKA gradlew.bat -- 표준 Gradle Wrapper 실행 스크립트 (Windows).
@rem gradle/wrapper/gradle-wrapper.jar (org.gradle.wrapper.GradleWrapperMain)와
@rem gradle/wrapper/gradle-wrapper.properties를 사용한다.
@rem gradle-wrapper.jar가 없다면 GRADLE_WRAPPER_JAR_안내.md를 참고해
@rem bootstrap-wrapper.yml 워크플로를 먼저 실행하세요.
@rem

@if "%DEBUG%"=="" @echo off
setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%"=="0" goto init

echo.
echo 오류: JAVA_HOME이 설정되어 있지 않고 PATH에서 java를 찾을 수 없습니다.
echo JAVA_HOME을 설정하거나 java를 PATH에 추가하세요.
goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo 오류: JAVA_HOME이 유효한 JDK/JRE 설치를 가리키지 않습니다: %JAVA_HOME%
goto fail

:init
set CLASSPATH=%APP_HOME%gradle\wrapper\gradle-wrapper.jar

if exist "%CLASSPATH%" goto execute

echo.
echo 오류: %CLASSPATH% 를 찾을 수 없습니다.
echo gradle-wrapper.jar가 아직 이 저장소에 커밋되지 않았습니다.
echo .github\workflows\bootstrap-wrapper.yml 을 GitHub Actions에서 한 번 실행하면
echo 공식 Gradle Wrapper 바이너리를 생성해 저장소에 커밋합니다.
echo 자세한 내용은 GRADLE_WRAPPER_JAR_안내.md 를 참고하세요.
goto fail

:execute
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

if %ERRORLEVEL% equ 0 goto mainEnd

:fail
exit /b 1

:mainEnd
endlocal
