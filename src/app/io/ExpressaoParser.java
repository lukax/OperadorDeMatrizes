package app.io;

import java.util.Arrays;
import java.util.Map;

import app.mat.AdicaoEscalar;
import app.mat.Escalar;
import app.mat.InversaoEscalar;
import app.mat.MultiplicacaoEscalar;
import app.mat.NegacaoEscalar;
import app.mat.base.Expressao;
import app.util.Token;
import app.util.TokenType;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ExpressaoParser {
	private ExpressaoTokenizer tokenizer;
	private Map<String, Expressao> variables;
	
	public ExpressaoParser(ExpressaoTokenizer tokenizer, Map<String, Expressao> variables){
		this.tokenizer = tokenizer;
		this.variables = variables;		
	}

	public Expressao parse() {
		Expressao result = expression();

		Token token = tokenizer.nextToken();
		if (token.getType() == TokenType.END) {
			return result;
		} 
		else {
			// TODO: end expected
			return null;
		}
	}

	private Expressao variable() {
		Expressao number = null;

		Token token = tokenizer.nextToken();
		if (token.getType() == TokenType.LPAREN) {
			// Se for parenteses, processar a expressão interior
			number = expression();

			Token nextToken = tokenizer.nextToken();
			if (nextToken.getType() != TokenType.RPAREN) {
				// TODO: Se não terminar com parenteses lançar excessao
			}
		} 
		else if (token.getType() == TokenType.NUM) {
			double value = Double.parseDouble(token.getValue());
			number = new Escalar(value);
		} 
		else if (token.getType() == TokenType.VAR) {
			String varName = token.getValue();
			
			number = variables.get(varName);
			if(number == null){
				//TODO: variavel nao existe na lista
			}
		} 
		else {
			// TODO: nao é um numero
			
		}

		return number;
	}
	
	private Expressao factor() {
		TokenType[] ops = { TokenType.MUL, TokenType.DIV };

		Expressao factor1 = variable();

		Token nextToken = tokenizer.nextToken();
		while (Arrays.asList(ops).contains(nextToken.getType())) {
			Expressao factor2 = variable();

			if (nextToken.getType() == TokenType.MUL) {
				factor1 = new MultiplicacaoEscalar(factor1, factor2);
			} 
			else if (nextToken.getType() == TokenType.DIV) {
				factor1 = new MultiplicacaoEscalar(factor1,
						new InversaoEscalar(factor2));
			}

			nextToken = tokenizer.nextToken();
		}
		tokenizer.revert();// O proximo token não é responsabilidade dessa
							// funcao

		return factor1;
	}

	private Expressao expression() {
		TokenType[] ops = { TokenType.ADD, TokenType.SUB };

		Expressao expression1 = factor();

		Token nextToken = tokenizer.nextToken();
		while (Arrays.asList(ops).contains(nextToken.getType())) {
			Expressao expression2 = factor();

			if (nextToken.getType() == TokenType.ADD) {
				expression1 = new AdicaoEscalar(expression1, expression2);
			} 
			else if (nextToken.getType() == TokenType.SUB) {
				expression1 = new AdicaoEscalar(expression1,
						new NegacaoEscalar(expression2));
			}
			
			nextToken = tokenizer.nextToken();
		}
		tokenizer.revert();

		return expression1;
	}

}
