import com.sun.source.tree.ExpressionTree;

public class Cell {
    private String formula;
    private int value;
    // the expression tree below represents the formula
    private ExpressionTree expressionTree;
    
    public void Evaluate (Spreadsheet spreadsheet) {};
}