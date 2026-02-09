FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

EXPOSE 8081

COPY ./target/msvc-worker-0.0.1-SNAPSHOT.jar msvc-worker.jar

ENTRYPOINT ["java", "-jar", "msvc-worker.jar"]