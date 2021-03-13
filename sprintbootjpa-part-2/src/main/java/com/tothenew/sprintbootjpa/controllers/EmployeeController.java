package com.tothenew.sprintbootjpa.controllers;

import com.tothenew.sprintbootjpa.entities.Employee;
import com.tothenew.sprintbootjpa.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/employee")
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> retrieveAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/jpql/1")
    public Object[] retrieveEmployeeSalaryGreaterThanAverageSalary() {
        return employeeService.getEmployeeSalaryGreaterThanAverageSalary();
    }

    @PutMapping("/jpql/2")
    public void updateEmployeeSalaryLessThanAverageSalary(@RequestParam Double value) {
        employeeService.updateEmployeeSalaryLessThanAverageSalary(value);
    }

    @DeleteMapping("/jpql/3")
    public void deleteEmployeeWithMinSalary() {
        employeeService.deleteEmployeeWithMinSalary();
    }

    @GetMapping("/nsl/1")
    public Object[] retrieveEmployeeLastNameWithSingh() {
        return employeeService.getEmployeeLastNameWithSingh();
    }

    @DeleteMapping("/nsl/2")
    public void deleteEmployeeAgeGreaterThan45(@RequestParam Integer age) {
        employeeService.deleteEmployeeAgeGreaterThan45(age);
    }
}
