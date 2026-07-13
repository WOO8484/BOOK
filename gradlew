#!/bin/sh

#
# BOOKA gradlew — 표준 Gradle Wrapper 실행 스크립트.
# gradle/wrapper/gradle-wrapper.jar (org.gradle.wrapper.GradleWrapperMain, 공식 Gradle Wrapper)와
# gradle/wrapper/gradle-wrapper.properties(distributionUrl=Gradle 9.4.1)를 사용해
# 지정된 Gradle 배포판을 (없으면) 내려받아 실제 빌드를 실행한다.
#
# gradle-wrapper.jar가 아직 커밋되지 않았다면 이 스크립트는 아래에서 즉시 실패하며
# 이유와 해결 방법을 출력한다. GRADLE_WRAPPER_JAR_안내.md 참조.
#

die () {
    echo
    echo "$*"
    echo
    exit 1
}

# APP_HOME 계산: 심볼릭 링크를 따라가며 이 스크립트 자신의 실제 위치를 찾는다.
PRG="$0"
while [ -h "$PRG" ] ; do
    ls=$(ls -ld "$PRG")
    link=$(expr "$ls" : '.*-> \(.*\)$')
    if expr "$link" : '/.*' >/dev/null; then
        PRG="$link"
    else
        PRG=$(dirname "$PRG")"/$link"
    fi
done
APP_HOME=$(cd "$(dirname "$PRG")" >/dev/null && pwd -P) || exit 1

APP_NAME="Gradle"
DEFAULT_JVM_OPTS="-Xmx64m -Xms64m"

MAX_FD="maximum"

warn () {
    echo "$*"
}

# OS 판별 (Cygwin/MSYS/Darwin 대응)
cygwin=false
msys=false
darwin=false
nonstop=false
case "$(uname)" in
  CYGWIN* ) cygwin=true ;;
  Darwin* ) darwin=true ;;
  MSYS* | MINGW* ) msys=true ;;
  NONSTOP* ) nonstop=true ;;
esac

CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$CLASSPATH" ]; then
    die "오류: $CLASSPATH 를 찾을 수 없습니다.
gradle-wrapper.jar가 아직 이 저장소에 커밋되지 않았습니다.
.github/workflows/bootstrap-wrapper.yml 을 GitHub Actions에서 한 번 실행(workflow_dispatch)하면
공식 Gradle Wrapper 바이너리를 생성해 저장소에 커밋합니다. 자세한 내용은
GRADLE_WRAPPER_JAR_안내.md 를 참고하세요."
fi

# JAVA_HOME 확인 및 java 실행 파일 결정
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "오류: JAVA_HOME이 유효한 JDK/JRE 설치를 가리키지 않습니다: $JAVA_HOME
JAVA_HOME 환경변수를 올바르게 설정하세요."
    fi
else
    JAVACMD="java"
    command -v java >/dev/null 2>&1 || die "오류: JAVA_HOME이 설정되어 있지 않고 PATH에서 'java'를 찾을 수 없습니다.
JAVA_HOME을 설정하거나 java를 PATH에 추가하세요."
fi

# Cygwin/MSYS 환경에서는 경로를 윈도우 형식으로 변환
if $cygwin || $msys ; then
    APP_HOME=$(cygpath --path --mixed "$APP_HOME")
    CLASSPATH=$(cygpath --path --mixed "$CLASSPATH")
fi

exec "$JAVACMD" $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS \
    "-Dorg.gradle.appname=$APP_NAME" \
    -classpath "$CLASSPATH" \
    org.gradle.wrapper.GradleWrapperMain \
    "$@"
