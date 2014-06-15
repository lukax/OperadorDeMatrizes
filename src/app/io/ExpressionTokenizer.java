package app.io;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.domain.Token;
import app.domain.TokenType;
import app.exception.InvalidSyntaxException;
import app.util.Tuple;

public class ExpressionTokenizer {

    private final String expressao;
    private int index;
    private boolean returnPreviousToken = false;
    private Token previousToken;

    public ExpressionTokenizer(String expressao) {
        this.expressao = expressao;
        this.index = 0;
    }

    public Token nextToken() {
        if (returnPreviousToken) {
            returnPreviousToken = false;
            return previousToken;
        }

        Token token = new Token();

        if (index == expressao.length()) {
            token.setType(TokenType.END);
        } else {
            String subExpressao = expressao.substring(index);

            char firstChar = subExpressao.charAt(0);
            switch (firstChar) {//For single character tokens
                case ' ':
                    index++;
                    return nextToken();
                case '+':
                    token.setType(TokenType.ADD);
                    token.setValue("" + firstChar);
                    index++;
                    break;
                case '-':
                    token.setType(TokenType.SUB);
                    token.setValue("" + firstChar);
                    index++;
                    break;
                case '*':
                    token.setType(TokenType.MUL);
                    token.setValue("" + firstChar);
                    index++;
                    break;
                case '/':
                    token.setType(TokenType.DIV);
                    token.setValue("" + firstChar);
                    index++;
                    break;
                case '(':
                    token.setType(TokenType.LPAREN);
                    token.setValue("" + firstChar);
                    index++;
                    break;
                case ')':
                    token.setType(TokenType.RPAREN);
                    token.setValue("" + firstChar);
                    index++;
                    break;
                default: { //For multiple character tokens
                    List<Tuple<TokenType, Matcher>> matchers = new ArrayList<Tuple<TokenType, Matcher>>();
                    
                    matchers.add(new Tuple<TokenType, Matcher>(TokenType.LET, Pattern.compile("^((LET)|(let))").matcher(subExpressao)));
                    matchers.add(new Tuple<TokenType, Matcher>(TokenType.DET, Pattern.compile("^det").matcher(subExpressao)));
                    matchers.add(new Tuple<TokenType, Matcher>(TokenType.SOL, Pattern.compile("^sol").matcher(subExpressao)));
                    matchers.add(new Tuple<TokenType, Matcher>(TokenType.NUM, Pattern.compile("^[-+]?[0-9]*\\.?[0-9]+").matcher(subExpressao)));
                    matchers.add(new Tuple<TokenType, Matcher>(TokenType.VAR, Pattern.compile("^[A-Za-z]+").matcher(subExpressao)));

                    for(Tuple<TokenType, Matcher> t : matchers) {
                    	if(t.getArg2().find()) {
                    		token.setType(t.getArg1());
                    		token.setValue(t.getArg2().group());
                    		break;
                    	}
                    }
                    
                    if (token.getType() == null && token.getValue() == null) 
                        throw new InvalidSyntaxException();
                    
                    index += token.getValue().length();
                }
            }
        }

        return (previousToken = token);
    }

    public void revert() {
        returnPreviousToken = true;
    }
}
