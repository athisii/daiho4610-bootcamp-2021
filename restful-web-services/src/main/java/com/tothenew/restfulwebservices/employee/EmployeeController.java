package com.tothenew.restfulwebservices.employee;

import com.tothenew.restfulwebservices.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequestMapping("/api/employees")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> retrieveAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee retrieveEmployee(@PathVariable int id) {
        Employee employee = employeeService.findOne(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with id " + id + " not found.");
        }
        return employee;
    }

    @PostMapping
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.save(employee);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEmployee.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable int id) {
        Employee employee = employeeService.deleteById(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with id " + id + " not found.");
        }

    }

    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        Employee returnEmployee = employeeService.update(id, employee);
        if (returnEmployee == null) {
            throw new EmployeeNotFoundException("Employee with id " + id + " not found.");
        }

    }
}
