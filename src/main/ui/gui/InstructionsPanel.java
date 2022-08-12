package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static ui.gui.Constants.*;

// represents a panel that contains information about the instructions menu
public class InstructionsPanel extends JPanel implements ActionListener {

    private MainPanel mainPanel;
    private JButton close;
    private JPanel instructions;

    // EFFECTS: creates a new instructions panel
    public InstructionsPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setPreferredSize(new Dimension(INSTRUCTIONS_PANEL_WIDTH, INSTRUCTIONS_PANEL_HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.gray);

        createButtons();
        createLabels();
        addComponents();
    }


    // MODIFIES: this
    // EFFECTS: creates the close button used in the instructions panel
    public void createButtons() {

        close = new JButton("Close Instructions");
        close.setAlignmentX(CENTER_ALIGNMENT);
        close.setMaximumSize(new Dimension(SETTINGS_PANEL_BUTTON_WIDTH, SETTINGS_PANEL_BUTTON_HEIGHT));
        close.addActionListener(this);

        instructions = new JPanel();
        instructions.setMaximumSize(new Dimension(INSTRUCTIONS_WIDTH, INSTRUCTIONS_HEIGHT));
        instructions.setBackground(Color.LIGHT_GRAY);
    }

    // MODIFIES: this
    // EFFECTS: creates all the text inside the instructions panel
    private void createLabels() {

        ArrayList<JLabel> labels = new ArrayList<>();

        labels.add(new JLabel("The objective of Minesweeper is to locate and flag all mines in a rectangular grid"));
        labels.add(new JLabel("without detonating any of them"));

        labels.add(new JLabel("To start the game, uncover a tile by left clicking any covered tile"));

        labels.add(new JLabel("When you uncover a tile, new tiles will appear that are either empty or have a "
                + "number on them"));

        labels.add(new JLabel("Tiles that are empty will have no bombs nearby, while tiles that have numbers on "
                + "them will"));
        labels.add(new JLabel("have that number of mines within one tile of that tile in all directions including "
                + "diagonals"));

        labels.add(new JLabel("If you think a tile is a mine, flag the tile by right clicking it"));

        labels.add(new JLabel("If you originally flagged a tile, but now determine that it does not contain a mine"));
        labels.add(new JLabel("un-flag it by right clicking it again"));

        for (JLabel l : labels) {

            l.setFont(new Font(l.getFont().getName(), Font.PLAIN, INSTRUCTIONS_PANEL_FONT_SIZE));
            l.setAlignmentX(CENTER_ALIGNMENT);
            instructions.add(l);
        }

        instructions.add(Box.createRigidArea(new Dimension(INSTRUCTIONS_PANEL_WIDTH,SETTINGS_PANEL_BUTTON_SPACING)), 7);
        instructions.add(Box.createRigidArea(new Dimension(INSTRUCTIONS_PANEL_WIDTH,SETTINGS_PANEL_BUTTON_SPACING)), 6);
        instructions.add(Box.createRigidArea(new Dimension(INSTRUCTIONS_PANEL_WIDTH,SETTINGS_PANEL_BUTTON_SPACING)), 4);
        instructions.add(Box.createRigidArea(new Dimension(INSTRUCTIONS_PANEL_WIDTH,SETTINGS_PANEL_BUTTON_SPACING)), 3);
        instructions.add(Box.createRigidArea(new Dimension(INSTRUCTIONS_PANEL_WIDTH,SETTINGS_PANEL_BUTTON_SPACING)), 2);
    }

    // MODIFIES: this
    // EFFECTS: adds the close buttons and instructions to this
    private void addComponents() {
        add(Box.createRigidArea(new Dimension(INSTRUCTIONS_PANEL_WIDTH, SETTINGS_PANEL_BUTTON_SPACING)));
        add(close);
        add(Box.createRigidArea(new Dimension(INSTRUCTIONS_PANEL_WIDTH, SETTINGS_PANEL_BUTTON_SPACING)));
        add(instructions);
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
