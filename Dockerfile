# 1. 베이스 이미지 설정 (Eclipse Temurin JDK 17)
FROM eclipse-temurin:17-jdk-jammy

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 JAR 파일 복사
# build.gradle에서 생성된 JAR 파일의 경로와 이름을 지정합니다.
COPY build/libs/babsim-backend-0.0.1-SNAPSHOT.jar app.jar

# 4. 애플리케이션 포트 노출
EXPOSE 8080

# 5. 애플리케이션 실행
# 환경 변수는 Jenkins 또는 Docker run 명령어에서 주입됩니다.
ENTRYPOINT ["java", "-jar", "app.jar"]
