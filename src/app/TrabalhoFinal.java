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
        } catch (OperadorDeMatrizesException | IOException e) {
            System.err.println(Messages.ERROR_INPUTFILE_ACCESS);
            System.err.println(e.getMessage());
            throw e;
        }

        //Processar variaveis
        List<String> rawVariables = reader.getVariables();
        Map<String, Expressao> variables = new HashMap<>();
        for (String var : rawVariables) {
            try {
                ExpressaoTokenizer tokenizer = new ExpressaoTokenizer(var);
                VariableParser parser = new VariableParser(tokenizer);
                Variable variable = parser.parse();
                variables.put(variable.getName(), variable.getExpressao());
            } catch (OperadorDeMatrizesException e) {
                processInvalidLine(e, var);
            }
        }

        //Processar expressoes
        List<String> rawExpressions = reader.getExpressions();
        List<Expressao> expressions = new ArrayList<>();
        for (String exp : rawExpressions) {
            try {
                ExpressaoTokenizer tokenizer = new ExpressaoTokenizer(exp);
                ExpressionParser parser = new ExpressionParser(tokenizer, variables);
                expressions.add(parser.parse());
            } catch (OperadorDeMatrizesException ex) {
                processInvalidLine(ex, exp);
            }
        }

        //Calcular expressoes
        List<Expressao<?>> results = new ArrayList<>();
        for (Expressao expression : expressions) {
            results.add(expression.calcular());
        }

        ExpressaoFileWriter writer;
        try {
            writer = new ExpressaoFileWriter(arquivoDeSaida);
            writer.write(results);
        } catch (IOException e) {
            System.err.println(Messages.ERROR_OUTPUTFILE_ACCESS);
            System.err.println(e.getMessage());
            throw e;
        }
    }

    static void processInvalidLine(OperadorDeMatrizesException e, String invalidLine) {
        System.err.println(Messages.ERROR_INVALID_LINE);
        System.err.println(invalidLine);
        System.err.println(e.getDetails());
        throw e;
    }
}
