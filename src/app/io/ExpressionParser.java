package app.io;

import java.util.Arrays;
import java.util.Map;

import app.domain.ExpressaoEscalar;
import app.domain.ExpressaoMatricial;
import app.domain.Token;
import app.domain.TokenType;
import app.exception.InvalidOperationException;
import app.exception.InvalidSyntaxException;
import app.exception.MissingVariableException;
import app.mat.AdicaoEscalar;
import app.mat.AdicaoMatricial;
import app.mat.AdicaoMista;
import app.mat.Determinante;
import app.mat.Escalar;
import app.mat.InversaoEscalar;
import app.mat.MultiplicacaoEscalar;
import app.mat.MultiplicacaoMatricial;
import app.mat.MultiplicacaoMista;
import app.mat.NegacaoEscalar;
import app.mat.NegacaoMatricial;
import app.mat.SolucaoDeSistema;
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
		
		//fim esperado
		throw new InvalidSyntaxException();
	}

	private Expressao variable() {
		Expressao var = null; // Matriz ou Escalar

		Token token = tokenizer.nextToken();
		if (token.getType() == TokenType.LPAREN) {
			// Se for parenteses, processar a expressão interior
			var = expression();

			Token nextToken = tokenizer.nextToken();
			if (nextToken.getType() != TokenType.RPAREN) {
				//Se não terminar com parenteses
				throw new InvalidSyntaxException();
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
				//variavel não declarada
				throw new MissingVariableException();
			}
		} 
		else {
			throw new InvalidSyntaxException();
		}

		return var;
	}
	
	private Expressao function(){
		TokenType[] funcs = { TokenType.DET, TokenType.SOL };
		
		Expressao var = null;
		
		Token token = tokenizer.nextToken();
		if(Arrays.asList(funcs).contains(token.getType())){
			var = variable();
			
			if(token.getType() == TokenType.DET)
				var = new Determinante(var);
			else if(token.getType() == TokenType.SOL)
				var = new SolucaoDeSistema(var);
			
		}
		else{
			//Nao é uma funcao, então é uma variavel
			tokenizer.revert();
			var = variable();
		}
		
		return var;
	}
	
	private Expressao factor() {
		TokenType[] ops = { TokenType.MUL, TokenType.DIV };

		Expressao factor1 = function();

		Token token = tokenizer.nextToken();
		while (Arrays.asList(ops).contains(token.getType())) {
			Expressao factor2 = variable();

			if (token.getType() == TokenType.MUL) {
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
			else if (token.getType() == TokenType.DIV) {
				if(factor1 instanceof ExpressaoEscalar && factor2 instanceof ExpressaoEscalar)
					//escalar / escalar
					factor1 = new MultiplicacaoEscalar(factor1, new InversaoEscalar(factor2));
				else if(factor1 instanceof ExpressaoMatricial && factor2 instanceof ExpressaoMatricial)
					//matriz / matriz
					throw new InvalidOperationException();
				else if(factor1 instanceof ExpressaoEscalar && factor2 instanceof ExpressaoMatricial)
					//escalar / matriz
					throw new InvalidOperationException();
				else if(factor1 instanceof ExpressaoMatricial && factor2 instanceof ExpressaoEscalar)
					//matriz / escalar
					throw new InvalidOperationException();
			}

			token = tokenizer.nextToken();
		}
		tokenizer.revert();// O proximo token não é responsabilidade dessa
							// funcao

		return factor1;
	}

	private Expressao expression() {
		TokenType[] ops = { TokenType.ADD, TokenType.SUB };

		Expressao expression1 = factor();

		Token token = tokenizer.nextToken();
		while (Arrays.asList(ops).contains(token.getType())) {
			Expressao expression2 = factor();

			if (token.getType() == TokenType.ADD) {
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
			else if (token.getType() == TokenType.SUB) {
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
			
			token = tokenizer.nextToken();
		}
		tokenizer.revert();

		return expression1;
	}

}
