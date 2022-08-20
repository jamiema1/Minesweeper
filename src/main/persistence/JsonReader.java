package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static model.Board.Difficulty.*;
import static model.Tile.TILES;

// represents a JSON reader that can read from a JSON file and create a player
// modelled after JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {

    private String sourceFile;
    private static final boolean MASTER = true;
    private static final boolean PLAYER = false;

    // EFFECTS: creates a JsonReader and sets its source file
    public JsonReader(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    // EFFECTS: returns the source file converted to a string
    private String readFile() throws IOException {
        StringBuilder content = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(sourceFile), StandardCharsets.UTF_8)) {
            stream.forEach(content::append);
        }

        return content.toString();
    }



    // EFFECTS: returns a player that has been created by reading a JSON file
    public Player read() throws IOException {
        String jsonData = readFile();
        JSONObject jsonObject = new JSONObject(jsonData);
        return createPlayer(jsonObject);
    }

    // EFFECTS: returns the leaderboard that has been created by reading a JSON file
    public Leaderboard read(boolean b) throws IOException {
        String jsonData = readFile();
        JSONObject jsonObject = new JSONObject(jsonData);
        return createLeaderboard(jsonObject);
    }




    // EFFECTS: creates a new player and adds its player and master board, as well as its moves
    private Player createPlayer(JSONObject jsonObject) {
        Player player = new Player(0,0,0, CUSTOM);
        addBoard(player, jsonObject, MASTER);
        addBoard(player, jsonObject, PLAYER);
        addMoves(player, jsonObject);
        addTime(player, jsonObject);
        return player;
    }

    // MODIFIES: player
    // EFFECTS: adds a board to the player from the JSON object
    //              - if playerType is true, reads the master board and copies the information to the player
    //              - else, reads the player board and copies the information to the player
    public void addBoard(Player player, JSONObject jsonObject, boolean playerType) {

        JSONObject jsonObjectBoard;
        if (playerType) {
            jsonObjectBoard = jsonObject.getJSONObject("master board");
        } else {
            jsonObjectBoard = jsonObject.getJSONObject("player board");
        }

        JSONArray jsonArray = jsonObjectBoard.getJSONArray("board");
        int mines = jsonObjectBoard.getInt("mines");
        Board.Difficulty difficulty = Board.Difficulty.valueOf(jsonObjectBoard.getString("difficulty"));
        boolean gameOver = jsonObjectBoard.getBoolean("game over");

        int height = jsonArray.length();
        int width = ((JSONArray) jsonArray.get(0)).length();

        if (playerType) {
            player.setMasterBoard(new Board(width, height, mines, difficulty));
        } else {
            player.setPlayerBoard(new Board(width, height, mines, difficulty));
            player.getPlayerBoard().setGameOver(gameOver);
        }

        addTiles(player, jsonArray, playerType, width, height);
    }

    // EFFECTS: adds tiles to a board from a JSON array
    private void addTiles(Player player, JSONArray jsonArray, Boolean playerType, int width, int height) {
        for (int i = 0; i < height; i++) {
            JSONArray jsonArrayRows = (JSONArray) jsonArray.get(i);
            for (int j = 0; j < width; j++) {
                JSONObject json = (JSONObject) jsonArrayRows.get(j);
                addTile(player, json, playerType, j, i);
            }

        }
    }

    // MODIFIES: player
    // EFFECTS: adds a tile to the board from the JSON object
    //              - if playerType is true, adds the tile to the master board
    //              - else, adds the tile to the player board
    private void addTile(Player player, JSONObject jsonObject, boolean playerType, int column, int row) {
        Tile t = findTile(Value.valueOf(jsonObject.getString("tile")));
        if (playerType) {
            player.getMasterBoard().setTileOnBoard(column, row, t);
        } else {
            player.getPlayerBoard().setTileOnBoard(column, row, t);
        }
    }

    // EFFECTS: given a value, finds a tile within TILES that has that value
    public Tile findTile(Value value) {
        for (Tile t : TILES) {
            if (t.getValue() == value) {
                return t;
            }
        }
        return null;
    }

    // EFFECTS: adds a list of moves to the player from the JSON object
    public void addMoves(Player player, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("moves");
        for (Object json : jsonArray) {
            JSONObject nextMove = (JSONObject) json;
            addMove(player, nextMove);
        }
    }

    // MODIFIES: player
    // EFFECTS: adds a move to the player from the JSON object
    private void addMove(Player player, JSONObject jsonObject) {
        int x = jsonObject.getInt("x");
        int y = jsonObject.getInt("y");
        int action = jsonObject.getInt("action");
        Move move = new Move(x,y,action);
        player.getMoves().add(move);
    }

    // MODIFIES: player
    // EFFECTS: adds a time to the player from the JSON object
    private void addTime(Player player, JSONObject jsonObject) {
        int time = jsonObject.getInt("time");
        player.setTime(time);
    }



    // EFFECTS: creates a new leaderboard and adds leaderboardEntries to it
    private Leaderboard createLeaderboard(JSONObject jsonObject) {
        Leaderboard leaderboard = new Leaderboard();
        addLeaderboard(leaderboard, jsonObject);
        return leaderboard;
    }


    // MODIFIES: leaderboard
    // EFFECTS: adds a list of leaderboardEntries to the leaderboard from the JSON object
    private void addLeaderboard(Leaderboard leaderboard, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("leaderboard");
        for (Object json : jsonArray) {
            JSONObject nextLeaderboardEntry = (JSONObject) json;
            addEntry(leaderboard, nextLeaderboardEntry);
        }
    }

    // MODIFIES: leaderboard
    // EFFECTS: adds a leaderboardEntry to the leaderboard from the JSON object
    private void addEntry(Leaderboard leaderboard, JSONObject jsonObject) {
        Board.Difficulty difficulty = Board.Difficulty.valueOf(jsonObject.getString("difficulty"));
        String name = jsonObject.getString("name");
        int time = jsonObject.getInt("time");
        LeaderboardEntry entry = new LeaderboardEntry(difficulty, name, time);
        leaderboard.getLeaderboard().add(entry);
    }
}
