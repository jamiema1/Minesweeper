package persistence;

import model.Player;
import static model.Tile.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// jUnit tests for JsonWriter class
public class JsonWriterTest {

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Player player;
    private static final String JSON_FILE_LOCATION_1 = "./data/testWriteToValidFile.json";
    private static final String JSON_FILE_LOCATION_2 = "";

    @Test
    public void testWriteToEmptyFile() {
        try {
            player = new Player(10,10,10);
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
    public void testWriteToInvalidFile() {
        try {
            player = new Player(10,10,10);
            jsonWriter = new JsonWriter(JSON_FILE_LOCATION_2);
            jsonWriter.write(player);
            fail();
        } catch (IOException e) {

        }
    }

}
