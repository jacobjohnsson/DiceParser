package parser;

import java.text.ParseException;
import java.util.Iterator;

import expression.*;
import tokenizer.Token;

/*
  Expr  --> Par { BinOp Par }
  Par   --> v | "(" Expr ")" | UnOp Par
  BinOp --> "+" | "-" | "*" | "/"
  UnOp  --> "-"
*/

public class Parser2 {

  Iterator<Token> tokens;
  Token currentToken;

  public Parser2(Iterator<Token> tokens) {
    this.tokens = tokens;
  }

  // public Expression parse() throws ParseException {
  //   currentToken = tokens.next();
  //   expr();
  // }
  //
  // private Expression expr() {
  // }

  private void eat(Token.Type type) throws ParseException {
    if (currentToken.sameType(type)) {
      currentToken = tokens.next();
    } else {
      throw new ParseException("Parse Failed.", 0);
    }
  }


  private void advance() {
    currentToken = tokens.next();
  }
}
