import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {

        DBMS database = new DBMS();

        Student student1 = new Student("S1", "John Doe", 85, 90);
        Student student2 = new Student("S2", "Olivia Martinez", 40, 58);
        Student student3 = new Student("S3", "Ethan Kim", 65, 35);
        Student student4 = new Student("S4", "Sophia Patel", 15, 55);
        Student student5 = new Student("S5", "Jackson Brown", 0, 99);
        Student student6 = new Student("S6", "Omar Gosh", 30, 70);
        Student student7 = new Student("S7", "David Hackney", 76, 19);
        Student student8 = new Student("S8", "Maleka Banu", 69, 80);
        Student student9 = new Student("S9", "Khayaam Kabir", 100, 92);

        database.insertStudent(student1);
        database.insertStudent(student2);
        database.insertStudent(student3);
        database.insertStudent(student4);
        database.insertStudent(student5);
        database.insertStudent(student6);

        Student queryResult1 = database.queryByStudentNumber("S1");
        List<Student> queryResult2 = database.queryByScore(100);
        List<Student> queryResult3 = database.queryByScore(87);
        List<Student> queryResult4 = Arrays.asList(student3, student6);

        System.out.println(queryResult1);
        System.out.println(queryResult2);
        System.out.println(queryResult3);
        System.out.println(queryResult4);


    }
}
