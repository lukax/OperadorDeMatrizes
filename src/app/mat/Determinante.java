package app.mat;

import app.domain.ExpressaoEscalar;
import app.mat.base.Expressao;
import app.mat.base.OperacaoUnaria;

public class Determinante extends OperacaoUnaria<Expressao<Matriz>, Escalar> implements ExpressaoEscalar{

	public Determinante(Expressao<Matriz> arg) {
		super(arg);
	}

	@Override
	public Escalar calcular() {
		Matriz m = arg.calcular();

		if (m.linhas() != m.colunas()) {
			// TODO: exception...
		}

		double resultado = determinante(m);

		return new Escalar(resultado);
	}

	private double determinante(Matriz maior) {
		int soma = 0;
		int uni;
		if (maior.linhas() == 1) {
			return maior.getValor(0, 0);
		}
		for (int i = 0; i < maior.linhas(); i++) {
			Matriz menor = new Matriz(maior.linhas() - 1, maior.colunas() - 1);

			for (int a = 1; a < maior.linhas(); a++) {
				for (int b = 0; b < maior.colunas(); b++) {
					if (b < i) {
						menor.setValor((a - 1), b, maior.getValor(a, b));
					} else if (b > i) {
						menor.setValor((a - 1), (b - 1), maior.getValor(a, b));
					}
				}
			}
			if (i % 2 == 0) {
				uni = 1;
			} else {
				uni = -1;
			}
			soma += uni * maior.getValor(0, i) * (determinante(menor));
		}
		return soma;
	}
}
