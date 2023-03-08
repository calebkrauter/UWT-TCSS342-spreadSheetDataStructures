package view;

import model.Spreadsheet;

import javax.swing.*;
import java.awt.*;

public class SpreadSheetPanel extends JPanel {
    
    final Spreadsheet spreadsheet;
    public SpreadSheetPanel() {
        setLayout(new BorderLayout());
        int rows = getRowSize();
        int columns = getColumnSize();
        spreadsheet = new Spreadsheet(rows, columns);
        LabelRows labelRows = new LabelRows(rows);
        LabelColumns labelColumns = new LabelColumns(columns);
        CellsPanel cellsPanel = new CellsPanel(spreadsheet);

        add(labelColumns, BorderLayout.NORTH);
        add(labelRows, BorderLayout.WEST);
        add(cellsPanel, BorderLayout.CENTER);
    }
    
    public int getRowSize() {
        return Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of rows"));
    }
    public int getColumnSize() {
        return Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of Columns"));
    }
}