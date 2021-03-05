package com.tothenew.restfulwebservices.employee;

import com.tothenew.restfulwebservices.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class EmployeeService {
    private static int employeeCount = 4;
    private static final List<Employee> employees = new ArrayList<>();

    static {
        employees.add(new Employee(1, "Saurabh", 35));
        employees.add(new Employee(2, "Athisii", 25));
        employees.add(new Employee(3, "Rose", 20));
        employees.add(new Employee(4, "Tom", 40));
    }

    public List<Employee> findAll() {
        return employees;
    }

    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(++employeeCount);
        }
        employees.add(employee);
        return employee;
    }

    public Employee findOne(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    public Employee deleteById(int id) {
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getId() == id) {
                iterator.remove();
                return employee;
            }
        }
        return null;
    }

    public Employee update(Integer id, Employee employee) {

        //If PathVariable(id) == Payload(Id) then do.
        if (id.equals(employee.getId())) {
            for (Employee emp : employees) {
                if (emp.getId().equals(id)) {
                    if (employee.getName() != null) {
                        emp.setName(employee.getName());
                    }
                    if (employee.getAge() != null) {
                        emp.setAge(employee.getAge());
                    }
                    return emp;
                }
            }
            return null;
        }
        throw new EmployeeNotFoundException("Mismatched PathVariable:id and Payload:id.");
    }
}
