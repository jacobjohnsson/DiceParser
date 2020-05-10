package tokenizer;

import java.util.Iterator;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.NoSuchElementException;

public class TokenIterator implements Iterator<Token> {

  private String input;
  private int index;
  private final String DICE = "^[0-9]+d[0-9]+";
  private final String NUM = "^\\d+";
  private final String BINOP = "^[+-@\\*/]"; // @\\* => *
  private final String LPAR = "^\\(";
  private final String RPAR = "^\\)";

  public TokenIterator(String s) {
    this.input = s;
    index = 0;
  }

  public boolean hasNext() {
    return index < input.length();
  }

  public Token next() {
    if (index >= input.length()) {
      return endToken();
    }

    return findDice(true);
  }

  public Token peek() throws NoSuchElementException {
    if (index >= input.length()) {
      return endToken();
    }
    return findDice(false);
  }

  private void consume(int x) {
    index = index + x;
  }

  private Token findDice(boolean shouldConsume) throws NoSuchElementException {
    String result = "";
    Pattern p = Pattern.compile(DICE);
    Matcher m = p.matcher(input.substring(index));
    if (m.find()) {
      result = m.group();
      if (shouldConsume) {
        consume(result.length());
      }
      return new Token(result, Token.Type.DICE);
    } else {
      return findDigit(shouldConsume);
    }
  }

  private Token findDigit(boolean shouldConsume) {
    String result = "";
    Pattern p = Pattern.compile(NUM);
    Matcher m = p.matcher(input.substring(index));
    if (m.find()) {
      result = m.group();
      if (shouldConsume) {
        consume(result.length());
      }
      return new Token(result, Token.Type.NUM);
    } else {
      return findBinOp(shouldConsume);
    }
  }

  private Token findBinOp(boolean shouldConsume) {
    String result = "";
    Pattern p = Pattern.compile(BINOP);
    Matcher m = p.matcher(input.substring(index));
    if (m.find()) {
      result = m.group();
      if (shouldConsume) {
        consume(result.length());
      }
      return new Token(result, Token.Type.BINOP);
    } else {
      return findLPar(shouldConsume);
    }
  }

  private Token findLPar(boolean shouldConsume) {
    String result = "";
    Pattern p = Pattern.compile(LPAR);
    Matcher m = p.matcher(input.substring(index));
    if (m.find()) {
      result = m.group();
      if (shouldConsume) {
        consume(result.length());
      }
      return new Token(result, Token.Type.LPAR);
    } else {
      return findRPar(shouldConsume);
    }
  }

  private Token findRPar(boolean shouldConsume) {
    String result = "";
    Pattern p = Pattern.compile(RPAR);
    Matcher m = p.matcher(input.substring(index));
    if (m.find()) {
      result = m.group();
      if (shouldConsume) {
        consume(result.length());
      }
      return new Token(result, Token.Type.RPAR);
    } else {
      return findTrash(shouldConsume);
    }
  }

  private Token findTrash(boolean shouldConsume) {
    String result = input.substring(index, index + 1);
    if (shouldConsume) {
      consume(result.length());
    }
    return new Token(result, Token.Type.UNKNOWN);
  }

  private Token endToken() {
    return new Token(".", Token.Type.END);
  }
}
