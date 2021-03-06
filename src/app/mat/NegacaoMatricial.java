package app.mat;

import app.domain.ExpressionType;
import app.mat.base.Expressao;
import app.mat.base.OperacaoUnaria;

public class NegacaoMatricial extends OperacaoUnaria<Expressao<Matriz>, Matriz> {

    public NegacaoMatricial(Expressao<Matriz> arg) {
        super(ExpressionType.MATRIX, arg);
    }

    @Override
    public Matriz calcular() {
        Matriz m = arg.calcular();

        Matriz resultado = new Matriz(m.linhas(), m.colunas());

        for (int lin = 0; lin < m.linhas(); lin++) {
            for (int col = 0; col < m.colunas(); col++) {
                double valor = m.getValor(lin, col);
                valor *= -1;
                resultado.setValor(lin, col, valor);
            }
        }

        return resultado;
    }

}
