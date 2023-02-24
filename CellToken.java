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
}
