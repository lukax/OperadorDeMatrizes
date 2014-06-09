package app.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import app.mat.AdicaoEscalar;
import app.mat.AdicaoMatricial;
import app.mat.Escalar;
import app.mat.Matriz;
import app.mat.MultiplicacaoEscalar;
import app.mat.MultiplicacaoMista;
import app.mat.base.Expressao;

@SuppressWarnings("rawtypes")
public class ExpressionParserTest {

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
	
	@Test
	public void parseMatrixExpression(){
		Map<String, Expressao> vars = new HashMap<String, Expressao>();
		Matriz varA = buildMatrix(2,2);
		Matriz varB = buildMatrix(2,2);
		vars.put("A", varA);
		vars.put("B", varB);
		
		ExpressaoTokenizer tokenizer = new ExpressaoTokenizer("A-B");
		ExpressionParser parser = new ExpressionParser(tokenizer, vars);
		
		Expressao exp = parser.parse();
	
		assertTrue(exp instanceof AdicaoMatricial);
		assertEquals(varA.getValor(1, 0) - varB.getValor(1, 0), ((AdicaoMatricial)exp).calcular().getValor(1, 0), 0);
	}
	
	@Test
	public void parseLongMatrixExpression(){
		Map<String, Expressao> vars = new HashMap<String, Expressao>();
		Matriz varA = buildMatrix(2,3);
		Matriz varB = buildMatrix(3,2);
		vars.put("A", varA);
		vars.put("B", varB);
		
		ExpressaoTokenizer tokenizer = new ExpressaoTokenizer("2*(A+B)");
		ExpressionParser parser = new ExpressionParser(tokenizer, vars);
		
		Expressao exp = parser.parse();
	
		assertTrue(exp instanceof MultiplicacaoMista);
		assertEquals(2*(varA.getValor(1, 0) + varB.getValor(1, 0)), ((MultiplicacaoMista)exp).calcular().getValor(1, 0), 0);
	}
	
	Expressao<?> buildExpressao(String expressao, Map<String, Expressao> vars){
		ExpressaoTokenizer tokenizer = new ExpressaoTokenizer(expressao);
		ExpressionParser parser = new ExpressionParser(tokenizer, ((vars != null) ? vars : new HashMap<String, Expressao>()));
		return parser.parse();
	}
	
	Matriz buildMatrix(int linhas, int colunas){
		Matriz m = new Matriz(linhas,colunas);
		for(int lin = 0; lin < linhas; lin++){
			for(int col = 0; col < colunas; col++){
				m.setValor(lin, col, new Random().nextDouble());
			}
		}
		return m;
	}
}