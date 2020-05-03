package parser;

import java.text.ParseException;

public class Recognizer {

  private final String s;
  private int index = 0;

  public Recognizer(String s) {
    this.s = s;
  }

  public void recognize() throws ParseException {
    expression();
    expect('.');
  }

  private char next() {
    return s.charAt(index);
  }

  private void consume() {
    if (next() == '.') {
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

  private void expression() throws ParseException {
    par();
    while (isBinOp(next())) {
      consume();
      par();
    }
  }

  private void par() throws ParseException {
    if (Character.isDigit(next())) {
      while (Character.isDigit(next())) {
        consume();
      }
    } else if (next() == '(') {
      consume();
      expression();
      expect(')');
    } else if (isUnOp(next())) {
      consume();
      par();
    } else {
      error();
    }
  }

  private boolean isBinOp(char c) {
    return (c == '+') || (c == '-') || (c == '*') || (c == '/');
  }
  private boolean isUnOp(char c) {
    return (c == '-') || (c == 'd') || Character.isDigit(c);
  }

}
