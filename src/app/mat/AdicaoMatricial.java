package app.mat;

import app.domain.ExpressionType;
import app.domain.Messages;
import app.exception.InvalidOperationException;
import app.mat.base.Expressao;
import app.mat.base.OperacaoBinaria;

public class AdicaoMatricial extends OperacaoBinaria<Expressao<Matriz>, Expressao<Matriz>, Matriz> {

    public AdicaoMatricial(Expressao<Matriz> arg1, Expressao<Matriz> arg2) {
        super(ExpressionType.MATRIX, arg1, arg2);
    }

    @Override
    public Matriz calcular() {
        Matriz m1 = arg1.calcular();
        Matriz m2 = arg2.calcular();

        if (m1.linhas() != m2.linhas() || m1.colunas() != m2.colunas()) {
            throw new InvalidOperationException(Messages.ERROR_INVALID_MATRIX);
        }

        Matriz resultado = new Matriz(m1.linhas(), m2.colunas());

        for (int lin = 0; lin < resultado.linhas(); lin++) {
            for (int col = 0; col < resultado.colunas(); col++) {
                double v1 = m1.getValor(lin, col);
                double v2 = m2.getValor(lin, col);
                double valor = (v1 + v2);
                resultado.setValor(lin, col, valor);
            }
        }

        return resultado;
    }

}
