package app.mat.base;

import app.domain.ExpressionType;

public abstract class Expressao<R extends Expressao<?>> {
	private ExpressionType type;

	public Expressao(ExpressionType type) {
		this.type = type;
	}
	
	public abstract R calcular();
	
	public ExpressionType getType() {
		return this.type;
	}
}
