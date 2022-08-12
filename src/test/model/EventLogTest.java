package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// jUnit tests for EventLog class
public class EventLogTest {

    private EventLog eventLog;
    private Event e1;
    private Event e2;
    private Event e3;

    @BeforeEach
    public void setup() {
        eventLog = EventLog.getInstance();
        e1 = new Event("a");
        e1 = new Event("b");
        e1 = new Event("c");
    }

    @Test
    public void testLogEvents() {
        eventLog.logEvent(e1);
        eventLog.logEvent(e2);
        eventLog.logEvent(e3);

        ArrayList<Event> eventList = new ArrayList<>();

        for (Event e : EventLog.getInstance()) {
            eventList.add(e);
        }

        assertTrue(eventList.contains(e1));
        assertTrue(eventList.contains(e2));
        assertTrue(eventList.contains(e3));
    }

    @Test
    public void testClear() {
        eventLog.logEvent(e1);
        eventLog.logEvent(e2);
        eventLog.logEvent(e3);

        eventLog.clear();
        ArrayList<Event> eventList = new ArrayList<>();

        for (Event e : EventLog.getInstance()) {
            eventList.add(e);
        }
        assertEquals(1,eventList.size());
    }

}
