package expression;

public class Plus extends BinOp {

  public Plus(Expression left, Expression right) {
    super(left, right);
  }

  protected int combine(Expression left, Expression right) {
    return left.eval() + right.eval();
  }
}
