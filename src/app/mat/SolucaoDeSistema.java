package app.mat;

import app.domain.ExpressionType;
import app.domain.Messages;
import app.exception.InvalidOperationException;
import app.mat.base.Expressao;
import app.mat.base.OperacaoUnaria;

public class SolucaoDeSistema extends OperacaoUnaria<Expressao<Matriz>, Matriz> {

    public SolucaoDeSistema(Expressao<Matriz> arg) {
        super(ExpressionType.MATRIX, arg);
    }

    @Override
    public Matriz calcular() {
        Matriz m = arg.calcular();

        if (m.linhas() != m.colunas() - 1) {
            throw new InvalidOperationException(Messages.ERROR_INVALID_MATRIX);
        }

        Matriz result = solucaoDeSistema(m);
        return result;
    }

    private Matriz solucaoDeSistema(Matriz m) {
        int lastCol = m.colunas() - 1;

        Matriz solution = new Matriz(m.linhas(), 1);

        Matriz D = new Matriz(m.linhas(), m.colunas() - 1);
        for (int lin = 0; lin < D.linhas(); lin++) {
            for (int col = 0; col < D.colunas(); col++) {
                D.setValor(lin, col, m.getValor(lin, col));
            }
        }

        double detD = new Determinante(D).calcular().getValor();

        for (int Dcol = 0; Dcol < D.colunas(); Dcol++) {
            Matriz DSUB = new Matriz(D.linhas(), D.colunas());
            for (int lin = 0; lin < DSUB.linhas(); lin++) {
                for (int col = 0; col < DSUB.colunas(); col++) {
                    if (col == Dcol) {
                        DSUB.setValor(lin, col, m.getValor(lin, lastCol));
                    } else {
                        DSUB.setValor(lin, col, m.getValor(lin, col));
                    }
                }
            }
            double detDSUB = new Determinante(DSUB).calcular().getValor();
            double result = detDSUB / detD;

            solution.setValor(Dcol, 0, result);
        }

        return solution;
    }

}
