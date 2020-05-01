package expression;

public class Mul extends BinOp {

  public Mul(Expression left, Expression right) {
    super(left, right);
  }

  protected int combine(Expression left, Expression right) {
    return left.eval() * right.eval();
  }

}
