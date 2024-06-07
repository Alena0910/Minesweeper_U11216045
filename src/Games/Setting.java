package Games;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Setting {
    public static Color bombColor = new Color(226, 180, 189);
    public static Color transparent = new Color(0, 0, 0, 0);
    public static Color cellBackgroundColor = new Color(159, 183, 152);
    
    public static Color[] buttonFontColor = {
        Color.BLUE, 
        Color.GREEN, 
        Color.MAGENTA, 
        Color.ORANGE, 
        Color.CYAN, 
        Color.PINK, 
        Color.YELLOW,
        Color.darkGray
    };

    public static Font titleFont = new Font("Arial", Font.BOLD, 20);
    public static Font buttonFont = new Font("Arial", Font.PLAIN, 20);

    public static int[] dir = {0, 1, 1, 0, 1, 1, 1, -1, -1, 1, -1, 0, 0, -1, -1, -1};
    public static int[] easySetting = {1, 10, 10, 10, 30};
    public static int[] mediumSetting = {2, 70, 20, 30, 20};
    public static int[] hardSetting = {3, 300, 30, 40, 20};

    
    public static JButton newButton(String text, Font font, int width, int height, Color color) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setPreferredSize(new Dimension(width, height));
        button.setContentAreaFilled(true);
        button.setForeground(Color.BLACK);
        button.setBackground(color);
        button.setBorder(new LineBorder(new Color(71, 108, 108), 2));
        return button;
    }
}
