package app.io;

import app.domain.Token;
import app.domain.TokenType;
import app.domain.Variable;
import app.exception.InvalidSyntaxException;
import app.mat.Escalar;
import app.mat.Matriz;

public class VariableParser {

    private final ExpressaoTokenizer tokenizer;

    public VariableParser(ExpressaoTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public Variable parse() {
        Variable var = new Variable();
        Token token = tokenizer.nextToken();

        if (token.getType() == TokenType.VAR) {
            switch (token.getValue()) {
                case "E": {
                    String varName = tokenizer.nextToken().getValue();
                    Escalar expressao = new Escalar(nextDouble());
                    var.setName(varName);
                    var.setExpressao(expressao);
                    break;
                }
                case "M": {
                    String varName = tokenizer.nextToken().getValue();
                    int linhas = nextInt();
                    int colunas = nextInt();
                    Matriz expressao = new Matriz(linhas, colunas);
                    for (int lin = 0; lin < linhas; lin++) {
                        for (int col = 0; col < colunas; col++) {
                            expressao.setValor(lin, col, nextDouble());
                        }
                    }
                    var.setName(varName);
                    var.setExpressao(expressao);
                    break;
                }
            }

            if (tokenizer.nextToken().getType() == TokenType.END) {
                return var;
            }
        }

        throw new InvalidSyntaxException();
    }

    private int nextInt() {
        int number;

        Token token = tokenizer.nextToken();
        if (token.getType() == TokenType.SUB) {
            Token nextToken = tokenizer.nextToken();
            if (nextToken.getType() == TokenType.NUM) {
                number = Integer.parseInt(nextToken.getValue());
                number *= -1;
                return number;
            }
        } else if (token.getType() == TokenType.NUM) {
            number = Integer.parseInt(token.getValue());
            return number;
        }

        throw new InvalidSyntaxException();
    }

    private double nextDouble() {
        double number;

        Token token = tokenizer.nextToken();
        if (token.getType() == TokenType.SUB) {
            Token nextToken = tokenizer.nextToken();
            if (nextToken.getType() == TokenType.NUM) {
                number = Double.parseDouble(nextToken.getValue());
                number *= -1;
                return number;
            }
        } else if (token.getType() == TokenType.NUM) {
            number = Double.parseDouble(token.getValue());
            return number;
        }

        throw new InvalidSyntaxException();
    }
}
