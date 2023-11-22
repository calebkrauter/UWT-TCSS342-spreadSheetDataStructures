/**
 * @author Andy Comfort
 */
package model;

/**
 * Represents a token that contains an integer.
 */
public class LiteralToken extends Token {
    
    /**
     * The value this token represents.
     */
    private final int value;
    
    /**
     * Instantiates a new Literal token.
     *
     * @param theValue the value
     */
    public LiteralToken(int theValue) {
        super("LITERAL");
        value = theValue;
    }
    
    /**
     * Gets the value.
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }
}