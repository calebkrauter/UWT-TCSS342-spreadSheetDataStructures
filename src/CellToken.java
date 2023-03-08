package src;

import java.util.Objects;

public class CellToken extends Token {
    private int column; // column A = 0, B = 1, etc.
    private int row;
    
    public CellToken() {
    }
    
    public void setColumn(int theColumn) {
        column = theColumn;
    }
    
    public void setRow(int theRow) {
        row = theRow;
    }
    
    public int getColumn() {
        return column;
    }
    
    public int getRow() {
        return row;
    }
    
    public String getValue() {
        int colVal = column + 1;
        
        StringBuilder sb = new StringBuilder();
        while (colVal-- > 0) {
            sb.insert(0, (char) ('A' + (colVal % 26)));
            colVal /= 26;
        }
        sb = sb.append(row);
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
    	return Objects.hash(getValue());
    }
    
    @Override
    public boolean equals(Object o) {
    	if (o.getClass() != CellToken.class)
    		return false;
    	CellToken ct = (CellToken) o;
    	return row == ct.row && column == ct.column;
    }
}