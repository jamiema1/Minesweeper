package persistence;

import model.Board;
import model.Leaderboard;
import model.Player;
import static model.Tile.*;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// jUnit tests for JsonWriter class
public class JsonWriterTest {

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Player player;
    private Leaderboard leaderboard;
    private static final String JSON_FILE_LOCATION_1 = "./data/testWritePlayerToValidFile.json";
    private static final String JSON_FILE_LOCATION_2 = "./data/testWriteLeaderboardToValidFile.json";
    private static final String JSON_FILE_LOCATION_3 = "";

    @Test
    public void testWritePlayerToEmptyFile() {
        try {
            player = new Player(10,10,10, Board.Difficulty.BEGINNER);
            jsonWriter = new JsonWriter(JSON_FILE_LOCATION_1);
            jsonReader = new JsonReader(JSON_FILE_LOCATION_1);

            jsonWriter.write(player);

            player = jsonReader.read();

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    assertEquals(COVERED,player.getPlayerBoard().getTileFromBoard(j,i));
                    assertEquals(COVERED,player.getMasterBoard().getTileFromBoard(j,i));
                }
            }
            assertEquals(0, player.getMoves().size());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testWriteLeaderboardToEmptyFile() {
        try {
            leaderboard = new Leaderboard();
            leaderboard.createLeaderBoard();
            jsonWriter = new JsonWriter(JSON_FILE_LOCATION_2);
            jsonReader = new JsonReader(JSON_FILE_LOCATION_2);

            jsonWriter.write(leaderboard);

            leaderboard = jsonReader.read(false);

            int x = 0;
            for (Board.Difficulty d : Board.Difficulty.values()) {
                for (int i = 0; i < 10; i++) {
                    assertEquals(d, leaderboard.getLeaderboard().get(i + x * 10).getDifficulty());
                    assertEquals("null", leaderboard.getLeaderboard().get(i + x * 10).getName());
                    assertEquals(999, leaderboard.getLeaderboard().get(i + x * 10).getTime());
                }
                x++;
            }
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testWriteToInvalidFile() {
        try {
            player = new Player(10,10,10, Board.Difficulty.BEGINNER);
            jsonWriter = new JsonWriter(JSON_FILE_LOCATION_3);
            jsonWriter.write(player);
            fail();
        } catch (IOException e) {
            // no exception should be thrown
        }
    }

}
