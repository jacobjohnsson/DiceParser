package parser;

import expression.NoExpression;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.ParseException;

import expression.Expression;

public class ParserTest {
  
  @Test
  public void addTest() {
    String expression = "1+1.";
    Parser p = new Parser(expression);
    try {
      Expression e = p.parse();
      assertEquals(e.eval(), 2);
    } catch (Exception exc) {
      fail("Failed to parse" + expression);
    }
  }

  @Test
    public void minusTest() {
    String expression = "3-1.";
    Parser p = new Parser(expression);
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
    String expression = "3*5.";
    Parser p = new Parser(expression);
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
    String expression = "9/3.";
    Parser p = new Parser(expression);
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
    String expression = "-4.";
    Parser p = new Parser(expression);
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
    String expression = "(2+4)*3.";
    Parser p = new Parser(expression);
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
    String expression = "(2+(-9)/3)*8.";
    Parser p = new Parser(expression);
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
    String expression = "(2+(-9)/3)*8+(5-(-1)+5)-1*1*1-(-2).";
    Parser p = new Parser(expression);
    Expression e = new NoExpression();
    try {
      e = p.parse();
    } catch (Exception exc) {
      fail("Failed to parse" + expression);
    }
    assertEquals(e.eval(), 4);
  }
}
