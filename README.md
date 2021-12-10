# Brest Java Course 2021 2

This is sample 'Human Resources' Spring boot web application.

## Requirements

* JDK 11
* Apache Maven

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
