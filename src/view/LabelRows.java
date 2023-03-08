package src.view;

import javax.swing.*;
import java.awt.*;

public class LabelRows extends JPanel {

    JLabel[] arrayOfLabels;
    LayoutManager gridLayout;

    private Dimension cellSize = new Dimension(75, 25);

    public LabelRows(int rowAmount) {
        arrayOfLabels = new JLabel[rowAmount];
        gridLayout = new GridLayout(rowAmount, 0, 0, 0);
        setLayout(gridLayout);
        produceDataBox(rowAmount);
    }

    private void produceDataBox(int amount) {
        for(int i = 0; i < amount; i++) {
            JLabel currentLabel = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            renderVisuals(currentLabel);
            add(currentLabel);
            currentLabel.setPreferredSize(cellSize);
            // Save the current cellField to an array to be accessed later.
            arrayOfLabels[i] = currentLabel;
        }
    }

    private void renderVisuals(JLabel currentLabel) {
        currentLabel.setBackground(Color.GREEN);
        currentLabel.setOpaque(true);
        currentLabel.setBorder(BorderFactory.createLineBorder(Color.black));
    }
}
