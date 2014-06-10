package app.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.exception.InvalidSyntaxException;

public class ExpressaoFileReader {

    private ArrayList<String> variaveis;
    private ArrayList<String> expressoes;

    public ExpressaoFileReader(String arquivo) throws IOException {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(arquivo));

            lerVariaveis(reader);
            lerExpressoes(reader);

            reader.close();

        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (NumberFormatException ex) {
            throw new InvalidSyntaxException();
        }
    }

    public List<String> getVariables() {
        return variaveis;
    }

    public List<String> getExpressions() {
        return expressoes;
    }

    private void lerVariaveis(BufferedReader reader) throws NumberFormatException, IOException {
        variaveis = new ArrayList<>();
        String nextLine = reader.readLine().trim();

        int nVariaveis = Integer.parseInt(nextLine);
        for (int i = 0; i < nVariaveis; i++) {
            nextLine = reader.readLine().trim();
            variaveis.add(nextLine);
        }
    }

    private void lerExpressoes(BufferedReader reader) throws NumberFormatException, IOException {
        expressoes = new ArrayList<>();
        String nextLine = reader.readLine().trim();

        int nExpressoes = Integer.parseInt(nextLine);
        for (int i = 0; i < nExpressoes; i++) {
            nextLine = reader.readLine().trim();
            expressoes.add(nextLine);
        }
    }
}
