import java.util.Stack;

public class Cell {
    
    private static final int BAD_CELL = -1;
    private String formula;
    private int value;
    // the expression tree below represents the formula
    private ExpressionTree expressionTree;
    private int amountOfCells = 0;
    
    public Cell() {
        setCellAmount(amountOfCells++);
        formula = "";
        value = 0;
        expressionTree = new ExpressionTree();
    }
    private void setCellAmount(int amountOfCells) {
        this.amountOfCells = amountOfCells;
    }
    public int getCellAmount() {
        return this.amountOfCells;
    }
    
    public void Evaluate (Spreadsheet spreadsheet) {
    	value = expressionTree.Evaluate(spreadsheet);
    }
    
    public String getFormula() {
        return formula;
    }
    
    public int getValue() {
    	return value;
    }
    
    public void setFormula(String s) {
    	expressionTree.BuildExpressionTree(getFormula(s));
    	formula = expressionTree.printTree();
    	System.out.println("Formula set too: " + formula);
    }
    
    /**
     * Return a string associated with a token
     *
     * @param expTreeToken an ExpressionTreeToken
     * @return a String associated with expTreeToken
     */
    static String printExpressionTreeToken(Token expTreeToken) {
        String returnString = "";

        if (expTreeToken instanceof OperatorToken) {
            returnString = ((OperatorToken) expTreeToken).getOperatorToken() + " ";
        } else if (expTreeToken instanceof CellToken) {
            returnString = ((CellToken) expTreeToken).getValue() + " ";
        } else if (expTreeToken instanceof LiteralToken) {
            returnString = ((LiteralToken) expTreeToken).getValue() + " ";
        } else {
            // This case should NEVER happen
            System.out.println("Error in printExpressionTreeToken.");
            System.exit(0);
        }
        return returnString;
    }
    
    /**
     * getCellToken
     * <p>
     * Assuming that the next chars in a String (at the given startIndex)
     * is a cell reference, set cellToken's column and row to the
     * cell's column and row.
     * If the cell reference is invalid, the row and column of the return CellToken
     * are both set to BadCell (which should be a final int that equals -1).
     * Also, return the index of the position in the string after processing
     * the cell reference.
     * (Possible improvement: instead of returning a CellToken with row and
     * column equal to BadCell, throw an exception that indicates a parsing error.)
     * <p>
     * A cell reference is defined to be a sequence of CAPITAL letters,
     * followed by a sequence of digits (0-9).  The letters refer to
     * columns as follows: A = 0, B = 1, C = 2, ..., Z = 25, AA = 26,
     * AB = 27, ..., AZ = 51, BA = 52, ..., ZA = 676, ..., ZZ = 701,
     * AAA = 702.  The digits represent the row number.
     *
     * @param inputString the input string
     * @param startIndex  the index of the first char to process
     * @param cellToken   a cellToken (essentially a return value)
     * @return index corresponding to the position in the string just after the cell reference
     */
    int getCellToken(String inputString, int startIndex, CellToken cellToken) {
        char ch;
        int column = 0;
        int row = 0;
        int index = startIndex;
        
        // handle a bad startIndex
        if ((startIndex < 0) || (startIndex >= inputString.length())) {
            cellToken.setColumn(BAD_CELL);
            cellToken.setRow(BAD_CELL);
            return index;
        }
        
        // get rid of leading whitespace characters
        while (index < inputString.length()) {
            ch = inputString.charAt(index);
            if (!Character.isWhitespace(ch)) {
                break;
            }
            index++;
        }
        if (index == inputString.length()) {
            // reached the end of the string before finding a capital letter
            cellToken.setColumn(BAD_CELL);
            cellToken.setRow(BAD_CELL);
            return index;
        }
        
        // ASSERT: index now points to the first non-whitespace character
        
        ch = inputString.charAt(index);
        // process CAPITAL alphabetic characters to calculate the column
        if (!Character.isUpperCase(ch)) {
            cellToken.setColumn(BAD_CELL);
            cellToken.setRow(BAD_CELL);
            return index;
        } else {
            column = ch - 'A';
            index++;
        }
        
        while (index < inputString.length()) {
            ch = inputString.charAt(index);
            if (Character.isUpperCase(ch)) {
                column = ((column + 1) * 26) + (ch - 'A');
                index++;
            } else {
                break;
            }
        }
        if (index == inputString.length()) {
            // reached the end of the string before fully parsing the cell reference
            cellToken.setColumn(BAD_CELL);
            cellToken.setRow(BAD_CELL);
            return index;
        }
        
        // ASSERT: We have processed leading whitespace and the
        // capital letters of the cell reference
        
        // read numeric characters to calculate the row
        if (Character.isDigit(ch)) {
            row = ch - '0';
            index++;
        } else {
            cellToken.setColumn(BAD_CELL);
            cellToken.setRow(BAD_CELL);
            return index;
        }
        
        while (index < inputString.length()) {
            ch = inputString.charAt(index);
            if (Character.isDigit(ch)) {
                row = (row * 10) + (ch - '0');
                index++;
            } else {
                break;
            }
        }
        
        // successfully parsed a cell reference
        cellToken.setColumn(column);
        cellToken.setRow(row);
        return index;
    }
    
    /**
     * getFormula
     * <p>
     * Given a string that represents a formula that is an infix
     * expression, return a stack of Tokens so that the expression,
     * when read from the bottom of the stack to the top of the stack,
     * is a postfix expression.
     * <p>
     * A formula is defined as a sequence of tokens that represents
     * a legal infix expression.
     * <p>
     * A token can consist of a numeric literal, a cell reference, or an
     * operator (+, -, *, /).
     * <p>
     * Multiplication (*) and division (/) have higher precedence than
     * addition (+) and subtraction (-).  Among operations within the same
     * level of precedence, grouping is from left to right.
     * <p>
     * This algorithm follows the algorithm described in Weiss, pages 105-108.
     */
    Stack getFormula(String formula) {
        Stack returnStack = new Stack();  // stack of Tokens (representing a postfix expression)
        boolean error = false;
        char ch = ' ';
        
        int literalValue = 0;
        int column = 0;
        int row = 0;
        
        int index = 0;  // index into formula
        Stack operatorStack = new Stack();  // stack of operators
        
        while (index < formula.length()) {
            // get rid of leading whitespace characters
            while (index < formula.length()) {
                ch = formula.charAt(index);
                if (!Character.isWhitespace(ch)) {
                    break;
                }
                index++;
            }
            
            if (index == formula.length()) {
                error = true;
                break;
            }
            
            // ASSERT: ch now contains the first character of the next token.
            if (isOperator(ch)) {
                // We found an operator token
                switch (ch) {
                    case OperatorToken.Plus:
                    case OperatorToken.Minus:
                    case OperatorToken.Mult:
                    case OperatorToken.Div:
                    case OperatorToken.LeftParen:
                        // push operatorTokens onto the output stack until
                        // we reach an operator on the operator stack that has
                        // lower priority than the current one.
                        OperatorToken stackOperator;
                        while (!operatorStack.isEmpty()) {
                            stackOperator = (OperatorToken) operatorStack.peek();
                            if ((stackOperator.priority() >= operatorPriority(ch)) &&
                                    (stackOperator.getOperatorToken() != OperatorToken.LeftParen)) {
                                
                                // output the operator to the return stack
                                operatorStack.pop();
                                returnStack.push(stackOperator);
                            } else {
                                break;
                            }
                        }
                        break;
                    
                    default:
                        // This case should NEVER happen
                        System.out.println("Error in getFormula.");
                        System.exit(0);
                        break;
                }
                // push the operator on the operator stack
                operatorStack.push(new OperatorToken(ch));
                
                index++;
                
            } else if (ch == ')') {    // maybe define OperatorToken.RightParen ?
                OperatorToken stackOperator;
                stackOperator = (OperatorToken) operatorStack.pop();
                // This code does not handle operatorStack underflow.
                while (stackOperator.getOperatorToken() != OperatorToken.LeftParen) {
                    // pop operators off the stack until a LeftParen appears and
                    // place the operators on the output stack
                    returnStack.push(stackOperator);
                    stackOperator = (OperatorToken) operatorStack.pop();
                }
                
                index++;
            } else if (Character.isDigit(ch)) {
                // We found a literal token
                literalValue = ch - '0';
                index++;
                while (index < formula.length()) {
                    ch = formula.charAt(index);
                    if (Character.isDigit(ch)) {
                        literalValue = (literalValue * 10) + (ch - '0');
                        index++;
                    } else {
                        break;
                    }
                }
                // place the literal on the output stack
                returnStack.push(new LiteralToken(literalValue));
                
            } else if (Character.isUpperCase(ch)) {
                // We found a cell reference token
                CellToken cellToken = new CellToken();
                index = getCellToken(formula, index, cellToken);
                if (cellToken.getRow() == BAD_CELL) {
                    error = true;
                    break;
                } else {
                    // place the cell reference on the output stack
                    returnStack.push(cellToken);
                }
                
            } else {
                error = true;
                break;
            }
        }
        
        // pop all remaining operators off the operator stack
        while (!operatorStack.isEmpty()) {
            returnStack.push(operatorStack.pop());
        }
        
        if (error) {
            // a parse error; return the empty stack
            //returnStack.makeEmpty();
        }
        
        return returnStack;
    }
    /**
     * Return true if the char ch is an operator of a formula.
     * Current operators are: +, -, *, /, (.
     *
     * @param ch a char
     * @return whether ch is an operator
     */
    boolean isOperator(char ch) {
        return ((ch == OperatorToken.Plus) ||
                (ch == OperatorToken.Minus) ||
                (ch == OperatorToken.Mult) ||
                (ch == OperatorToken.Div) ||
                (ch == OperatorToken.LeftParen));
    }
    
    /**
     * Given an operator, return its priority.
     * <p>
     * priorities:
     * +, - : 0
     * *, / : 1
     * (    : 2
     *
     * @param ch a char
     * @return the priority of the operator
     */
    int operatorPriority(char ch) {
        if (!isOperator(ch)) {
            // This case should NEVER happen
            System.out.println("Error in operatorPriority.");
            System.exit(0);
        }
        switch (ch) {
            case OperatorToken.Plus, OperatorToken.Minus:
                return 0;
            case OperatorToken.Mult, OperatorToken.Div:
                return 1;
            case OperatorToken.LeftParen:
                return 2;
            
            default:
                // This case should NEVER happen
                System.out.println("Error in operatorPriority.");
                System.exit(0);
                break;
        }
        return 0;
    }
}