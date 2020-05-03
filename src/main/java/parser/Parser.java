/*
  A recursive descent parser.
  For details see https://www.engr.mun.ca/~theo/Misc/exp_parsing.htm
*/

package parser;

import java.text.ParseException;
import java.util.Deque;
import java.util.LinkedList;
import expression.*;

public class Parser {

  private final char SENTINEL = 's';
  private final char END = '.';

  Deque<Character> operators = new LinkedList<Character>();
  Deque<Expression> operands = new LinkedList<Expression>();

  private final String s;
  private int index = 0;

  public Parser(String s) {
    this.s = s;
  }

  public Expression parse() throws ParseException {
    operators.push(SENTINEL);

    expression();
    expect(END);
    return operands.pop();
  }

  private void expression() throws ParseException {
    par();
    while (isBinOp(next())) {
      pushOperator(binary(next()));
      consume();
      par();
    }
    while (operators.peek() != SENTINEL) {
      popOperator();
    }
  }

  private void par() throws ParseException {
    char next = next();

    if (Character.isDigit(next)) {
      // Can't read several digits in a row, "123"
      operands.push(mkLeaf(next));
      consume();
    } else if (next == '(') {
      consume();
      operators.push(SENTINEL);
      expression();
      expect(')');
      operators.pop();
    } else if (isUnOp(next)) {
      pushOperator(unary(next));
      consume();
      par();
    } else {
      error();
    }
  }

  private void popOperator() throws ParseException {
    if (isBinOp(operators.peek())) {
      Expression t1 = operands.pop();
      Expression t0 = operands.pop();
      operands.push(mkNode(operators.pop(), t1, t0));
    } else {
      operands.push(mkNode(operators.pop(), operands.pop()));
    }
  }

  private void pushOperator(char op) throws ParseException {
    while (higherPrecedence(operators.peek(), op)) {
      popOperator();
    }
    operators.push(op);
  }


  private Expression mkLeaf(char c) {
    return new Constant(Character.getNumericValue(c));
  }

  private Expression mkNode(char op, Expression t1, Expression t0) throws ParseException {
    switch (op) {
      case '+': return new Plus(t0, t1);
      case '-': return new Minus(t0, t1);
      case '*': return new Mul(t0, t1);
      case '/': return new Div(t0, t1);
      default: throw new ParseException("Parse exception", 0);
    }
  }

  private Expression mkNode(char op, Expression t0) throws ParseException {
    switch (op) {
      case 'n': return new Neg(t0);
      default: throw new ParseException("Parse exception", 0);
    }
  }

  private Character binary(char c) throws ParseException {
    switch (c) {
      case '+': return '+';
      case '-': return '-';
      case '*': return '*';
      case '/': return '/';
      default: throw new ParseException("Parse exception", 0);
    }
  }

  private Character unary(char c) throws ParseException {
    switch (c) {
      case '-': return 'n';
      default: throw new ParseException("Parse exception", 0);
    }
  }

  private char next() {
    return s.charAt(index);
  }

  private void consume() {
    if (next() == END) {
      return;
    }
    index++;
  }

  private void error() throws ParseException {
    throw new ParseException("Error while parsing " + s, 0);
  }

  private void expect(char c) throws ParseException {
    if (next() == c) {
      consume();
    } else {
      error();
    }
  }

  private boolean isBinOp(char c) {
    return (c == '+') || (c == '-') || (c == '*') || (c == '/') || (c == 'd');
  }
  private boolean isUnOp(char c) {
    return (c == '-');
  }

  // Returns true if operator a has higher precedence than operator b.
  private boolean higherPrecedence(char a, char b) {
    if (a == SENTINEL) return false;
    return b == SENTINEL || a == '*' || a == '/' || b == '+' || b == '-';
  }

}
