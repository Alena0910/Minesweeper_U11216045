package Options;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import Games.Minesweeper;

public class Options {

    private static String[] gameModes = {"Easy", "Medium", "Hard"};
    static int Height = 500, Width = 500;
    static int mineAmount = 10;
    static int cellSize = 20;
    static int row = 10, col = 10;

    public static void openOptions() {
        JFrame frame = new JFrame("Options");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Width, Height);
        frame.setLayout(new GridBagLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(Width, Height));
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

        JButton easy = new JButton("Easy");
        easy.setFont(new Font("Arial", Font.PLAIN, 20));
        easy.setPreferredSize(new Dimension(100, 50));
        easy.setContentAreaFilled(true);
        easy.setForeground(Color.BLACK);
        easy.setBackground(Color.WHITE);
        easy.setBorder(new LineBorder(new Color(71, 108, 108), 2));

        JButton medium = new JButton("Medium");
        medium.setFont(new Font("Arial", Font.PLAIN, 20));
        medium.setPreferredSize(new Dimension(100, 50));
        medium.setContentAreaFilled(true);
        medium.setForeground(Color.BLACK);
        medium.setBackground(Color.WHITE);
        medium.setBorder(new LineBorder(new Color(71, 108, 108), 2));

        JButton hard = new JButton("Hard");
        hard.setFont(new Font("Arial", Font.PLAIN, 20));
        hard.setPreferredSize(new Dimension(100, 50));
        hard.setContentAreaFilled(true);
        hard.setForeground(Color.BLACK);
        hard.setBackground(Color.WHITE);
        hard.setBorder(new LineBorder(new Color(71, 108, 108), 2));

        panel.add(easy, gbc);
        panel.add(medium, gbc);
        panel.add(hard, gbc);

        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mineAmount = 10;
                row = 10;
                col = 10;
                cellSize = 30;
                openMinesweeper(1, cellSize * col, cellSize * row, row, col, mineAmount, cellSize);
                frame.dispose();
            }
        });
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mineAmount = 70;
                row = 20;
                col = 30;
                cellSize = 20;
                openMinesweeper(2, cellSize * col, cellSize * row, row, col, mineAmount, cellSize);
                frame.dispose();
            }
        });
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mineAmount = 320;
                row = 30;
                col = 40;
                cellSize = 20;
                openMinesweeper(3, cellSize * col, cellSize * row, row, col, mineAmount, cellSize);
                frame.dispose();
            }
        });

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
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