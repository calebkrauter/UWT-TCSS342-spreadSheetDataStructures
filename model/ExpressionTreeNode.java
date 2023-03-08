package model;

public class ExpressionTreeNode {
    private final Token token;
    
    final ExpressionTreeNode left;
    final ExpressionTreeNode right;
    
    public ExpressionTreeNode(Token theToken, ExpressionTreeNode leftSubtree, ExpressionTreeNode rightSubtree) {
        token = theToken;
        left = leftSubtree;
        right = rightSubtree;
    }
    
    public Token getToken() {
        return token;
    }
}