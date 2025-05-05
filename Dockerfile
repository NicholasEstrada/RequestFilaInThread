FROM openjdk:21-jdk-slim
VOLUME /tmp
WORKDIR /app
COPY target/crawler-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
