# 使用docker compose集成打包
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/xiaper-2.1.3-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Dspring.config.location=/xiaper/application.properties","-jar","/app.jar"]
EXPOSE 8000
