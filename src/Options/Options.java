package Options;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Games.Minesweeper;
import Games.Setting;

public class Options {

    private static String[] gameModes = {"Easy", "Medium", "Hard"};
    static int Height = 500, Width = 500;
    static int mineAmount = 10;
    static int cellSize = 20;
    static int row = 10, col = 10;

    public static void openOptions(String username) {
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

        JButton easy = Setting.newButton("Easy", Setting.buttonFont, 100, 50, Color.WHITE);
        JButton medium = Setting.newButton("Medium", Setting.buttonFont, 100, 50, Color.WHITE);
        JButton hard = Setting.newButton("Hard", Setting.buttonFont, 100, 50, Color.WHITE);
        JButton rank = Setting.newButton("Ranking List", Setting.buttonFont, 150, 50, Color.WHITE);

        panel.add(easy, gbc);
        panel.add(medium, gbc);
        panel.add(hard, gbc);
        panel.add(rank, gbc);

        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mineAmount = Setting.easySetting[1];
                row = Setting.easySetting[2];
                col = Setting.easySetting[3];
                cellSize = Setting.easySetting[4];
                openMinesweeper(username, 1, cellSize * col, cellSize * row, row, col, mineAmount, cellSize);
                frame.dispose();
            }
        });
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mineAmount = Setting.mediumSetting[1];
                row = Setting.mediumSetting[2];
                col = Setting.mediumSetting[3];
                cellSize = Setting.mediumSetting[4];
                openMinesweeper(username, 2, cellSize * col, cellSize * row, row, col, mineAmount, cellSize);
                frame.dispose();
            }
        });
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mineAmount = Setting.hardSetting[1];
                row = Setting.hardSetting[2];
                col = Setting.hardSetting[3];
                cellSize = Setting.hardSetting[4];
                openMinesweeper(username, 3, cellSize * col, cellSize * row, row, col, mineAmount, cellSize);
                frame.dispose();
            }
        });
        rank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RankingList.openRankingList(username);
            }
        });

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void openMinesweeper(String username, int gameMode, int Width, int Height, int row, int col, int mineAmount, int cellSize) {
        
        String titleString = gameModes[gameMode - 1] + " Minesweeper";
        
        JFrame frame = new JFrame(titleString);
        frame.setVisible(true);
        frame.setSize(Width + 200, Height + 200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        Minesweeper minesweeper = new Minesweeper(frame, username, gameMode, Width, Height, row, col, mineAmount, cellSize);

        frame.add(minesweeper, BorderLayout.CENTER);
        frame.pack();
        minesweeper.requestFocus();
    }
}