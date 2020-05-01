/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package parser;
import expression.*;

public class Main {
  public static void main(String[] args) {
    new Main().run(args);
  }

  public void run(String[] args) {
    //test();
    String input = "(2+(-9)/3)*4.";
    Recognizer recognizer = new Recognizer(input);
    try {
      recognizer.recognize();
    } catch(Exception e) {
      System.out.println("Recognition went wrong.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

    Parser parser = new Parser(input);
    try {
      Expression expr = parser.parse();
      System.out.println("Evaluation: " + expr.eval());
    } catch(Exception e) {
      System.out.println("Parsing went wrong.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

  }

  private void test() {
    System.out.println("1d3");
    Expression exp0 = new Dice(3);
    System.out.println(exp0.eval());

    System.out.println("3+1d6");
    Expression exp1 = new Plus(new Constant(3), new Dice(6));
    System.out.println(exp1.eval());

    System.out.println("1d6-2");
    Expression exp2 = new Minus(new Dice(6), new Constant(2));
    System.out.println(exp2.eval());

    System.out.println("1d20*2");
    Expression exp3 = new Mul(new Dice(20), new Constant(2));
    System.out.println(exp3.eval());

    System.out.println("1d12/3");
    Expression exp4 = new Div(new Dice(12), new Constant(3));
    System.out.println(exp4.eval());

    System.out.println("-(2 * 1d6 + 2)");
    Expression exp5 = new Neg(new Plus(new Mul(new Dice(6), new Constant(2)), new Constant(2)));
    System.out.println(exp5.eval());
  }


}