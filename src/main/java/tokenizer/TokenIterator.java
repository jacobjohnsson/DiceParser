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
  private final String LPAR = "^("; // @\\* => *
  private final String RPAR = "^)"; // @\\* => *

  public TokenIterator(String s) {
    this.input = s;
    index = 0;
  }

  public boolean hasNext() {
    return index < input.length();
  }

  public Token next() throws NoSuchElementException {
    if (index >= input.length()) {
      throw new NoSuchElementException();
    }

    Token result = findDice();
    return result;
  }

  public Token peek() throws NoSuchElementException {
    if (index >= input.length()) {
      throw new NoSuchElementException();
    }
    return findDice();
  }

  private void consume(int x) {
    index = index + x;
  }

  private Token findDice() throws NoSuchElementException {
    String result = "";
    Pattern p = Pattern.compile(DICE);
    Matcher m = p.matcher(input.substring(index));
    if (m.find()) {
      result = m.group();
      consume(result.length());
      return new Token(result, Token.Type.DICE);
    } else {
      return findDigit();
    }
  }

  private Token findDigit() {
    String result = "";
    Pattern p = Pattern.compile(NUM);
    Matcher m = p.matcher(input.substring(index));
    if (m.find()) {
      result = m.group();
      consume(result.length());
      return new Token(result, Token.Type.NUM);
    } else {
      return findBinOp();
    }
  }

  private Token findBinOp() {
    String result = "";
    Pattern p = Pattern.compile(BINOP);
    Matcher m = p.matcher(input.substring(index));
    if (m.find()) {
      result = m.group();
      consume(result.length());
      return new Token(result, Token.Type.BINOP);
    } else {
      return findTrash();
    }
  }

  private Token findLPar() {
    String result = "";
    Pattern p = Pattern.compile(LPAR);
    Matcher m = p.matcher(input.substring(index));
    if (m.find()) {
      result = m.group();
      consume(result.length());
      return new Token(result, Token.Type.LPAR);
    } else {
      return findRPar();
    }
  }

  private Token findRPar() {
    String result = "";
    Pattern p = Pattern.compile(RPAR);
    Matcher m = p.matcher(input.substring(index));
    if (m.find()) {
      result = m.group();
      consume(result.length());
      return new Token(result, Token.Type.RPAR);
    } else {
      return findTrash();
    }
  }

  private Token findTrash() {
    String result = input.substring(index, index + 1);
    consume(result.length());
    return new Token(result, Token.Type.UNKNOWN);
  }
}
