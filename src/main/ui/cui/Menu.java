package ui.cui;

//import exceptions.LoadInvalidBoardException;

import java.util.Scanner;

// contains all menu screens that display information for how to play and allow the user to start a game
public class Menu {
    private Scanner scanner;

    // EFFECTS: calls printOptions
    public Menu() {
        scanner = new Scanner(System.in);
        printOptions();
    }

    // EFFECTS: displays the starting options a user can choose from
    //              - instructions
    //              - new game
    //              - exit
    public void printOptions() {
        boolean endGame = false;
        while (!endGame) {
            System.out.println("How to play: enter 0");
            System.out.println("Start game: enter 1");
            System.out.println("To exit: enter 2");

            int input = scanner.nextInt();

            switch (input) {
                case 0:
                    instructionScreen();
                    break;
                case 1:
                    gameOptions();
                    break;
                case 2:
                    endGame = true;
                    break;
            }
        }
    }

    // EFFECTS: displays the instructions screen
    public void instructionScreen() {
        boolean validOption = false;

        while (!validOption) {

            System.out.println("To view the tile symbols: enter 0");
            System.out.println("To learn the basics of how to play: enter 1");
            System.out.println("To exit: enter 2");
            int input = scanner.nextInt();

            switch (input) {
                case 0:
                    printTileTypes();
                    break;
                case 1:
                    introduction();
                    break;
                case 2:
                    validOption = true;
                    break;
            }
        }
    }

    // EFFECTS: displays the tile types
    public void printTileTypes() {
        System.out.println("The following symbols are used to represent different states a tile can have:");
        System.out.println("X   | Flagged mine");
        System.out.println("+   | Incorrectly flagged mine");
        System.out.println("!   | Clicked mine");
        System.out.println("*   | Mine");
        System.out.println("O   | Covered tile, can be opened or flagged");
        System.out.println("    | (blank space) Empty tile, no mines nearby");
        System.out.println("1-8 | Corresponding number of mines nearby\n");

        boolean validOption = false;

        while (!validOption) {

            System.out.println("To exit: enter 0");
            int input = scanner.nextInt();

            if (input == 0) {
                validOption = true;
            }
        }
    }

    // EFFECTS: displays a demo for how to play the game
    public void introduction() {
        demoBoard();
        demoLeftClick();
        demoRightClick();

        boolean validOption = false;

        while (!validOption) {

            System.out.println("To exit: enter 0");
            int input = scanner.nextInt();

            if (input == 0) {
                validOption = true;
            }
        }
    }

    // EFFECTS: displays the demo board
    public void demoBoard() {
        System.out.println("Consider the following board:");
        System.out.println("mines: 1");
        System.out.println("  012");
        System.out.println("  ---");
        System.out.println("0|OOO");
        System.out.println("1|OOO");
        System.out.println("2|OOO\n");
    }

    // EFFECTS: displays the demo board after a left click
    public void demoLeftClick() {
        System.out.println("To open a tile on the board, enter the tiles x-position followed by its y-position,"
                + " then 0 to indicate you want to open the tile.");
        System.out.println("For example, to open the tile in the top right corner,"
                + " you would type: 2 0 0.\n");
        System.out.println("mines: 1");
        System.out.println("  012");
        System.out.println("  ---");
        System.out.println("0|   ");
        System.out.println("1|11 ");
        System.out.println("2|O1 \n");
    }

    // EFFECTS: displays the demo board after a right click
    public void demoRightClick() {
        System.out.println("To flag a tile on the board and indicate that it is a mine, enter the tiles x-position"
                + " followed by its y-position, then 1 to indicate you want to flag the tile.");
        System.out.println("For example, to flag the tile in the bottom left corner,"
                + " you would type: 0 2 1.\n");
        System.out.println("mines: 0");
        System.out.println("  012");
        System.out.println("  ---");
        System.out.println("0|   ");
        System.out.println("1|11 ");
        System.out.println("2|X1 \n");
    }

    // EFFECTS: displays the board options a user can choose from
    public void gameOptions() {
        boolean validOption = false;
        while (!validOption) {
            System.out.println("Beginner: enter 0");
            System.out.println("Intermediate: enter 1");
            System.out.println("Expert: enter 2");
            System.out.println("Custom: enter 3");
            System.out.println("Load saved board: enter 4");

            int input = scanner.nextInt();

            validOption = startGame(input);
        }
    }

    // EFFECTS: allows the user to select the board type they would like to play on
    public boolean startGame(int input) {
        try {
            switch (input) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    new PlayerApplication(input);
                    break;
                default:
                    return false;
            }
        } catch (Exception e) { //LoadInvalidBoardException e) {
            return false;
        }
        return true;
    }
}
