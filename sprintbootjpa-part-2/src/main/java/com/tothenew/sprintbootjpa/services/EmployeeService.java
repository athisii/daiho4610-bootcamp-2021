package com.tothenew.sprintbootjpa.services;

import com.tothenew.sprintbootjpa.entities.Employee;
import com.tothenew.sprintbootjpa.repos.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Object[] getEmployeeSalaryGreaterThanAverageSalary() {
        return employeeRepository.getEmployeeSalaryGreaterThanAverageSalary();
    }

    public void updateEmployeeSalaryLessThanAverageSalary(Double value) {
        employeeRepository.updateEmployeeSalaryLessThanAvgSalary(value);
    }

    public void deleteEmployeeWithMinSalary() {
        employeeRepository.deleteEmployeeWithMinSalary();
    }

    public Object[] getEmployeeLastNameWithSingh() {
        return employeeRepository.findEmployeeLastNameWithSingh();
    }

    public void deleteEmployeeAgeGreaterThan45(Integer age) {
        employeeRepository.deleteEmployeeAgeGreaterThan45(age);
    }
}
