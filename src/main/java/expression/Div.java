package expression;

public class Div extends BinOp {

  public Div(Expression left, Expression right) {
    super(left, right);
  }

  public int combine(Expression left, Expression right) {
    return left.eval() / right.eval();
  }

}
