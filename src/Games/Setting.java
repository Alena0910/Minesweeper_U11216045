package Games;

import java.awt.*;

public class Setting {
    static Color bombColor = new Color(255, 90, 95);
    static Color transparent = new Color(0, 0, 0, 0);
    static Color cellBackgroundColor = new Color(159, 183, 152);
    static Font titleFont = new Font("Arial", Font.BOLD, 20);
    
    static Color[] buttonFontColor = {
        Color.BLUE, 
        Color.GREEN, 
        Color.MAGENTA, 
        Color.ORANGE, 
        Color.CYAN, 
        Color.PINK, 
        Color.YELLOW,
        Color.darkGray
    };

    static int[] dir = {0, 1, 1, 0, 1, 1, 1, -1, -1, 1, -1, 0, 0, -1, -1, -1};
}
