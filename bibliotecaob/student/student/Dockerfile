FROM openjdk:17-jdk-alpine

COPY target/student.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java","-jar","/app.jar"]
