# Calling Tariff Challenge

Project made with Java 8, Spring Boot 2.3, Hibernate, Maven, MySQL, H2, Lombok and TDD.

## Main URLs

| URL  | Tipo |  Description |
| ---- | ---- |---- |
| /ddd  | GET  | Return all DDDs.
| /call-plans  | GET  | Return all call plans.
| /call-tariff-maps  | GET  | Returns all tariff maps by origin and destination of a call
| /call-records/calculate | POST  | Simulates the calculation of a call.


### /call-records/calculate - POST

Example of a request body:

```json
{
    "duration": 20 ,
    "originCode":  51,
    "destinationCode":  11,
    "callPlanUuid": "17dd8d60-f794-4b7e-b5dd-6c379ee7e44b"
}
```

Description of the fields:

| Field  | Mandatory |  Description |
| ---- | ---- |---- |
| duration  | Yes  | Duration in minutes of the call
| originCode  | No  | DDD of the origin
| destinationCode  | No  | DDD of the destiny
| callPlanUuid  | No  | Uuid of the plan

## Structure

Brief description of the current structure of the project:

- `controllers`: controllers
- `services`: they are reusable in the project . They do communication with controllers, services, entities and repositories. Related with the domain;
- `repositories`: repositories;
- `entities`: entities;
- `helpers`: classes and methods that can be reused in the project and that aren't related with the domain;
- `factories`: adopted solution to create entities. It's not a permanent solution;
- `configs`: system configurations, like CORS, Spring Security and insertion of essential data;
- `strategies`: not used yet, but will be used to tasks like calculations.

## Tests

The services and entities of the application were developed with TDD. Only HomeController and CallRecordController were developed with TDD.


To run the Unit Tests, execute the following command:

```shell script
mvn clean test
```

To run the Unit Tests and Integration Tests, execute the following command:

```shell script
mvn clean verify
```

## Installation

**1 - Database**

This application uses MySQL database.

It's possible to configure the database information in the file `application.properties` or by inserting the database data when executing the project in the terminal with `-D` prefix.

**2 - Compilation and execution**

The first way to run the application is by using the `Spring Boot` plugin. In the project root folder, execute the following commands:

```shell script
mvn clean verify
mvn spring-boot:run
```
Another way to run the project, is by compiling and then running it. The commands are:

```shell script
mvn clean verify
java -jar target/calling_tariff-0.1.0-SNAPSHOT.jar
```
