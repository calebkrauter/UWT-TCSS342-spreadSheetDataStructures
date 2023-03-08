package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Stack;

@SuppressWarnings("SpellCheckingInspection")
public class Spreadsheet implements PropertyChangeEnabledSpreadSheet {
    
    private static int rows;
    
    private static int cols;
    static private Cell[][] cellArray;
	
	/** The property change support. */
	private final PropertyChangeSupport myPcs = new PropertyChangeSupport(Spreadsheet.class);
    
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
    
    public int getNumColumns() {
        return cols;
    }
    
    public int getNumRows() {
        return rows;
    }
    
    public Cell getCell(int row, int column) {
        return cellArray[row][column];
    }
    
    public int getCellValue(CellToken cellToken) {
    	return cellArray[cellToken.getRow()][cellToken.getColumn()].getValue();
    }
    
    public void changeCellFormulaAndRecalculate(CellToken cellToken, String s) {
    	Cell c = cellArray[cellToken.getRow()][cellToken.getColumn()];
    	// dereferences itself from dependents first
    	// useful for when the user changes the formula
    	if (c.getDependents() != null) {
    		for (CellToken ct: c.getDependents()) {
    			cellArray[ct.getRow()][ct.getColumn()].removeReferences(c);
    		}
    	}
    	String prevFormula = c.getFormula();
    	c.setFormula(s);
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
    	Stack<Cell> ts = TopologicalSort.sort(cellArray, indegrees);
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