/*
  A recursive descent parser.
  For details see https://www.engr.mun.ca/~theo/Misc/exp_parsing.htm
*/

package parser;

import java.text.ParseException;
import java.util.Deque;
import java.util.LinkedList;

import tokenizer.TokenIterator;
import tokenizer.Token.Type;
import tokenizer.Token;
import expression.*;

public class Parser {

  // private final char SENTINEL = 's';
  // private final char END = '.';

  Deque<Token> operators = new LinkedList<Token>();
  Deque<Expression> operands = new LinkedList<Expression>();

  Iterator<Token> tokens;

  public Parser(TokenIterator tokens) {
    this.tokens = tokens;
  }

  public Expression parse() throws ParseException {
    operators.push(new Token("s", Type.SENTINEL));

    expression();
    expect(Type.END);
    return operands.pop();
  }

  private void expression() throws ParseException {
    par();
    Token next = tokens.peek();
    while (isBinOp(next)) {
      pushOperator(binary(next));
      par();
    }
    while (!operators.peek().sameType(Type.SENTINEL)) {
      popOperator();
    }
  }

  private void par() throws ParseException {
    Token next = tokens.peek();

    if (next.sameType(Type.NUM)) {
      // Can't read several digits in a row, "123"
      operands.push(mkLeaf(next));
      consume();
    } else if (next.sameType(Type.LPAR)) {
      consume();
      operators.push(new Token("s", Type.SENTINEL));
      expression();
      expect(Type.RPAR);
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

  private void pushOperator(Token op) throws ParseException {
    while (higherPrecedence(operators.peek(), op)) {
      popOperator();
    }
    operators.push(op);
  }

  // ADD DICE HERE
  private Expression mkLeaf(Token t) {
    return new Constant(Integer.parseInt(t.getValue()));
  }

  private Expression mkNode(Token t, Expression t1, Expression t0) throws ParseException {
    switch (t.getValue()) {
      case "+": return new Plus(t0, t1);
      case "-": return new Minus(t0, t1);
      case "*": return new Mul(t0, t1);
      case "/": return new Div(t0, t1);
      default: throw new ParseException("Parse exception", 0);
    }
  }

  private Expression mkNode(Token t, Expression t0) throws ParseException {
    switch (t.getValue()) {
      case "n": return new Neg(t0);
      default: throw new ParseException("Parse exception", 0);
    }
  }

  private Token binary(Token t) throws ParseException {
    return t;
  }

  private Token unary(Token t) throws ParseException {
    switch (t.getValue()) {
      case "-": return new Token("n", Type.UNOP);
      default: throw new ParseException("Parse exception", 0);
    }
  }

  private void consume() {
    if (tokens.next().sameType(Type.END)) {
      return;
    }
    index++;
  }

  private void error() throws ParseException {
    throw new ParseException("Error while parsing " + s, 0);
  }

  private void expect(Type type) throws ParseException {
    if (tokens.next().sameType(type)) {
      consume();
    } else {
      error();
    }
  }

  private boolean isBinOp(Token t) {
    return t.sameType(Type.BINOP);
  }
  private boolean isUnOp(Token t) {
    return t.getValue() == "-";
  }

  // Returns true if operator a has higher precedence than operator b.
  private boolean higherPrecedence(Token a, Token b) {
    if (a.sameType(Type.SENTINEL)) return false;
    return b.sameType(Type.SENTINEL) ||
            a.getValue() == "*" ||
            a.getValue() == "/" ||
            b.getValue() == "+" ||
            b.getValue() == "-";
  }

}
