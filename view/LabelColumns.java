/**
 * @author Caleb Krauter
 * @author Andy Comfort
 */

package view;

import javax.swing.*;
import java.awt.*;

/**
 * Adds labels to columns of the spreadsheet.
 */
public class LabelColumns extends JPanel {

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
    private final Dimension labelSize = new Dimension(75, 25);

    /**
     * A function used to set up the layout and produce the labels.
     * @param columnAmount
     */
    public LabelColumns(int columnAmount) {
        arrayOfLabels = new JLabel[columnAmount];
        gridLayout = new GridLayout(0, columnAmount + 1, 0, 0);
        setLayout(gridLayout);
        produceDataBox(columnAmount);
    }

    /**
     * Produces data boxes, in this class data boxes are column labels.
     * @param amount
     */
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

    /**
     * Updates the visuals of the current label.
     * @param currentLabel
     */
    private void renderVisuals(JLabel currentLabel) {
        currentLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        currentLabel.setBackground(Color.GREEN);
        currentLabel.setOpaque(true);
        currentLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        currentLabel.setPreferredSize(labelSize);
    }

    /**
     * Generates a column label to be displayed from A to at least ZZ.
     * @param i
     * @return a String value representing the text label of the column.
     */
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
