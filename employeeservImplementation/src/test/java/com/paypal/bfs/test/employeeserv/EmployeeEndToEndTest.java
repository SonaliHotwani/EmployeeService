package com.paypal.bfs.test.employeeserv;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class EmployeeEndToEndTest {

    @LocalServerPort
    int serverPort;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void shouldCreateAnEmployee() throws URISyntaxException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        //Creating an Employee
        final String uri = "http://localhost:" + serverPort + "/v1/bfs/employees";
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestString = "{" +
                "\"first_name\": \"Sonali\"," +
                "\"last_name\": \"Hotwani\"," +
                "\"date_of_birth\": \"2020-01-01\"," +
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
        final ResponseEntity<String> postResponse = restTemplate.postForEntity(new URI(uri), request, String.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());

        JsonNode root = objectMapper.readTree(postResponse.getBody());
        final int id = root.path("id").asInt();

        //Fetching the employee which is created
        final ResponseEntity<String> getResponse = restTemplate.getForEntity(new URI(uri + "/" + id), String.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        String employeeString = getResponse.getBody();
        String expectedResponseBody = "{" +
                "\"id\":1," +
                "\"first_name\":\"Sonali\"," +
                "\"last_name\":\"Hotwani\"," +
                "\"date_of_birth\":\"2020-01-01\"," +
                "\"address\":{" +
                "\"line1\":\"addr 1\"," +
                "\"line2\":\"addr 2\"," +
                "\"city\":\"city\"," +
                "\"state\":\"state\"," +
                "\"country\":\"country\"," +
                "\"zip_code\":\"123456\"" +
                "}" +
                "}";
        assertEquals(expectedResponseBody, employeeString);
    }
}
