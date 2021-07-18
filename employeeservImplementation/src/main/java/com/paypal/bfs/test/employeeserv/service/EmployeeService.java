package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.model.CreateEmployeeRequest;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    private EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee save(CreateEmployeeRequest createEmployeeRequest) {
        final EmployeeEntity employeeEntity = new EmployeeEntity(createEmployeeRequest);
        final EmployeeEntity newEmployeeEntity = repository.save(employeeEntity);
        return newEmployeeEntity.toEmployee();
    }

    public Employee findById(Integer employeeId) {
        final Optional<EmployeeEntity> employee = repository.findById(employeeId);
        if (employee.isPresent()) {
            return employee.get().toEmployee();
        } else throw new EmployeeNotFoundException(employeeId);
    }
}
