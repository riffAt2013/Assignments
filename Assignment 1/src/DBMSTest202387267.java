import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DBMSTest202387267 {
    DBMS dbms = new DBMS();
    Student student1 = new Student("S1", "John Doe", 85, 90);
    Student student2 = new Student("S2", "Olivia Martinez", 40, 58);
    Student student3 = new Student("S3", "Ethan Kim", 65, 35);
    Student student4 = new Student("S4", "Sophia Patel", 15, 55);
    Student student5 = new Student("S5", "Jackson Brown", 0, 99);
    Student student6 = new Student("S6", "Omar Gosh", 30, 70);
    Student student7 = new Student("S7", "David Hackney", 76, 19);
    Student student8 = new Student("S8", "Maleka Banu", 69, 80);
    Student student9 = new Student("S9", "Khayaam Kabir", 100, 92);

    void insertTestData(){
        Student[] students = new Student[] {
                student1,
                student2,
                student3,
                student4,
                student5,
                student6,
                student7,
                student8,
                student9
        };
        for (Student student : students) {
            dbms.insertStudent(student);
        }
    }

    /**
     * Checking queryByStudentNumber() using studentNumberIndex
     * Student number "S123456" doesn't exist in the database but "S9" does
     * The test passes if search returns null for queryOutput1 and student9 for queryOutput2
     */
    @Test
    public void testCase1() {
        insertTestData();
        Student queryOutput1 = dbms.queryByStudentNumber("S123456");
        Student queryOutput2 = dbms.queryByStudentNumber("S9");
        assertNull(queryOutput1);
        assertTrue(queryOutput2 == student9);
    }

    /**
     * Checking queryByScore() using overallScoreIndex
     * Student S3 and S6 both pulled off 100 in overall scores.
     * The test passes if search returns both of them using the overallScoreIndex
     */
    @Test
    public void testCase2() {
        insertTestData();
        List<Student> queryOutput = dbms.queryByScore(100);
        List<Student> expectedOutput = Arrays.asList(student3, student6);
        assertTrue(queryOutput.size() == expectedOutput.size() && queryOutput.containsAll(expectedOutput)
                && expectedOutput.containsAll(queryOutput));
    }

    /**
     * Checking delete using deleteStudent().
     * We'll insert all the students but delete student2 (Name:S2, Overall Score 98)
     * The test will pass if queryByScore and queryByStudentNumber both returns null
     */
    @Test
    public void testCase3() {
        insertTestData();
        dbms.deleteStudent(student2);
        Student queryOutput1 = dbms.queryByStudentNumber("S2");
        List<Student> queryOutput2 = dbms.queryByScore(98);
        assertNull(queryOutput1);
        assertTrue(queryOutput2.isEmpty());
    }
}
