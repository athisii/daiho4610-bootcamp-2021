// Q5. Write a program to sort the Student objects based on Score , if the score are same then sort on First Name.
//   Class Student{ String Name; Double Score; Double Age


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Student implements Comparable<Student> {
    private String name;
    private Double score, age;

    public Student(String name, Double score, Double age) {
        this.name = name;
        this.score = score;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Double getScore() {
        return score;
    }

    public Double getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", age=" + age +
                '}';
    }


    @Override
    public int compareTo(Student other) {
        var val = this.getScore() - other.getScore();
        if (val == 0.0) {
            return this.getName().compareToIgnoreCase(other.getName());
        }
        return val > 0 ? 1 : -1;
    }

//     @Override
//    public int compareTo(Student student) {
//        return Comparator.comparing(Student::getScore)
//                .thenComparing(Student::getName)
//                .compare(this, student);
//    }
}


public class Q5 {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Suarabh", 10.0, 34.0));
        students.add(new Student("Jack", 9.0, 24.0));
        students.add(new Student("Mark", 10.0, 23.0));
        students.add(new Student("Ekhe", 8.8, 30.0));
        students.add(new Student("Athisii", 9.0, 25.0));
        Collections.sort(students);
        students.forEach(System.out::println);

    }

}
