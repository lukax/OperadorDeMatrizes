package app.mat;

import app.domain.ExpressionType;
import app.mat.base.Expressao;
import app.mat.base.OperacaoBinaria;

public class AdicaoMista extends OperacaoBinaria<Expressao<Escalar>, Expressao<Matriz>, Matriz> {

    public AdicaoMista(Expressao<Escalar> arg1, Expressao<Matriz> arg2) {
        super(ExpressionType.MATRIX, arg1, arg2);
    }

    @Override
    public Matriz calcular() {
        double v = arg1.calcular().getValor();
        Matriz m = arg2.calcular();

        Matriz resultado = new Matriz(m.linhas(), m.colunas());

        for (int lin = 0; lin < resultado.linhas(); lin++) {
            for (int col = 0; col < resultado.colunas(); col++) {
                double valor = (m.getValor(lin, col) + v);
                resultado.setValor(lin, col, valor);
            }
        }

        return resultado;
    }

}
