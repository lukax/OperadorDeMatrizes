package app.mat;

import app.mat.base.Expressao;
import app.mat.base.OperacaoBinaria;

public class AdicaoEscalar extends
        OperacaoBinaria<Expressao<Escalar>, Expressao<Escalar>, Escalar> {

    public AdicaoEscalar(Expressao<Escalar> arg1, Expressao<Escalar> arg2) {
        super(arg1, arg2);
    }

    @Override
    public Escalar calcular() {
        double v1 = arg1.calcular().getValor();
        double v2 = arg2.calcular().getValor();
        double resultado = (v1 + v2);

        return new Escalar(resultado);
    }

}
