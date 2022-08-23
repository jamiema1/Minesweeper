package ui.gui;

import model.Leaderboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static ui.gui.Constants.*;

// represents a panel that contains information about the settings menu
public class SettingsPanel extends JPanel implements ActionListener {

    private MainPanel mainPanel;
    private InstructionsPanel instructionsPanel;
    private NewGamePanel newGamePanel;
    private LeaderboardPanel leaderboardPanel;
    private Popup moreSettingsPopup;

    private JButton close;
    private JButton newGame;
    private JButton howToPlay;
    private JButton leaderboard;
    private JButton quit;

    // EFFECTS: calls initialize and instantiate
    public SettingsPanel(MainPanel mainPanel) {
        initialize();
        instantiateFields(mainPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel and setups the features of the panel
    private void initialize() {
        setPreferredSize(new Dimension(SETTINGS_PANEL_WIDTH,SETTINGS_PANEL_HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.gray);
    }

    // MODIFIES: this, mainPanel
    // EFFECTS: instantiates all fields of this
    public void instantiateFields(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        instructionsPanel = new InstructionsPanel(mainPanel);
        newGamePanel = new NewGamePanel(mainPanel);
        leaderboardPanel = new LeaderboardPanel(mainPanel);

        createButtons();
    }

    // MODIFIES: this
    // EFFECTS: creates four buttons on the settings panel, adds them to the buttons list, formats and adds an
    //          action listener to them
    public void createButtons() {

        ArrayList<JButton> buttons = new ArrayList<>();

        close = new JButton("Close Settings");
        newGame = new JButton("New Game");
        howToPlay = new JButton("How To Play");
        leaderboard = new JButton("Leaderboard");
        quit = new JButton("Quit");

        buttons.add(close);
        buttons.add(newGame);
        buttons.add(howToPlay);
        buttons.add(leaderboard);
        buttons.add(quit);

        for (JButton b : buttons) {
            b.setAlignmentX(CENTER_ALIGNMENT);
            b.setMaximumSize(new Dimension(SETTINGS_PANEL_BUTTON_WIDTH, SETTINGS_PANEL_BUTTON_HEIGHT));

            b.addActionListener(this);

            add(Box.createRigidArea(new Dimension(SETTINGS_PANEL_WIDTH, SETTINGS_PANEL_BUTTON_SPACING)));
            add(b);
        }
    }

    // MODIFIES: this, mainPanel
    // EFFECTS: if the user clicks close,
    //              - hides the settings popup
    //          else if the user clicks newGame
    //              - creates and shows another popup that displays the newGame panel
    //          else if the user clicks howToPlay
    //              - creates and shows another popup that displays the howToPlay panel
    //          else if the user clicks quit
    //              - ends the application
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            mainPanel.getSettingsPopup().hide();
            mainPanel.createSettingsPanelPopup();
        } else if (e.getSource() == newGame) {
            createMoreSettingsPopup(0);
            moreSettingsPopup.show();
        } else if (e.getSource() == howToPlay) {
            createMoreSettingsPopup(1);
            moreSettingsPopup.show();
        } else if (e.getSource() == leaderboard) {
            createMoreSettingsPopup(2);
            moreSettingsPopup.show();
        } else if (e.getSource() == quit) {
            mainPanel.dispose();
        }
    }


    // MODIFIES: this, instructionsPanel, newGamePanel
    // EFFECTS: if type is true,
    //              - creates a popup with the instructionsPanel
    //          else,
    //              - creates a popup with the newGamePanel
    public void createMoreSettingsPopup(int type) {
        PopupFactory pf = new PopupFactory();
        if (type == 0) {
            moreSettingsPopup = pf.getPopup(this, newGamePanel,(SCREEN_WIDTH - GAME_PANEL_WIDTH) / 2,
                (SCREEN_HEIGHT - GAME_PANEL_HEIGHT) / 2);
        } else if (type == 1) {
            moreSettingsPopup = pf.getPopup(this, instructionsPanel,(SCREEN_WIDTH - INSTRUCTIONS_PANEL_WIDTH) / 2,
                    (SCREEN_HEIGHT - INSTRUCTIONS_PANEL_HEIGHT) / 2);
        } else {
            moreSettingsPopup = pf.getPopup(this, leaderboardPanel,(SCREEN_WIDTH - LEADERBOARD_PANEL_WIDTH) / 2,
                    (SCREEN_HEIGHT - LEADERBOARD_PANEL_HEIGHT) / 2);
        }
    }

    // getters and setters
    public Popup getMoreSettingsPopup() {
        return moreSettingsPopup;
    }

    public LeaderboardPanel getLeaderboardPanel() {
        return leaderboardPanel;
    }
}
