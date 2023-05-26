FROM openjdk:17-alpine
ADD target/upprpo-jar-with-dependencies.jar upprpo
ENTRYPOINT ["java", "-jar", "upprpo"]
