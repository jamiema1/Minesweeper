package ui.gui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.exceptions.LoadInvalidBoardException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import static ui.gui.Constants.*;

// represents the main panel that all other panels are added to
public class MainPanel extends JFrame implements MouseListener, WindowListener {

    private Player player;
    private Leaderboard leaderboard;
    private JButton settingsButton;
    private BoardPanel boardPanel;
    private SettingsPanel settingsPanel;
    private NewGamePanel newGamePanel;

    private Popup settingsPopup;

    private JPanel headerPanel;
    private JPanel footerPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private JsonWriter jsonWriterPlayer;
    private JsonReader jsonReaderPlayer;
    private static final String JSON_FILE_LOCATION_PLAYER = "./data/player.json";
    private JsonWriter jsonWriterLeaderboard;
    private JsonReader jsonReaderLeaderboard;
    private static final String JSON_FILE_LOCATION_LEADERBOARD = "./data/leaderboard.json";

    // EFFECTS: calls initialize
    public MainPanel() {
        initialize();
    }

    // MODIFIES: this
    // EFFECTS: creates a panel the size of the screen and setups the features of the panel
    public void initialize() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(0,0));
        addWindowListener(this);

        instantiateFields();
        addComponents();

        repaint();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: instantiates all fields of this
    public void instantiateFields() {

        player = new Player(INITIAL_BOARD_WIDTH, INITIAL_BOARD_HEIGHT, INITIAL_BOARD_MINES,
                Board.Difficulty.BEGINNER);

        jsonWriterPlayer = new JsonWriter(JSON_FILE_LOCATION_PLAYER);
        jsonReaderPlayer = new JsonReader(JSON_FILE_LOCATION_PLAYER);
        jsonWriterLeaderboard = new JsonWriter(JSON_FILE_LOCATION_LEADERBOARD);
        jsonReaderLeaderboard = new JsonReader(JSON_FILE_LOCATION_LEADERBOARD);

        loadLeaderboard();

        headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, HEADER_HORIZONTAL_GAP,
                (HEADER_HEIGHT - SETTINGS_BUTTON_HEIGHT) / 2));
        headerPanel.setMaximumSize(new Dimension(SCREEN_WIDTH, HEADER_HEIGHT));
        //headerPanel.setBackground(Color.pink);

        settingsButton = new JButton(resizeImage(new ImageIcon("./data/images/settingsIcon.png"),
                SETTINGS_BUTTON_WIDTH,SETTINGS_BUTTON_HEIGHT));
        settingsButton.setPreferredSize(new Dimension(SETTINGS_BUTTON_WIDTH,SETTINGS_BUTTON_HEIGHT));
        settingsButton.addMouseListener(this);

        settingsPanel = new SettingsPanel(this);
        boardPanel = new BoardPanel(this);

        //player.addObserver(boardPanel);
        createSettingsPanelPopup();
    }

    // MODIFIES: this
    // EFFECTS: adds the settingsButton and boardPanel to this
    public void addComponents() {
        headerPanel.add(settingsButton,1);
        add(headerPanel, BorderLayout.PAGE_START);
        add(boardPanel, BorderLayout.CENTER);
    }

    /**
     * Popup
     */

    // MODIFIES: this
    // EFFECTS: creates a popup for the settings panel
    public void createSettingsPanelPopup() {
        PopupFactory pf = new PopupFactory();
        settingsPopup = pf.getPopup(this.getContentPane(), settingsPanel,(SCREEN_WIDTH - SETTINGS_PANEL_WIDTH) / 2,
                (SCREEN_HEIGHT - SETTINGS_PANEL_HEIGHT) / 2);
    }

    /**
     * Side panels
     */

    // MODIFIES: this
    // EFFECTS: updates the side panels given a new width and height
    public void updateSidePanels(int boardPanelWidth, int boardPanelHeight) {

        footerPanel = new JPanel();
        createSidePanel(footerPanel,SCREEN_WIDTH, SCREEN_HEIGHT - (TASKBAR_HEIGHT + HEADER_HEIGHT + boardPanelHeight));

        leftPanel = new JPanel();
        createSidePanel(leftPanel, (SCREEN_WIDTH - boardPanelWidth) / 2, boardPanelHeight);

        rightPanel = new JPanel();
        createSidePanel(rightPanel, (SCREEN_WIDTH - boardPanelWidth) / 2, boardPanelHeight);

        add(footerPanel, BorderLayout.PAGE_END);
        add(leftPanel, BorderLayout.LINE_START);
        add(rightPanel, BorderLayout.LINE_END);
    }

    // EFFECTS: specifies the layout and dimensions of a given side panel
    public void createSidePanel(JPanel panel, int width, int height) {
        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(width, height));
    }

    // MODIFIES: this
    // EFFECTS: removes the side panels, creates popups and resets the player and boardPanel
    public void cleanup() {
        remove(leftPanel);
        remove(rightPanel);
        remove(footerPanel);
        remove(boardPanel);

        createSettingsPanelPopup();

        boardPanel.cleanup();

        BoardPanel newBoardPanel = new BoardPanel(this);

        if (player.getTime() != 1) {
            newBoardPanel.startTime();
        }

        add(newBoardPanel, BorderLayout.CENTER);
        setBoardPanel(newBoardPanel);
    }

    /**
     * JSON handlers
     */

    // MODIFIES: this
    // EFFECTS: loads a player from a JSON file, throws a load invalid board exception if game over is true
    public void loadPlayer() throws LoadInvalidBoardException {
        try {
            Player newPlayer = jsonReaderPlayer.read();
            if (newPlayer.getPlayerBoard().getGameOver()) {
                System.out.println("Cannot load board that is complete: " + JSON_FILE_LOCATION_PLAYER);
                throw new LoadInvalidBoardException();
            }
            newGamePanel.setNewPlayer(newPlayer);
            //System.out.println("Loaded player from: " + JSON_FILE_LOCATION_PLAYER);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_FILE_LOCATION_PLAYER);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves a player to a JSON file
    public void savePlayer() {
        try {
            jsonWriterPlayer.write(player);
            //System.out.println("Saved player to: " + JSON_FILE_LOCATION_PLAYER);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_FILE_LOCATION_PLAYER);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads a leaderboard from a JSON file
    public void loadLeaderboard() {
        try {
            leaderboard = jsonReaderLeaderboard.read(false);
            //System.out.println("Loaded leaderboard from: " + JSON_FILE_LOCATION_LEADERBOARD);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_FILE_LOCATION_LEADERBOARD);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves a leaderboard to a JSON file
    public void saveLeaderboard() {
        try {
            jsonWriterLeaderboard.write(leaderboard);
            //System.out.println("Saved leaderboard to: " + JSON_FILE_LOCATION_LEADERBOARD);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_FILE_LOCATION_LEADERBOARD);
        }
    }

    /**
     * getters and setters
     */

    public SettingsPanel getSettingsPanel() {
        return settingsPanel;
    }

    public void setBoardPanel(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    public void setNewGamePanel(NewGamePanel newGamePanel) {
        this.newGamePanel = newGamePanel;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Popup getSettingsPopup() {
        return settingsPopup;
    }

    public JPanel getHeaderPanel() {
        return headerPanel;
    }

    public JPanel getFooterPanel() {
        return footerPanel;
    }

    public JPanel getLeftPanel() {
        return leftPanel;
    }

    public JPanel getRightPanel() {
        return rightPanel;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    /**
     * Event listeners
     */

    // Mouse event listeners
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        settingsPopup.show();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // Window event listeners
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        // printLog(EventLog.getInstance());
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // printLog(EventLog.getInstance());
    }

    // EFFECTS: prints out all events in the eventLog
    public void printLog(EventLog eventlog) {
        for (Event event : eventlog) {
            System.out.println(event);
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
