package model;

public class LiteralToken extends Token {
    private final int value;
    
    public LiteralToken(int theValue) {
        super("LITERAL");
        value = theValue;
    }
    
    public int getValue() {
        return value;
    }
}