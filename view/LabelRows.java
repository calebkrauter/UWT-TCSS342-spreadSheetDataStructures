/**
 * @author Caleb Krauter
 * @author Andy Comfort
 */

package view;

import javax.swing.*;
import java.awt.*;

/**
 * Labels the rows of the spreadsheet.
 */
public class LabelRows extends JPanel {

    /**
     * An array that contains labels for the columns.
     */
    final JLabel[] arrayOfLabels;

    /**
     * A gridLayout used for organizing the labels in reference to the cells.
     */
    final LayoutManager gridLayout;

    /**
     * A reference and declaration to the size of a given label.
     */
    private final Dimension cellSize = new Dimension(75, 25);

    /**
     * A function used to set up the layout and produce the labels.
     * @param rowAmount
     */
    public LabelRows(int rowAmount) {
        arrayOfLabels = new JLabel[rowAmount];
        gridLayout = new GridLayout(rowAmount, 0, 0, 0);
        setLayout(gridLayout);
        produceDataBox(rowAmount);
    }

    /**
     * Produces data boxes, in this class data boxes are row labels.
     * @param amount
     */
    private void produceDataBox(int amount) {
        for(int i = 0; i < amount; i++) {
            JLabel currentLabel = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            renderVisuals(currentLabel);
            add(currentLabel);
            arrayOfLabels[i] = currentLabel;
        }
    }

    /**
     * Updates the visuals of the current label.
     * @param currentLabel
     */
    private void renderVisuals(JLabel currentLabel) {
        currentLabel.setBackground(Color.GREEN);
        currentLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        currentLabel.setOpaque(true);
        currentLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        currentLabel.setPreferredSize(cellSize);
    }
}
