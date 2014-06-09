package app.mat;

import app.domain.ExpressaoMatricial;
import app.mat.base.Expressao;

public class Matriz extends Expressao<Matriz> implements ExpressaoMatricial{

	private double[][] valores;
	
	public Matriz(int linhas, int colunas){
		valores = new double[linhas][colunas];
	}
	
	public int linhas(){
		return valores.length;
	}
	
	public int colunas(){
		return valores[0].length;
	}
	
	public double getValor(int lin, int col){
		return valores[lin][col];
	}
	
	public void setValor(int lin, int col, double valor){
		valores[lin][col] = valor;
	}
	
	@Override
	public Matriz calcular() {
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("M " + linhas() + " " + colunas() + " ");
		for(int lin = 0; lin < linhas(); lin++){
			for(int col = 0; col < colunas(); col++){
				if(lin == (linhas() - 1) && col == (colunas() -1))
					sb.append(getValor(lin, col));
				sb.append(getValor(lin, col) + " ");
			}
		}
		
		return sb.toString();
	}
}
