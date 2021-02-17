// Q4. Write a program to sort Employee objects based on highest salary using Comparator.
//  Employee class{ Double Age; Double Salary; String Name

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Employee implements Comparator<Employee> {
    private String name;
    private Double age;
    private Double salary;

    public Employee() {

    }

    public Employee(String name, Double age, Double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public Double getAge() {
        return age;
    }

    public Double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }

    @Override
    public int compare(Employee employee1, Employee employee2) {
        return (int) (employee1.getSalary() - employee2.getSalary());
    }
}

public class Q4 {

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Saurabh", 34.0, 100.0));
        employees.add(new Employee("Athisii", 25.0, 50.0));
        employees.add(new Employee("Mercy", 20.0, 40.0));
        employees.add(new Employee("Rose", 30.0, 70.0));
        employees.sort(new Employee()); //Comparator
        employees.forEach(System.out::println);
    }

}
