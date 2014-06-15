package app.io;

import java.util.List;

import app.domain.Token;
import app.domain.TokenType;
import app.domain.Variable;

public class InputParser {
	private Token token;
	private ExpressionTokenizer tokenizer;
	
	public InputParser(String expression) {
		tokenizer = new ExpressionTokenizer(expression);
		this.token = tokenizer.nextToken();
		tokenizer.revert();
	}
	
	public Object parse(List<Variable> vars) {
		if(token.getType() == TokenType.LET) {
			return new VariableParser(tokenizer).parse();
		}else {
			return new ExpressionParser(tokenizer, vars).parse();
		}
	}
}
