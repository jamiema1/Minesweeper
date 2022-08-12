package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static model.Tile.*;

// jUnit tests for Tile class
public class TileTest {

    private Tile tile1;
    private Tile tile2;

    @BeforeEach
    public void setup() {
        tile1 = new Tile(Value.COVERED, "O");
        tile2 = new Tile(Value.EMPTY, " ", 0);
    }


    @Test
    public void testConstructor1() {
        assertEquals(Value.COVERED, tile1.getValue());
        assertEquals("O",tile1.getSymbol());
    }

    @Test
    public void testConstructor2() {
        assertEquals(Value.EMPTY, tile2.getValue());
        assertEquals(" ",tile2.getSymbol());
        assertEquals(0, tile2.getNumber());
    }

    @Test
    public void testGetImage() {
        assertNull(tile2.getImageIcon());
    }
}
