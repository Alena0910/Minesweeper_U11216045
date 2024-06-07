package Games;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import Options.Options;

public class GameOverFrame {

    private static String[] gameModes = {"Easy", "Medium", "Hard"};

    public static void openGameOverFrame(JFrame gameframe, String username, int gameMode, boolean win, int totalTime) {
        JFrame frame = new JFrame("Options");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLayout(new GridBagLayout());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(600, 300));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel(gameModes[gameMode - 1] + " Minesweeper");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        panel.add(title, gbc);

        String labelText = win ? "You Win! Total Time: " + totalTime : "Game Over!";

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(label, gbc);

        JButton RestartButton = newButton("Restart", Setting.buttonFont, 100, 50, Color.WHITE);
        JButton OptionsButton = newButton("Options", Setting.buttonFont, 100, 50, Color.WHITE);
        JButton ExitButton = newButton("Exit", Setting.buttonFont, 100, 50, Color.WHITE);

        panel.add(RestartButton, gbc);
        panel.add(OptionsButton, gbc);
        panel.add(ExitButton, gbc);

        RestartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameframe.dispose();
                frame.dispose();
                int[] gameSetting = gameMode == 1 ? Setting.easySetting : gameMode == 2 ? Setting.mediumSetting : Setting.hardSetting;
                Options.openMinesweeper(username, gameMode, gameSetting[3] * gameSetting[4], gameSetting[2] * gameSetting[4], gameSetting[2], gameSetting[3], gameSetting[1], gameSetting[4]);
            }
        });
        OptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Options.openOptions(username);
                gameframe.dispose();
                frame.dispose();
            }
        });
        ExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.exit(0);
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
}
