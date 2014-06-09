package app.io;

import app.util.Token;
import app.util.TokenType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressaoTokenizer {

    private final String expressao;
    private int index;
    private boolean returnPreviousToken = false;
    private Token previousToken;
    
    public ExpressaoTokenizer(String expressao) {
        this.index = 0;
        this.expressao = expressao.trim();
    }

    public Token nextToken() {
        if(returnPreviousToken){
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
                    String parsed = null;

                    //Find det()
                    Matcher detMatcher = Pattern.compile("^det").matcher(subExpressao);
                    //Find sol()
                    Matcher solMatcher = Pattern.compile("^sol").matcher(subExpressao);
                    //Find real number
                    Matcher numberMatcher = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]+").matcher(subExpressao);
                    //Find variable name
                    Matcher variableMatcher = Pattern.compile("^[A-Za-z]+").matcher(subExpressao);

                    if (detMatcher.find()) {
                        parsed = detMatcher.group();
                        token.setType(TokenType.DET);
                    } 
                    else if (solMatcher.find()) {
                        parsed = solMatcher.group();
                        token.setType(TokenType.SOL);
                    } 
                    else if (numberMatcher.find()) {
                        parsed = numberMatcher.group();
                        token.setType(TokenType.NUM);
                    } 
                    else if (variableMatcher.find()) {
                        parsed = variableMatcher.group();
                        token.setType(TokenType.VAR);
                    } 
                    else {
                        //TODO: exception, not a valid token
                    }

                    token.setValue(parsed);
                    index += parsed.length();
                }
            }
        }

        return (previousToken = token);
    }
    
    public void revert(){
        returnPreviousToken = true;
    }
}