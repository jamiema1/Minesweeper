package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonWriting;

import java.util.ArrayList;
import java.util.Random;

import static model.Tile.*;

// represents a board consisting of a grid of tiles
public class Board implements JsonWriting {

    // represents the different possible difficulties a board can have
    public enum Difficulty {
        BEGINNER,
        INTERMEDIATE,
        EXPERT,
        CUSTOM
    }

    private int width;
    private int height;
    private int mines;
    private Difficulty difficulty;
    private int remainingMines;
    private boolean gameOver;
    private Tile[][] board;

    // EFFECTS: creates a player board of given width, height and mines that is full of covered tiles
    public Board(int width, int height, int mines, Difficulty difficulty) {

        this.width = width;
        this.height = height;
        this.mines = mines;
        this.difficulty = difficulty;
        remainingMines = mines;
        gameOver = false;
        board = new Tile[height][width];

        generatePlayerBoard();
    }

    // getters and setters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMines() {
        return mines;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getRemainingMines() {
        return remainingMines;
    }

    public void setRemainingMines(int remainingMines) {
        this.remainingMines = remainingMines;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Tile getTileFromBoard(int x, int y) {
        return board[y][x];
    }

    public void setTileOnBoard(int x, int y, Tile tile) {
        board[y][x] = tile;
    }

    // MODIFIES: this
    // EFFECTS: generates a board of given size filled with covered tiles and logs the event
    public void generatePlayerBoard() {
        generateEntireBoardWithTiles(COVERED);
        EventLog.getInstance().logEvent(new Event("Created a new player board"));
    }

    // MODIFIES: this
    // EFFECTS: generates a board of given size filled with mines and number tiles and logs the event
    public void generateMasterBoard(int x, int y) {
        generateEntireBoardWithTiles(EMPTY);
        generateMineTiles(x,y);
        generateNumberTiles();
        EventLog.getInstance().logEvent(new Event("Created a new master board"));
    }

    // REQUIRES: width > 0 and height > 0
    // MODIFIES: this
    // EFFECTS: fills the entire board with a given tile
    public void generateEntireBoardWithTiles(Tile tile) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                setTileOnBoard(j,i, tile);
            }
        }
    }

    // REQUIRES: width > 0 and height > 0
    // MODIFIES: this
    // EFFECTS: randomly sets tiles to be mines and ensures the coordinate (x,y) is always empty
    public void generateMineTiles(int x, int y) {

        Random random = new Random();

        Coordinates coordinates = new Coordinates(x,y);
        ArrayList<Coordinates> coords = coordinates.findSurroundingCoordinates(this);
        coords.add(coordinates);
        for (int i = 0; i < mines; i++) {
            boolean newSpaceForMine = false;
            while (!newSpaceForMine) {
                int mineX = random.nextInt(width);
                int mineY = random.nextInt(height);
                if (getTileFromBoard(mineX,mineY) != MINE) {
                    boolean validSpot = true;
                    for (Coordinates c : coords) {
                        if (c.getX() == mineX && c.getY() == mineY) {
                            validSpot = false;
                        }
                    }
                    if (validSpot) {
                        setTileOnBoard(mineX, mineY, MINE);
                        newSpaceForMine = true;
                    }
                }
            }
        }
    }

    // REQUIRES: width > 0 and height > 0
    // MODIFIES: this
    // EFFECTS: assigns tiles that are near mines to their corresponding number
    public void generateNumberTiles() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getTileFromBoard(j,i) != MINE) {

                    int minesNearby = 0;

                    Coordinates coordinates = new Coordinates(j,i);
                    ArrayList<Coordinates> coords = coordinates.findSurroundingCoordinates(this);

                    for (Coordinates c : coords) {
                        if (getTileFromBoard(c.getX(), c.getY()) == MINE) {
                            minesNearby++;
                        }
                    }

                    for (Tile t : TILES) {
                        if (t.getNumber() == minesNearby) {
                            setTileOnBoard(j, i, t);
                        }
                    }
                }
            }
        }
    }

    // REQUIRES: width > 0 and height > 0
    // MODIFIES: this
    // EFFECTS: goes through the whole board and changes the tiles on the player board
    //              - if the master board tile is a mine and the player board tile is covered, then the player board
    //                tile changes to a mine
    //              - else if the master board tile is not a mine and the player board tile is flagged, then the player
    //                board tile changes to an incorrect flag
    public void generateMinesOnPlayerBoard(Board masterBoard) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (masterBoard.getTileFromBoard(j,i) == MINE && getTileFromBoard(j,i) == COVERED) {
                    setTileOnBoard(j, i, MINE);
                } else if (masterBoard.getTileFromBoard(j,i) != MINE && getTileFromBoard(j,i) == FLAGGED) {
                    setTileOnBoard(j, i, INCORRECT_FLAG);
                }
            }
        }
    }

    // REQUIRES: width > 0 and height > 0
    // EFFECTS: returns true if all tiles are not covered, false otherwise
    public boolean checkEntireBoardOpened() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getTileFromBoard(j,i) == COVERED) {
                    return false;
                }
            }
        }
        return true;
    }

    // EFFECTS: if the number of remaining mines are 0 and the entire board is opened, then gameOver is set to true and
    //          returns true, returns false otherwise
    public boolean checkForWin() {
        boolean win = getRemainingMines() == 0 && checkEntireBoardOpened();
        gameOver = win;
        return win;
    }

    // REQUIRES: width > 0 and height > 0
    // EFFECTS: if any tile on the board is a clicked mine, returns true, false otherwise
    public boolean checkForLose() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getTileFromBoard(j,i) == CLICKED_MINE) {
                    gameOver = true;
                    return true;
                }
            }
        }
        return false;
    }

    // EFFECTS: returns the board, number of remaining mines, difficulty, and game over state as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("board", boardToJson());
        json.put("mines", remainingMines);
        json.put("difficulty", difficulty);
        json.put("game over", gameOver);

        return json;
    }

    // EFFECTS: returns the board as a JSON array
    private JSONArray boardToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < height; i++) {
            JSONArray jsonArrayRows = new JSONArray();
            for (int j = 0; j < width; j++) {
                Tile tile = getTileFromBoard(j,i);
                jsonArrayRows.put(tile.toJson());
            }
            jsonArray.put(i, jsonArrayRows);
        }
        return jsonArray;
    }
}
