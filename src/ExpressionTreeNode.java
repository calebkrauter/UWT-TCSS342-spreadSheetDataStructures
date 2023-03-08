package src;

public class ExpressionTreeNode {
    private Token token;
    
    ExpressionTreeNode left;
    ExpressionTreeNode right;
    
    public ExpressionTreeNode(Token theToken, ExpressionTreeNode leftSubtree, ExpressionTreeNode rightSubtree) {
        token = theToken;
        left = leftSubtree;
        right = rightSubtree;
    }
    
    public Token getToken() {
        return token;
    }
}