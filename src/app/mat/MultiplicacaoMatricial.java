package app.mat;

import app.domain.ExpressaoMatricial;
import app.mat.base.Expressao;
import app.mat.base.OperacaoBinaria;

public class MultiplicacaoMatricial extends OperacaoBinaria<Expressao<Matriz>, Expressao<Matriz>, Matriz> implements ExpressaoMatricial{

	public MultiplicacaoMatricial(Expressao<Matriz> arg1, Expressao<Matriz> arg2) {
		super(arg1, arg2);
	}

	@Override
	public Matriz calcular() {
		Matriz m1 = arg1.calcular();
		Matriz m2 = arg2.calcular();
		
		if(m1.colunas() != m2.linhas()){
			//TODO: exception
		}
		
		Matriz resultado = new Matriz(m1.linhas(), m2.colunas());
		
		for(int lin = 0; lin < resultado.linhas(); lin++){
			for(int col = 0; col < resultado.colunas(); col++){
				double valor = 0;
				
				for(int a = 0; a < m1.colunas(); a++){
					valor += (m1.getValor(lin, a) * m2.getValor(a, col));
				}
				
				resultado.setValor(lin, col, valor);
			}
		}
		
		return resultado;
	}
}
