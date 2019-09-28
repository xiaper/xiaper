
FROM openjdk:8-jdk-alpine

LABEL maintainer="XIAPER Docker Maintainers <270580156@qq.com>"

ENV XIAPER_VERSION 2.1.3-SNAPSHOT

VOLUME /tmp

COPY application.properties /config/application.properties

COPY target/xiaper-2.1.3-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8000
