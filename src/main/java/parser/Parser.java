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

  private Deque<Token> operators = new LinkedList<Token>();
  private Deque<Expression> operands = new LinkedList<Expression>();

  private TokenIterator tokens;

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
    for (Token next = tokens.peek();isBinOp(next); next = tokens.peek()) {
      pushOperator(binary(next));
      consume();
      par();
    }
    while (!operators.peek().sameType(Type.SENTINEL)) {
      popOperator();
    }
  }

  private void par() throws ParseException {
    Token next = tokens.peek();

    if (next.sameType(Type.NUM) || next.sameType(Type.DICE)) {
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

  private Expression mkLeaf(Token t) throws ParseException {
    switch (t.getType()) {
      case NUM:
        return new Constant(Integer.parseInt(t.getValue()));
      case DICE:
        return mkDice(t);
      default: throw new ParseException("Parse Exception", 0);
    }
  }

  private Expression mkDice(Token t) {
    String dice = t.getValue();
    int nbr = Integer.parseInt(dice.split("d")[0]);
    int sides = Integer.parseInt(dice.split("d")[1]);
    return new Dice(nbr, sides);
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
    tokens.next();
  }

  private void error() throws ParseException {
    throw new ParseException("Error while parsing:", 0);
  }

  private void expect(Type type) throws ParseException {
    if (tokens.peek().sameType(type)) {
      consume();
    } else {
      error();
    }
  }

  private boolean isBinOp(Token t) {
    return t.sameType(Type.BINOP);
  }
  private boolean isUnOp(Token t) {
    return t.getValue().equals("-");
  }

  private boolean higherPrecedence(Token a, Token b) {
    if (a.sameType(Type.SENTINEL)) return false;
    return b.sameType(Type.SENTINEL) ||
            a.getValue().equals("*") ||
            a.getValue().equals("/") ||
            b.getValue().equals("+") ||
            b.getValue().equals("-");
  }
}
