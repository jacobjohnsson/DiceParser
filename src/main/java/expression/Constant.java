package expression;

public class Constant implements Expression {
  private int c;

  public Constant (int c) {
    this.c = c;
  }

  public int eval() {
    return c;
  }

}
