import java.util.Stack;

public class ExpressionTree {
    private ExpressionTreeNode root;
    
    // Constructor with no root
    public ExpressionTree() {
        root = null;
    }
    
    public void makeEmpty(){
        root = null;
    }
    public String printTree() {
    	StringBuilder sb = new StringBuilder(128);
        print(root, sb);
        return sb.toString();
    }
    
    public int Evaluate(Spreadsheet spreadsheet) {
    	return evalTree(spreadsheet, root);
    }
    
    private int evalTree(Spreadsheet spreadsheet, ExpressionTreeNode n) {
    	if (n == null)
    		return 0;
    	
    	// returns value of the leafs to be operated on
    	if (n.left == null && n.right == null) {
    		if (n.getToken() instanceof CellToken) {
    			// not yet implemented
            } else if (n.getToken() instanceof LiteralToken) {
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
    
    private ExpressionTreeNode GetExpressionTree(Stack s) {
        ExpressionTreeNode returnTree;
        Token token;
        
        if (s.isEmpty()) {
            return null;
        }
        
        token = (Token) s.pop();  // need to handle stack underflow
        if ((token instanceof LiteralToken) ||
                (token instanceof CellToken) ) {
            // Literals and Cells are leaves in the expression tree
        	returnTree = new ExpressionTreeNode(token, null, null);
            return returnTree;
        } else if (token instanceof OperatorToken) {
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
    
    public void BuildExpressionTree (Stack s) {
        root = GetExpressionTree(s);
    }
    
    private void print(ExpressionTreeNode n, StringBuilder sb) {
    	if (n == null)
    		return;
    	if (n.getToken() instanceof OperatorToken)
    		sb.append('(');
    	print(n.left, sb);
    	if (n.getToken() instanceof OperatorToken) {
            sb.append(" " + ((OperatorToken) n.getToken()).getOperatorToken() + " ");
        } else if (n.getToken() instanceof CellToken) {
        	sb.append(((CellToken) n.getToken()).getValue());
        } else if (n.getToken() instanceof LiteralToken) {
        	sb.append(((LiteralToken) n.getToken()).getValue());
        } else {
            // This case should NEVER happen
            System.out.println("Error in printExpressionTreeNode.");
            System.exit(0);
        }
    	print(n.right, sb);
    	if (n.getToken() instanceof OperatorToken)
    		sb.append(')');
    }
}