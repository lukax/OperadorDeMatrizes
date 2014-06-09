package app.mat;

import app.mat.base.Expressao;

public class Escalar extends Expressao<Escalar> {

	private double valor;
	
	public Escalar(double valor){
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
		return "E " + String.valueOf(getValor());
	}
}
