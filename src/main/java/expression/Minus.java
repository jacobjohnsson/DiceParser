package expression;

public class Minus extends BinOp {

  public Minus(Expression left, Expression right) {
    super(left, right);
  }

  protected int combine(Expression left, Expression right) {
    return left.eval() - right.eval();
  }
}
