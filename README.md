# Brest Java Course 2021 2

This is sample 'Human Resources' web application.

## Requirements

* JDK 11
* Apache Maven

## Build application:
```
mvn clean install
```

## Run integration tests:
```
mvn clean verify
```

## Run project information ( coverage, dependency, etc. ):
```
mvn site
mvn site:stage

open in browser: ${project}/target/staging/index.html
```

## Rest server

### Start Rest using Maven Jetty plugin

To start Rest using Maven Jetty plugin use:

```
cd department-management-rest-app
mvn jetty:run
```

## Available REST endpoints

### version

```
curl --request GET 'http://localhost:8088/version'
```

### department_dtos

```
curl --request GET 'http://localhost:8088/department_dtos'
```

Pretty print json:

```
curl --request GET 'http://localhost:8088/department_dtos' | json_pp
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
curl --request DELETE 'http://localhost:8088/departments/3'
```
