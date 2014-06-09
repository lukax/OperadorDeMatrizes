package app.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.domain.Messages;

public class ExpressaoFileReader {

    private ArrayList<String> variaveis;
    private ArrayList<String> expressoes;

    public ExpressaoFileReader(String arquivo) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(arquivo));

            lerVariaveis(reader);
            reader.readLine(); /* Pula linha */
            lerExpressoes(reader);

        } catch (FileNotFoundException ex) {

        } catch (NumberFormatException | IOException e) {

        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println(Messages.ERROR_CLOSE_FILE);
            }
        }
    }

    public List<String> getVariaveis() {
        return variaveis;
    }

    public List<String> getExpressoes() {
        return expressoes;
    }

    private void lerVariaveis(BufferedReader reader) throws NumberFormatException, IOException {
        variaveis = new ArrayList<>();
        int nVariaveis = Integer.parseInt(reader.readLine());

        for (int i = 0; i < nVariaveis; i++) {
            String nextLine = reader.readLine();
            variaveis.add(nextLine);
        }
    }

    private void lerExpressoes(BufferedReader reader) throws NumberFormatException, IOException {
        expressoes = new ArrayList<>();
        int nExpressoes = Integer.parseInt(reader.readLine());

        for (int i = 0; i < nExpressoes; i++) {
            String nextLine = reader.readLine();
            expressoes.add(nextLine);
        }
    }
}
