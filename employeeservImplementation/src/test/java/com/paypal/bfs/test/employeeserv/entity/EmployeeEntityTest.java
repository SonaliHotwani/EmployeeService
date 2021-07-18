package com.paypal.bfs.test.employeeserv.entity;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.CreateEmployeeRequest;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EmployeeEntityTest {

    @Test
    public void shouldCreateEmployeeEntityWithIdFromCreateEmployeeRequest() {
        final CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setFirstName("Sonali");
        request.setLastName("Hotwani");
        final Date dateOfBirth = new Date();
        request.setDateOfBirth(dateOfBirth);
        final Address address = new Address();
        address.setLine1("Address Line 1");
        address.setLine2("Address Line 2");
        address.setCity("City");
        address.setState("State");
        address.setCountry("Country");
        address.setZipCode("123456");
        request.setAddress(address);
        final EmployeeEntity employeeEntity = new EmployeeEntity(request);

        assertEquals("Sonali", employeeEntity.getFirstName());
        assertEquals("Hotwani", employeeEntity.getLastName());
        assertEquals(dateOfBirth, employeeEntity.getDateOfBirth());
        assertEquals("Address Line 1", employeeEntity.getAddressLine1());
        assertEquals("Address Line 2", employeeEntity.getAddressLine2());
        assertEquals("City", employeeEntity.getCity());
        assertEquals("State", employeeEntity.getState());
        assertEquals("Country", employeeEntity.getCountry());
        assertEquals("123456", employeeEntity.getZipCode());
    }

    @Test
    public void shouldCreateAnEmployeeFromEmployeeEntity() {
        final Date dateOfBirth = new Date();
        final EmployeeEntity employeeEntity = new EmployeeEntity(
                1, "Sonali", "Hotwani", dateOfBirth, "addr 1", "addr 2", "city", "state", "country", "123456");
        final Employee employee = employeeEntity.toEmployee();
        assertNotNull(employee);
        assertEquals(Integer.valueOf(1), employee.getId());
        assertEquals("Sonali", employee.getFirstName());
        assertEquals("Hotwani", employee.getLastName());
        assertEquals(dateOfBirth, employee.getDateOfBirth());
        assertEquals("addr 1", employee.getAddress().getLine1());
        assertEquals("addr 2", employee.getAddress().getLine2());
        assertEquals("city", employee.getAddress().getCity());
        assertEquals("state", employee.getAddress().getState());
        assertEquals("country", employee.getAddress().getCountry());
        assertEquals("123456", employee.getAddress().getZipCode());

    }
}
