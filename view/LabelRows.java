package view;

import javax.swing.*;
import java.awt.*;

public class LabelRows extends JPanel {

    final JLabel[] arrayOfLabels;
    final LayoutManager gridLayout;

    private final Dimension cellSize = new Dimension(75, 25);

    public LabelRows(int rowAmount) {
        arrayOfLabels = new JLabel[rowAmount];
        gridLayout = new GridLayout(rowAmount, 0, 0, 0);
        setLayout(gridLayout);
        produceDataBox(rowAmount);
    }

    private void produceDataBox(int amount) {
        for(int i = 0; i < amount; i++) {
            JLabel currentLabel = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            renderVisuals(currentLabel);
            add(currentLabel);
            arrayOfLabels[i] = currentLabel;
        }
    }

    private void renderVisuals(JLabel currentLabel) {
        currentLabel.setBackground(Color.GREEN);
        currentLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        currentLabel.setOpaque(true);
        currentLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        currentLabel.setPreferredSize(cellSize);
    }
}
