package app.io;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import app.domain.Token;
import app.domain.TokenType;

public class ExpressaoTokenizerTest {

    @Test
    public void identifyNumbers() {
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("1.878");
        Token token = tokenizer.nextToken();

        assertEquals(token.getType(), TokenType.NUM);
        assertEquals(token.getValue(), "1.878");
    }

    @Test
    public void identifyFuncs() {
    	ExpressionTokenizer tokenizer = new ExpressionTokenizer("det");
        Token token = tokenizer.nextToken();

        assertEquals(token.getType(), TokenType.DET);
        assertEquals(token.getValue(), "det");
    }

    @Test
    public void identifyVariables() {
    	ExpressionTokenizer tokenizer = new ExpressionTokenizer("a");
        Token token = tokenizer.nextToken();

        assertEquals(token.getType(), TokenType.VAR);
        assertEquals(token.getValue(), "a");
    }

    @Test
    public void identifyExpressions() {
    	ExpressionTokenizer t = new ExpressionTokenizer("35.1+sol(A)*(1+2)");
        Token token;

        token = t.nextToken();
        assertEquals(TokenType.NUM, token.getType());
        assertEquals("35.1", token.getValue());

        assertEquals(TokenType.ADD, t.nextToken().getType());

        assertEquals(TokenType.SOL, t.nextToken().getType());

        assertEquals(TokenType.LPAREN, t.nextToken().getType());

        token = t.nextToken();
        assertEquals(TokenType.VAR, token.getType());
        assertEquals("A", token.getValue());

        assertEquals(TokenType.RPAREN, t.nextToken().getType());

        token = t.nextToken();
        assertEquals(TokenType.MUL, token.getType());

        assertEquals(TokenType.LPAREN, t.nextToken().getType());

        token = t.nextToken();
        assertEquals(TokenType.NUM, token.getType());
        assertEquals("1", token.getValue());

        assertEquals(TokenType.ADD, t.nextToken().getType());

        token = t.nextToken();
        assertEquals(TokenType.NUM, token.getType());
        assertEquals("2", token.getValue());

        assertEquals(TokenType.RPAREN, t.nextToken().getType());

        assertEquals(TokenType.END, t.nextToken().getType());
    }
}
