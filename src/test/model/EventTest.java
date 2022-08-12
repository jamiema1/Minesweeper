package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

// jUnit tests for Event class
public class EventTest {

    private Event e1;
    private Event e2;
    private Event e3;
    private Date date;

    @BeforeEach
    public void setup() {
        e1 = new Event("a");
        e2 = new Event("b");
        e3 = new Event("c");
        date = Calendar.getInstance().getTime();
    }

    @Test
    public void testConstructor() {
        assertEquals(date, e1.getDate());
        assertEquals("a", e1.getDescription());
    }

    @Test
    public void testEqual() {
        assertFalse(e1.equals(null));
        assertFalse(e1.equals(1));
        assertFalse(e1.equals(e2));
        assertTrue(e1.equals(e1));
    }

    @Test
    public void testHashCode() {
        assertEquals(13 * date.hashCode() + "a".hashCode(), e1.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(date.toString() + "\n" + "a", e1.toString());
    }
}
