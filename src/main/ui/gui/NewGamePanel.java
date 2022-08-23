package ui.gui;

import model.Player;
import ui.exceptions.LoadInvalidBoardException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static ui.gui.Constants.*;
import static model.Board.Difficulty.*;

// represents a panel that contains information about creating a new game
public class NewGamePanel extends JPanel implements ActionListener {

    private static final int GAME_OPTION_WIDTH = 450;
    private static final int GAME_OPTION_HEIGHT = 200;
    private static final int GAME_PANEL_SPACING = (GAME_PANEL_HEIGHT - SETTINGS_PANEL_BUTTON_HEIGHT * 2
            - GAME_OPTION_HEIGHT) / 4;

    private static final int ROWS = 5;
    private static final int COLS = 4;

    private Player newPlayer;

    private JButton beginner;
    private JButton intermediate;
    private JButton expert;
    private JButton custom;
    private JButton close;
    private JButton load;

    private JTextField customWidth;
    private JTextField customHeight;
    private JTextField customMines;

    private MainPanel mainPanel;
    private Popup loadBoardPopup;
    private Timer timer;
    private JPanel gameOptions;


    // EFFECTS: calls initialize and instantiate
    public NewGamePanel(MainPanel mainPanel) {
        initialize();
        instantiateFields(mainPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel and setups the features of the panel
    public void initialize() {
        setPreferredSize(new Dimension(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.gray);
    }

    // MODIFIES: this, mainPanel
    // EFFECTS: instantiates all fields of this
    public void instantiateFields(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        mainPanel.setNewGamePanel(this);

        createCloseAndLoadButtons();
        createGameOptions();

        addComponents();
    }

    // getters and setters
    public void setNewPlayer(Player newPlayer) {
        this.newPlayer = newPlayer;
    }

    // MODIFIES: this
    // EFFECTS: creates the close and load buttons
    public void createCloseAndLoadButtons() {

        close = new JButton("Close Game Options");
        createButton(close);

        load = new JButton("Load Saved Board");
        createButton(load);
    }

    // EFFECTS: modifies a given button
    private void createButton(JButton button) {
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(SETTINGS_PANEL_BUTTON_WIDTH, SETTINGS_PANEL_BUTTON_HEIGHT));

        button.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: creates the game options that the user can choose from
    public void createGameOptions() {
        gameOptions = new JPanel();
        gameOptions.setLayout(new GridLayout(ROWS, COLS));
        gameOptions.setMaximumSize(new Dimension(GAME_OPTION_WIDTH, GAME_OPTION_HEIGHT));
        gameOptions.setBackground(Color.LIGHT_GRAY);

        createHeader();
        createSizeLabels();
        createDifficultyButtons();
        createCustomTextField();
    }

    // MODIFIES: this
    // EFFECTS: creates a row containing headers
    public void createHeader() {

        ArrayList<JLabel> labels = new ArrayList<>();
        labels.add(new JLabel("Difficulty"));
        labels.add(new JLabel("Width"));
        labels.add(new JLabel("Height"));
        labels.add(new JLabel("Mines"));

        for (JLabel l : labels) {
            l.setHorizontalAlignment(SwingConstants.CENTER);
            l.setFont(new Font(l.getFont().getName(), Font.BOLD, GAME_PANEL_FONT_SIZE));
            gameOptions.add(l);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates all of the labels used in the grid
    public void createSizeLabels() {

        ArrayList<JLabel> labels = new ArrayList<>();

        labels.add(new JLabel("9"));
        labels.add(new JLabel("9"));
        labels.add(new JLabel("10"));

        labels.add(new JLabel("16"));
        labels.add(new JLabel("16"));
        labels.add(new JLabel("40"));

        labels.add(new JLabel("30"));
        labels.add(new JLabel("16"));
        labels.add(new JLabel("99"));

        for (JLabel l : labels) {
            l.setHorizontalAlignment(SwingConstants.CENTER);
            l.setFont(new Font(l.getFont().getName(), Font.PLAIN, GAME_PANEL_FONT_SIZE));
            gameOptions.add(l);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the buttons used for selecting the difficulties
    public void createDifficultyButtons() {

        ArrayList<JButton> buttons = new ArrayList<>();

        beginner = new JButton("Beginner");
        intermediate = new JButton("Intermediate");
        expert = new JButton("Expert");
        custom = new JButton("Custom");

        buttons.add(beginner);
        buttons.add(intermediate);
        buttons.add(expert);
        buttons.add(custom);

        int i = 1;
        for (JButton b : buttons) {
            b.addActionListener(this);
            gameOptions.add(b, i * COLS);
            i++;
        }
    }

    // MODIFIES: this
    // EFFECTS: creates user input boxes for the user to specify dimensions for the custom board
    private void createCustomTextField() {
        ArrayList<JTextField> textFields = new ArrayList<>();

        customWidth = new JTextField(String.valueOf(INITIAL_BOARD_WIDTH));
        customHeight = new JTextField(String.valueOf(INITIAL_BOARD_HEIGHT));
        customMines = new JTextField(String.valueOf(INITIAL_BOARD_MINES));

        textFields.add(customWidth);
        textFields.add(customHeight);
        textFields.add(customMines);


        for (JTextField t : textFields) {
            t.setHorizontalAlignment(JTextField.CENTER);
            t.setFont(new Font(t.getFont().getName(), Font.PLAIN, GAME_PANEL_FONT_SIZE));
            gameOptions.add(t);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds all the components to the panel
    private void addComponents() {
        add(Box.createRigidArea(new Dimension(GAME_PANEL_WIDTH, GAME_PANEL_SPACING)));
        add(close);
        add(Box.createRigidArea(new Dimension(GAME_PANEL_WIDTH, GAME_PANEL_SPACING)));

        add(gameOptions);

        add(Box.createRigidArea(new Dimension(GAME_PANEL_WIDTH, GAME_PANEL_SPACING)));
        add(load);
        add(Box.createRigidArea(new Dimension(GAME_PANEL_WIDTH, GAME_PANEL_SPACING)));
    }

    // MODIFIES: this, mainPanel
    // EFFECTS: specifies what behaviour should occur when a given button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            timer.stop();
            loadBoardPopup.hide();
            createLoadBoardPopup();
        } else if (e.getSource() == close) {
            mainPanel.getSettingsPanel().getMoreSettingsPopup().hide();
        } else if (e.getSource() == load) {
            try {
                mainPanel.loadPlayer();
                cleanUp();
            } catch (LoadInvalidBoardException exception) {
                loadInvalidBoard();
            }
        } else {
            if (e.getSource() == beginner) {
                newPlayer = new Player(9, 9, 10, BEGINNER);
            } else if (e.getSource() == intermediate) {
                newPlayer = new Player(16, 16, 40, INTERMEDIATE);
            } else if (e.getSource() == expert) {
                newPlayer = new Player(30, 16, 99, EXPERT);
            } else if (e.getSource() == custom) {
                int width = Integer.parseInt(customWidth.getText());
                int height = Integer.parseInt(customHeight.getText());
                int mines = Integer.parseInt(customMines.getText());
                newPlayer = new Player(width, height, mines, CUSTOM);
            }
            cleanUp();
        }
    }

    // MODIFIES: this, mainPanel
    // EFFECTS: resets everything and creates a new board
    private void cleanUp() {

        mainPanel.getSettingsPopup().hide();
        mainPanel.getSettingsPanel().getMoreSettingsPopup().hide();

        mainPanel.setPlayer(newPlayer);
        mainPanel.cleanup();
    }

    public void createLoadBoardPopup() {
        JLabel loadBoardLabel = new JLabel("Invalid Board");
        loadBoardLabel.setFont(new Font(loadBoardLabel.getFont().getName(), Font.PLAIN, GAME_PANEL_FONT_SIZE));
        loadBoardLabel.setForeground(Color.RED);

        JPanel loadBoardPanel = new JPanel();
        int width = 150;
        int height = 40;
        loadBoardPanel.setPreferredSize(new Dimension(width, height));
        loadBoardPanel.add(loadBoardLabel);

        PopupFactory pf = new PopupFactory();
        loadBoardPopup = pf.getPopup(this, loadBoardPanel, (SCREEN_WIDTH - width) / 2,
                (SCREEN_HEIGHT - height) / 2);
    }

    private void loadInvalidBoard() {
        createLoadBoardPopup();
        loadBoardPopup.show();
        timer = new Timer(1500, this);
        timer.start();
    }
}
