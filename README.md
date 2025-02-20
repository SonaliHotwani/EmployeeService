# employeeserv

## Application Overview
employeeserv is a spring boot rest application which would provide the CRUD operations for `Employee` resource.

There are three modules in this application
- employeeservApi - This module contains the interface.
	- `v1/schema/employee.json` defines the employee resource.
	- `jsonschema2pojo-maven-plugin` is being used to create `Employee POJO` from json file.
	- `EmployeeResource.java` is the interface for CRUD operations on `Employee` resource.
		- GET `/v1/bfs/employees/{id}` endpoint is defined to fetch the resource.
- employeeservImplementation - This module contains the implementation for the rest endpoints.
	- `EmployeeResourceImpl.java` implements the `EmployeeResource` interface.
- employeeservFunctionalTests - This module would have the functional tests.

## How to run the application
- Please have Maven version `3.3.3` & Java 8 on your system.
- Use command `mvn clean install` to build the project.
- Use command `mvn spring-boot:run` from `employeeservImplementation` folder to run the project.
- Use postman or curl to access `http://localhost:8080/v1/bfs/employees/{id}` GET endpoint. It will return an Employee resource.
- For Creating an Employee `http://localhost:8080/v1/bfs/employees` POST endpoint with request body
```json
{
	"first_name": "Sonali",
	"last_name": "Hotwani",
	"date_of_birth": "1995-08-05", //format: yyyy-MM-dd
	"address": {
		"line1": "addr 1",
		"line2": "addr 2",
		"city": "city",
		"state": "state",
		"country": "country",
		"zip_code": "123456"
	}
}
```
