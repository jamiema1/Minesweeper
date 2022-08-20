package ui.gui;


import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static ui.gui.Constants.*;

// represents a panel that contains information about the current board
public class BoardPanel extends JPanel implements ActionListener {

    private int tileSize;
    private int boardPanelWidth;
    private int boardPanelHeight;

    private JLabel gameOverText;
    private JLabel mines;
    private JLabel timeLabel;

    private Timer timer;

    private MainPanel mainPanel;
    private Player player;

    // EFFECTS: calls initialize and instantiateFields
    public BoardPanel(MainPanel mainPanel) {
        initialize(mainPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel with grid layout and sets its size
    public void initialize(MainPanel mainPanel) {
        player = mainPanel.getPlayer();
        instantiateFields(mainPanel);

        setPreferredSize(new Dimension(boardPanelWidth, boardPanelHeight));
        setLayout(new GridLayout(player.getPlayerBoard().getHeight(), player.getPlayerBoard().getWidth()));

        updateTimer();
        updateBoard(player, null);
        mainPanel.updateSidePanels(boardPanelWidth, boardPanelHeight);
    }

    // MODIFIES: this
    // EFFECTS: instantiates all fields of this
    private void instantiateFields(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        Board playerBoard = player.getPlayerBoard();
        int playerBoardWidth = playerBoard.getWidth();
        int playerBoardHeight = playerBoard.getHeight();

        tileSize = Math.min(MAX_BOARD_WIDTH / playerBoardWidth, MAX_BOARD_HEIGHT / playerBoardHeight);

        if (tileSize > 50) {
            tileSize = 50;
        }

        boardPanelWidth = tileSize * playerBoardWidth;
        boardPanelHeight = tileSize * playerBoardHeight;

        timer = new Timer(1000,this);

    }

    // MODIFIES: this, mainPanel
    // EFFECTS: removes the game over label and mines label if they are not null
    public void removeLabels() {
        if (gameOverText != null) {
            gameOverText.setVisible(false);
            mainPanel.getFooterPanel().remove(gameOverText);
        }
        if (mines != null) {
            mines.setVisible(false);
            mainPanel.getHeaderPanel().remove(mines);
        }
    }

    // MODIFIES: this, mainPanel
    // EFFECTS: updates the mine label to display the new remaining mine count
    public void updateMinesLabel(Player player) {

        mines = new JLabel("Mines: " + player.getPlayerBoard().getRemainingMines());
        mines.setFont(new Font(mines.getFont().getName(), Font.PLAIN, HEADER_FONT_SIZE));
        mainPanel.getHeaderPanel().add(mines);
    }

    // MODIFIES: this
    // EFFECTS: updates the whole board by removing everything on this and then displays each tile on the player board
    private void displayEntireBoard(Player player) {

        removeAll();

        for (int i = 0; i < player.getPlayerBoard().getHeight(); i++) {
            for (int j = 0; j < player.getPlayerBoard().getWidth(); j++) {

                JButton button = new JButton();

                addButtonListener(button, j, i);

                ImageIcon icon = resizeImage(player.getPlayerBoard().getTileFromBoard(j, i).getImageIcon(),
                        tileSize, tileSize);
                button.setIcon(icon);

                add(button);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: updates only buttons with coordinates in newTiles by removing each tile individually and then adding
    //          them back again
    private void displayPartOfBoard(Player player, ArrayList<Coordinates> newTiles) {

        for (Coordinates c : newTiles) {
            JButton button = new JButton();

            int posX = c.getX();
            int posY = c.getY();
            int index = posX + player.getPlayerBoard().getWidth() * posY;

            addButtonListener(button, posX, posY);

            ImageIcon icon = resizeImage(player.getPlayerBoard().getTileFromBoard(posX, posY).getImageIcon(),
                    tileSize, tileSize);
            button.setIcon(icon);

            remove(index);
            add(button, index);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a button listener to a given button and calls makeMove if activated
    private void addButtonListener(JButton button, int posX, int posY) {
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int action = 0;
                if (e.getButton() == 3) {
                    action = 1;
                }
                makeMove(mainPanel.getPlayer().makeMove(new Move(posX, posY, action)));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    // MODIFIES: this, mainPanel
    // EFFECTS: if the player move size is 1, starts the timer, then updates and saves the board
    public void makeMove(ArrayList<Coordinates> newTiles) {
        if (player.getMoves().size() == 1) {
            timer.start();
        }
        updateBoard(player, newTiles);
        mainPanel.savePlayer();
    }

//    @Override
//    public void update(Observable o, Object arg) {
//        makeMove((Player) o, (ArrayList<Coordinates>) arg);
//    }

    // MODIFIES: this, mainPanel
    // EFFECTS: updates all the labels, updates the board, and checks if the user has won or lost
    //              - if the user has won, adds a new leaderboardEntry
    public void updateBoard(Player player, ArrayList<Coordinates> newTiles) {

        removeLabels();
        updateMinesLabel(player);

        if (newTiles == null) {
            displayEntireBoard(player);
        } else {
            displayPartOfBoard(player, newTiles);

            if (player.getPlayerBoard().checkForLose()) {
                displayEntireBoard(player);
                setGameOverText("You Lose!", new ImageIcon("./data/images/bombExplodingGIF.gif"));
            } else if (player.getPlayerBoard().checkForWin()) {
                setGameOverText("You win!", new ImageIcon("./data/images/confettiGIF.gif"));

                mainPanel.getLeaderboard().addEntry(new LeaderboardEntry(player.getPlayerBoard().getDifficulty(),
                        "Jamie", player.getTime()));
                mainPanel.saveLeaderboard();
            }
        }

        revalidate();
        repaint();
    }

    // MODIFIES: this, mainPanel
    // EFFECTS: sets the game over text and stops the timer
    private void setGameOverText(String text, ImageIcon img) {
        gameOverText = new JLabel(text, SwingConstants.CENTER);
        gameOverText.setFont(new Font(gameOverText.getFont().getName(), Font.PLAIN, HEADER_FONT_SIZE));
        timer.stop();
        mainPanel.getLeftPanel().add(new JLabel(img));
        mainPanel.getRightPanel().add(new JLabel(img));
        mainPanel.getFooterPanel().add(gameOverText);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateTimer();
    }

    // MODIFIES: this
    // EFFECTS: updates the current timer
    private void updateTimer() {
        removeTimer();


        int time = player.getTime();

        if (time != 0) {
            mainPanel.savePlayer();
        }

        timeLabel = new JLabel("Time: " + time);
        timeLabel.setFont(new Font(timeLabel.getFont().getName(), Font.PLAIN, HEADER_FONT_SIZE));

        mainPanel.getHeaderPanel().add(timeLabel,0);

        time++;
        player.setTime(time);
    }

    // MODIFIES: this, mainPanel
    // EFFECTS: removes the timer
    public void removeTimer() {
        if (timeLabel != null) {
            timeLabel.setVisible(false);
            mainPanel.getHeaderPanel().remove(timeLabel);
        }
    }

    // EFFECTS: starts the timer
    public void startTime() {
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: removes all labels and stops the timer
    public void cleanup() {
        removeLabels();
        timer.stop();
        removeTimer();
        setVisible(false);
    }
}