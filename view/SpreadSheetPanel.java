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
        int rows = getRowSize();
        int columns = getColumnSize();

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
     * A function for getting the value of the amount of rows from the user.
     * @return
     */
    public int getRowSize() {
        return Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of rows"));
    }

    /**
     * A function for getting the value of the amount of columns from the user.
     * @return
     */
    public int getColumnSize() {
        return Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of Columns"));
    }
}