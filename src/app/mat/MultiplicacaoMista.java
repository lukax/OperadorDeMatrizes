package app.mat;

import app.domain.ExpressaoMatricial;
import app.mat.base.Expressao;
import app.mat.base.OperacaoBinaria;

public class MultiplicacaoMista extends OperacaoBinaria<Expressao<Escalar>, Expressao<Matriz>, Matriz> implements ExpressaoMatricial {

	public MultiplicacaoMista(Expressao<Escalar> arg1, Expressao<Matriz> arg2) {
		super(arg1, arg2);
	}

	@Override
	public Matriz calcular() {
		double v = arg1.calcular().getValor();
		Matriz m = arg2.calcular();
		
		Matriz resultado = new Matriz(m.linhas(), m.colunas());
		
		for(int lin = 0; lin < resultado.linhas(); lin++){
			for(int col = 0; col < resultado.colunas(); col++){
				double valor = (v * m.getValor(lin, col));
				resultado.setValor(lin, col, valor);
			}
		}
		
		return resultado;
	}

}
