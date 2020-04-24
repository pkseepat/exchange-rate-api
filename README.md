# Exchange Rates Microservice

## Introduction

Exchange Rates Microservice is a SpringBoot RESTful service. This service is to view
current GBP, USD, HKD exchange rates against Euro

Access swagger UI at http://localhost:8080/exchange_rate_rest_war_exploded/swagger-ui.html
## Set up

- Tomcat 9.0.34 is required to run the application
- Different modules are added to based on functionality
## How To Run

From the service directory, run with:

    mvn spring-boot:run or configure to run in IDE 

## How To Test

### Unit tests

From the service directory, run with:

    mvn test

### Integration tests


## How To Debug

In IntelliJ run configuration, add Tomcat server with settings:

    Configuration

    - Application server as Tomcat 9.0.34
    - Add build (eg: exploded war)


