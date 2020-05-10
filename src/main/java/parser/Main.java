package parser;

import expression.*;

import java.util.Iterator;
import tokenizer.TokenIterator;
import tokenizer.Token;

public class Main {
  public static void main(String[] args) {
    if (args.length == 0 || args.length > 1) {
      System.out.println("Expected one argument.");
      return;
    }

    new Main().run(args);
  }

  public void run(String[] args) {
    String input = args[0];

    TokenIterator ti = new TokenIterator(input);
    Parser parser = new Parser(ti);
    try {
      Expression expr = parser.parse();
      System.out.print(expr.eval());
    } catch(Exception e) {
      System.out.println("Parsing went wrong.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

  }
}
