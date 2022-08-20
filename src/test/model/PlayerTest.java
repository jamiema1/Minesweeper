package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// TODO
public class PlayerTest {

    private Player player1;
    private Player player2;

    private Move move;
    private Move move2;
    private Move move3;

    private JSONObject json;

    @BeforeEach
    public void setup() {
        player1 = new Player(5,8,4, Board.Difficulty.CUSTOM);
        player2 = new Player(10,10,15, Board.Difficulty.CUSTOM);
        move = new Move(0,0,0);
        move2 = new Move(9,9,1);
        move3 = new Move(9,9,0);
        json = new JSONObject();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, player1.getMoves().size());
        assertEquals(5, player1.getPlayerBoard().getWidth());
        assertEquals(8, player1.getPlayerBoard().getHeight());
        assertEquals(4, player1.getPlayerBoard().getMines());
        assertEquals(0, player1.getTime());

        assertEquals(0, player2.getMoves().size());
        assertEquals(10, player2.getPlayerBoard().getWidth());
        assertEquals(10, player2.getPlayerBoard().getHeight());
        assertEquals(15, player2.getPlayerBoard().getMines());
    }

    @Test
    public void testMakeMove() {
        player2.makeMove(move);
        assertEquals(1, player2.getMoves().size());
        assertEquals(move, player2.getMoves().get(0));

        player2.makeMove(move2);
        assertEquals(2, player2.getMoves().size());
        assertEquals(move, player2.getMoves().get(0));
        assertEquals(move2, player2.getMoves().get(1));

        player2.makeMove(move3);
        assertEquals(2, player2.getMoves().size());
        assertEquals(move, player2.getMoves().get(0));
        assertEquals(move2, player2.getMoves().get(1));
    }


    @Test
    public void testToJson() {

        try {
            JsonReader jsonReader;
            jsonReader = new JsonReader("./data/testReadValidFile.json");
            player1 = jsonReader.read();
        } catch (IOException e) {
            fail();
        }

        json = player1.toJson();

        JSONArray obj = (JSONArray) json.get("moves");
        JSONObject jsonMove = (JSONObject) obj.get(0);
        assertEquals(0, jsonMove.get("x"));
        assertEquals(0, jsonMove.get("y"));
        assertEquals(0, jsonMove.get("action"));

    }
}
