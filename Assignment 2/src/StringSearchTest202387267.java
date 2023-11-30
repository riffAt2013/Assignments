import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class StringSearchTest202387267 {
    private final AbstractStringSearch search = new StringSearch();

    // test for single match. Checks index returned via called method against expected output index.
    @Test
    public void testOne()
    {
        String text = "i'm batman, she's batwoman!";
        String pattern = "batman";
        int expectedResult = 4;
        int actualResult = search.search(pattern, text);
        assertEquals(expectedResult, actualResult);
    }

    // test for no match. checks output from called method against expected default value for no match
    @Test
    public void testTwo()
    {
        String text = "i'm batman, she's batwoman!";
        String pattern = "alfred";
        int expectedResult = -1;
        int actualResult = search.search(pattern, text);
        assertEquals(expectedResult, actualResult);
    }

    // test for wild card match. Checks returned index value via called method against expected output index.
    @Test
    public void testThree()
    {
        String text = "His cell no. can be +3779";
        String pattern = "+?779";
        int expectedResult = 20;
        int actualResult = search.search(pattern, text);
        assertEquals(expectedResult, actualResult);
    }

    // test for a blank pattern. Expecting a -1 since no processing can be done.
    @Test
    public void testFour()
    {
        String text = "He's supporting Argentina in the Soccer World Cup!";
        String pattern = "";
        int expectedResult = -1;
        int actualResult = search.search(pattern, text);
        assertEquals(expectedResult, actualResult);
    }
}
