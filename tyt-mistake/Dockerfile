FROM maven:3.9.1 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17-slim
## burada jar yolunu değiştirince bakalım ne olacak ** /user/local/lib/demo.jar
COPY --from=build /home/app/target/*.jar /adnan/demo.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/adnan/demo.jar"]