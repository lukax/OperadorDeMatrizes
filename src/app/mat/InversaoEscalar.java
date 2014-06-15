package app.mat;

import app.domain.ExpressionType;
import app.domain.Messages;
import app.exception.InvalidOperationException;
import app.mat.base.Expressao;
import app.mat.base.OperacaoUnaria;

public class InversaoEscalar extends OperacaoUnaria<Expressao<Escalar>, Escalar> {

    public InversaoEscalar(Expressao<Escalar> arg) {
        super(ExpressionType.ESCALAR, arg);
    }

    @Override
    public Escalar calcular() {
        double valor = arg.calcular().getValor();

        if (valor == 0) {
            throw new InvalidOperationException(Messages.ERROR_INVALID_ESCALAR);
        }

        double resultado = (1 / valor);

        return new Escalar(resultado);
    }

}
