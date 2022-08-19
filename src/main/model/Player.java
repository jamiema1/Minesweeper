package model;

import javafx.beans.InvalidationListener;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonWriting;

import java.util.ArrayList;
import java.util.Observable;

// represents a player that has a player and master board, as well as a list of moves it has made so far
public class Player extends Observable implements JsonWriting {

    private ArrayList<Move> moves;
    private Board playerBoard;
    private Board masterBoard;
    private int time;

    // EFFECTS: creates a new player with a fixed width, height, and mines
    public Player(int width, int height, int mines) {
        moves = new ArrayList<>();
        playerBoard = new Board(width,height,mines);
        masterBoard = new Board(width,height,mines);
        time = 0;
    }

    // getters and setters
    public Board getPlayerBoard() {
        return playerBoard;
    }

    public void setPlayerBoard(Board playerBoard) {
        this.playerBoard = playerBoard;
    }

    public Board getMasterBoard() {
        return masterBoard;
    }

    public void setMasterBoard(Board masterBoard) {
        this.masterBoard = masterBoard;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    // MODIFIES: this
    // EFFECTS: makes a move on the player board given a posX, posY and action
    //              - if moves is empty, calls makeFirstMove,
    //              - else, calls makeMove
    //          then, if newTiles is not empty, adds the move to moves and logs the event
    //          returns newTiles
    public ArrayList<Coordinates> makeMove(Move move) {
        ArrayList<Coordinates> newTiles = null;

        if (!playerBoard.getGameOver()) {
            if (moves.isEmpty()) {
                newTiles = move.makeFirstMove(playerBoard, masterBoard);
            } else {
                newTiles = move.makeMove(playerBoard, masterBoard);
            }

            if (newTiles != null) {
                moves.add(move);
                EventLog.getInstance().logEvent(new Event("Added a new Move: " + move.getX() + " "
                        + move.getY() + " " + move.getAction()));

                setChanged();
                notifyObservers(newTiles);
            }
        }

        return newTiles;
    }

    // EFFECTS: returns a JSON object that contains a player
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("player board", playerBoard.toJson());
        json.put("master board", masterBoard.toJson());
        json.put("moves", movesToJson());
        json.put("time", time);
        return json;
    }

    // EFFECTS: returns moves that a player has made as a JSON array
    private JSONArray movesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Move move : moves) {
            jsonArray.put(move.toJson());
        }

        return jsonArray;
    }
}
