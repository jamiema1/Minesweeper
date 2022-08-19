package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        beginnerEntry1 = new LeaderboardEntry(Leaderboard.Difficulty.beginner, "a", 10);
        beginnerEntry2 = new LeaderboardEntry(Leaderboard.Difficulty.beginner, "b", 1000);
        beginnerEntry3 = new LeaderboardEntry(Leaderboard.Difficulty.beginner, "c", 998);

        intermediateEntry1 = new LeaderboardEntry(Leaderboard.Difficulty.intermediate, "d", 50);
        intermediateEntry2 = new LeaderboardEntry(Leaderboard.Difficulty.intermediate, "d", 40);
        intermediateEntry3 = new LeaderboardEntry(Leaderboard.Difficulty.intermediate, "d", 9);

        expertEntry1 = new LeaderboardEntry(Leaderboard.Difficulty.expert, "e", 900);

    }


    @Test
    public void testAddEntry() {

        leaderboard.addEntry(beginnerEntry1);
        assertEquals(30, leaderboard.getLeaderboard().size());
        assertEquals(beginnerEntry1, leaderboard.getLeaderboard().get(0));

        leaderboard.addEntry(beginnerEntry2);
        assertEquals(30, leaderboard.getLeaderboard().size());
        assertEquals(beginnerEntry1, leaderboard.getLeaderboard().get(0));

        leaderboard.addEntry(beginnerEntry3);
        assertEquals(30, leaderboard.getLeaderboard().size());
        assertEquals(beginnerEntry1, leaderboard.getLeaderboard().get(0));
        assertEquals(beginnerEntry3, leaderboard.getLeaderboard().get(1));



        leaderboard.addEntry(intermediateEntry1);
        assertEquals(30, leaderboard.getLeaderboard().size());
        assertEquals(beginnerEntry1, leaderboard.getLeaderboard().get(0));
        assertEquals(beginnerEntry3, leaderboard.getLeaderboard().get(1));
        assertEquals(intermediateEntry1, leaderboard.getLeaderboard().get(10));

        leaderboard.addEntry(intermediateEntry2);
        assertEquals(30, leaderboard.getLeaderboard().size());
        assertEquals(beginnerEntry1, leaderboard.getLeaderboard().get(0));
        assertEquals(beginnerEntry3, leaderboard.getLeaderboard().get(1));
        assertEquals(intermediateEntry1, leaderboard.getLeaderboard().get(11));
        assertEquals(intermediateEntry2, leaderboard.getLeaderboard().get(10));

        leaderboard.addEntry(expertEntry1);
        assertEquals(30, leaderboard.getLeaderboard().size());
        assertEquals(beginnerEntry1, leaderboard.getLeaderboard().get(0));
        assertEquals(beginnerEntry3, leaderboard.getLeaderboard().get(1));
        assertEquals(intermediateEntry1, leaderboard.getLeaderboard().get(11));
        assertEquals(intermediateEntry2, leaderboard.getLeaderboard().get(10));
        assertEquals(expertEntry1, leaderboard.getLeaderboard().get(20));

        leaderboard.addEntry(intermediateEntry3);
        assertEquals(30, leaderboard.getLeaderboard().size());
        assertEquals(beginnerEntry1, leaderboard.getLeaderboard().get(0));
        assertEquals(beginnerEntry3, leaderboard.getLeaderboard().get(1));
        assertEquals(intermediateEntry1, leaderboard.getLeaderboard().get(12));
        assertEquals(intermediateEntry2, leaderboard.getLeaderboard().get(11));
        assertEquals(intermediateEntry3, leaderboard.getLeaderboard().get(10));
        assertEquals(expertEntry1, leaderboard.getLeaderboard().get(20));


    }

    @Test
    public void testCreateLeaderboard() {
        assertEquals(30, leaderboard.getLeaderboard().size());

        int x = 0;
        for (Leaderboard.Difficulty d : Leaderboard.Difficulty.values()) {
            for (int i = 0; i < 10; i++) {
                assertEquals(999, leaderboard.getLeaderboard().get(i + x * 10).getTime());
                assertEquals(d, leaderboard.getLeaderboard().get(i + x * 10).getDifficulty());
                assertEquals("null", leaderboard.getLeaderboard().get(i + x * 10).getName());
            }
            x++;
        }
    }
}
