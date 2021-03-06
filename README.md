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

## Assignment
enhance the existing project and see you complete the following requirements:

- `employee.json` has only `name`, and `id` elements. Please add `date of birth` and `address` elements to the `Employee` resource. Address will have `line1`, `line2`, `city`, `state`, `country` and `zip_code` elements. `line2` is an optional element.
- Add one more operation in `EmployeeResource` to create an employee. `EmployeeResource` will have two operations, one to create, and another to retrieve the employee resource.
- Implement create and retrieve operations in `EmployeeResourceImpl.java`.
- Resource created using create endpoint should be retrieved using retrieve/get endpoint.
- Please use h2 in-memory database or any other in-memory database to persist the `Employee` resource. Dependency for h2 in-memory database is already added to the parent pom.
- Please make sure the validations are done for the requests.
- Response codes are as per rest guidelines.
- Error handling in case of failures.

## Assignment submission
Thank you very much for your time to take this test. Please upload this complete solution in Github and send us the link to `bfs-sor-interview@paypal.com`.

## Reasons

- Used different entity object for persisting data. The persistence layer can change depending on the database we choose. It is better to keep entity object and api layer separate.
- Currently, I have flattened address in entity. I did not feel the use of a separate address entity because there is a one-to-one relation between employee and address, as per use case a separate entity can also be created.

##References
- [jsonschema2pojo Plugin](https://github.com/joelittlejohn/jsonschema2pojo)
- [JsonSchemaPojo Properties](https://joelittlejohn.github.io/jsonschema2pojo/site/1.1.1/generate-mojo.html#dateType)
- [JsonSchemaPojo Reference](https://github.com/joelittlejohn/jsonschema2pojo/wiki/Reference)
- [Understanding Json Schema](http://json-schema.org/understanding-json-schema/structuring.html)
