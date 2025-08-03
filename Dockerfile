# 1. Tomcat 베이스 이미지 사용
FROM tomcat:9-jdk17

# 2. 기존 webapps 디렉토리 초기화 (선택적으로 필요)
RUN rm -rf /usr/local/tomcat/webapps/*

# 3. 빌드된 WAR 파일을 복사 (Gradle 기준)
COPY build/libs/*.war /usr/local/tomcat/webapps/ROOT.war


# 4. 톰캣이 기본 포트로 8080을 사용 중이므로, Docker에서도 노출
EXPOSE 8080