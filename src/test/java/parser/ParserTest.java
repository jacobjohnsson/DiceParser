package parser;

import expression.NoExpression;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.ParseException;

import expression.Expression;
import tokenizer.TokenIterator;

public class ParserTest {

  @Test
  public void addTest() {
    String expression = "1+1";
    TokenIterator ti = new TokenIterator(expression);
    Parser p = new Parser(ti);

    try {
      Expression e = p.parse();
      assertEquals(e.eval(), 2);
    } catch (Exception exc) {
      fail("Failed to parse" + expression);
    }
  }

  @Test
    public void minusTest() {
    String expression = "3-1";
    TokenIterator ti = new TokenIterator(expression);
    Parser p = new Parser(ti);
    Expression e = new NoExpression();
    try {
      e = p.parse();
    } catch (Exception exc) {
      fail("Failed to parse" + expression);
    }
    assertEquals(e.eval(), 2);
  }

  @Test
  public void multiplicationTest() {
    String expression = "3*5";
    TokenIterator ti = new TokenIterator(expression);
    Parser p = new Parser(ti);
    Expression e = new NoExpression();
    try {
      e = p.parse();
    } catch (Exception exc) {
      fail("Failed to parse" + expression);
    }
    assertEquals(e.eval(), 15);
  }

  @Test
  public void divisionTest() {
    String expression = "9/3";
    TokenIterator ti = new TokenIterator(expression);
    Parser p = new Parser(ti);
    Expression e = new NoExpression();
    try {
      e = p.parse();
    } catch (Exception exc) {
      fail("Failed to parse" + expression);
    }
    assertEquals(e.eval(), 3);
  }

  @Test
  public void negateTest() {
    String expression = "-4";
    TokenIterator ti = new TokenIterator(expression);
    Parser p = new Parser(ti);
    Expression e = new NoExpression();
    try {
      e = p.parse();
    } catch (Exception exc) {
      fail("Failed to parse" + expression);
    }
    assertEquals(e.eval(), -4);
  }

  @Test
  public void paranTest() {
    String expression = "(2+4)*3";
    TokenIterator ti = new TokenIterator(expression);
    Parser p = new Parser(ti);
    Expression e = new NoExpression();
    try {
      e = p.parse();
    } catch (Exception exc) {
      fail("Failed to parse" + expression);
    }
    assertEquals(e.eval(), 18);
  }

  @Test
  public void mediumTest() {
    String expression = "(2+(-9)/3)*8";
    TokenIterator ti = new TokenIterator(expression);
    Parser p = new Parser(ti);
    Expression e = new NoExpression();
    try {
      e = p.parse();
    } catch (Exception exc) {
      fail("Failed to parse" + expression);
    }
    assertEquals(e.eval(), -8);
  }

  @Test
  public void largeTest() {
    String expression = "(2+(-9)/3)*8+(5-(-1)+5)-1*1*1-(-2)";
    TokenIterator ti = new TokenIterator(expression);
    Parser p = new Parser(ti);
    Expression e = new NoExpression();
    try {
      e = p.parse();
    } catch (Exception exc) {
      fail("Failed to parse" + expression);
    }
    assertEquals(4, e.eval());
  }

  @Test
  public void smallDiceTest() {
    for (int i = 0; i < 50; i++) {
      String expression = "1d6";
      TokenIterator ti = new TokenIterator(expression);
      Parser p = new Parser(ti);
      Expression e = new NoExpression();
      try {
        e = p.parse();
      } catch (Exception exc) {
        fail("Failed to parse" + expression);
      }
      assertTrue(1 <= e.eval() && e.eval() <= 6);
    }
  }

  @Test
  public void multipleDiceTest() {
    for (int i = 0; i < 50; i++) {
      String expression = "10*1d10";
      TokenIterator ti = new TokenIterator(expression);
      Parser p = new Parser(ti);
      Expression e = new NoExpression();
      try {
        e = p.parse();
      } catch (Exception exc) {
        fail("Failed to parse" + expression);
      }
      assertTrue(e.eval() % 10 == 0);
    }
  }
}
