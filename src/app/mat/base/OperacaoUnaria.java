package app.mat.base;


public abstract class OperacaoUnaria<T extends Expressao<?>, 
									 R extends Expressao<?>> extends Expressao<R> {

	protected T arg;

	public OperacaoUnaria(T arg) {
		this.arg = arg;
	}

	public T getArgumento() {
		return arg;
	}
}
