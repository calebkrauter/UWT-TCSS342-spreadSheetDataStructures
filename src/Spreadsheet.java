package src;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Stack;

public class Spreadsheet {
    
    private static final int BAD_CELL = -1;
    
    private static int rows;
    
    private static int cols;
    static private Cell[][] cellArray;
    
    /** The property change support. */
    private final PropertyChangeSupport myPcs = new PropertyChangeSupport(Cell.class);
    
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
    
    public static void printValues() {
    	for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("|" + cellArray[i][j].getValue() + "|");
            }
            System.out.println();
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
    
    public String printCellFormula(CellToken cellToken) {
        return cellArray[cellToken.getRow()][cellToken.getColumn()].getFormula();
    }
    
    public int getCellValue(CellToken cellToken) {
    	return cellArray[cellToken.getRow()][cellToken.getColumn()].getValue();
    }
    
    public void printAllFormulas() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("|" + cellArray[i][j].getFormula() + "|");
            }
            System.out.println();
        }
    }
    
    /**
     * getCellToken
     * <p>
     * Assuming that the next chars in a String (at the given startIndex)
     * is a cell reference, set cellToken's column and row to the
     * cell's column and row.
     * If the cell reference is invalid, the row and column of the return CellToken
     * are both set to BadCell (which should be a final int that equals -1).
     * Also, return the index of the position in the string after processing
     * the cell reference.
     * (Possible improvement: instead of returning a CellToken with row and
     * column equal to BadCell, throw an exception that indicates a parsing error.)
     * <p>
     * A cell reference is defined to be a sequence of CAPITAL letters,
     * followed by a sequence of digits (0-9).  The letters refer to
     * columns as follows: A = 0, B = 1, C = 2, ..., Z = 25, AA = 26,
     * AB = 27, ..., AZ = 51, BA = 52, ..., ZA = 676, ..., ZZ = 701,
     * AAA = 702.  The digits represent the row number.
     *
     * @param inputString the input string
     * @param startIndex  the index of the first char to process
     * @param cellToken   a cellToken (essentially a return value)
     * @return index corresponding to the position in the string just after the cell reference
     */
    int getCellToken(String inputString, int startIndex, CellToken cellToken) {
        char ch;
        int column = 0;
        int row = 0;
        int index = startIndex;
        
        // handle a bad startIndex
        if ((startIndex < 0) || (startIndex >= inputString.length())) {
            cellToken.setColumn(BAD_CELL);
            cellToken.setRow(BAD_CELL);
            return index;
        }
        
        // get rid of leading whitespace characters
        while (index < inputString.length()) {
            ch = inputString.charAt(index);
            if (!Character.isWhitespace(ch)) {
                break;
            }
            index++;
        }
        if (index == inputString.length()) {
            // reached the end of the string before finding a capital letter
            cellToken.setColumn(BAD_CELL);
            cellToken.setRow(BAD_CELL);
            return index;
        }
        
        // ASSERT: index now points to the first non-whitespace character
        
        ch = inputString.charAt(index);
        // process CAPITAL alphabetic characters to calculate the column
        if (!Character.isUpperCase(ch)) {
            cellToken.setColumn(BAD_CELL);
            cellToken.setRow(BAD_CELL);
            return index;
        } else {
            column = ch - 'A';
            index++;
        }
        
        while (index < inputString.length()) {
            ch = inputString.charAt(index);
            if (Character.isUpperCase(ch)) {
                column = ((column + 1) * 26) + (ch - 'A');
                index++;
            } else {
                break;
            }
        }
        if (index == inputString.length()) {
            // reached the end of the string before fully parsing the cell reference
            cellToken.setColumn(BAD_CELL);
            cellToken.setRow(BAD_CELL);
            return index;
        }
        
        // ASSERT: We have processed leading whitespace and the
        // capital letters of the cell reference
        
        // read numeric characters to calculate the row
        if (Character.isDigit(ch)) {
            row = ch - '0';
            index++;
        } else {
            cellToken.setColumn(BAD_CELL);
            cellToken.setRow(BAD_CELL);
            return index;
        }
        
        while (index < inputString.length()) {
            ch = inputString.charAt(index);
            if (Character.isDigit(ch)) {
                row = (row * 10) + (ch - '0');
                index++;
            } else {
                break;
            }
        }
        
        // successfully parsed a cell reference
        cellToken.setColumn(column);
        cellToken.setRow(row);
        return index;
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
    	} else {
    		while (!ts.isEmpty()) {
        		ts.peek().evaluate(this);
        		for (Cell r : ts.pop().getReferences()) {
        			r.evaluate(this);
        		}
        	}
    	}
    }
}