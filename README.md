![logo](https://static.andersenlab.com/andersenlab/new-andersensite/logo-social.png)

# Andersen Test task
## Countries and cities
### Task statement
Create an enterprise-grade "Country and city list" application which allows the user to do following:
*  Browse though the paginated list of cities with logos
* Display unique cities names
* Get all cities by country name
* Search by city name
* Edit the city (both name and logos) - Editing the city should be only allowed for users with role EDITOR

### Technical stack

Java 17

* Core
    * Spring Boot 3.1.2
    * Lombok
    * Mapstruct
* DB
    * PostgreSQL
    * Liquibase
    * Hibernate
* Testing
    * Spock
    * Testcontainers
* Misc
    * Keycloak
    * MinIO S3
    * Docker
    * Swagger

## How to run
You need Docker to run application
1) Clone the repository

```shell
git clone https://github.com/skfl/countries-and-cities.git
```
2) Move to repo directory
```shell
cd countries-and-cities
```
3) Run docker-compose
```shell
docker-compose up
```
## Contributor
Edward Rogachkov

## Contacts 
* <a href="https://t.me/ssskyfall" style="text-decoration: none;color: white"> Telegram</a>
    
