package model;

import org.json.JSONObject;
import persistence.JsonWriting;

import javax.swing.*;

// represents a tile on the board with a value, symbol and possible number
public class Tile implements JsonWriting {
    private Value value;
    private String symbol;
    private ImageIcon imageIcon;
    private int number;

    public static final Tile FLAGGED = new Tile(Value.FLAGGED,  "X",
            new ImageIcon("./data/images/flaggedTile.png"));
    public static final Tile INCORRECT_FLAG = new Tile(Value.INCORRECT_FLAG,  "+",
            new ImageIcon("./data/images/incorrectFlagTile.png"));
    public static final Tile CLICKED_MINE = new Tile(Value.CLICKED_MINE,  "!",
            new ImageIcon("./data/images/clickedMineTile.png"));
    public static final Tile MINE = new Tile(Value.MINE,  "*",
            new ImageIcon("./data/images/mineTile.png"));
    public static final Tile COVERED = new Tile(Value.COVERED, "O",
            new ImageIcon("./data/images/coveredTile.png"));
    public static final Tile EMPTY = new Tile(Value.EMPTY, " ",
            new ImageIcon("./data/images/emptyTile.png"), 0);
    public static final Tile ONE = new Tile(Value.ONE,  "1",
            new ImageIcon("./data/images/oneTile.png"), 1);
    public static final Tile TWO = new Tile(Value.TWO,"2",
            new ImageIcon("./data/images/twoTile.png"), 2);
    public static final Tile THREE = new Tile(Value.THREE, "3",
            new ImageIcon("./data/images/threeTile.png"), 3);
    public static final Tile FOUR = new Tile(Value.FOUR, "4",
            new ImageIcon("./data/images/fourTile.png"),4);
    public static final Tile FIVE = new Tile(Value.FIVE, "5",
            new ImageIcon("./data/images/fiveTile.png"), 5);
    public static final Tile SIX = new Tile(Value.SIX, "6",
            new ImageIcon("./data/images/sixTile.png"), 6);
    public static final Tile SEVEN = new Tile(Value.SEVEN, "7",
            new ImageIcon("./data/images/sevenTile.png"), 7);
    public static final Tile EIGHT = new Tile(Value.EIGHT, "8",
            new ImageIcon("./data/images/eightTile.png"), 8);

    public static final Tile[] TILES = new Tile[]{FLAGGED, INCORRECT_FLAG, CLICKED_MINE, MINE, COVERED, EMPTY,
            ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT};

    // EFFECTS: creates a tile with a given value and symbol
    public Tile(Value value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    // EFFECTS: creates a tile with a given value, symbol and number
    public Tile(Value value, String symbol, int number) {
        this.value = value;
        this.symbol = symbol;
        this.number = number;
    }

    // EFFECTS: creates a tile with a given value, symbol and number
    public Tile(Value value, String symbol, ImageIcon imageIcon) {
        this.value = value;
        this.symbol = symbol;
        this.imageIcon = imageIcon;
    }

    // EFFECTS: creates a tile with a given value, symbol and number
    public Tile(Value value, String symbol, ImageIcon imageIcon, int number) {
        this.value = value;
        this.symbol = symbol;
        this.imageIcon = imageIcon;
        this.number = number;
    }

    // getters and setters
    public Value getValue() {
        return value;
    }

    public String getSymbol() {
        return symbol;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public int getNumber() {
        return number;
    }

    // EFFECTS: returns a JSON object that contains a tile
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tile", value);
        return json;
    }
}
