package com.tothenew.springbootjpa.repository;

import com.tothenew.springbootjpa.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByName(String name);

    List<Employee> findByNameStartsWith(String value);

    List<Employee> findByAgeBetween(int lower, int upper);
}
