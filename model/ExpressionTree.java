/**
 * @author Bairu Li
 * @author Andy Comfort
 */
package model;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Converts a post-fix expression stack into a expression tree.
 * Have the ability to evaluate itself and print the formula.
 */
public class ExpressionTree {
	/** The root of the tree.*/
    private ExpressionTreeNode root;
    /** The cells that this tree depends on.*/
    private final Set<CellToken> dependentCells;
    
    /**
     * Constructs a expression tree. It has 0 height with the root as null.
     */
    public ExpressionTree() {
        root = null;
        dependentCells = new HashSet<>();
    }
    /**
     * Evaluates itself and returns the value after evaluation.
     * 
     * @param spreadsheet the spreadsheet of the model which is used to get the values of
     * the cells that this expression tree depends on
     * 
     * @return the evaluated value of this expression tree
     */
    public int evaluate(Spreadsheet spreadsheet) {
    	return evalTree(spreadsheet, root);
    }
    
    /**
     * Helper method to recursively evaluate this expression tree. 
     *  
     * @param spreadsheet the spreadsheet of the model which is used to get the values of
     * the cells that this expression tree depends on
     * @param n the root of the subtree
     * @return the value of the expression subtree
     */
    private int evalTree(Spreadsheet spreadsheet, ExpressionTreeNode n) {
    	if (n == null) {
    		return 0;
    	}
    	
    	// returns the value of the leafs to be operated on
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
    	
    	// operates accordingly to the operator
    	char op = ((OperatorToken) n.getToken()).getOperatorToken();
    	if (op == '+')
    		return leftEval + rightEval;
    	if (op == '-')
    		return leftEval - rightEval;
    	if (op == '*')
    		return leftEval * rightEval;
    	return leftEval / rightEval;
    }
    
    /**
     * Converts a post-fix expression stack to a expression tree.
     * 
     * @param s the expression stack
     */
    public void BuildExpressionTree (Stack<Token> s) {
        dependentCells.clear();
        root = GetExpressionTree(s);
    }
    
    /**
     * Helper method to build the expression tree from a post-fix expression stack recursively. 
     * 
     * @param s the expression stack
     * @return the root of the subtree
     */
    private ExpressionTreeNode GetExpressionTree(Stack<Token> s) {
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
    
    /**
     * Gets the dependent cells as a set of cell tokens.
     * 
     * @return a set of cell tokens
     */
    public Set<CellToken> getDependents() {
    	return dependentCells;
    }
}