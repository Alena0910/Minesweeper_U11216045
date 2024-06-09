package Games;

import java.awt.*;
import java.awt.event.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import javax.swing.*;

public class Minesweeper extends JPanel implements ActionListener, KeyListener {

    JFrame frame = new JFrame("Minesweeper");

    int cellSize = 10, Rows = 10, Columns = 10;
    int mineAmount = 10, totalEmptyTiles = 0;
    int gameMode = 1;
    private static String[] gameModes = {"Easy", "Medium", "Hard"};

    // String bomb = "💣", flag = "🚩";
    String bomb = "X", flag = "O";
    String username = new String();
    Timer timer;
    int timeCounter = 0;
    boolean gameOver = false, win = false;
    int safe = 0;

    MineTile[][] board = new MineTile[500][500];
    ArrayList<MineTile> mineList = new ArrayList<MineTile>();

    public Minesweeper(JFrame frame, String username, int gameMode, int boardWidth, int boardHeight, int Rows, int Columns, int mineAmount, int cellSize) {
        this.frame = frame;
        this.cellSize = cellSize;
        this.Rows = Rows;
        this.Columns = Columns;
        this.mineAmount = mineAmount;
        this.gameMode = gameMode;
        this.totalEmptyTiles = Rows * Columns - mineAmount;
        this.username = username;

        setPreferredSize(new Dimension(boardWidth + 200, boardHeight + 200));

        addKeyListener(this);
        setFocusable(true);
        requestFocus();

        JPanel panel = newPanel(Setting.transparent, boardWidth + 200, boardHeight + 200);
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = newLabel(gameModes[gameMode - 1] + " Minesweeper", Setting.titleFont, JLabel.CENTER);
        panel.add(title, gbc);

        gbc.gridy = 1;
        JLabel timerLabel = newLabel("Time: 0", Setting.titleFont, JLabel.CENTER);
        panel.add(timerLabel, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);

        JPanel gamePanel = newPanel(Setting.transparent, boardWidth, boardHeight);
        panel.add(gamePanel, gbc);

        tileHandle(gamePanel);

        timeCounter = 0;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeCounter++;
                timerLabel.setText("Time: " + timeCounter);
                if(safe == totalEmptyTiles){
                    win = true;
                    revealMines();
                }
            }
        });
        timer.start();

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        setMines(mineAmount, Rows, Columns, board, mineList);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    public static JPanel newPanel(Color color, int boardWidth, int boardHeight) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setPreferredSize(new Dimension(boardWidth, boardHeight));
        panel.setBackground(color);
        return panel;
    }

    public static JLabel newLabel(String text, Font font, int alignment) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(font);
        label.setHorizontalAlignment(alignment);
        return label;
    }

    void tileHandle(JPanel gamePanel){
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                MineTile tile = new MineTile(i, j, cellSize);
                board[i][j] = tile;

                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial", Font.BOLD, 16));
                tile.setText("");
                tile.setBackground(Setting.cellBackgroundColor);

                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        MineTile tile = (MineTile) e.getSource();
                        //left click
                        if(e.getButton() == MouseEvent.BUTTON1 && gameOver == false){
                            if(tile.getText() == ""){
                                if(mineList.contains(tile)){
                                    gameOver = true;
                                    revealMines();
                                }
                                else{
                                    checkMine(tile.r, tile.c, gamePanel);
                                }
                            }
                            if(safe == totalEmptyTiles){
                                win = true;
                                gameOver = true;
                            }
                        }
                        //right click
                        else if(e.getButton() == MouseEvent.BUTTON3 && gameOver == false){
                            if(tile.getText() == ""){
                                tile.setText(flag);
                            }
                            else if(tile.getText() == flag){
                                tile.setText("");
                            }
                        }
                        if(gameOver == true){
                            timer.stop();
                            GameOverFrame.openGameOverFrame(frame, username, gameMode, win, timeCounter);
                            revealMines();
                        }
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                });

                GridBagConstraints tileGbc = new GridBagConstraints();
                tileGbc.gridx = j;
                tileGbc.gridy = i;
                tileGbc.fill = GridBagConstraints.BOTH; // 確保元件填滿空間
                tileGbc.weightx = 1.0; // 水平方向均匀分布
                tileGbc.weighty = 1.0; // 垂直方向均匀分布

                gamePanel.add(tile, tileGbc);
            }
        }
    }

    void setMines(int mineAmount, int Rows, int Columns, MineTile[][] board, ArrayList<MineTile> mineList){

        SecureRandom sr = new SecureRandom();
        int r = sr.nextInt(Rows);
        int c = sr.nextInt(Columns);

        for(int i = 0; i < mineAmount; i++){
            r = sr.nextInt(Rows);
            c = sr.nextInt(Columns);
            if(!mineList.contains(board[r][c])) mineList.add(board[r][c]);
            else i--;
        }
    }

    void revealMines(){
        for(MineTile tile : mineList){
            tile.setBackground(Setting.bombColor);
            tile.setText(bomb);
        }
    }

    void checkMine(int r, int c, JPanel gamePanel){
        MineTile tile = board[r][c];
        tile.setEnabled(false);
        int minesFound = 0;
    
        for(int i = 0; i < 8; i++){
            int x = r + Setting.dir[i * 2];
            int y = c + Setting.dir[i * 2 + 1];
    
            minesFound += minesCount(x, y);
        }
    
        tile.setBackground(Color.WHITE);
        
        if(minesFound > 0){
            tile.setForeground(Setting.buttonFontColor[minesFound - 1]);
            tile.setText(String.valueOf(minesFound));
        }
        else{
            tile.setText("");
            for(int i = 0; i < 8; i++){
                int x = r + Setting.dir[i * 2];
                int y = c + Setting.dir[i * 2 + 1];
    
                if(x >= 0 && x < Rows && y >= 0 && y < Columns && board[x][y].isEnabled()){
                    checkMine(x, y, gamePanel);
                }
            }
        }
    
        safe++;
    }

    int minesCount(int r, int c){
        if(r < 0 || r >= Rows || c < 0 || c >= Columns) return 0;
        if(mineList.contains(board[r][c])) return 1;
        return 0;
    }
}
