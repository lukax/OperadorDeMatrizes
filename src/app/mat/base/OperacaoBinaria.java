package app.mat.base;

import app.domain.ExpressionType;

public abstract class OperacaoBinaria<T1 extends Expressao<?>, 
									  T2 extends Expressao<?>, 
									  R extends Expressao<?>> extends Expressao<R> {

	protected T1 arg1;
	protected T2 arg2;

	public OperacaoBinaria(ExpressionType type, T1 arg1, T2 arg2) {
		super(type);
		this.arg1 = arg1;
		this.arg2 = arg2;
	}

	public T1 getArgumento1() {
		return arg1;
	}

	public T2 getArgumento2() {
		return arg2;
	}
}
