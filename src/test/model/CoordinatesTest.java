package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// jUnit tests for Coordinates class
class CoordinatesTest {
    private Coordinates topLeft;
    private Coordinates topLeft1;
    private Coordinates topLeft2;
    private Coordinates topLeft3;

    private Coordinates topRight;
    private Coordinates topRight1;
    private Coordinates topRight2;
    private Coordinates topRight3;

    private Coordinates bottomRight;
    private Coordinates bottomRight1;
    private Coordinates bottomRight2;
    private Coordinates bottomRight3;

    private Coordinates bottomLeft;
    private Coordinates bottomLeft1;
    private Coordinates bottomLeft2;
    private Coordinates bottomLeft3;

    private Coordinates top;
    private Coordinates top1;
    private Coordinates top2;
    private Coordinates top3;
    private Coordinates top4;
    private Coordinates top5;

    private Coordinates right;
    private Coordinates right1;
    private Coordinates right2;
    private Coordinates right3;
    private Coordinates right4;
    private Coordinates right5;

    private Coordinates bottom;
    private Coordinates bottom1;
    private Coordinates bottom2;
    private Coordinates bottom3;
    private Coordinates bottom4;
    private Coordinates bottom5;

    private Coordinates left;
    private Coordinates left1;
    private Coordinates left2;
    private Coordinates left3;
    private Coordinates left4;
    private Coordinates left5;

    private Coordinates middle;
    private Coordinates middle1;
    private Coordinates middle2;
    private Coordinates middle3;
    private Coordinates middle4;
    private Coordinates middle5;
    private Coordinates middle6;
    private Coordinates middle7;
    private Coordinates middle8;

    private Board board = new Board(10,10,10, Board.Difficulty.CUSTOM);
    private int width = board.getWidth();
    private int height = board.getHeight();


    @BeforeEach
    public void setup() {

        topLeft = new Coordinates(0, 0);
        topLeft1 = new Coordinates(1, 0);
        topLeft2 = new Coordinates(0, 1);
        topLeft3 = new Coordinates(1, 1);

        topRight = new Coordinates(width - 1, 0);
        topRight1 = new Coordinates(width - 1, 1);
        topRight2 = new Coordinates(width - 2, 0);
        topRight3 = new Coordinates(width - 2, 1);

        bottomRight = new Coordinates(width - 1, height - 1);
        bottomRight1 = new Coordinates(width - 1, height - 2);
        bottomRight2 = new Coordinates(width - 2, height - 1);
        bottomRight3 = new Coordinates(width - 2, height - 2);

        bottomLeft = new Coordinates(0, height - 1);
        bottomLeft1 = new Coordinates(0, height - 2);
        bottomLeft2 = new Coordinates(1, height - 1);
        bottomLeft3 = new Coordinates(1, height - 2);

        top = new Coordinates(width / 2, 0);
        top1 = new Coordinates(width / 2 + 1, 0);
        top2 = new Coordinates(width / 2, 1);
        top3 = new Coordinates(width / 2 - 1, 0);
        top4 = new Coordinates(width / 2 + 1, 1);
        top5 = new Coordinates(width / 2 - 1, 1);

        right = new Coordinates(width - 1, height / 2);
        right1 = new Coordinates(width - 1, height / 2 - 1);
        right2 = new Coordinates(width - 1, height / 2 + 1);
        right3 = new Coordinates(width - 2, height / 2);
        right4 = new Coordinates(width - 2, height / 2 + 1);
        right5 = new Coordinates(width - 2, height / 2 - 1);

        bottom = new Coordinates(width / 2, height - 1);
        bottom1 = new Coordinates(width / 2, height - 2);
        bottom2 = new Coordinates(width / 2 + 1, height - 1);
        bottom3 = new Coordinates(width / 2 - 1, height - 1);
        bottom4 = new Coordinates(width / 2 + 1, height - 2);
        bottom5 = new Coordinates(width / 2 - 1, height - 2);

        left = new Coordinates(0, height / 2);
        left1 = new Coordinates(0, height / 2 - 1);
        left2 = new Coordinates(1, height / 2);
        left3 = new Coordinates(0, height / 2 + 1);
        left4 = new Coordinates(1, height / 2 - 1);
        left5 = new Coordinates(1, height / 2 + 1);

        middle = new Coordinates(width / 2, height / 2);
        middle1 = new Coordinates(width / 2, height / 2 - 1);
        middle2 = new Coordinates(width / 2 + 1, height / 2);
        middle3 = new Coordinates(width / 2, height / 2 + 1);
        middle4 = new Coordinates(width / 2 - 1, height / 2);
        middle5 = new Coordinates(width / 2 + 1, height / 2 - 1);
        middle6 = new Coordinates(width / 2 + 1, height / 2 + 1);
        middle7 = new Coordinates(width / 2 - 1, height / 2 + 1);
        middle8 = new Coordinates(width / 2 - 1, height / 2 - 1);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, topLeft.getX());
        assertEquals(0, topLeft.getY());
    }

    @Test
    public void testFindSurroundingCoordinates() {
        testFindSurroundingCoordinatesCorners(topLeft, topLeft1, topLeft2, topLeft3);
        testFindSurroundingCoordinatesCorners(topRight, topRight1, topRight2, topRight3);
        testFindSurroundingCoordinatesCorners(bottomRight, bottomRight1, bottomRight2, bottomRight3);
        testFindSurroundingCoordinatesCorners(bottomLeft, bottomLeft1, bottomLeft2, bottomLeft3);

        testFindSurroundingCoordinatesEdges(top, top1, top2, top3, top4, top5);
        testFindSurroundingCoordinatesEdges(right, right1, right2, right3, right4, right5);
        testFindSurroundingCoordinatesEdges(bottom, bottom1, bottom2, bottom3, bottom4, bottom5);
        testFindSurroundingCoordinatesEdges(left, left1, left2, left3, left4, left5);

        testFindSurroundingCoordinatesCenter(middle, middle1, middle2, middle3, middle4, middle5, middle6, middle7,
                middle8);
    }

    public void testFindSurroundingCoordinatesCorners(Coordinates c0, Coordinates c1, Coordinates c2, Coordinates c3) {
        ArrayList<Coordinates> coordinates = new ArrayList<>();
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);
        for (int i = 0; i < coordinates.size(); i++) {
            assertEquals(coordinates.get(i).getX(), c0.findSurroundingCoordinates(board).get(i).getX());
            assertEquals(coordinates.get(i).getY(), c0.findSurroundingCoordinates(board).get(i).getY());
        }
    }

    public void testFindSurroundingCoordinatesEdges(Coordinates c0, Coordinates c1, Coordinates c2, Coordinates c3,
                                                    Coordinates c4, Coordinates c5) {
        ArrayList<Coordinates> coordinates = new ArrayList<>();
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);
        coordinates.add(c4);
        coordinates.add(c5);
        for (int i = 0; i < coordinates.size(); i++) {
            assertEquals(coordinates.get(i).getX(), c0.findSurroundingCoordinates(board).get(i).getX());
            assertEquals(coordinates.get(i).getY(), c0.findSurroundingCoordinates(board).get(i).getY());
        }
    }

    public void testFindSurroundingCoordinatesCenter(Coordinates c0, Coordinates c1, Coordinates c2, Coordinates c3,
                                                   Coordinates c4, Coordinates c5,  Coordinates c6,  Coordinates c7,
                                                    Coordinates c8) {
        ArrayList<Coordinates> coordinates = new ArrayList<>();
        coordinates.add(c1);
        coordinates.add(c2);
        coordinates.add(c3);
        coordinates.add(c4);
        coordinates.add(c5);
        coordinates.add(c6);
        coordinates.add(c7);
        coordinates.add(c8);
        for (int i = 0; i < coordinates.size(); i++) {
            assertEquals(coordinates.get(i).getX(), c0.findSurroundingCoordinates(board).get(i).getX());
            assertEquals(coordinates.get(i).getY(), c0.findSurroundingCoordinates(board).get(i).getY());
        }
    }
}