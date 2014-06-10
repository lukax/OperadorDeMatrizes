package app.io;

import app.domain.Token;
import app.domain.TokenType;
import app.domain.Variable;
import app.exception.InvalidSyntaxException;
import app.mat.Escalar;
import app.mat.Matriz;

public class VariableParser {
	
	private ExpressaoTokenizer tokenizer;

	public VariableParser(ExpressaoTokenizer tokenizer){
		this.tokenizer = tokenizer;
	}
	
	public Variable parse(){
		Variable var = new Variable();
		Token token = tokenizer.nextToken();
		
		if(token.getType() == TokenType.VAR){
			if(token.getValue().equals("E")){
				String varName = tokenizer.nextToken().getValue();
				double value = Double.parseDouble(tokenizer.nextToken().getValue());
				Escalar expressao = new Escalar(value);
				
				var.setName(varName);
				var.setExpressao(expressao);
			}
			else if(token.getValue().equals("M")){
				String varName = tokenizer.nextToken().getValue();
				int linhas = Integer.parseInt(tokenizer.nextToken().getValue());
				int colunas = Integer.parseInt(tokenizer.nextToken().getValue());
				Matriz expressao = new Matriz(linhas, colunas);
				
				for(int lin = 0; lin < linhas; lin++){
					for(int col = 0; col < colunas; col++){
						double value = Double.parseDouble(tokenizer.nextToken().getValue());
						expressao.setValor(lin, col, value);
					}
				}
				
				var.setName(varName);
				var.setExpressao(expressao);
			}
			
			if(tokenizer.nextToken().getType() == TokenType.END)
				return var;				
		}
		
		throw new InvalidSyntaxException();
	}
	
}
