public class LiteralToken extends Token {
    private int value;
    
    public LiteralToken(int theValue) {
        value = theValue;
    }
    
    public int getValue() {
        return value;
    }
}