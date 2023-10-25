import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class DBMSTest202387267 {
    private final DBMSInterface dbms = new DBMS();

    @Test
    public void alwaysPass() {
        assertEquals(true, true);
    }

    @Test
    public void alwaysFail() {
        assertEquals(true, false);
    }
}
