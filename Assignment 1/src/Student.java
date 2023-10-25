/**
 * Student class to facilitate student information as objects
 * @author Rifat Bin Masud
 */

public class Student {
    private String studentNumber;
    private String name;
    private int midtermScore;
    private int finalScore;

    /**
     * creates a student object with the following parameters
     * @param studentNumber ID of the student
     * @param name name of the student
     * @param midtermScore score of mid term
     * @param finalScore score of final
     */
    public Student(String studentNumber, String name, int midtermScore, int finalScore) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.midtermScore = midtermScore;
        this.finalScore = finalScore;
    }

    /**
     * Gets student number, the primary ID
     * @return A string representaiton for the student number field
     */
    public String getStudentNumber() {
        return studentNumber;
    }

    /**
     * Sets student number, the primary ID
     * @param studentNumber primary ID of the student
     */
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    /**
     * Gets a student name
     * @return A string representation of the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the student name
     * @param name Name of the student
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public int getMidtermScore() {
        return midtermScore;
    }

    /**
     *
     * @param midtermScore
     */
    public void setMidtermScore(int midtermScore) {
        this.midtermScore = midtermScore;
    }

    /**
     *
     * @return
     */
    public int getFinalScore() {
        return finalScore;
    }

    /**
     *
     * @param finalScore
     */
    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    /**
     *
     * @return
     */
    public int getOverallScore() {
        return midtermScore + finalScore;
    }


    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Student Number: " + studentNumber + ", Name: " + name + ", Overall Score: " + getOverallScore();
    }

    /**
     * Checks if two student Identities are same or not
     * @param obj Ideally a student object being passed
     * @return A boolean. True if two students are the same, else false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            Student student = (Student) obj;
            return studentNumber.equals(student.studentNumber) &&
                name.equals(student.name) &&
                midtermScore == student.midtermScore &&
                finalScore == student.finalScore;
        }
        return false;
    }
}
