package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;

import static model.Board.Difficulty.*;
import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardTest {

    private Leaderboard leaderboard;
    private LeaderboardEntry beginnerEntry1;
    private LeaderboardEntry beginnerEntry2;
    private LeaderboardEntry beginnerEntry3;
    private LeaderboardEntry intermediateEntry1;
    private LeaderboardEntry intermediateEntry2;
    private LeaderboardEntry intermediateEntry3;
    private LeaderboardEntry expertEntry1;

    @BeforeEach
    public void setup() {

        leaderboard = new Leaderboard();
        leaderboard.createLeaderBoard();

        beginnerEntry1 = new LeaderboardEntry(BEGINNER, "a", 10);
        beginnerEntry2 = new LeaderboardEntry(BEGINNER, "b", 1000);
        beginnerEntry3 = new LeaderboardEntry(BEGINNER, "c", 998);

        intermediateEntry1 = new LeaderboardEntry(INTERMEDIATE, "d", 50);
        intermediateEntry2 = new LeaderboardEntry(INTERMEDIATE, "d", 40);
        intermediateEntry3 = new LeaderboardEntry(INTERMEDIATE, "d", 9);

        expertEntry1 = new LeaderboardEntry(EXPERT, "e", 900);
    }

    @Test
    public void testAddEntry() {

        leaderboard.addEntry(beginnerEntry1);
        assertEquals(40, leaderboard.getLeaderboard().size());
        assertEquals(beginnerEntry1, leaderboard.getLeaderboard().get(0));

        leaderboard.addEntry(beginnerEntry2);
        assertEquals(40, leaderboard.getLeaderboard().size());
        assertEquals(beginnerEntry1, leaderboard.getLeaderboard().get(0));

        leaderboard.addEntry(beginnerEntry3);
        assertEquals(40, leaderboard.getLeaderboard().size());
        assertEquals(beginnerEntry1, leaderboard.getLeaderboard().get(0));
        assertEquals(beginnerEntry3, leaderboard.getLeaderboard().get(1));



        leaderboard.addEntry(intermediateEntry1);
        assertEquals(40, leaderboard.getLeaderboard().size());
        assertEquals(beginnerEntry1, leaderboard.getLeaderboard().get(0));
        assertEquals(beginnerEntry3, leaderboard.getLeaderboard().get(1));
        assertEquals(intermediateEntry1, leaderboard.getLeaderboard().get(10));

        leaderboard.addEntry(intermediateEntry2);
        assertEquals(40, leaderboard.getLeaderboard().size());
        assertEquals(beginnerEntry1, leaderboard.getLeaderboard().get(0));
        assertEquals(beginnerEntry3, leaderboard.getLeaderboard().get(1));
        assertEquals(intermediateEntry1, leaderboard.getLeaderboard().get(11));
        assertEquals(intermediateEntry2, leaderboard.getLeaderboard().get(10));

        leaderboard.addEntry(expertEntry1);
        assertEquals(40, leaderboard.getLeaderboard().size());
        assertEquals(beginnerEntry1, leaderboard.getLeaderboard().get(0));
        assertEquals(beginnerEntry3, leaderboard.getLeaderboard().get(1));
        assertEquals(intermediateEntry1, leaderboard.getLeaderboard().get(11));
        assertEquals(intermediateEntry2, leaderboard.getLeaderboard().get(10));
        assertEquals(expertEntry1, leaderboard.getLeaderboard().get(20));

        leaderboard.addEntry(intermediateEntry3);
        assertEquals(40, leaderboard.getLeaderboard().size());
        assertEquals(beginnerEntry1, leaderboard.getLeaderboard().get(0));
        assertEquals(beginnerEntry3, leaderboard.getLeaderboard().get(1));
        assertEquals(intermediateEntry1, leaderboard.getLeaderboard().get(12));
        assertEquals(intermediateEntry2, leaderboard.getLeaderboard().get(11));
        assertEquals(intermediateEntry3, leaderboard.getLeaderboard().get(10));
        assertEquals(expertEntry1, leaderboard.getLeaderboard().get(20));


    }

    @Test
    public void testCreateLeaderboard() {
        assertEquals(40, leaderboard.getLeaderboard().size());

        int x = 0;
        for (Board.Difficulty d : Board.Difficulty.values()) {
            for (int i = 0; i < 10; i++) {
                assertEquals(999, leaderboard.getLeaderboard().get(i + x * 10).getTime());
                assertEquals(d, leaderboard.getLeaderboard().get(i + x * 10).getDifficulty());
                assertEquals("null", leaderboard.getLeaderboard().get(i + x * 10).getName());
            }
            x++;
        }
    }

    @Test
    public void testToJson() {
        try {
            JsonReader jsonReader;
            jsonReader = new JsonReader("./data/leaderboardTest.json");
            leaderboard = jsonReader.read(false);
        } catch (IOException e) {
            fail();
        }

        JSONObject json = leaderboard.toJson();

        JSONArray obj = (JSONArray) json.get("leaderboard");
        JSONObject jsonEntry = (JSONObject) obj.get(0);
        assertEquals(BEGINNER, jsonEntry.get("difficulty"));
        assertEquals("null", jsonEntry.get("name"));
        assertEquals(999, jsonEntry.get("time"));
    }
}
