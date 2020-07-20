FROM maven:3.6.3-jdk-11
WORKDIR /app
COPY src .
COPY pom.xml .
RUN mvn package


FROM openjdk:11-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
