package Options;


import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.*;
import java.io.*;


public class RankingList {

    private static final String[] columnNames = {"Rank", "Username", "Total Time"};
    private static final String[] textures = {"EasyRank.txt", "MediumRank.txt", "HardRank.txt"};
    private static String[] rankName = new String[10];
    private static int[] rankScore = new int[10];

    private static Color transparent = new Color(0, 0, 0, 0);

    
    public static void openRankingList(String username){
        JFrame frame = new JFrame("Ranking List");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(400, 600));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        UIManager.put("TabbedPane.contentOpaque", false);
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        tabbedPane.setPreferredSize(new Dimension(400, 500));
        tabbedPane.setBackground(transparent); // 未選擇的標籤的背景顏色
        tabbedPane.setForeground(Color.BLACK);
        tabbedPane.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                highlight = transparent; // 選擇標籤的背景顏色
                lightHighlight = transparent; // 選擇標籤的邊框顏色
                shadow = transparent; // 未選擇標籤的背景顏色
                darkShadow = transparent; // 未選擇標籤的邊框顏色
                focus = transparent; // 標籤被選中時的顏色
            }
            @Override
            protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {}
        });

        JTable table1 = createTable();
        JTable table2 = createTable();
        JTable table3 = createTable();

        tabbedPane.add("Easy", createTablePanel("Easy", table1));
        tabbedPane.add("Medium", createTablePanel("Medium", table2));
        tabbedPane.add("Hard", createTablePanel("Hard", table3));

        tabbedPane.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e){
                int index = tabbedPane.getSelectedIndex();
                switch(index){
                    case 0:
                        readRankingList(table1, 1);
                        table1.repaint(); // 手動重繪
                        break;
                    case 1:
                        readRankingList(table2, 2);
                        table2.repaint(); // 手動重繪
                        break;
                    case 2:
                        readRankingList(table3, 3);
                        table3.repaint(); // 手動重繪
                        break;
                }
            }
        });
        

        panel.add(tabbedPane, gbc);

        readRankingList(table1, 1);
        readRankingList(table2, 2);
        readRankingList(table3, 3);

        frame.getContentPane().setBackground(transparent);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }

    private static JPanel createTablePanel(String title, JTable table) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(transparent); // 標題顏色
        panel.setOpaque(false); // 確保面板透明

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("微軟正黑體", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        table.setBorder(BorderFactory.createEmptyBorder()); // Remove table border
        scrollPane.setOpaque(false); // 確保滾動窗格透明
        scrollPane.getViewport().setOpaque(false); // 確保視圖區域透明

        CustomTableCellRenderer centerRenderer = new CustomTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Center align table headers and set header transparent
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        header.setForeground(Color.BLACK);
        header.setBackground(transparent);
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        header.setOpaque(false);

        // Set table grid color to transparent
        table.setGridColor(transparent);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private static JTable createTable(){
        JTable table = new JTable();
        table.setOpaque(false);
        table.setShowGrid(false); // Remove grid lines if needed
        table.setPreferredScrollableViewportSize(new Dimension(380, 250));
        table.setFillsViewportHeight(true); // Fill the entire height of the scroll pane
        table.setBackground(transparent);
        table.setForeground(Color.BLACK);
        table.setFont(new Font("微軟正黑體", Font.BOLD, 16));
        table.setRowHeight(30);
        return table;
    }

    private static class CustomTableCellRenderer extends DefaultTableCellRenderer {
        public CustomTableCellRenderer() {
            setOpaque(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setBackground(transparent); // 設置單元格背景透明
            setOpaque(false); // 設置單元格透明
            return this;
        }
    }

    public static void readRankingList(JTable table, int game){
        String filename = textures[game - 1];
        String[] name = new String[10];
        int[] scoreList = new int[10];
        int counter = 0;
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);        
        table.setModel(model);
        File file = new File(filename);
        try{
            if(!file.exists()){
                file.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
                for(int i = 0; i < 10; i++){
                    bw.write("N/A");
                    bw.newLine();
                    bw.write(Integer.toString(Integer.MAX_VALUE));
                    bw.newLine();
                }
                bw.flush();
                bw.close();
            }
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while((line = br.readLine()) != null){
                if(counter >= 10) break;
                name[counter] = line;
                line = br.readLine();
                scoreList[counter] = Integer.parseInt(line);
                model.addRow(new Object[]{counter + 1, name[counter], scoreList[counter]});
                counter++;
            }
            br.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        while(counter < 10){
            name[counter] = "N/A";
            scoreList[counter] = Integer.MAX_VALUE;
            model.addRow(new Object[]{counter + 1, name[counter], scoreList[counter]});
            counter++;
        }
        rankName = name;
        rankScore = scoreList;
    }


    public static void readRankingList(int game){
        String filename = textures[game - 1];
        String[] name = new String[10];
        int[] scoreList = new int[10];
        int counter = 0;
        File file = new File(filename);
        try{
            if(!file.exists()){
                file.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
                for(int i = 0; i < 10; i++){
                    bw.write("N/A");
                    bw.newLine();
                    bw.write(Integer.toString(Integer.MAX_VALUE));
                    bw.newLine();
                }
                bw.flush();
                bw.close();
            }
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while((line = br.readLine()) != null){
                if(counter >= 10) break;
                name[counter] = line;
                line = br.readLine();
                scoreList[counter] = Integer.parseInt(line);
                counter++;
            }
            while(counter < 10){
                name[counter] = "N/A";
                scoreList[counter] = Integer.MAX_VALUE;
                counter++;
            }
            rankName = name;
            rankScore = scoreList;
            br.close();
            
        } catch(IOException e){
            e.printStackTrace();
        }
    }


    public static void rewriteRankingList(int game, String username, int score){
        String filename = textures[game - 1];
        String[] name = rankName;
        int[] scoreList = rankScore;
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            if(score > 0){
                for(int i = 0; i < 10; i++){
                    if(score < scoreList[i]){
                        for(int j = 9; j > i; j--){
                            name[j] = name[j - 1];
                            scoreList[j] = scoreList[j - 1];
                        }
                        name[i] = username;
                        scoreList[i] = score;
                        break;
                    }
                }
            }
            for(int i = 0; i < 10; i++){
                bw.write(name[i]);
                bw.newLine();
                bw.write(Integer.toString(scoreList[i]));
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
