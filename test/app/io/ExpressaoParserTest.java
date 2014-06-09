package app.io;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import app.mat.AdicaoEscalar;
import app.mat.Escalar;
import app.mat.MultiplicacaoEscalar;
import app.mat.base.Expressao;

@SuppressWarnings("rawtypes")
public class ExpressaoParserTest {

	@Test
	public void parseNumber() {
		Expressao<?> expressao = buildExpressao("5", null);
		
		assertTrue(expressao instanceof Escalar);
		assertEquals(5, ((Escalar)expressao).calcular().getValor(), 0);
		assertEquals("E 5.0", expressao.toString());
	}
	
	@Test
	public void parseFactor(){
		Expressao<?> expressao = buildExpressao("5*5", null);
		
		assertTrue(expressao instanceof MultiplicacaoEscalar);
	
		MultiplicacaoEscalar mul = (MultiplicacaoEscalar) expressao;
		Escalar escalar = mul.calcular();
		assertEquals(25, escalar.getValor(), 0);
		assertEquals("E 25.0", escalar.toString());
	}
	
	@Test
	public void parseLongFactor(){
		Expressao<?> expressao = buildExpressao("5*5*5*1/1/10", null);
		
		assertTrue(expressao instanceof MultiplicacaoEscalar);
	
		MultiplicacaoEscalar mul = (MultiplicacaoEscalar) expressao;
		Escalar escalar = mul.calcular();
		assertEquals(12.5, escalar.getValor(), 0);
		assertEquals("E 12.5", escalar.toString());	
	}
	
	@Test
	public void parseExpression(){
		Expressao<?> expressao = buildExpressao("1+1", null);
		
		assertTrue(expressao instanceof AdicaoEscalar);
	
		AdicaoEscalar add = (AdicaoEscalar) expressao;
		assertEquals(2f, add.calcular().getValor(), 0);
	}
	
	@Test
	public void parseLongExpression(){
		Expressao<?> expressao = buildExpressao("1+1+5", null);
		
		assertTrue(expressao instanceof AdicaoEscalar);
	
		AdicaoEscalar add = (AdicaoEscalar) expressao;
		assertEquals(7f, add.calcular().getValor(), 0);
	}
	
	@Test
	public void parseExpressionWithParenthesis(){
		Expressao<?> expressao = buildExpressao("5*(1+1)+5", null);
		
		assertTrue(expressao instanceof AdicaoEscalar);
	
		AdicaoEscalar add = (AdicaoEscalar) expressao;
		assertEquals(15f, add.calcular().getValor(), 0);
	}
	
	@Test
	public void parseExpressionWithVariable(){
		Map<String, Expressao> vars = new HashMap<String, Expressao>();
		Escalar varA = new Escalar(5);
		Escalar varB = new Escalar(10);
		vars.put("A", varA);
		vars.put("B", varB);
		
		Expressao<?> expressao = buildExpressao("A+B", vars);
		assertTrue(expressao instanceof AdicaoEscalar);
		
		AdicaoEscalar add = (AdicaoEscalar)expressao;
		assertEquals(15, add.calcular().getValor(), 0);
	}
	
	Expressao<?> buildExpressao(String expressao, Map<String, Expressao> vars){
		ExpressaoTokenizer tokenizer = new ExpressaoTokenizer(expressao);
		ExpressaoParser parser = new ExpressaoParser(tokenizer, ((vars != null) ? vars : new HashMap<String, Expressao>()));
		return parser.parse();
	}
}
