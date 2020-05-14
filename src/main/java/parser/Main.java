package parser;

import expression.*;

import java.util.Iterator;
import tokenizer.TokenIterator;
import tokenizer.Token;

public class Main {
  public static void main(String[] args) {
    new Main().run(args);
  }

  public void run(String[] args) {

    for (int i = 0; i < args.length; i++) {
      String input = args[i];
      int result = parse(input);
      System.out.println(input + ": " + result);
    }
  }

  private int parse(String input) {
    int result = 0;
    TokenIterator ti = new TokenIterator(input);
    Parser parser = new Parser(ti);

    try {
      Expression expr = parser.parse();
      result = expr.eval();
    } catch(Exception e) {
      System.out.println("Parsing went wrong.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

    return result;
  }
}
