package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.model.CreateEmployeeRequest;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeResourceImplIntegrationTest {

    @LocalServerPort
    int serverPort;

    @Autowired
    EmployeeRepository repository;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void shouldCreateAnEmployee() throws URISyntaxException {
        final CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setFirstName("BFS");
        request.setLastName("Developer");

        final String uri = "http://localhost:" + serverPort + "/v1/bfs/employees";
        final ResponseEntity<Employee> response = restTemplate.postForEntity(new URI(uri), request, Employee.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        final Employee employee = response.getBody();

        final List<EmployeeEntity> employees = repository.findAll();
        assertEquals(1, employees.size());
        assertEquals(Objects.requireNonNull(employee).getId(), employees.get(0).getId());
    }

    @Test
    public void shouldGetAnEmployeeByIdWhichIsPresent() throws URISyntaxException {
        final CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setFirstName("BFS");
        request.setLastName("Developer");
        final EmployeeEntity employeeEntity = repository.save(new EmployeeEntity(request));

        final String uri = "http://localhost:" + serverPort + "/v1/bfs/employees/" + employeeEntity.getId();
        final ResponseEntity<Employee> response = restTemplate.getForEntity(new URI(uri), Employee.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        final Employee employee = response.getBody();
        assertEquals(employeeEntity.getId(), Objects.requireNonNull(employee).getId());
        assertEquals(employeeEntity.toEmployee(), employee);
    }

    @Test
    public void shouldReturn404StatusCodeForEmployeeNotFound() throws URISyntaxException {
        final String uri = "http://localhost:" + serverPort + "/v1/bfs/employees/1";
        final ResponseEntity<String> response = restTemplate.getForEntity(new URI(uri), String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Employee with id 1 not found"));
    }

    @Test
    public void shouldReturnBadRequestForNonNumericValueToGetAnEmployee() throws URISyntaxException {
        final String uri = "http://localhost:" + serverPort + "/v1/bfs/employees/NonNumericEmployeeId";
        final ResponseEntity<String> response = restTemplate.getForEntity(new URI(uri), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Employee Id must be a numeric value"));
    }

    @Test
    public void shouldReturnBadRequestForInvalidCreateEmployeeRequest() throws URISyntaxException {
        final CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setFirstName("BFS");
        final String uri = "http://localhost:" + serverPort + "/v1/bfs/employees";
        final ResponseEntity<String> response = restTemplate.postForEntity(new URI(uri), request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        String expectedResponse = "{" +
                "  \"message\": \"lastName must not be null\"" +
                "}";
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void shouldReturnInternalServerErrorForAnyUnhandledException() throws URISyntaxException {

        final String uri = "http://localhost:" + serverPort + "/v1/bfs/employees";
        String requestString = "Any Random Text";
        final ResponseEntity<String> response = restTemplate.postForEntity(new URI(uri), requestString, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        String expectedResponse = "{" +
                "  \"message\": \"Internal Server Error\"" +
                "}";
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void shouldReturnBadRequestForInvalidDateFormat() throws URISyntaxException {

        final String uri = "http://localhost:" + serverPort + "/v1/bfs/employees";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestString = "{" +
                "\"first_name\": \"Sonali\"," +
                "\"last_name\": \"Hotwani\"," +
                "\"date_of_birth\": \"01-01-2020\"," +
                "\"address\": {" +
                "\"line1\": \"addr 1\"," +
                "\"line2\": \"addr 2\"," +
                "\"city\": \"city\"," +
                "\"state\": \"state\"," +
                "\"country\": \"country\"," +
                "\"zip_code\": \"123456\"" +
                "}" +
                "}";
        HttpEntity<String> request =
                new HttpEntity<>(requestString, headers);
        final ResponseEntity<String> response = restTemplate.postForEntity(new URI(uri), request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        String expectedResponse = "{  \"message\": \"Cannot deserialize value of type `java.util.Date` from String \"01-01-2020\": not a valid representation (error: Failed to parse Date value '01-01-2020': Cannot parse date \"01-01-2020\": not compatible with any of standard forms (\"yyyy-MM-dd'T'HH:mm:ss.SSSZ\", \"yyyy-MM-dd'T'HH:mm:ss.SSS\", \"EEE, dd MMM yyyy HH:mm:ss zzz\", \"yyyy-MM-dd\"))\n" +
                " at [Source: (PushbackInputStream); line: 1, column: 65] (through reference chain: com.paypal.bfs.test.employeeserv.api.model.CreateEmployeeRequest[\"date_of_birth\"])\"}";
        assertEquals(expectedResponse, response.getBody());
    }
}
