/**
 * @author Caleb Krauter
 * @author Andy Comfort
 */
package view;

import model.CellToken;
import model.Spreadsheet;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

/**
 * A panel of cells that creates each cell and initializes intractable cells.
 */
public class CellsPanel extends JPanel implements PropertyChangeListener {
    /**
     * Represents the number of rows.
     */
    private final int numRows;
    
    /**
     * Represents the number of columns.
     */
    private final int numCols;
    
    /**
     * An array of JTextFields representing the cells.
     */
    final JTextField[][] cellArr;
    
    /**
     * A layout manager used to organize the cells in relation to
     * the labels.
     */
    final LayoutManager gridLayout;
    
    /**
     * INTERACTION_SOUND a string that represents the path to an audio file
     */
    private static final String INTERACTION_SOUND = "interaction.wav";
    
    /**
     * Reference to the spreadsheet.
     */
    private final Spreadsheet spreadsheet;
    
    /**
     * CellsPanel constructor which accepts a parameter,
     * sets the layout, and initializes the cells and their listeners.
     * @param spreadsheet
     */
    public CellsPanel(Spreadsheet spreadsheet) {
        this.spreadsheet = spreadsheet;
        this.numRows = spreadsheet.getNumRows();
        this.numCols = spreadsheet.getNumColumns();
        gridLayout = new GridLayout(numRows, numCols, 0, 0);
        cellArr = new JTextField[numRows][numCols];
        setLayout(gridLayout);
        initializeCellFields();
        addListeners();
    }
    
    /**
     * A function for adding listeners to cells. Audio is initiated in
     * this function.
     */
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
    
    /**
     * Play audio if possible, otherwise throw exceptions.
     */
    private void tryAudio() {
        try {
            playAudio();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e2) {
            audioError();
        }
    }
    
    /**
     * Plays audio for setting an input in a cell otherwise throws
     * exception(s).
     * @throws LineUnavailableException
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    private void playAudio() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(INTERACTION_SOUND));
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }
    
    /**
     * Show dialog for audio error.
     */
    public void audioError() {
        JOptionPane.showMessageDialog(this, "Audio Error. Program Shutting Down.");
        System.exit(0);
    }
    
    /**
     * Create and initialize cells.
     */
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
