package src;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ExpressionTree {
    private ExpressionTreeNode root;
    private Set<CellToken> dependentCells;
    
    // Constructor with no root
    public ExpressionTree() {
        root = null;
        dependentCells = new HashSet<>();
    }
    
    public int evaluate(Spreadsheet spreadsheet) {
    	return evalTree(spreadsheet, root);
    }
    
    private int evalTree(Spreadsheet spreadsheet, ExpressionTreeNode n) {
    	if (n == null) {
    		return 0;
    	}
    	
    	// returns value of the leafs to be operated on
    	if (n.left == null && n.right == null) {
    		if (n.getToken().getType().equals("CELL")) {
    			return spreadsheet.getCellValue((CellToken) n.getToken());
            } else if (n.getToken().getType().equals("LITERAL")) {
            	return ((LiteralToken) n.getToken()).getValue();
            }
    	}
    	
    	// post order traversal
    	int leftEval = evalTree(spreadsheet, n.left);
    	int rightEval = evalTree(spreadsheet, n.right);
    	
    	// operates accordingly
    	char op = ((OperatorToken) n.getToken()).getOperatorToken();
    	if (op == '+')
    		return leftEval + rightEval;
    	if (op == '-')
    		return leftEval - rightEval;
    	if (op == '*')
    		return leftEval * rightEval;
    	return leftEval / rightEval;
    }
    
    public void BuildExpressionTree (Stack s) {
        dependentCells.clear();
        root = GetExpressionTree(s);
    }
    
    private ExpressionTreeNode GetExpressionTree(Stack s) {
        ExpressionTreeNode returnTree;
        Token token;
        
        if (s.isEmpty()) {
            return null;
        }
        
        token = (Token) s.pop();  // need to handle stack underflow
        if ((token.getType().equals("LITERAL")) ||
                (token.getType().equals("CELL"))) {
            // Literals and Cells are leaves in the expression tree
        	returnTree = new ExpressionTreeNode(token, null, null);
        	// adds dependent cells
        	if (token.getType().equals("CELL")) {
        		dependentCells.add((CellToken) token);
        	}
            return returnTree;
        } else if (token.getType().equals("OPERATOR")) {
            // Continue finding tokens that will form the
            // right subtree and left subtree.
            ExpressionTreeNode rightSubtree = GetExpressionTree (s);
            ExpressionTreeNode leftSubtree  = GetExpressionTree (s);
            returnTree =
                    new ExpressionTreeNode(token, leftSubtree, rightSubtree);
            return returnTree;
        }
        // never reaching
        return null;
    }
    
    public Set<CellToken> getDependents() {
    	return dependentCells;
    }
}