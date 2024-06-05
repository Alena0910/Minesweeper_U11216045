package Games;

import java.awt.*;

import javax.swing.*;

public class MineTile extends JButton{
    int r;
    int c;

    public MineTile(int r, int c, int cellSize){
        this.r = r;
        this.c = c;

        setBackground(Setting.cellBackgroundColor);
        setForeground(Color.BLACK);
        setPreferredSize(new Dimension(cellSize, cellSize));
    }
}
