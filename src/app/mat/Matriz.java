package app.mat;

import app.domain.ExpressaoMatricial;
import app.domain.Messages;
import app.mat.base.Expressao;
import java.text.DecimalFormat;

public class Matriz extends Expressao<Matriz> implements ExpressaoMatricial {

    private double[][] valores;

    public Matriz(int linhas, int colunas) {
        if (linhas < 0 || colunas < 0) {
            throw new IllegalArgumentException(Messages.ERROR_INVALID_MATRIX_SIZE);
        }
        valores = new double[linhas][colunas];
    }

    public int linhas() {
        return valores.length;
    }

    public int colunas() {
        return valores[0].length;
    }

    public double getValor(int lin, int col) {
        if ((lin < 0 || col < 0) || (lin > linhas() - 1 || col > colunas() - 1)) {
            throw new IllegalArgumentException(Messages.ERROR_INVALID_MATRIX_POS);
        }
        return valores[lin][col];
    }

    public void setValor(int lin, int col, double valor) {
        if ((lin < 0 || col < 0) || (lin > linhas() - 1 || col > colunas() - 1)) {
            throw new IllegalArgumentException(Messages.ERROR_INVALID_MATRIX_POS);
        }
        valores[lin][col] = valor;
    }

    @Override
    public Matriz calcular() {
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("M").append(" ").append(linhas()).append(" ").append(colunas()).append(" ");
        for (int lin = 0; lin < linhas(); lin++) {
            for (int col = 0; col < colunas(); col++) {
                String value = new DecimalFormat("0.0000").format(getValor(lin, col));
                sb.append(value);
                if (!(lin == (linhas() - 1) && col == (colunas() - 1))) {
                    sb.append(" ");
                }
            }
        }

        return sb.toString();
    }
}
