package com.tothenew.springbootjpa.controller;

import com.tothenew.springbootjpa.entity.Employee;
import com.tothenew.springbootjpa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/employee")
@RestController
public class EmployController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public List<Employee> retrieveAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/count")
    public String countEmployee() {
        return employeeService.countEmployee();
    }

    @GetMapping("/sort")
    public List<Employee> retrievePaginatedAndSortedEmployees() {
        return employeeService.getPaginatedAndSortedEmployeesByAge();
    }

    @GetMapping("/findbyname")
    public List<Employee> retrieveEmployeeByName(@RequestParam String name) {
        return employeeService.getEmployeeByName(name);
    }

    @GetMapping("/startswith")
    public List<Employee> retrieveEmployeeNameStartsWith(@RequestParam String value) {
        return employeeService.getEmployeeNameStartsWith(value);
    }

    @GetMapping("/agebetween28and32")
    public List<Employee> retrieveEmployeeAgeBetween28And32() {
        return employeeService.getEmployeeAgeBetween28and32();
    }
}
