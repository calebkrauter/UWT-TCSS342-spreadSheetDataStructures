/**
 * @author Bairu Li
 * @author Andy Comfort
 */
package model;
/**
 * Stores a token in the expression tree as a node with left and right child pointers.
 */
public class ExpressionTreeNode {
	/** Token as the node's data item.*/
    private final Token token;
    /** Left child pointer.*/
    final ExpressionTreeNode left;
    /** Right child pointer.*/
    final ExpressionTreeNode right;
    
    /**
     * Constructs a node that stores a token with left and right pointers.
     * 
     * @param theToken the token as data
     * @param leftSubtree the left pointer
     * @param rightSubtree the right pointer
     */
    public ExpressionTreeNode(Token theToken, ExpressionTreeNode leftSubtree, ExpressionTreeNode rightSubtree) {
        token = theToken;
        left = leftSubtree;
        right = rightSubtree;
    }
    
    /**
     * Returns this node's token.
     * 
     * @return the token
     */
    public Token getToken() {
        return token;
    }
}