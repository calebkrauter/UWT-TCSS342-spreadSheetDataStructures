package src.view;

import src.Cell;
import src.CellToken;
import src.Spreadsheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

// TODO - review the overall code and update this code.
public class CellsPanel extends JPanel {
    
    private int numRows;
    private int numCols;
    JTextField[][] cellArr;
    LayoutManager gridLayout;
    private Dimension cellSize;
    private Spreadsheet spreadsheet;

    public CellsPanel(Spreadsheet spreadsheet) {
        this.spreadsheet = spreadsheet;
        this.numRows = spreadsheet.getNumRows();
        this.numCols = spreadsheet.getNumColumns();
        gridLayout = new GridLayout(numRows, numCols, 0, 0);
        cellArr = new JTextField[numRows][numCols];
        cellSize = new Dimension(75, 25);
        setLayout(gridLayout);
        initializeCellFields();
        addListeners();
    }

    private void addListeners() {
        // Look at all the cells and add an action to each.
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int finalI = i;
                int finalJ = j;
                cellArr[i][j].addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        CellToken ct = new CellToken();
                        ct.setRow(finalI);
                        ct.setColumn(finalJ);
                        spreadsheet.changeCellFormulaAndRecalculate(ct, cellArr[finalI][finalJ].getText());
                        for (int i = 0; i < numRows; i++) {
                            for (int j = 0; j < numCols; j++) {
                                if (spreadsheet.getCell(i,j).hasFormula())
                                    cellArr[i][j].setText(String.valueOf(spreadsheet.getCell(i, j).getValue()));
                            }
                        }
                    }
                });
            }
        }
    }

    private void initializeCellFields() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                cellArr[i][j] = new JTextField(spreadsheet.getCell(i, j).getValue());
                cellArr[i][j].setPreferredSize(cellSize);
                cellArr[i][j].setEditable(true);
                this.add(cellArr[i][j]);
            }
        }
    }
}
