#FROM maven:3.6.0-jdk-11-slim AS build
#COPY ./src /usr/src/app/src
#COPY ./pom.xml /usr/src/app

#RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:17
EXPOSE 8080
#WORKDIR ./hr
#COPY /target/fmss-hr.jar app.jar
ADD hr/target/fmss-hr.jar fmss-hr.jar
ENTRYPOINT ["java", "-jar", "/fmss-hr.jar"]
