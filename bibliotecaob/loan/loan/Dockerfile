FROM openjdk:17-jdk-alpine

COPY target/loan.jar app.jar

EXPOSE 8070

ENTRYPOINT ["java","-jar","/app.jar"]
