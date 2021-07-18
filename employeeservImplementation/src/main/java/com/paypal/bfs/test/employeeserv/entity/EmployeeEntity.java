package com.paypal.bfs.test.employeeserv.entity;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.CreateEmployeeRequest;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    private String city;
    private String state;
    private String country;

    @Column(name = "zip_code")
    private String zipCode;

    public EmployeeEntity(CreateEmployeeRequest employeeRequest) {
        this.firstName = employeeRequest.getFirstName();
        this.lastName = employeeRequest.getLastName();
        this.dateOfBirth = employeeRequest.getDateOfBirth();
        final Address address = employeeRequest.getAddress();
        if (address != null) {
            this.addressLine1 = address.getLine1();
            this.addressLine2 = address.getLine2();
            this.city = address.getCity();
            this.state = address.getState();
            this.country = address.getCountry();
            this.zipCode = address.getZipCode();
        }
    }

    public Employee toEmployee() {
        final Employee employee = new Employee();
        employee.setId(this.getId());
        employee.setFirstName(this.firstName);
        employee.setLastName(this.lastName);
        employee.setDateOfBirth(this.dateOfBirth);
        if (Strings.isNotEmpty(addressLine1)) {
            final Address address = new Address();
            address.setLine1(this.addressLine1);
            address.setLine2(this.addressLine2);
            address.setCity(this.city);
            address.setState(this.state);
            address.setCountry(this.country);
            address.setZipCode(this.zipCode);
            employee.setAddress(address);
        }
        return employee;
    }
}
