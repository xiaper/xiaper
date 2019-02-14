FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/xiaper-cloud-2.1.2-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8000
