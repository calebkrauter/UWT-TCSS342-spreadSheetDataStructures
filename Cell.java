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
    
//    public void setFormula(String s) {
//    	formula = s;
//    	System.out.println(formula);
//    }
    
    public void setFormula(Stack expTreeTokenStack) {
//    	while (!expTreeTokenStack.isEmpty()) {
//            Token expTreeToken = (Token) expTreeTokenStack.pop();
//            if (expTreeToken instanceof OperatorToken) {
//            	System.out.println(((OperatorToken) expTreeToken).getOperatorToken());
//            } else if (expTreeToken instanceof CellToken) {
//            	System.out.println(((CellToken) expTreeToken).getValue());
//            } else if (expTreeToken instanceof LiteralToken) {
//            	System.out.println(((LiteralToken) expTreeToken).getValue());
//            } else {
//                // This case should NEVER happen
//                System.out.println("Error in printExpressionTreeToken.");
//            }
//        }
    	expressionTree.BuildExpressionTree(expTreeTokenStack);
    	//System.out.println(expTreeTokenStack);
    	// not working
    	expressionTree.printTree();
    }
}