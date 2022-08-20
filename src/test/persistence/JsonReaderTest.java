package persistence;

//import exceptions.LoadInvalidBoardException;
import model.Board;
import model.Player;
import model.Value;
import org.junit.jupiter.api.Test;
import ui.gui.MainPanel;

import java.io.IOException;

import static model.Tile.*;
import static org.junit.jupiter.api.Assertions.*;

// jUnit tests for JsonReader class
public class JsonReaderTest {

    private JsonReader jsonReader;
    private Player player;
    private MainPanel mp;
    private static final String JSON_FILE_LOCATION_1 = "./data/testReadValidFile.json";
    private static final String JSON_FILE_LOCATION_2 = "";

    @Test
    public void testReadValidFile() {
        try {
            player = new Player(4,4,1, Board.Difficulty.CUSTOM);
            jsonReader = new JsonReader(JSON_FILE_LOCATION_1);

            player = jsonReader.read();

            assertEquals(MINE, player.getMasterBoard().getTileFromBoard(2,2));
            assertEquals(FLAGGED, player.getPlayerBoard().getTileFromBoard(2,2));

            assertEquals(3, player.getMoves().size());
            assertEquals(0,player.getMoves().get(0).getX());
            assertEquals(0,player.getMoves().get(0).getY());
            assertEquals(0,player.getMoves().get(0).getAction());
            assertEquals(3,player.getMoves().get(1).getX());
            assertEquals(2,player.getMoves().get(1).getY());
            assertEquals(0,player.getMoves().get(1).getAction());
            assertEquals(2,player.getMoves().get(2).getX());
            assertEquals(2,player.getMoves().get(2).getY());
            assertEquals(1,player.getMoves().get(2).getAction());

        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testReadInvalidFile() {
        try {
            player = new Player(10,10,10, Board.Difficulty.CUSTOM);
            jsonReader = new JsonReader(JSON_FILE_LOCATION_2);

            player = jsonReader.read();
            fail();
        } catch (IOException e) {
            // no exception should be thrown
        }
    }

    @Test
    public void testFindTile() {
        jsonReader = new JsonReader(JSON_FILE_LOCATION_2);
        assertEquals(COVERED,jsonReader.findTile(Value.COVERED));
        assertNull(jsonReader.findTile(null));
    }

}
