package com.tothenew.sprintbootjpa.repos;

import com.tothenew.sprintbootjpa.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("select firstName, lastName from Employee" +
            " where salary>(select avg(salary) from Employee)" +
            " order by age")
//    @Query("select firstName, lastName from Employee" +
//            " where salary>(select avg(salary) from Employee)" +
//            " order by salary desc")
    Object[] getEmployeeSalaryGreaterThanAverageSalary();

    @Transactional
    @Modifying
    @Query("update Employee set salary=:value where salary < (select avg(salary) from Employee)")
    void updateEmployeeSalaryLessThanAvgSalary(@Param("value") Double value);

    @Transactional
    @Modifying
    @Query("delete from Employee where salary=(select min(salary) from Employee)")
    void deleteEmployeeWithMinSalary();

    @Query(value = "select emp_Id, emp_First_Name, emp_Age from employee_Table where emp_Last_Name='Singh'", nativeQuery = true)
    Object[] findEmployeeLastNameWithSingh();

    @Transactional
    @Modifying
    @Query(value = "delete from employee_Table where emp_Age>:age", nativeQuery = true)
    void deleteEmployeeAgeGreaterThan45(@Param("age") Integer age);

}
