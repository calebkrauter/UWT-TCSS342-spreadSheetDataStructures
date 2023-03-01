import java.util.Stack;

public class Cell {

    private String formula;
    private int value;
    // the expression tree below represents the formula
    private ExpressionTree expressionTree;
    
    public Cell() {
        formula = "";
        value = 0;
        expressionTree = new ExpressionTree();
    }
    
    public void Evaluate (Spreadsheet spreadsheet) {
    	
    }
    
    public String getFormula() {
    	return formula;
    }
    
    public void setFormula(Stack expTreeTokenStack) {
    	expressionTree.BuildExpressionTree(expTreeTokenStack);
    	// not working
    	expressionTree.printTree();
    }
}