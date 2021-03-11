package com.tothenew.springbootjpa.service;

import com.tothenew.springbootjpa.entity.Employee;
import com.tothenew.springbootjpa.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(Integer id, Employee employee) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Employee with id " + id + " does not exist."
                ));
        emp.setName(employee.getName());
        emp.setAge(employee.getAge());
        emp.setLocation(employee.getLocation());
        employeeRepository.save(emp);
        return emp;
    }

    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    public String countEmployee() {
        return "Number of employees: " + employeeRepository.count();
    }

    public List<Employee> getEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }

    public List<Employee> getEmployeeNameStartsWith(String value) {
        return employeeRepository.findByNameStartsWith(value);
    }

    public List<Employee> getEmployeeAgeBetween28and32() {
        return employeeRepository.findByAgeBetween(28, 32);
    }

    public List<Employee> getPaginatedAndSortedEmployeesByAge() {
        PageRequest page = PageRequest.of(0, 3, Sort.Direction.DESC, "age");
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll(page).forEach(employees::add);
        return employees;
    }
}
