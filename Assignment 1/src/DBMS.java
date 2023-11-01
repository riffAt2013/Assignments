import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class DBMS implements DBMSInterface {
    private StorageBackendInterface<String, Student> studentNumberIndex;
    private StorageBackendInterface<Integer, Student> overallScoreIndex;

    public DBMS() {
        this.studentNumberIndex = new MyStorageBackend<>();
        this.overallScoreIndex = new MyStorageBackend<>();
    }

    public void insertStudent(Student student) {
        studentNumberIndex.insert(String.valueOf(student.getStudentNumber().hashCode()), student);
        overallScoreIndex.insert(student.getOverallScore(), student);
    }

    public Student queryByStudentNumber(String studentNumber) {
        int hashCode = studentNumber.hashCode();
        List<Student> results = studentNumberIndex.search(String.valueOf(hashCode));
        if (!results.isEmpty()) {
            return results.get(0); // Return the first result if found
        } else {
            // Handle the case where the list of results is empty
            return null; // Or you can throw a custom exception
        }
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

class MyStorageBackend<T extends Comparable<T>, U> implements StorageBackendInterface<T, U> {
    SkipList<T,U> skipList=new SkipList(3, 0.5f);
    @Override
    public void insert(T key, U item) {
        skipList.insertElement(key,item);
    }

    @Override
    public List<U> search(T key) {
        return skipList.Search(key);
    }

    @Override
    public void delete(T key) {
        skipList.deleteElement(key);
    }
    // your magical code here
}


// Class to implement node
final class Node<T extends Comparable<T>, U> {
    T key;

    // Array to hold pointers to node of different level
    Node[] forward;
    U student;
    Node(T key, int level)
    {
        this.key = key;
        // Allocate memory to forward
        forward = new Node[level + 1];
    }
};

// Class for Skip list
class SkipList<T extends Comparable<T>,U> {
    // Maximum level for this skip list
    int MAXLVL;

    // P is the fraction of the nodes with level
    // i pointers also having level i+1 pointers
    float P;

    // current level of skip list
    int level;

    // pointer to header node
    Node<T, U> header;

    SkipList(int MAXLVL, float P)
    {
        this.MAXLVL = MAXLVL;
        this.P = P;
        level = 0;

        // create header node and initialize key to -1
        header = new Node(-1, MAXLVL);
    }

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

    Node createNode(T key, int level)
    {
        Node n = new Node(key, level);
        return n;
    }

    // Insert given key in skip list

    void insertElement(T key, U value)
    {
        Node current = header;

        // create update array and initialize it
        Node update[] = new Node[MAXLVL + 1];

            /* start from highest level of skip list
                    move the current pointer forward while
            key is greater than key of node next to
            current Otherwise inserted current in update
            and move one level down and continue search
            */
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].key.compareTo(key)<0)
                current = current.forward[i];
            update[i] = current;
        }

            /* reached level 0 and forward pointer to
            right, which is desired position to
            insert key.
            */
        current = current.forward[0];

            /* if current is NULL that means we have reached
            to end of the level or current's key is not
            equal to key to insert that means we have to
            insert node between update[0] and current node
        */
        if (current == null || current.key != key || Search((T)current.key) != value ) {
            // Generate a random level for node
            int rlevel = randomLevel();

            // If random level is greater than list's
            // current level (node with highest level
            // inserted in list so far), initialize
            // update value with pointer to header for
            // further use
            if (rlevel > level) {
                for (int i = level + 1; i < rlevel + 1;
                     i++)
                    update[i] = header;

                // Update the list current level
                level = rlevel;
            }

            // create new node with random level
            // generated
            Node n = createNode(key, rlevel);
            n.student = value;
            // insert node by rearranging pointers
            for (int i = 0; i <= rlevel; i++) {
                n.forward[i] = update[i].forward[i];
                update[i].forward[i] = n;
            }
            System.out.println(
                    "Successfully Inserted key " + key);
        }
    }

    public List<U> Search(T key){
        List<U> results = new ArrayList<>();
        Node current = header;

        /* start from highest level of skip list
                move the current pointer forward while
        key is greater than key of node next to
        current    and move one level down and continue search
        */
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
            return results; // Return a list of results with similar keys
        } else {
            return Collections.emptyList(); // Return an empty list if key is not found
        }

    }

    void deleteElement(T key){
        Node current = header;
        // create update array and initialize it
        Node[] update = new Node[MAXLVL+1];

            /* start from highest level of skip list
            move the current pointer forward while
            key is greater than key of node next to
            current Otherwise inserted current in update
            and move one level down and continue search
            */
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].key.compareTo(key)<0)
                current = current.forward[i];
            update[i] = current;
        }

            /* reached level 0 and forward pointer to
            right, which is desired position to
            delete key.
            */
        current = current.forward[0];

        if(current!=null && current.key==key){

            // delete node by rearranging pointers
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


    void printList() {
        Node current = header;

        for (int level = this.level; level >= 0; level--) {
            System.out.print("Level " + level + ": ");

            current = header.forward[level]; // Move to the first node in the current level

            while (current != null) {
                System.out.print("[" + current.key + ": " + current.student + "] ");
                current = current.forward[level];
            }

            System.out.println(); // Move to the next line for the next level
        }
    }
}