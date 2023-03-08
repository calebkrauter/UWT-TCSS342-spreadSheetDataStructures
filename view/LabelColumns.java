package view;

import javax.swing.*;
import java.awt.*;

public class LabelColumns extends JPanel {
    final JLabel[] arrayOfLabels;
    final LayoutManager gridLayout;
    
    private final Dimension cellSize = new Dimension(75, 25);
    
    public LabelColumns(int columnAmount) {
        arrayOfLabels = new JLabel[columnAmount];
        gridLayout = new GridLayout(0, columnAmount + 1, 0, 0);
        setLayout(gridLayout);
        produceDataBox(columnAmount);
    }
    
    private void produceDataBox(int amount) {
        JLabel currentLabel = new JLabel("");
        renderVisuals(currentLabel);
        add(currentLabel);
        
        for (int i = 0; i < amount; i++) {
            currentLabel = new JLabel(getColumnLabel(i), SwingConstants.CENTER);
            renderVisuals(currentLabel);
            add(currentLabel);
            arrayOfLabels[i] = currentLabel;
        }
    }
    
    private void renderVisuals(JLabel currentLabel) {
        currentLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        currentLabel.setBackground(Color.GREEN);
        currentLabel.setOpaque(true);
        currentLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        currentLabel.setPreferredSize(cellSize);
    }
    
    public String getColumnLabel(int i) {
        int colVal = i + 1;
        
        StringBuilder sb = new StringBuilder();
        while (colVal-- > 0) {
            sb.insert(0, (char) ('A' + (colVal % 26)));
            colVal /= 26;
        }
        
        return sb.toString();
    }
}