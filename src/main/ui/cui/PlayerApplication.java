package ui.cui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.exceptions.LoadInvalidBoardException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// represents a player playing the game
public class PlayerApplication {

    private Scanner scanner;
    private Player player;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_FILE_LOCATION = "./data/player.json";

    // creates a PlayerApplication with a given difficulty
    public PlayerApplication(int difficulty) throws Exception { //LoadInvalidBoardException {
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_FILE_LOCATION);
        jsonReader = new JsonReader(JSON_FILE_LOCATION);
        startGame(difficulty);
    }

    // MODIFIES: this
    // EFFECTS: creates a new player based on the given difficulty and starts to run the game
    private void startGame(int difficulty) throws Exception { //LoadInvalidBoardException {

        if (difficulty == 0) {
            player = new Player(9,9,10);
        } else if (difficulty == 1) {
            player = new Player(16,16,40);
        } else if (difficulty == 2) {
            player = new Player(30, 16, 99);
        } else if (difficulty == 3) {
            int width = askForDimension("Width");
            int height = askForDimension("Height");
            int mines = askForMines(width, height);
            player = new Player(width, height, mines);
        } else if (difficulty == 4) {
            loadPlayer();
        }

        runGame();
    }

    // MODIFIES: this
    // EFFECTS: loads a player from a JSON file, throws a load invalid board exception if game over is true
    private void loadPlayer() throws LoadInvalidBoardException {
        try {
            player = jsonReader.read();
            if (player.getPlayerBoard().getGameOver()) {
                System.out.println("Cannot load board that is complete: " + JSON_FILE_LOCATION);
                throw new LoadInvalidBoardException();
            }
            System.out.println("Loaded player from: " + JSON_FILE_LOCATION);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_FILE_LOCATION);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves a player to a JSON file
    private void savePlayer() {
        try {
            jsonWriter.write(player);
            //System.out.println("Saved player to: " + JSON_FILE_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_FILE_LOCATION);
        }
    }

    // EFFECTS: asks the user for a dimension and returns that number if it is >=4
    public int askForDimension(String text) {
        int dimension = 0;
        while (dimension <= 3) {
            System.out.println(text + ":");
            dimension = scanner.nextInt();
        }
        return dimension;
    }

    // EFFECTS: asks the user for a number of mines and returns that number if it produces a solvable board
    public int askForMines(int width, int height) {
        int mines = 0;
        while (mines <= 0 || mines > width * height - 9) {
            System.out.println("Mines:");
            mines = scanner.nextInt();
        }
        return mines;
    }

    // EFFECTS: creates a loop asking the user to make a move. Checks if the player has lost or won and saves the player
    //          after every move
    public void runGame() {
        printAllMoves();
        printBoard(player.getPlayerBoard());

        while (!player.getPlayerBoard().getGameOver()) {
            askForMove();
            printBoard(player.getPlayerBoard());
            //printBoard(player.getMasterBoard());

            if (!checkForLose()) {
                checkForWin();
            }
            savePlayer();
        }
        printAllMoves();
    }

    // EFFECTS: asks the user to make a move
    private void askForMove() {
        ArrayList<Coordinates> validMove = null;

        while (validMove == null) {

            System.out.println("X Y Action:");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int action = scanner.nextInt();

            validMove = player.makeMove(new Move(x, y, action));
        }
    }

    // EFFECTS: if the number of remaining mines are 0 and the entire board is opened, then prints "You win!"
    public void checkForWin() {
        if (player.getPlayerBoard().checkForWin()) {
            System.out.println("You win!\n");
        }
    }

    // EFFECTS: if any tile on the board is a mine, then prints "You lose!" and returns true, returns false otherwise
    public boolean checkForLose() {
        if (player.getPlayerBoard().checkForLose()) {
            System.out.println("You lose!\n");
            return true;
        }
        return false;
    }

    // EFFECTS: prints out the current board
    public void printBoard(Board board) {
        int width = board.getWidth();
        int height = board.getHeight();

        System.out.println('\n' + "Mines: " + board.getRemainingMines() + '\n');

        printWidthHeader(width,height);

        for (int i = 0; i < height; i++) {
            printSpacing(height, i);
            for (int j = 0; j < width; j++) {
                for (Tile t : Tile.TILES) {
                    if (t == board.getTileFromBoard(j,i)) {
                        System.out.print(t.getSymbol());
                    }
                }
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }

    // EFFECTS: prints out the width indexes above the board
    public void printWidthHeader(int width, int height) {
        int widthMagnitude = (int) Math.floor(Math.log10(width - 1));
        for (int i = widthMagnitude + 1; i > 0; i--) {
            printWidthHeaderSpacing(height);
            for (int j = 0; j < width; j++) {
                System.out.print((j / (int) Math.pow(10, i - 1)) % 10);
            }
            System.out.print('\n');
        }

        printWidthHeaderSpacing(height);

        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }

        System.out.print('\n');
    }

    // EFFECTS: prints out the blank spacing before the width header lines
    public void printWidthHeaderSpacing(int height) {
        int heightMagnitude = (int) Math.floor(Math.log10(height - 1));
        for (int i = 0; i < heightMagnitude + 2; i++) {
            System.out.print(" ");
        }
    }

    // EFFECTS: prints out the spacing before the height index entries
    public void printSpacing(int height, int index) {
        int heightMagnitude = (int) Math.floor(Math.log10(height - 1));
        int indexMagnitude = (int) Math.floor(Math.log10(index));
        if (index == 0) {
            for (int i = 0; i < heightMagnitude; i++) {
                System.out.print(" ");
            }
        } else {
            for (int i = 0; i < heightMagnitude - indexMagnitude; i++) {
                System.out.print(" ");
            }
        }
        System.out.print(index + "|");
    }

    // EFFECTS: prints out a list of all the moves made
    public void printAllMoves() {
        System.out.println("Moves:");
        for (Move m : player.getMoves()) {
            System.out.println(m.getX() + " " + m.getY() + " " + m.getAction());
        }
        System.out.print('\n');
    }
}
