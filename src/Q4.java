//Create an Employee Class with instance variables (String) name, (Integer)age, (String)city
// and get the instance of the Class using constructor reference


@FunctionalInterface
interface EmployeeI {
    Employee getEmployee();
}

class Employee {
    private String name;
    private Integer age;
    private String city;
}

public class Q4 {
    public static void main(String[] args) {
        EmployeeI employeeI = Employee::new;
        Employee employee1 = employeeI.getEmployee();
    }
}
