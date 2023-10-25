import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DBMS implements DBMSInterface {
    private StorageBackendInterface<String, Student> studentNumberIndex;
    private StorageBackendInterface<Integer, Student> overallScoreIndex;

    public DBMS() {
        this.studentNumberIndex = new MyStorageBackend<>(
            (s) -> 123456   // your perfect hash function here
            );
        this.overallScoreIndex = new MyStorageBackend<>(
            (s) -> 123456   // your perfect hash function here
            );
    }

    public void insertStudent(Student student) {
        studentNumberIndex.insert(student.getStudentNumber(), student);
        overallScoreIndex.insert(student.getOverallScore(), student);
    }

    public Student queryByStudentNumber(String studentNumber) {
        return studentNumberIndex.search(studentNumber).get(0);
    }

    public List<Student> queryByScore(int score) {
        return overallScoreIndex.search(score);
    }

    @Override
    public void deleteStudent(Student student) {
        this.studentNumberIndex.delete(student.getStudentNumber());
        this.overallScoreIndex.delete(student.getOverallScore());
    }
}

@FunctionalInterface
interface MyHashFunction<T> {
    int hash(T key);
}

class MyStorageBackend<T, U> implements StorageBackendInterface<T, U> {
    // your magical code here
}
