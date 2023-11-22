/**
 * @author Bairu Li
 * @author Andy Comfort
 */
package model;

import java.util.Objects;

/**
 * Represents a token of a single cell in the
 * spreadsheet.
 */
public class CellToken extends Token {
    
    /** The column of the cell this token represents. */
    private int column; // column A = 0, B = 1, etc.
    
    /** The row of the cell this token represents. */
    private int row;
    
    /**
     * Instantiates a new Cell token.
     */
    public CellToken() {
        super("CELL");
    }
    
    /**
     * Sets the column of this token.
     *
     * @param theColumn the column
     */
    public void setColumn(int theColumn) {
        column = theColumn;
    }
    
    /**
     * Sets the row of this token.
     *
     * @param theRow the row
     */
    public void setRow(int theRow) {
        row = theRow;
    }
    
    /**
     * Gets the column of this token.
     *
     * @return the column
     */
    public int getColumn() {
        return column;
    }
    
    /**
     * Gets the row of this token.
     *
     * @return the row
     */
    public int getRow() {
        return row;
    }
    
    /**
     * Gets the value of this token in the form of the
     * column as a letter plus the row. A cell token
     * with a column value of zero and row value of
     * 5 would return A5.
     *
     * @return the value of the token.
     */
    public String getValue() {
        int colVal = column + 1;
        
        StringBuilder sb = new StringBuilder();
        while (colVal-- > 0) {
            sb.insert(0, (char) ('A' + (colVal % 26)));
            colVal /= 26;
        }
        sb.append(row);
        return sb.toString();
    }
    
    /**
     * Generates a hash code representation of this CellToken.
     *
     * @return the hash code representation of this CellToken
     */
    @Override
    public int hashCode() {
    	return Objects.hash(getValue());
    }
    
    /**
     * Compares this CellToken to another object to determine equality.
     *
     * @param o the object to compare to
     * @return true, if items have equal row and column
     */
    @Override
    public boolean equals(Object o) {
    	if (o.getClass() != CellToken.class)
    		return false;
    	CellToken ct = (CellToken) o;
    	return row == ct.row && column == ct.column;
    }
}