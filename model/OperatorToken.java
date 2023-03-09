/**
 * @author Andy Comfort
 */
package model;

/**
 * Represents a token that contains a
 * mathematical operator.
 */
public class OperatorToken extends Token {
    
    /**
     * The constant plus.
     */
    public static final char Plus  = '+';
    
    /**
     * The constant minus.
     */
    public static final char Minus = '-';
    
    /**
     * The constant multiplication.
     */
    public static final char Mult  = '*';
    
    /**
     * The constant division.
     */
    public static final char Div   = '/';
    /**
     * The constant division.
     */
    public static final char Carot   = '^';
    /**
     * The constant left parentheses.
     */
    public static final char LeftParen  = '(';
    
    /**
     * The operator this token represents.
     */
    private final char operatorToken;
    
    /**
     * Instantiates a new Operator token.
     *
     * @param operatorToken the operator token
     */
    public OperatorToken(char operatorToken) {
        super("OPERATOR");
        this.operatorToken = operatorToken;
    }
    
    /**
     * Return the priority of this OperatorToken.
     *
     * priorities:
     *   +, - : 0
     *   *, / : 1
     *   (    : 2
     *
     * @return  the priority of operatorToken
     */
    public int priority() {
        switch (this.operatorToken) {
            case Plus, Minus -> {
                return 0;
            }
            case Mult, Div -> {
                return 1;
            }
            case Carot -> {
                return 2;
            }
            case LeftParen -> {
                return 3;
            }
            default -> {
                // This case should NEVER happen
                System.out.println("Error in priority.");
                System.exit(0);
            }
        }
        return -1;
    }
    
    /**
     * Gets operator token.
     *
     * @return the operator token
     */
    public char getOperatorToken() {
        return operatorToken;
    }
}