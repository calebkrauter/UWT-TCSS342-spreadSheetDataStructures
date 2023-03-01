public class ExpressionTreeNode {
    private Token token;
    
    ExpressionTreeNode left;
    ExpressionTreeNode right;
    
    public ExpressionTreeNode(Token token, ExpressionTreeNode leftSubtree, ExpressionTreeNode rightSubtree) {
    }
    
    public Token getToken() {
    	return token;
    }
}