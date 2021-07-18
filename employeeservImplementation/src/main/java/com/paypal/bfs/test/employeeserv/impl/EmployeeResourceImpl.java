package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.CreateEmployeeRequest;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    private EmployeeService employeeService;

    public EmployeeResourceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {
        try {
            final Integer employeeIdentifier = Integer.valueOf(id);
            return new ResponseEntity<>(employeeService.findById(employeeIdentifier), HttpStatus.OK);
        } catch (EmployeeNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        } catch (NumberFormatException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee Id must be a numeric value", exception);
        }
    }

    @Override
    public ResponseEntity<Employee> createEmployee(@RequestBody @Validated CreateEmployeeRequest createEmployeeRequest) {
        final Employee newEmployee = employeeService.save(createEmployeeRequest);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }
}
