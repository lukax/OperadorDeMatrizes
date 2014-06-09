package app.io;

import static org.junit.Assert.*;

import org.junit.Test;

import app.domain.Variable;
import app.mat.Escalar;
import app.mat.Matriz;
import app.mat.base.Expressao;

public class VariableParserTest {

	@Test
	public void parseEscalar() {
		ExpressaoTokenizer tokenizer = new ExpressaoTokenizer("E A 13.1");
		VariableParser parser = new VariableParser(tokenizer);
		
		Variable var = parser.parse();
		Expressao<?> expressao = var.getExpressao();
		
		assertEquals("A", var.getName());
		assertTrue(expressao instanceof Escalar);
		assertEquals(13.1, ((Escalar)expressao).getValor(), 0);
	}

	@Test
	public void parseMatriz(){
		ExpressaoTokenizer tokenizer = new ExpressaoTokenizer("M B 3 2 1.3 53 1 3 7.6 9");
		VariableParser parser = new VariableParser(tokenizer);
		
		Variable var = parser.parse();
		Expressao<?> expressao = var.getExpressao();
		
		assertEquals("B", var.getName());
		assertTrue(expressao instanceof Matriz);
		assertEquals(3, ((Matriz)expressao).linhas(), 0);
		assertEquals(2, ((Matriz)expressao).colunas(), 0);
		assertEquals(1.3, ((Matriz)expressao).getValor(0, 0), 0);
		assertEquals(53, ((Matriz)expressao).getValor(0, 1), 0);
		assertEquals(9, ((Matriz)expressao).getValor(2, 1), 0);
	}
}
