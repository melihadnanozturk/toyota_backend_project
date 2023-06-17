FROM maven:3.9.1 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17-slim
COPY --from=build /home/app/target/*.jar /user/local/lib/demo.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/user/local/lib/demo.jar"]