package ui.gui;

import model.Board;
import model.Leaderboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.gui.Constants.*;


public class LeaderboardPanel extends JPanel implements ActionListener {

    private static final int ROWS = 10;
    private static final int COLS = 12;

    private MainPanel mainPanel;
    private JPanel leaderboardPanel;

    private JButton close;

    // EFFECTS: calls initialize and instantiate
    public LeaderboardPanel(MainPanel mainPanel) {
        initialize();
        instantiateFields(mainPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel and setups the features of the panel
    private void initialize() {
        setPreferredSize(new Dimension(LEADERBOARD_PANEL_WIDTH, LEADERBOARD_PANEL_HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.gray);
    }

    // MODIFIES: this, mainPanel
    // EFFECTS: instantiates all fields of this
    public void instantiateFields(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        createButtons();
        createLeaderboardPanel();
        createLabels();
        addComponents();
    }

    private void createButtons() {
        close = new JButton("Close Settings");
        close.setAlignmentX(CENTER_ALIGNMENT);
        close.setMaximumSize(new Dimension(SETTINGS_PANEL_BUTTON_WIDTH, SETTINGS_PANEL_BUTTON_HEIGHT));
        close.addActionListener(this);
    }

    private void createLeaderboardPanel() {
        leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new GridLayout(ROWS, COLS));
        leaderboardPanel.setMaximumSize(new Dimension(LEADERBOARD_WIDTH, LEADERBOARD_HEIGHT));
        leaderboardPanel.setBackground(Color.LIGHT_GRAY);
    }

    private void createLabels() {
        Leaderboard leaderboard = mainPanel.getLeaderboard();

        for (int i = 0; i < 10; i++) {
            for (Board.Difficulty d : Board.Difficulty.values()) {
                leaderboardPanel.add(new JLabel(Integer.toString(i + 1)));
            }
        }

        int index;
        int counter = 1;
        for (int i = 0; i < 10; i++) {
            index = 0;
            for (Board.Difficulty d : Board.Difficulty.values()) {
                leaderboardPanel.add(new JLabel(leaderboard.getLeaderboard().get(i + index * 10).getName()), counter);
                index++;
                counter += 2;
            }
        }

        counter = 2;
        for (int i = 0; i < 10; i++) {
            index = 0;
            for (Board.Difficulty d : Board.Difficulty.values()) {
                leaderboardPanel.add(new JLabel(Integer.toString(
                        leaderboard.getLeaderboard().get((i + index * 10)).getTime())), counter);
                index++;
                counter += 3;
            }
        }
    }

    private void addComponents() {
        add(Box.createRigidArea(new Dimension(LEADERBOARD_PANEL_WIDTH, SETTINGS_PANEL_BUTTON_SPACING)));
        add(close);
        add(Box.createRigidArea(new Dimension(LEADERBOARD_PANEL_WIDTH, SETTINGS_PANEL_BUTTON_SPACING)));
        add(leaderboardPanel);
    }

    // MODIFIES: this
    // EFFECTS: specifies what behaviour should occur when a given button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            mainPanel.getSettingsPanel().getMoreSettingsPopup().hide();
        }
    }
}

