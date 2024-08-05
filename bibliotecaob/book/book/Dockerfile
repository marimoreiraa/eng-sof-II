FROM openjdk:17-jdk-alpine

COPY target/book.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
