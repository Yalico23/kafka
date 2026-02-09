FROM amazoncorretto:21-alpine-jdk

EXPOSE 8080

COPY ./target/msvc-news-0.0.1-SNAPSHOT.jar msvc-news.jar

ENTRYPOINT ["java", "-jar", "msvc-news.jar"]