package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Tile.*;
import static org.junit.jupiter.api.Assertions.*;

// jUnit tests for Move class
public class MoveTest {

    private Move leftClick;
    private Move rightClick;
    private Move invalidAction;
    private Move invalidMove1;
    private Move invalidMove2;
    private Move invalidMove3;
    private Move invalidMove4;
    private Move invalidMove5;

    private Board coveredBoard;
    private Board flaggedBoard;
    private Board oneMineBoard;
    private Board oneBoard;
    private Board oneEmptyBoard;
    private static final int width = 5;
    private static final int height = 5;
    private static final int mines = 1;

    private static final int posX = 0;
    private static final int posY = 0;
    private static final int invalidXPos1 = -1;
    private static final int invalidXPos2 = width;
    private static final int invalidYPos1 = -1;
    private static final int invalidYPos2 = height;

    @BeforeEach
    public void setup() {
        leftClick = new Move(posX,posY,0);
        rightClick = new Move(posX,posY,1);
        invalidAction = new Move(posX,posY, 2);

        invalidMove1 = new Move(invalidXPos1, posY,0);
        invalidMove2 = new Move(invalidXPos2, posY,0);
        invalidMove3 = new Move(posX, invalidYPos1,0);
        invalidMove4 = new Move(posX, invalidYPos2,0);
        invalidMove5 = new Move(invalidXPos1, invalidYPos1, 1);

        coveredBoard = new Board(width, height, mines, Board.Difficulty.CUSTOM);

        flaggedBoard = new Board(width, height, mines, Board.Difficulty.CUSTOM);
        flaggedBoard.setTileOnBoard(posX, posY, FLAGGED);

        oneMineBoard = new Board(width, height, mines, Board.Difficulty.CUSTOM);
        oneMineBoard.setTileOnBoard(posX, posY, MINE);

        oneBoard = new Board(width, height, mines, Board.Difficulty.CUSTOM);
        oneBoard.setTileOnBoard(posX, posY, ONE);

        oneEmptyBoard = new Board(width, height, mines, Board.Difficulty.CUSTOM);
        oneEmptyBoard.setTileOnBoard(posX, posY, EMPTY);
        oneEmptyBoard.setTileOnBoard(posX + 1, posY, ONE);
        oneEmptyBoard.setTileOnBoard(posX, posY + 1, TWO);
        oneEmptyBoard.setTileOnBoard(posX + 1, posY + 1, SIX);
    }


    @Test
    public void testMakeFirstMove() {
        assertTrue(invalidMove1.makeFirstMove(coveredBoard,oneBoard).isEmpty());
        assertTrue(invalidMove2.makeFirstMove(coveredBoard,oneBoard).isEmpty());
        assertTrue(invalidMove3.makeFirstMove(coveredBoard,oneBoard).isEmpty());
        assertTrue(invalidMove4.makeFirstMove(coveredBoard,oneBoard).isEmpty());
        assertTrue(invalidMove5.makeFirstMove(coveredBoard,oneBoard).isEmpty());

        assertTrue(invalidAction.makeFirstMove(coveredBoard,oneBoard).isEmpty());
        assertTrue(rightClick.makeFirstMove(coveredBoard,oneBoard).isEmpty());

        assertTrue(leftClick.makeFirstMove(coveredBoard,oneBoard).size() < width * height);
        assertFalse(leftClick.makeFirstMove(coveredBoard,oneBoard).isEmpty());
        assertEquals(posX,leftClick.makeFirstMove(coveredBoard,oneBoard).get(0).getX());
        assertEquals(posY,leftClick.makeFirstMove(coveredBoard,oneBoard).get(0).getY());
    }

    @Test
    public void testMakeMoveOutOfBounds() {

        assertTrue(invalidMove1.makeMove(coveredBoard, oneBoard).isEmpty());
        assertTrue(invalidMove2.makeMove(coveredBoard, oneBoard).isEmpty());
        assertTrue(invalidMove3.makeMove(coveredBoard, oneBoard).isEmpty());
        assertTrue(invalidMove4.makeMove(coveredBoard, oneBoard).isEmpty());
        assertTrue(invalidMove5.makeMove(coveredBoard, oneBoard).isEmpty());
    }

    @Test
    public void testMakeMoveInvalidAction() {
        assertTrue(invalidAction.makeMove(coveredBoard,oneBoard).isEmpty());
        assertEquals(2,invalidAction.getAction());
    }

    @Test
    public void testMakeMoveFailToMeetTileRequirement() {
        assertEquals(posX, leftClick.getX());
        assertEquals(posY, leftClick.getY());
        assertTrue(leftClick.makeMove(oneEmptyBoard,oneBoard).isEmpty());
        assertTrue(rightClick.makeMove(oneEmptyBoard,oneBoard).isEmpty());
        coveredBoard.setRemainingMines(0);
        assertTrue(rightClick.makeMove(coveredBoard,oneBoard).isEmpty());
    }

    @Test
    public void testMakeMoveMeetsAllRequirements() {
        assertEquals(1, rightClick.makeMove(coveredBoard,oneBoard).size());
        assertEquals(2, rightClick.makeMove(coveredBoard,oneBoard).size());
        assertEquals(3, rightClick.makeMove(flaggedBoard,oneBoard).size());
        assertEquals(1, leftClick.makeMove(coveredBoard,oneBoard).size());
    }


    @Test
    public void testLeftClickMine() {
        leftClick.leftClick(coveredBoard, oneMineBoard);
        assertEquals(CLICKED_MINE, coveredBoard.getTileFromBoard(posX, posY));
    }

    @Test
    public void testLeftClickEmpty() {
        leftClick.leftClick(coveredBoard,oneEmptyBoard);
        assertEquals(EMPTY,coveredBoard.getTileFromBoard(posX,posY));
        assertEquals(ONE,coveredBoard.getTileFromBoard(posX + 1,posY));
        assertEquals(TWO,coveredBoard.getTileFromBoard(posX,posY + 1));
        assertEquals(SIX,coveredBoard.getTileFromBoard(posX + 1,posY + 1));
    }

    @Test
    public void testLeftClickNumber() {
        leftClick.leftClick(coveredBoard,oneBoard);
        assertEquals(ONE,coveredBoard.getTileFromBoard(posX,posY));
    }


    @Test
    public void testRightClickFlagged() {
        assertEquals(1,flaggedBoard.getRemainingMines());
        assertEquals(FLAGGED, flaggedBoard.getTileFromBoard(posX,posY));
        rightClick.rightClick(flaggedBoard);
        assertEquals(COVERED, flaggedBoard.getTileFromBoard(posX,posY));
        assertEquals(2,flaggedBoard.getRemainingMines());
    }

    @Test
    public void testRightClickCovered() {
        assertEquals(1,coveredBoard.getRemainingMines());
        assertEquals(COVERED, coveredBoard.getTileFromBoard(posX,posY));
        rightClick.rightClick(coveredBoard);
        assertEquals(FLAGGED, coveredBoard.getTileFromBoard(posX,posY));
        assertEquals(0,coveredBoard.getRemainingMines());
    }
}
