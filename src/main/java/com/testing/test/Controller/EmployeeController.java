package com.testing.test.Controller;


import com.testing.test.Dao.EmployeeDAO;
import com.testing.test.Dao.Employees;
import com.testing.test.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeController {

    @GetMapping(path = "/helloworld", produces = "application/json")
    public List<Employee> getHelloWorld() {
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee(1, "santosh", "sahani", "santosh.sahani@gmail.com");
        Employee employee1 = new Employee(1, "santosh1", "sahani", "santosh.sahani@gmail.com");
        employees.add(employee);
        employees.add(employee);
        return employees;
    }


    @Autowired
    private EmployeeDAO employeeDao;

    @GetMapping(
            path = "/employees",
            produces = "application/json")

    public Employees getEmployees()
    {

        return employeeDao
                .getAllEmployees();
    }

    @PostMapping(
            path = "/employees",
            consumes = "application/json",
            produces = "application/json")

    public ResponseEntity<Object> addEmployee(
            @RequestBody Employee employee)
    {

        // Creating an ID of an employee
        // from the number of employees
        Integer id
                = employeeDao
                .getAllEmployees()
                .getEmployeeList()
                .size()
                + 1;

        employee.setId(id);

        employeeDao
                .addEmployee(employee);

        URI location
                = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(
                        employee.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }
}
