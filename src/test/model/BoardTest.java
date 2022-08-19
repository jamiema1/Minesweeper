package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static model.Tile.*;

// jUnit tests for Board class
public class BoardTest {

    private Board emptyBoard;
    private Board oneMineBoard;
    private Board twoMineBoard;
    private Board clickedMineBoard;
    private Board flaggedBoard;
    private Board playerBoard;
    private Board masterBoard;
    private Board fullBoard;
    private int width;
    private int height;
    private int mines;

    @BeforeEach
    public void setup() {
        width = 5;
        height = 5;
        mines = 5;

        playerBoard = new Board(width, height, mines);
        masterBoard = new Board(width, height, mines);

        emptyBoard = new Board(width, height, mines);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                emptyBoard.setTileOnBoard(j,i,EMPTY);
            }
        }
        emptyBoard.setRemainingMines(0);

        oneMineBoard = new Board(width, height, mines);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                oneMineBoard.setTileOnBoard(j,i,EMPTY);
            }
        }
        oneMineBoard.setTileOnBoard(0,0,MINE);
        oneMineBoard.setRemainingMines(1);

        twoMineBoard = new Board(width, height, mines);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                twoMineBoard.setTileOnBoard(j,i,EMPTY);
            }
        }
        twoMineBoard.setTileOnBoard(0,0,MINE);
        twoMineBoard.setTileOnBoard(1,0,MINE);
        twoMineBoard.setRemainingMines(2);

        clickedMineBoard = new Board(width, height, mines);
        clickedMineBoard.setTileOnBoard(0,0,CLICKED_MINE);
        clickedMineBoard.setRemainingMines(1);

        flaggedBoard = new Board(width, height, mines);
        flaggedBoard.setTileOnBoard(1,0,FLAGGED);
        flaggedBoard.setRemainingMines(0);

        fullBoard = new Board(width, height, width * height - 4);
    }

    @Test
    public void testConstructor() {
        assertEquals(width,playerBoard.getWidth());
        assertEquals(height,playerBoard.getHeight());
        assertEquals(mines,playerBoard.getMines());
    }

    @Test
    public void testGeneratePlayerBoard() {
        playerBoard.generatePlayerBoard();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                assertEquals(COVERED, playerBoard.getTileFromBoard(j,i));
            }
        }
    }

    @Test
    public void testGenerateMasterBoard() {
        playerBoard.generateMasterBoard(0,0);
        int mine = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                assertFalse(playerBoard.getTileFromBoard(j,i) == COVERED);
                if (playerBoard.getTileFromBoard(j,i) == MINE) {
                    mine++;
                }
            }
        }
        assertEquals(mines,mine);

        masterBoard.generateMasterBoard(1,1);
        int mine1 = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                assertFalse(masterBoard.getTileFromBoard(j,i) == COVERED);
                if (masterBoard.getTileFromBoard(j,i) == MINE) {
                    mine1++;
                }
            }
        }

        assertEquals(EMPTY, masterBoard.getTileFromBoard(1,1));
        assertEquals(mines,mine1);

        fullBoard.generateMasterBoard(0,0);
        int mine2 = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i == 0 && j == 0) || (i == 1 && j == 0) || (i == 0 && j == 1)  || (i == 1 && j == 1)) {
                    assertTrue(fullBoard.getTileFromBoard(j,i) != MINE);
                } else {
                    assertTrue(fullBoard.getTileFromBoard(j, i) == MINE);
                    mine2++;
                }
            }
        }
        assertEquals(width * height - 4, mine2);
    }

    @Test
    public void testGenerateEntireBoardWithTiles() {
        playerBoard.generateEntireBoardWithTiles(COVERED);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                assertEquals(COVERED, playerBoard.getTileFromBoard(j,i));
            }
        }
        playerBoard.generateEntireBoardWithTiles(ONE);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                assertEquals(ONE, playerBoard.getTileFromBoard(j,i));
            }
        }
    }

    // TODO
    @Test
    public void testGenerateMineTiles() {
        playerBoard.generateMineTiles(-1,-1);
        int mine = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (playerBoard.getTileFromBoard(j,i) == MINE) {
                    mine++;
                }
            }
        }
        assertEquals(mines,mine);

        masterBoard.generateMineTiles(1,1);
        int mine1 = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (masterBoard.getTileFromBoard(j,i) == MINE) {
                    mine1++;
                }
            }
        }
        //assertEquals(EMPTY, masterBoard.getTileFromBoard(1,1));
        //assertEquals(mines,mine1);
    }

    @Test
    public void testGenerateNumberTiles() {

        oneMineBoard.generateNumberTiles();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((j == 1 && i == 0) || (j == 0 && i == 1) || (j == 1 && i == 1)) {
                    assertEquals(ONE, oneMineBoard.getTileFromBoard(j,i));
                } else if (j == 0 && i == 0) {
                    assertEquals(MINE, oneMineBoard.getTileFromBoard(j,i));
                } else {
                    assertEquals(EMPTY, oneMineBoard.getTileFromBoard(j,i));

                }
            }
        }


        twoMineBoard.generateNumberTiles();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((j == 2 && i == 0) || (j == 2 && i == 1)) {
                    assertEquals(ONE, twoMineBoard.getTileFromBoard(j,i));
                } else if ((j == 1 && i == 1) || (j == 0 && i == 1)) {
                    assertEquals(TWO, twoMineBoard.getTileFromBoard(j,i));
                } else if ((j == 1 && i == 0) || (j == 0 && i == 0)) {
                    assertEquals(MINE, twoMineBoard.getTileFromBoard(j,i));
                } else {
                    assertEquals(EMPTY, twoMineBoard.getTileFromBoard(j,i));
                }
            }
        }
    }

    @Test
    public void testGenerateMinesOnPlayerBoard() {
        playerBoard.generateMinesOnPlayerBoard(oneMineBoard);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 && j == 0) {
                    assertEquals(MINE, playerBoard.getTileFromBoard(j,i));
                } else {
                    assertEquals(COVERED, playerBoard.getTileFromBoard(j,i));
                }
            }
        }

        flaggedBoard.generateMinesOnPlayerBoard(oneMineBoard);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 && j == 1) {
                    assertEquals(INCORRECT_FLAG, flaggedBoard.getTileFromBoard(j,i));
                } else if (i == 0 && j == 0) {
                    assertEquals(MINE, flaggedBoard.getTileFromBoard(j,i));
                } else {
                    assertEquals(COVERED, flaggedBoard.getTileFromBoard(j,i));
                }
            }
        }
    }

    @Test
    public void testCheckEntireBoardOpened() {
        assertFalse(playerBoard.checkEntireBoardOpened());
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                playerBoard.setTileOnBoard(j,i,EMPTY);
            }
        }
        assertTrue(playerBoard.checkEntireBoardOpened());
    }

    @Test
    public void testCheckForWin() {

        emptyBoard.setRemainingMines(1);
        assertFalse(emptyBoard.checkForWin());
        assertFalse(emptyBoard.getGameOver());

        assertFalse(playerBoard.checkForWin());
        assertFalse(playerBoard.getGameOver());

        playerBoard.setRemainingMines(0);
        assertFalse(playerBoard.checkForWin());
        assertFalse(emptyBoard.getGameOver());

        assertFalse(oneMineBoard.checkForWin());
        assertFalse(oneMineBoard.getGameOver());


        emptyBoard.setRemainingMines(0);
        assertTrue(emptyBoard.checkForWin());
        assertTrue(emptyBoard.getGameOver());
    }

    @Test
    public void testCheckForLose() {
        assertFalse(emptyBoard.checkForLose());
        assertFalse(emptyBoard.getGameOver());

        assertFalse(playerBoard.checkForLose());
        assertFalse(playerBoard.getGameOver());

        assertFalse(oneMineBoard.checkForLose());
        assertFalse(oneMineBoard.getGameOver());

        assertTrue(clickedMineBoard.checkForLose());
        assertTrue(clickedMineBoard.getGameOver());
    }
}
