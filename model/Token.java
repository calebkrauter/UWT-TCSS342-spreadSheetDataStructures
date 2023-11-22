/**
 * @author Andy Comfort
 */
package model;

/**
 * Represents a general token that can either
 * be a CellToken, OperatorToken, or LiteralToken.
 */
public class Token {
    
    /** The type of this token. */
    private final String type;
    
    /**
     * Instantiates a new Token.
     *
     * @param type the type
     */
    public Token(String type) {
        this.type = type;
    }
    
    /**
     * Gets the type of token.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }
}
