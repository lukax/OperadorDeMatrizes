package app.mat;

import app.domain.ExpressaoMatricial;
import app.mat.base.Expressao;
import app.mat.base.OperacaoUnaria;

public class SolucaoDeSistema extends OperacaoUnaria<Expressao<Matriz>, Matriz> implements ExpressaoMatricial{

	public SolucaoDeSistema(Expressao<Matriz> arg){
		super(arg);
	}
	
	@Override
	public Matriz calcular() {
		// TODO Auto-generated method stub
		return null;
	}

}
