package model;

import java.util.ArrayList;

// represents a pair of x and y coordinates
public class Coordinates {
    private int posX;
    private int posY;

    // EFFECTS: creates a coordinate with a given x and y position
    public Coordinates(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    // getters and setters
    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    // REQUIRES: 0 <= x < width and 0 <= y < height
    // EFFECTS: returns a list of valid coordinates that represent the positions one move away from the
    //          given x and y positions
    public ArrayList<Coordinates> findSurroundingCoordinates(Board board) {
        ArrayList<Coordinates> coords = new ArrayList<>();
        findOrthogonalCoordinates(board, coords);
        findDiagonalCoordinates(board,  coords);
        return coords;
    }

    // REQUIRES: 0 <= x < width and 0 <= y < height
    // EFFECTS: returns a list of valid coordinates that represent the positions one move away from the
    //          given x and y positions orthogonally
    public void findOrthogonalCoordinates(Board board, ArrayList<Coordinates> coords) {
        int width = board.getWidth();
        int height = board.getHeight();

        if (posY > 0) {
            Coordinates c = new Coordinates(posX, posY - 1);
            coords.add(c);
        }
        if (posX < width - 1) {
            Coordinates c = new Coordinates(posX + 1, posY);
            coords.add(c);
        }
        if (posY < height - 1) {
            Coordinates c = new Coordinates(posX, posY + 1);
            coords.add(c);
        }
        if (posX > 0) {
            Coordinates c = new Coordinates(posX - 1, posY);
            coords.add(c);
        }
    }

    // REQUIRES: 0 <= x < width and 0 <= y < height
    // EFFECTS: returns a list of valid coordinates that represent the positions one move away from the
    //          given x and y positions diagonally
    public void findDiagonalCoordinates(Board board, ArrayList<Coordinates> coords) {
        int width = board.getWidth();
        int height = board.getHeight();

        if (posX < width - 1 && posY > 0) {
            Coordinates c = new Coordinates(posX + 1, posY - 1);
            coords.add(c);
        }
        if (posX < width - 1 && posY < height - 1) {
            Coordinates c = new Coordinates(posX + 1, posY + 1);
            coords.add(c);
        }
        if (posX > 0 && posY < height - 1) {
            Coordinates c = new Coordinates(posX - 1, posY + 1);
            coords.add(c);
        }
        if (posX > 0 && posY > 0) {
            Coordinates c = new Coordinates(posX - 1, posY - 1);
            coords.add(c);
        }
    }
}
