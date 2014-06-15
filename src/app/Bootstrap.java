package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import app.domain.Messages;
import app.domain.Variable;
import app.exception.base.OperadorDeMatrizesException;
import app.io.ExpressionFileReader;
import app.io.ExpressionFileWriter;
import app.io.ExpressionParser;
import app.io.ExpressionTokenizer;
import app.io.InputParser;
import app.io.VariableParser;
import app.mat.base.Expressao;

/**
 *
 * @author Lucas Espindola
 */

public class Bootstrap {

    public static void main(String[] args) throws Exception {
    	showMotd();
    	if(args != null) {
    		switch(args[0]) {
	    		case "-c":
	    			consoleSetup();
	    			break;
				case "-f":
	    			if(args.length == 3) fileSetup(args[1], args[2]);
	    			else showHelp();
	    			break;
				default:
					showHelp();
					break;
			}
    	}
    }
    
    static void consoleSetup() {
    	Scanner scanner = new Scanner(System.in);
    	List<Variable> vars = new ArrayList<Variable>();
    	while(scanner.hasNext()) {
    		String nextLine = scanner.nextLine();
    		Object obj = new InputParser(nextLine).parse(vars);
    		if(obj instanceof Variable) {
    			vars.add((Variable)obj);
    		}
    		else if(obj instanceof Expressao) {
    			Expressao<?> result = ((Expressao<?>)obj).calcular();
    			System.out.println("> " + result.toString());
    		}
    	}
    	scanner.close();
    }
    
    static void fileSetup(String inputFile, String outputFile) throws Exception {
        ExpressionFileReader reader = null;
        try {
            reader = new ExpressionFileReader(inputFile);
        } catch (OperadorDeMatrizesException | IOException e) {
            System.err.println(Messages.ERROR_INPUTFILE_ACCESS);
            System.err.println(e.getMessage());
            throw e;
        }

        List<Variable> variables = new ArrayList<>();
        List<Expressao<?>> expressions = new ArrayList<>();
        List<Expressao<?>> results = new ArrayList<>();

        //Get variables
        for (String var : reader.getVariables()) {
            try {
                ExpressionTokenizer tokenizer = new ExpressionTokenizer(var);
                VariableParser parser = new VariableParser(tokenizer);
                Variable variable = parser.parse();
                variables.add(variable);
            } catch (OperadorDeMatrizesException e) {
                processInvalidInput(e, var);
            }
        }

        //Get expressions
        for (String exp : reader.getExpressions()) {
            try {
                ExpressionTokenizer tokenizer = new ExpressionTokenizer(exp);
                ExpressionParser parser = new ExpressionParser(tokenizer, variables);
                expressions.add(parser.parse());
            } catch (OperadorDeMatrizesException ex) {
                processInvalidInput(ex, exp);
            }
        }

        //Calculate expressions
        for (Expressao<?> expression : expressions) {
            results.add(expression.calcular());
        }

        //Write results
        ExpressionFileWriter writer;
        try {
            writer = new ExpressionFileWriter(outputFile);
            writer.write(results);
        } catch (IOException e) {
            System.err.println(Messages.ERROR_OUTPUTFILE_ACCESS);
            System.err.println(e.getMessage());
            throw e;
        }
        
        System.out.println("Operação concluída com sucesso");
    }

    static void processInvalidInput(OperadorDeMatrizesException e, String invalidLine) {
        System.err.println(Messages.ERROR_INVALID_LINE);
        System.err.println(invalidLine);
        System.err.println(e.getDetails());
        throw e;
    }
    
    static void showMotd() {
    	System.out.println("Operador de Matrizes v1.0 - Lucas Espindola");
    }
    
    static void showHelp() {
		System.out.println("Uso: OperadorDeMatrizes.jar [-f <entrada.txt> <saida.txt>] [-c]");
		System.out.println(" ");
		System.out.println("	-f	Entrada e saída por arquivos");
		System.out.println("	-c	Entrada e saída na linha de comando");    		
		System.out.println(" ");
    	System.err.println("Ex: OperadorDeMatrizes.jar \"arquivodeentrada.txt\" \"arquivodesaida.txt\" ");
    }
}
