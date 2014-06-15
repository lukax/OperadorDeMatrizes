package app.mat;

import java.text.DecimalFormat;

import app.domain.ExpressionType;
import app.mat.base.Expressao;

public class Escalar extends Expressao<Escalar> {

    private double valor;

    public Escalar(double valor) {
    	super(ExpressionType.ESCALAR);
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
