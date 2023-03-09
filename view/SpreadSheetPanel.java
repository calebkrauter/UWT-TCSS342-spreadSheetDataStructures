/**
 * @author Caleb Krauter
 * @author Andy Comfort
 */

package view;

import model.Spreadsheet;

import javax.swing.*;
import java.awt.*;

/**
 * A panel containing other panels to form the GUI. This panel
 * uses the borderLayout to help organize the elements like the panels
 * containing labels and the panel containing cells.
 */
public class SpreadSheetPanel extends JPanel {

    /**
     * A reference to spreadsheet used to share the value of the rows
     * and columns with the CellsPanel.
     */
    final Spreadsheet spreadsheet;

    public SpreadSheetPanel() {
        int rows = getInput("rows");
        int columns = getInput("columns");

        setLayout(new BorderLayout());
        spreadsheet = new Spreadsheet(rows, columns);

        // Initialize other panels.
        LabelRows labelRows = new LabelRows(rows);
        LabelColumns labelColumns = new LabelColumns(columns);
        CellsPanel cellsPanel = new CellsPanel(spreadsheet);

        // Add the panels to this panel.
        add(labelColumns, BorderLayout.NORTH);
        add(labelRows, BorderLayout.WEST);
        add(cellsPanel, BorderLayout.CENTER);
    }
    
    /**
     * A function for getting the number of columns or rows from the user.
     * @param dim the dimension
     * @return column/row number
     */
    public int getInput(String dim) {
        boolean isValidInput = false;
        int size = 0;
    
        while(!isValidInput) {
            final String input = JOptionPane.showInputDialog(this, "Enter number of " + dim);
            // Validates user input
            if (input == null) { // If user hits cancel button
                JOptionPane.showMessageDialog(this,
                        "Exiting Spreadsheet.");
                System.exit(0);
            } else {
                try {
                    size = Integer.parseInt(input);
                    if (size < 1) {
                        JOptionPane.showMessageDialog(this,
                                "Invalid value! Number of " + dim + " must be greater than zero.");
                        continue;
                    }
                    isValidInput = true;
                } catch (final NumberFormatException e) {
                    JOptionPane.showMessageDialog(this,
                            "Invalid value! Number of " + dim + " must be a number.");
                }
            }
        }
        return size;
    }
}