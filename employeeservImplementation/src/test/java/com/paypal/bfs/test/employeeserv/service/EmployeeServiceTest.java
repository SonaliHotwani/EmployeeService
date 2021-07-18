package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.CreateEmployeeRequest;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    @Test
    public void shouldReturnEmployeeById() {
        final Date dateOfBirth = new Date();
        final EmployeeEntity employeeEntity = new EmployeeEntity(1, "Sonali", "Hotwani", dateOfBirth, "addr 1", "addr 2", "city", "state", "country", "123456");
        final EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById(1)).thenReturn(Optional.of(employeeEntity));

        final EmployeeService service = new EmployeeService(repository);
        final Employee employee = service.findById(1);

        assertEquals(Integer.valueOf(1), employee.getId());
    }

    @Test
    public void shouldThrowEmployeeNotFoundException() {
        final EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById(1)).thenReturn(Optional.empty());

        final EmployeeService service = new EmployeeService(repository);
        try {
            service.findById(1);
        } catch (Exception exception) {
            assertTrue(exception instanceof EmployeeNotFoundException);
            assertEquals("Employee with id 1 not found", exception.getMessage());
        }
    }

    @Test
    public void shouldCreateEmployeeEntityFromRequestAndSave() throws ParseException {
        final Date dateOfBirth = new SimpleDateFormat("yyyy-mm-dd").parse("2020-01-01");
        final EmployeeEntity employeeEntity = new EmployeeEntity(1, "Sonali", "Hotwani", dateOfBirth, "addr 1", "addr 2", "city", "state", "country", "123456");
        final EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.save(any())).thenReturn(employeeEntity);

        final CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setFirstName("Sonali");
        request.setLastName("Hotwani");
        request.setDateOfBirth(dateOfBirth);
        final Address address = new Address();
        address.setLine1("addr 1");
        address.setLine2("addr 2");
        address.setCity("city");
        address.setState("state");
        address.setCountry("country");
        address.setZipCode("123456");
        request.setAddress(address);

        final EmployeeService service = new EmployeeService(repository);
        final Employee employee = service.save(request);

        assertEquals(Integer.valueOf(1), employee.getId());
    }

}
