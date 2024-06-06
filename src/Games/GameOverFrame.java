package Games;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class GameOverFrame {

    private static String[] gameModes = {"Easy", "Medium", "Hard"};

    public static void openGameOverFrame(int gameMode, int boardWidth, int boardHeight, int Rows, int Columns, int mineAmount, int cellSize, boolean win) {
        JFrame frame = new JFrame("Options");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(boardWidth, boardHeight);
        frame.setLayout(new GridBagLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(boardWidth, boardHeight));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Minesweeper");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        panel.add(title, gbc);

        JLabel label = new JLabel("Options");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(label, gbc);

        Font buttonFont = new Font("Arial", Font.PLAIN, 20);

        JButton Restart = newButton("Restart", buttonFont, 100, 50, Color.WHITE);
        JButton Options = newButton("Options", buttonFont, 100, 50, Color.WHITE);
        JButton Exit = newButton("Exit", buttonFont, 100, 50, Color.WHITE);

        panel.add(Restart, gbc);
        panel.add(Options, gbc);
        panel.add(Exit, gbc);

        Restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMinesweeper(gameMode, boardWidth, boardHeight, Rows, Columns, mineAmount, cellSize);
                frame.dispose();
            }
        });

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    static JButton newButton(String text, Font font, int width, int height, Color color) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setPreferredSize(new Dimension(width, height));
        button.setContentAreaFilled(true);
        button.setForeground(Color.BLACK);
        button.setBackground(color);
        button.setBorder(new LineBorder(new Color(71, 108, 108), 2));
        return button;
    }
    
    public static void openMinesweeper(int gameMode, int Width, int Height, int row, int col, int mineAmount, int cellSize) {
        
        String titleString = gameModes[gameMode - 1] + " Minesweeper";
        
        JFrame frame = new JFrame(titleString);
        frame.setVisible(true);
        frame.setSize(Width + 200, Height + 200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        Minesweeper minesweeper = new Minesweeper(frame, gameMode, Width, Height, row, col, mineAmount, cellSize);

        frame.add(minesweeper, BorderLayout.CENTER);
        frame.pack();
        minesweeper.requestFocus();
    }
}
