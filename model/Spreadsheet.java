/**
 * @author Bairu Li
 * @author Andy Comfort
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Represents a spreadsheet of interactive cells
 * that has the ability to represent basic mathematical
 * formulas.
 */
public class Spreadsheet implements PropertyChangeEnabledSpreadSheet {
	
	/** The number of rows in this spreadsheet. */
    private static int rows;
	
	/** The number of columns in this spreadsheet. */
    private static int cols;
	
	/** The array of cells representing this spreadsheet. */
    static private Cell[][] cellArray;
	
	/** The property change support. */
	private final PropertyChangeSupport myPcs = new PropertyChangeSupport(Spreadsheet.class);
	
	/**
	 * Instantiates a new Spreadsheet.
	 *
	 * @param rowSize    the row size
	 * @param columnSize the column size
	 */
	public Spreadsheet(int rowSize, int columnSize) {
        rows = rowSize;
        cols = columnSize;
        
        cellArray = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
        	for (int c = 0; c < cols; c++) {
        		cellArray[r][c] = new Cell();
        	}
        }
    }
	
	/**
	 * Gets the number of columns.
	 *
	 * @return the number of columns
	 */
	public int getNumColumns() {
        return cols;
    }
	
	/**
	 * Gets the number of rows.
	 *
	 * @return the number of rows
	 */
	public int getNumRows() {
        return rows;
    }
	
	/**
	 * Gets a cell based on row and column.
	 *
	 * @param row    the row of the cell
	 * @param column the column of the cell
	 * @return the cell
	 */
	public Cell getCell(int row, int column) {
        return cellArray[row][column];
    }
	
	/**
	 * Gets a cell value.
	 *
	 * @param cellToken the cell token
	 * @return the cell value
	 */
	public int getCellValue(CellToken cellToken) {
    	return cellArray[cellToken.getRow()][cellToken.getColumn()].getValue();
    }
	
	/**
	 * Changes a cell formula and recalculates the spreadsheet.
	 *
	 * @param cellToken  the cell token
	 * @param newFormula the newFormula
	 */
	public void changeCellFormulaAndRecalculate(CellToken cellToken, String newFormula) {
    	Cell c = cellArray[cellToken.getRow()][cellToken.getColumn()];
    	// dereferences itself from dependents first
    	// useful for when the user changes the formula
    	if (c.getDependents() != null) {
    		for (CellToken ct: c.getDependents()) {
    			cellArray[ct.getRow()][ct.getColumn()].removeReferences(c);
    		}
    	}
		// saves previous formula in case of a cycle
    	String prevFormula = c.getFormula();
    	c.setFormula(newFormula);
    	// goes through the cells that it is dependent on and reference itself on them
    	if (c.getDependents() != null) {
    		for (CellToken ct: c.getDependents()) {
    			cellArray[ct.getRow()][ct.getColumn()].addReferences(c);
    		}
    	}
    	
    	// copies all the indegrees to pass into the topological sort
    	int[][] indegrees = new int[rows][cols];
    	for (int row = 0; row < rows; row++) {
    		for (int col = 0; col < cols; col++) {
        		if(cellArray[row][col].hasFormula())
                    indegrees[row][col] = cellArray[row][col].getIndegrees();
        	}
    	}
    	
    	// sorts the cells and puts it in a stack to evaluate the cells in correct order
    	Stack<Cell> ts = topologicalSort(indegrees);
    	if (ts == null) { // cycle found
			changeCellFormulaAndRecalculate(cellToken, prevFormula);
			myPcs.firePropertyChange(PROPERTY_CYCLE, 0, cellToken);
    	} else {
    		while (!ts.isEmpty()) {
        		ts.peek().evaluate(this);
        		for (Cell r : ts.pop().getReferences()) {
        			r.evaluate(this);
        		}
        	}
    	}
    }
	
	/**
	 * Iterates through the cell array and returns a stack
	 * where each cell appears after all cells it points to.
	 *
	 * @param theIndegrees the indegrees of each cell
	 * @return the stack of cells in topological order
	 */
	public static Stack<Cell> topologicalSort(final int[][] theIndegrees) {
		Stack<Cell> s = new Stack<>();
		Queue<Integer> q = new LinkedList<>();
		int len = cellArray.length;
		
		// Add cells with 0 indegrees to the queue
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < cellArray[i].length; j++) {
				if (theIndegrees[i][j] == 0) {
					q.add(i * len + j);
				}
			}
		}
		
		while (!q.isEmpty()) {
			int dq = q.poll();
			Cell c = cellArray[dq / len][dq % len];
			
			// If the cell has no dependents, skip to the next iteration
			if (c.getDependents() == null) {
				continue;
			}
			
			// Decrement the indegrees of the cell's dependents
			for (final CellToken dependents : c.getDependents()) {
				int row = dependents.getRow();
				int col = dependents.getColumn();
				
				if (theIndegrees[row][col] != -1) {
					theIndegrees[row][col]--;
					if (theIndegrees[row][col] == 0) {
						q.add(row * len + col);
					}
				}
			}
			
			// Add the processed cell to the output stack
			s.add(c);
		}
		
		if (s.size() != len * cellArray[0].length) {
			return null;
		}
		
		return s;
	}
	
	/**
	 * Adds a property change listener.
	 *
	 * @param theListener the listener
	 */
	@Override
	public void addPropertyChangeListener(final PropertyChangeListener theListener) {
		myPcs.addPropertyChangeListener(theListener);
	}
	
	/**
	 * Adds a property change listener.
	 *
	 * @param thePropertyName the property name
	 * @param theListener the listener
	 */
	@Override
	public void addPropertyChangeListener(final String thePropertyName,
										  final PropertyChangeListener theListener) {
		myPcs.addPropertyChangeListener(thePropertyName, theListener);
		
	}
	
	/**
	 * Removes a property change listener.
	 *
	 * @param theListener the listener
	 */
	@Override
	public void removePropertyChangeListener(final PropertyChangeListener theListener) {
		myPcs.removePropertyChangeListener(theListener);
		
	}
	
	/**
	 * Removes a property change listener.
	 *
	 * @param thePropertyName the property name
	 * @param theListener the listener
	 */
	@Override
	public void removePropertyChangeListener(final String thePropertyName,
											 final PropertyChangeListener theListener) {
		myPcs.removePropertyChangeListener(thePropertyName, theListener);
		
	}
}