package src;

public class LiteralToken extends Token {
    private int value;
    
    public LiteralToken(int theValue) {
        super("LITERAL");
        value = theValue;
    }
    
    public int getValue() {
        return value;
    }
}