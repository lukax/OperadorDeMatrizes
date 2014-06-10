package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.domain.Messages;
import app.domain.Variable;
import app.exception.base.OperadorDeMatrizesException;
import app.io.ExpressaoFileReader;
import app.io.ExpressaoFileWriter;
import app.io.ExpressaoTokenizer;
import app.io.ExpressionParser;
import app.io.VariableParser;
import app.mat.base.Expressao;

@SuppressWarnings({"rawtypes"})
public class TrabalhoFinal {

	public static void main(String[] args) throws IOException {
		String arquivoDeEntrada = "asdf";
		String arquivoDeSaida = "asdff";
		
		ExpressaoFileReader reader = null;
		try {
			reader = new ExpressaoFileReader(arquivoDeEntrada);
		} catch(OperadorDeMatrizesException e){
			System.err.println(Messages.ERROR_INPUTFILE_ACCESS);
			throw e;	
		} catch (IOException e) {
			System.err.println(Messages.ERROR_INPUTFILE_ACCESS);
			throw e;
		}

		//Processar variaveis
		List<String> rawVariables = reader.getVariables();
		Map<String, Expressao> variables = new HashMap<String, Expressao>();
		for(String variable : rawVariables){
			try{
				ExpressaoTokenizer tokenizer = new ExpressaoTokenizer(variable);
				VariableParser parser = new VariableParser(tokenizer);
				Variable var = parser.parse();
				variables.put(var.getName(), var.getExpressao());
			} catch(OperadorDeMatrizesException e){
				processInvalidLine(e, variable);
			}
		}

		//Processar expressoes
		List<String> rawExpressions = reader.getExpressions();
		List<Expressao> expressions = new ArrayList<Expressao>();
		for(String expression : rawExpressions){
			try{ 
				ExpressaoTokenizer tokenizer = new ExpressaoTokenizer(expression);
				ExpressionParser parser = new ExpressionParser(tokenizer, variables);
				expressions.add(parser.parse());
			} catch(OperadorDeMatrizesException e){
				processInvalidLine(e, expression);
			}
		}
		
		//Calcular expressoes
		List<Expressao<?>> results = new ArrayList<Expressao<?>>();
		for(int i = 0; i < expressions.size(); i++){
			results.add(expressions.get(i).calcular());
		}
		
		ExpressaoFileWriter writer = null;
		try {
			writer = new ExpressaoFileWriter(arquivoDeSaida);
			writer.write(results);
		} catch (IOException e) {
			System.err.println(Messages.ERROR_OUTPUTFILE_ACCESS);
			throw e;
		}
	}
	
	static void processInvalidLine(OperadorDeMatrizesException e, String invalidLine){
		System.err.println(Messages.ERROR_INVALID_LINE);
		System.err.println(invalidLine);
		throw e;
	}
}
