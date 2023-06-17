# maven build alınır
MAINTAINER madnan_ozturk
FROM maven:3.9.1 AS build
COPY src /home/src
COPY pom.xml /home
RUN mvn -f /home/pom.xml clean package

# jdk eklenir, default profile verilir
# jar dosyası kopyalanır ve port belirlenir
FROM openjdk:17-slim
ENV PROFILE=test
COPY --from=build /home/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=${PROFILE}","-jar","app.jar"]