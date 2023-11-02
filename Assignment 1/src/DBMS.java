import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * The `DBMS` Database Management System class is responsible for managing student records
 * and provides operations to insert, query, and delete student information using two different
 * storage indexes based on student number and overall score.
 * implements the `DBMSInterface` to define its core functionality.
 *
 * @author Rifat Bin Masud
 */
public class DBMS implements DBMSInterface {
    private StorageBackendInterface<String, Student> studentNumberIndex;
    private StorageBackendInterface<Integer, Student> overallScoreIndex;

    /**
     * Constructs a new instance of the `DBMS` class, initializing storage indexes for student
     * information based on two criteria.
     * student number and overall score.
     */
    public DBMS() {
        this.studentNumberIndex = new MyStorageBackend<>();
        this.overallScoreIndex = new MyStorageBackend<>();
    }

    /**
     * Inserts a student's information into the DBMS, indexing it by student number and overall score.
     *
     * @param student The `Student` object which is to be inserted into the database.
     */
    public void insertStudent(Student student) {
        studentNumberIndex.insert(String.valueOf(student.getStudentNumber().hashCode()), student);
        overallScoreIndex.insert(student.getOverallScore(), student);
    }

    /**
     * Retrieves a student's information by their student number.
     *
     * @param studentNumber The student number for which to retrieve the associated student information.
     * @return The `Student` object corresponding to the provided student number, or `null` if not found.
     */
    public Student queryByStudentNumber(String studentNumber) {
        int hashCode = studentNumber.hashCode();
        List<Student> results = studentNumberIndex.search(String.valueOf(hashCode));
        if (!results.isEmpty()) {
            return results.get(0);
        } else {
            return null;
        }
    }

    /**
     * Retrieves a list of students with their overall score matching the given score.
     *
     * @param score The overall score to search for.
     * @return A list of `Student` objects with an overall score matching the provided score.
     */
    public List<Student> queryByScore(int score) {
        return overallScoreIndex.search(score);
    }

/**
 * Deletes a student's information from the DBMS using their student number and overall score
 * as keys. Overriding deleteStudent from DBMSInterface.
 *
 * @param student The `Student` object to be deleted from the database.
 */
    @Override
    public void deleteStudent(Student student) {
        int hashCode = student.getStudentNumber().hashCode();
        this.studentNumberIndex.delete(String.valueOf(hashCode));
        this.overallScoreIndex.delete(student.getOverallScore());
    }
}

@FunctionalInterface
interface MyHashFunction<T> {
    int hash(T key);
}

/**
 * The `MyStorageBackend` class is used to provide storage and retrieval of key-value
 * pairs using a Skip List data structure. Implementing `StorageBackendInterface`,
 *
 * @param <T> The type of keys used for indexing.
 * @param <U> The type of values associated with the keys.
 */
class MyStorageBackend<T extends Comparable<T>, U> implements StorageBackendInterface<T, U> {
    SkipList<T,U> skipList=new SkipList(3, 0.5f);

    /**
     * Inserts a key-value pair into the storage backend using a Skip List.
     *
     * @param key  The key to index the value.
     * @param item The value associated with the key.
     */
    @Override
    public void insert(T key, U item) {
        skipList.insertElement(key, item);
    }

    /**
     * Searches for a value associated with a given key in the storage backend using a Skip List.
     *
     * @param key The key to search for in the storage backend.
     * @return A list of values associated with the provided key, or an empty list if not found.
     */
    @Override
    public List<U> search(T key) {
        return skipList.Search(key);
    }

    /**
     * Deletes a key-value pair from the storage backend using a Skip List.
     *
     * @param key The key to delete from the storage backend.
     */
    @Override
    public void delete(T key) {
        skipList.deleteElement(key);
    }
}

/**
        The `Node` class represents a node in a data structure, which is typically used in a Skip List.
        * Each node stores a key-value pair, where the key is a comparable element and the value is an associated
        * data item.
        *
        * @param <T> The type of keys used for indexing and comparisons.
        * @param <U> The type of values associated with the keys.
        */
final class Node<T extends Comparable<T>, U> {
    T key;
    U student;
    Node[] forward;

    /**
     * Constructs a new `Node` with the given key and level.
     *
     * @param key    The key associated with this node.
     * @param level  The level of the node, which determines its position in the data structure.
     */
    Node(T key, int level)
    {
        this.key = key;
        // Allocate memory to forward
        forward = new Node[level + 1];
    }
};

/**
 * The `SkipList` class represents a data structure that provides fast search, insertion, and deletion of elements
 * using a hierarchical structure with multiple levels of linked lists.
 *
 * @param <T> The type of keys used for indexing and comparisons extending comparable
 * @param <U> The type of values associated with the keys.
 */
class SkipList<T extends Comparable<T>,U> {
    int MAXLVL;
    int level;
    float P;
    Node<T, U> header;

    /**
     * Constructs a new instance of the `SkipList` class with the specified maximum level and P-value.
     *
     * @param MAXLVL The maximum level of the skip list.
     * @param P      The probability for a node to have a pointer at each level.
     */
    SkipList(int MAXLVL, float P)
    {
        this.MAXLVL = MAXLVL;
        this.P = P;
        level = 0;
        header = new Node(-1, MAXLVL);
    }

    /**
     * Generates a random level for a node based on the given probability P.
     *
     * @return The randomly generated level for a node.
     */
    int randomLevel()
    {
        float r = (float)Math.random();
        int lvl = 0;
        while (r < P && lvl < MAXLVL) {
            lvl++;
            r = (float)Math.random();
        }
        return lvl;
    }

    /**
     * Creates a new node with the specified key and level.
     *
     * @param key   The key associated with the node.
     * @param level The level of the node.
     * @return A new node with the specified key and level.
     */
    Node createNode(T key, int level)
    {
        Node n = new Node(key, level);
        return n;
    }

    /**
     * Inserts a key-value pair into the skip list.
     *
     * @param key   The key to insert.
     * @param value The value associated with the key.
     */
    void insertElement(T key, U value)
    {
        Node current = header;
        Node update[] = new Node[MAXLVL + 1];

        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].key.compareTo(key)<0)
                current = current.forward[i];
            update[i] = current;
        }

        current = current.forward[0];

        if (current == null || current.key != key || Search((T)current.key) != value ) {
            int rlevel = randomLevel();
            if (rlevel > level) {
                for (int i = level + 1; i < rlevel + 1;
                     i++)
                    update[i] = header;
                level = rlevel;
            }

            Node n = createNode(key, rlevel);
            n.student = value;
            for (int i = 0; i <= rlevel; i++) {
                n.forward[i] = update[i].forward[i];
                update[i].forward[i] = n;
            }
            System.out.println(
                    "Successfully Inserted key " + key);
        }
    }

    /**
     * Searches for values associated with a given key in the skip list.
     *
     * @param key The key to search for in the skip list.
     * @return A list of values associated with the provided key, or an empty list if not found.
     */
    public List<U> Search(T key){
        List<U> results = new ArrayList<>();
        Node current = header;
        for (int i = level; i >=0 ; i--) {
            while (current.forward[i] != null && current.forward[i].key.compareTo(key)<0) {
                current = current.forward[i];
            }
        }
        current = current.forward[0];

        while (current != null && current.key.compareTo(key) <= 0) {
            if (current.key.equals(key)) {
                results.add((U) current.student);
            }
            current = current.forward[0];
        }

        if (!results.isEmpty()) {
            return results;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Deletes a key-value pair from the skip list.
     *
     * @param key The key to delete from the skip list.
     */
    void deleteElement(T key){
        Node current = header;
        Node[] update = new Node[MAXLVL+1];

        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].key.compareTo(key)<0)
                current = current.forward[i];
            update[i] = current;
        }

        current = current.forward[0];
        if(current!=null || current.key==key){
            for (int i = 0; i < level; i++) {
                if (update[i].forward[i] != current)
                    break;
                update[i].forward[i] = current.forward[i];
            }
            //if it was the only node in that level and you deleted it, delete the level
            while(level>0 && header.forward[level] ==null){
                level --;
            }
            System.out.println("Successfully deleted key "+ key);
        }
    }

    /**
     * Prints the contents of the skip list, displaying each level with its nodes.
     */
    void printList() {
        Node current = header;

        for (int level = this.level; level >= 0; level--) {
            System.out.print("Level " + level + ": ");
            current = header.forward[level];
            while (current != null) {
                System.out.print("[" + current.key + ": " + current.student + "] ");
                current = current.forward[level];
            }
            System.out.println();
        }
    }
}