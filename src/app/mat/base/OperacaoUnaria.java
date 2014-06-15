package app.mat.base;

import app.domain.ExpressionType;


public abstract class OperacaoUnaria<T extends Expressao<?>, 
									 R extends Expressao<?>> extends Expressao<R> {

	protected T arg;

	public OperacaoUnaria(ExpressionType type, T arg) {
		super(type);
		this.arg = arg;
	}

	public T getArgumento() {
		return arg;
	}
}
