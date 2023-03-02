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
    public void printTree() {
        print(root);
    }
    public int Evaluate(Spreadsheet spreadsheet){ return 0; };
    
    private ExpressionTreeNode GetExpressionTree(Stack s) {
        ExpressionTreeNode returnTree;
        Token token;
        
        System.out.println("Ran");
        
        if (s.isEmpty()) {
        	System.out.println("Stack is empty");
        	// never reaching
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
    
    void BuildExpressionTree (Stack s) {
        root = GetExpressionTree(s);
    }
    
    private void print(ExpressionTreeNode n) {
    	if (n == null)
    		return;
    	
    	print(n.left);
    	System.out.println(n.getToken());
    	print(n.right);
    }
}