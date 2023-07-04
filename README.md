# TOYOTA-32Bit Backend Project

> This project was created in order to follow up the vehicles produced in the factory,
> to follow up the errors on the vehicle and to indicate these errors to the user as a location on the picture.

Framework :

    * Spring Boot 
    * Spring Data 
    * Spring Web
    * JWT
    * Junit   
    * Mockito
    * Log4j
    * Easy-Random

Databases :

    * PostgreSql

Technologies :

    * Lombok
    * Docker

# SETUP

## Firstly

> Project generated by Spring Initializer

## Building With Maven

```
mvn clean install
```

## Building With Maven And Skip Tests

```
mvn clean install -DskipTests
```

## Profiles

This project has two profiles.

    * For local : "dev"
    * For docker : "test" (default for docker)

> Dev Profile: <br>
> For Developers. After the development you can want to test feature, but you can not send live product for test, for
> this reason you can use {dev} profile for your local

> Test Profile: <br>
> For Server or other machine that different local. You can want to run project on server or other machine that
> different local, you must use test profile. This profile was create for docker and docker-compose

## Must Run

If you are setting up the project for the first time and the project is up and running, please follow the step below
after the initial setup
> * There are not any user information, you can not enter system if you don't have any user information.
>* Same time There are noy any TerminalService information in database

> For this getting this information you must run {readyForProject.sql} <br><br>
> readyForProject.sql = \toyota_backend_project\tyt-conduct\src\main\resources\readyForProject.sql

## Postman Collection

> For test with postman you can use postmanCollection

> 32Bit PostmanCollection = \toyota_backend_project\32Bit.postman_collection.json




    