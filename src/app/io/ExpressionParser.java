package app.io;

import java.util.Arrays;
import java.util.Map;

import app.domain.ExpressaoEscalar;
import app.domain.ExpressaoMatricial;
import app.domain.Token;
import app.domain.TokenType;
import app.mat.AdicaoEscalar;
import app.mat.AdicaoMatricial;
import app.mat.AdicaoMista;
import app.mat.Escalar;
import app.mat.InversaoEscalar;
import app.mat.MultiplicacaoEscalar;
import app.mat.MultiplicacaoMatricial;
import app.mat.MultiplicacaoMista;
import app.mat.NegacaoEscalar;
import app.mat.NegacaoMatricial;
import app.mat.base.Expressao;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ExpressionParser {
	private ExpressaoTokenizer tokenizer;
	private Map<String, Expressao> variables;
	
	public ExpressionParser(ExpressaoTokenizer tokenizer, Map<String, Expressao> variables){
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
		Expressao var = null; // Pode ser tipo Matriz ou Escalar

		Token token = tokenizer.nextToken();
		if (token.getType() == TokenType.LPAREN) {
			// Se for parenteses, processar a expressão interior
			var = expression();

			Token nextToken = tokenizer.nextToken();
			if (nextToken.getType() != TokenType.RPAREN) {
				// TODO: Se não terminar com parenteses lançar excessao
			}
		} 
		else if (token.getType() == TokenType.NUM) {
			double value = Double.parseDouble(token.getValue());
			var = new Escalar(value);
		} 
		else if (token.getType() == TokenType.VAR) {
			String varName = token.getValue();
			
			var = variables.get(varName);
			if(var == null){
				//TODO: variavel nao existe na lista
			}
		} 
		else {
			// TODO: nao é um numero
			
		}

		return var;
	}
	
	private Expressao factor() {
		TokenType[] ops = { TokenType.MUL, TokenType.DIV };

		Expressao factor1 = variable();

		Token nextToken = tokenizer.nextToken();
		while (Arrays.asList(ops).contains(nextToken.getType())) {
			Expressao factor2 = variable();

			if (nextToken.getType() == TokenType.MUL) {
				if(factor1 instanceof ExpressaoEscalar && factor2 instanceof ExpressaoEscalar)
					//escalar * escalar
					factor1 = new MultiplicacaoEscalar(factor1, factor2);
				else if(factor1 instanceof ExpressaoMatricial && factor2 instanceof ExpressaoMatricial)
					//matriz * matriz
					factor1 = new MultiplicacaoMatricial(factor1, factor2);
				else if(factor1 instanceof ExpressaoEscalar && factor2 instanceof ExpressaoMatricial)
					//escalar * matriz
					factor1 = new MultiplicacaoMista(factor1, factor2);
				else if(factor1 instanceof ExpressaoMatricial && factor2 instanceof ExpressaoEscalar)
					//matriz * escalar
					factor1 = new MultiplicacaoMista(factor2, factor1);
			} 
			else if (nextToken.getType() == TokenType.DIV) {
				if(factor1 instanceof ExpressaoEscalar && factor2 instanceof ExpressaoEscalar)
					//escalar / escalar
					factor1 = new MultiplicacaoEscalar(factor1, new InversaoEscalar(factor2));
				else if(factor1 instanceof ExpressaoMatricial && factor2 instanceof ExpressaoMatricial)
					//matriz / matriz
					throw new RuntimeException();
				else if(factor1 instanceof ExpressaoEscalar && factor2 instanceof ExpressaoMatricial)
					//escalar / matriz
					throw new RuntimeException(); 
				else if(factor1 instanceof ExpressaoMatricial && factor2 instanceof ExpressaoEscalar)
					//matriz / escalar
					throw new RuntimeException();
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
				if(expression1 instanceof ExpressaoEscalar && expression2 instanceof ExpressaoEscalar)
					//escalar + escalar
					expression1 = new AdicaoEscalar(expression1, expression2);
				else if(expression1 instanceof ExpressaoMatricial && expression2 instanceof ExpressaoMatricial)
					//matriz + matriz
					expression1 = new AdicaoMatricial(expression1, expression2);
				else if(expression1 instanceof ExpressaoEscalar && expression2 instanceof ExpressaoMatricial)
					//escalar + matriz
					expression1 = new AdicaoMista(expression1, expression2);
				else if(expression1 instanceof ExpressaoMatricial && expression2 instanceof ExpressaoEscalar)
					//matriz + escalar
					expression1 = new AdicaoMista(expression2, expression1);
			}
			else if (nextToken.getType() == TokenType.SUB) {
				if(expression1 instanceof ExpressaoEscalar && expression2 instanceof ExpressaoEscalar)
					//escalar - escalar
					expression1 = new AdicaoEscalar(expression1, new NegacaoEscalar(expression2));
				else if(expression1 instanceof ExpressaoMatricial && expression2 instanceof ExpressaoMatricial)
					//matriz - matriz
					expression1 = new AdicaoMatricial(expression1, new NegacaoMatricial(expression2));
				else if(expression1 instanceof ExpressaoEscalar && expression2 instanceof ExpressaoMatricial)
					//escalar - matriz
					expression1 = new AdicaoMista(expression1, new NegacaoMatricial(expression2));
				else if(expression1 instanceof ExpressaoMatricial && expression2 instanceof ExpressaoEscalar)
					//matriz - escalar
					expression1 = new AdicaoMista(new NegacaoEscalar(expression2), expression1);
			}
			
			nextToken = tokenizer.nextToken();
		}
		tokenizer.revert();

		return expression1;
	}

}
