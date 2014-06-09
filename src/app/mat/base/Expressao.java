package app.mat.base;

public abstract class Expressao<R extends Expressao<?>> {
	public abstract R calcular();
}
