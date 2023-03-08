package src.view;

import src.Cell;
import src.CellToken;
import src.Spreadsheet;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;


public class CellsPanel extends JPanel implements PropertyChangeListener {
    
    private int numRows;
    private int numCols;
    JTextField[][] cellArr;
    LayoutManager gridLayout;
    
    /**
     * INTERACTION_SOUND a string that represents the path to audio
     */
    private static final String INTERACTION_SOUND = "interaction.wav";
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
        spreadsheet.addPropertyChangeListener(this);
        
        // Look at all the cells and add an action to each.
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int finalI = i;
                int finalJ = j;
                cellArr[i][j].addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tryAudio();
                        CellToken ct = new CellToken();
                        ct.setRow(finalI);
                        ct.setColumn(finalJ);
                        spreadsheet.changeCellFormulaAndRecalculate(ct, cellArr[finalI][finalJ].getText());
                        for (int i = 0; i < numRows; i++) {
                            for (int j = 0; j < numCols; j++) {
                                if (spreadsheet.getCell(i,j).hasFormula()) {
                                    cellArr[i][j].setText(String.valueOf(spreadsheet.getCell(i, j).getValue()));
                                    cellArr[i][j].setToolTipText(spreadsheet.getCell(i, j).getFormula());
                                }
                            }
                        }
                    }
                });
            }
        }
    }
    
    private void tryAudio() {
        try {
            playAudio(INTERACTION_SOUND);
        } catch (LineUnavailableException e2) {
            audioError();
        } catch (IOException e2) {
            audioError();
        } catch (UnsupportedAudioFileException e2) {
            audioError();
        }
    }
    private void playAudio(String theAudio) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(theAudio));
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }
    
    /**
     * JOptionPane for audio error.
     */
    public void audioError() {
        JOptionPane.showMessageDialog(this, "Audio Error. Program Shutting Down.");
        System.exit(0);
    }

    private void initializeCellFields() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                cellArr[i][j] = new JTextField(spreadsheet.getCell(i, j).getValue());
                cellArr[i][j].setFont(new Font("Verdana", Font.ROMAN_BASELINE, 15));
                cellArr[i][j].setEditable(true);
                cellArr[i][j].setHorizontalAlignment(JTextField.CENTER);
                cellArr[i][j].setForeground(Color.BLUE);
                this.add(cellArr[i][j]);
            }
        }
    }
    
    /**
     * Detects property changes in the spreadsheet.
     *
     * @param theEvent the event
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        // Displays an error message if a cycle is found
        JOptionPane.showMessageDialog(this, "Cycle found! Cell reverted to previous state.");
        CellToken c = (CellToken) theEvent.getNewValue();
        int row = c.getRow();
        int col = c.getColumn();
        if (spreadsheet.getCell(row, col).hasFormula()) {
            cellArr[row][col].setText(String.valueOf(
                    spreadsheet.getCell(row, col).getValue()));
        } else {
            cellArr[row][col].setText("");
        }
        cellArr[row][col].setToolTipText(spreadsheet.getCell(row, col).getFormula());
    }
}
