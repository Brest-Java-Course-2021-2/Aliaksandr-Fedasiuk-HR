# Brest Java Course 2021 2

This is sample 'Human Resources' Spring boot web application.

## Requirements

* JDK 11
* Apache Maven

## Subprojects
* [angular-app](./angular-app) - a web app based on NodeJs + Angular, uses [rest-server](./rest-app)
* [dao](./dao) - a layer for working with one of the database instance from [db](./db)
* [db](./db) - diff configurations (schema + init scripts) to up `prod|test` instance of the database
* [documentation](./documentation) - a documentation
* [model](./model) - todo: db + ui  
* [rest-client](./rest-client) - spring beans based on `RestTemplate`
* [rest-server](./rest-server) - a spring boot app (REST API)
* [service](./service) - spring beans that use [dao](./dao) for direct connection to the database [db](./db)
* [web-app](./web-app) - a spring boot app (UI) that uses REST API through [rest-client](./rest-client) 

## Build application:
```
mvn clean install
```

To start Rest server:
```
java -jar ./rest-app/target/rest-app-1.0-SNAPSHOT.jar
```

## Available REST endpoints

### version
```
curl --request GET 'http://localhost:8088/version'
```
### department-dtos
```
curl --request GET 'http://localhost:8088/department-dtos'
```

Pretty print json:

```
curl --request GET 'http://localhost:8088/department-dtos' | json_pp
```

### departments

#### findAll

```
curl --request GET 'http://localhost:8088/departments' | json_pp
```

#### findById

```
curl --request GET 'http://localhost:8088/departments/1' | json_pp
```

### create

```
curl --request POST 'http://localhost:8088/departments' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
	"departmentName": "JAVA"
}'
```

## update

```
curl --request PUT 'http://localhost:8088/departments' \
--header 'Content-Type: application/json' \
--data-raw '{
   "departmentId": 4,
   "departmentName": "Java"
}'
```

## delete

```
curl --request DELETE 'http://localhost:8088/departments/4'
```
