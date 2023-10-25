import java.util.List;

public interface DBMSInterface {

    // insertion interface for the backend
    public void insertStudent(Student student);

/*
     query interface for hash function 1 ==> queryByStudentNumber = querying using student number for the hash index
*/
    public Student queryByStudentNumber(String studentNumber);

/*
     query interface for hash function 2 ==> queryByScore = querying using  for overall score for the hash index
*/
    public List<Student> queryByScore(int score);

    // deletion interface for the backend
    public void deleteStudent(Student student);
}
