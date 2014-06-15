package app.mat;

import app.domain.ExpressionType;
import app.mat.base.Expressao;
import app.mat.base.OperacaoUnaria;

public class NegacaoEscalar extends OperacaoUnaria<Expressao<Escalar>, Escalar> {

    public NegacaoEscalar(Expressao<Escalar> arg) {
        super(ExpressionType.ESCALAR, arg);
    }

    @Override
    public Escalar calcular() {
        double valor = arg.calcular().getValor();
        double resultado = valor * -1;

        return new Escalar(resultado);
    }
}
