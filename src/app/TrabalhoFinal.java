package app;

import java.util.List;
import java.util.Map;

import app.io.ExpressaoFileReader;
import app.io.ExpressaoFileWriter;
import app.mat.Escalar;
import app.mat.base.Expressao;

public class TrabalhoFinal {

	public static void main(String[] args) {
		String arquivoDeEntrada = args[0];
		String arquivoDeSaida = args[1];
		
		ExpressaoFileReader reader = new ExpressaoFileReader(arquivoDeEntrada);
		List<String> variaveis = reader.getVariaveis();
		List<String> expressoes = reader.getExpressoes();
	
		List<Map<String, Escalar>> parsedVariaveis = null;
		List<Expressao<?>> parsedExpressoes = null;

		for(String variavel : variaveis){
			
		}
		
		for(String expressao : expressoes){
			
		}
		
		ExpressaoFileWriter writer = new ExpressaoFileWriter(arquivoDeSaida);
		writer.write(parsedExpressoes);
	}
}
