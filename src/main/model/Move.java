package model;

import org.json.JSONObject;
import persistence.JsonWriting;

import java.util.ArrayList;

import static model.Tile.*;

// represents a move that a player makes
public class Move implements JsonWriting {

    private int posX;
    private int posY;
    private int action;

    private ArrayList<Coordinates> updatedCoordinates;

    // EFFECTS: creates a move with given x, y, and action
    public Move(int x, int y, int action) {
        this.posX = x;
        this.posY = y;
        this.action = action;
        updatedCoordinates = new ArrayList<>();
    }

    // getters and setters
    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public int getAction() {
        return action;
    }


    // REQUIRES: move must be the first move, player board has to be completely covered
    // MODIFIES: this, masterBoard
    // EFFECTS: makes a move on the player board given a posX, posY and action
    //              - if the move is legal (inbounds) and the action is 0, generates the master board with the position
    //                (posX,posY) being empty, calls leftClick, and adds the coordinate to updatedCoordinates
    //          returns updatedCoordinates
    public ArrayList<Coordinates> makeFirstMove(Board playerBoard, Board masterBoard) {
        boolean inbounds = posX >= 0 && posX < playerBoard.getWidth() && posY >= 0 && posY < playerBoard.getHeight();

        if (inbounds && action == 0) {
            masterBoard.generateMasterBoard(posX,posY);
            updatedCoordinates.add(new Coordinates(posX, posY));
            leftClick(playerBoard,masterBoard);
        }
        return updatedCoordinates;
    }

    // MODIFIES: this
    // EFFECTS: makes a move on the player board given a posX, posY and action
    //              - if the move is legal,
    //                    - if the action is 0 and the tile at coordinates (posX,posY) is covered, call leftClick and
    //                      adds the coordinate to updatedCoordinates
    //                    - else if the action is 1 and the tile at coordinates (posX,posY) is either covered or
    //                      flagged, call rightClick and adds the coordinate to updatedCoordinates
    //          returns updatedCoordinates
    public ArrayList<Coordinates> makeMove(Board playerBoard, Board masterBoard) {
        boolean inbounds = posX >= 0 && posX < playerBoard.getWidth() && posY >= 0 && posY < playerBoard.getHeight();

        if (inbounds) {
            boolean tileIsCovered = playerBoard.getTileFromBoard(posX, posY) == COVERED;
            boolean tileIsFlagged = playerBoard.getTileFromBoard(posX, posY) == FLAGGED;

            if (action == 0 && tileIsCovered) {
                updatedCoordinates.add(new Coordinates(posX, posY));
                leftClick(playerBoard,masterBoard);
            } else if (action == 1 && tileIsFlagged) {
                updatedCoordinates.add(new Coordinates(posX, posY));
                rightClick(playerBoard);
            } else if (action == 1 && tileIsCovered && playerBoard.getRemainingMines() > 0) {
                updatedCoordinates.add(new Coordinates(posX, posY));
                rightClick(playerBoard);
            }
        }

        return updatedCoordinates;
    }

    // REQUIRES: tile at coordinates (posX,posY) is covered
    // MODIFIES: playerBoard
    // EFFECTS: sets the tile at coordinates (posX,posY) on the player board to the tile at the same position on the
    //          master board
    //              - if the tile is a mine, sets the player board tile to a clicked mine
    //              - else if the tile is empty, calls makeMove on the surrounding tiles and adds all the coordinates
    //                to updatedCoordinates
    public void leftClick(Board playerBoard, Board masterBoard) {

        Tile tile = masterBoard.getTileFromBoard(posX, posY);
        playerBoard.setTileOnBoard(posX, posY, tile);
        if (tile == MINE) {
            playerBoard.setTileOnBoard(posX, posY, CLICKED_MINE);
            playerBoard.generateMinesOnPlayerBoard(masterBoard);
        } else if (tile == EMPTY) {
            Coordinates c = new Coordinates(posX, posY);
            ArrayList<Coordinates> coords = c.findSurroundingCoordinates(playerBoard);
            for (Coordinates coordinates : coords) {
                Move nextMove = new Move(coordinates.getX(), coordinates.getY(), action);
                updatedCoordinates.addAll(nextMove.makeMove(playerBoard, masterBoard));
            }
        }
    }

    // REQUIRES: tile at coordinates (posX,posY) is either flagged or covered
    // MODIFIES: playerBoard
    // EFFECTS: if the tiles at coordinates (posX,posY) on the player board is flagged, set it to covered and increase
    //          the remaining mines by 1
    //          else, set the tile to flagged and decrease the remaining mines by 1
    public void rightClick(Board playerBoard) {

        int remainingMines = playerBoard.getRemainingMines();

        if (playerBoard.getTileFromBoard(posX, posY) == FLAGGED) {
            playerBoard.setTileOnBoard(posX, posY, COVERED);
            remainingMines++;
        } else {
            playerBoard.setTileOnBoard(posX, posY, FLAGGED);
            remainingMines--;
        }
        playerBoard.setRemainingMines(remainingMines);
    }

    // EFFECTS: returns a JSON object that contains a move
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", posX);
        json.put("y", posY);
        json.put("action", action);
        return json;
    }
}
