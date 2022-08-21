package ui.gui;

import javax.swing.*;
import java.awt.*;

// represents constants used by all classes in the gui package
public class Constants {

    public static final Dimension SCREEN_DIMENSIONS = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int SCREEN_WIDTH = SCREEN_DIMENSIONS.width;
    public static final int SCREEN_HEIGHT = SCREEN_DIMENSIONS.height;

    public static final int TASKBAR_HEIGHT = 30;
    public static final int HEADER_HEIGHT = 50;
    public static final int HEADER_HORIZONTAL_GAP = 100;
    public static final int HEADER_FONT_SIZE = 30;

    public static final int MAX_BOARD_WIDTH = SCREEN_WIDTH - HEADER_HEIGHT * 3;
    public static final int MAX_BOARD_HEIGHT = SCREEN_HEIGHT - HEADER_HEIGHT * 3;

    public static final int INITIAL_BOARD_WIDTH = 9;
    public static final int INITIAL_BOARD_HEIGHT = 9;
    public static final int INITIAL_BOARD_MINES = 10;

    public static final int SETTINGS_BUTTON_WIDTH = 30;
    public static final int SETTINGS_BUTTON_HEIGHT = 30;

    public static final int SETTINGS_PANEL_WIDTH = 400;
    public static final int SETTINGS_PANEL_HEIGHT = 400;
    public static final int SETTINGS_PANEL_BUTTON_WIDTH = 200;
    public static final int SETTINGS_PANEL_BUTTON_HEIGHT = 50;
    public static final int SETTINGS_PANEL_BUTTON_SPACING = (SETTINGS_PANEL_HEIGHT - 5
            * SETTINGS_PANEL_BUTTON_HEIGHT) / 6;

    public static final int GAME_PANEL_WIDTH = 600;
    public static final int GAME_PANEL_HEIGHT = 400;
    public static final int GAME_PANEL_FONT_SIZE = 20;

    public static final int INSTRUCTIONS_PANEL_WIDTH = 1000;
    public static final int INSTRUCTIONS_PANEL_HEIGHT = 600;
    public static final int INSTRUCTIONS_WIDTH = INSTRUCTIONS_PANEL_WIDTH - SETTINGS_PANEL_BUTTON_SPACING * 2;
    public static final int INSTRUCTIONS_HEIGHT = INSTRUCTIONS_PANEL_HEIGHT - SETTINGS_BUTTON_HEIGHT
            - SETTINGS_PANEL_BUTTON_SPACING * 5;
    public static final int INSTRUCTIONS_PANEL_FONT_SIZE = GAME_PANEL_FONT_SIZE;

    public static final int LEADERBOARD_PANEL_WIDTH = 1000;
    public static final int LEADERBOARD_PANEL_HEIGHT = 600;
    public static final int LEADERBOARD_WIDTH = LEADERBOARD_PANEL_WIDTH - SETTINGS_PANEL_BUTTON_SPACING * 2;
    public static final int LEADERBOARD_HEIGHT = LEADERBOARD_PANEL_HEIGHT - SETTINGS_BUTTON_HEIGHT
            - SETTINGS_PANEL_BUTTON_SPACING * 3;

    public static final int NAME_PANEL_WIDTH = 500;
    public static final int NAME_PANEL_HEIGHT = 50;

    // EFFECTS: resizes a given icon to have a given width and height and returns the new image
    public static ImageIcon resizeImage(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
