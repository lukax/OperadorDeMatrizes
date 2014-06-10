package app.mat;

import app.domain.ExpressaoEscalar;
import app.mat.base.Expressao;
import java.text.DecimalFormat;

public class Escalar extends Expressao<Escalar> implements ExpressaoEscalar {

    private double valor;

    public Escalar(double valor) {
        this.valor = valor;
    }

    @Override
    public Escalar calcular() {
        return this;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        String value = new DecimalFormat("0.0000").format(getValor());
        return "E " + value;
    }
}
