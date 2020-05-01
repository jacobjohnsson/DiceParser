package expression;

public abstract class BinOp implements Expression {
  protected Expression left;
  protected Expression right;
  private boolean evaluated = false;
  private int result;

  public BinOp(Expression left, Expression right) {
    this.left = left;
    this.right = right;
  }

  public int eval() {
    if (!evaluated) {
      result = combine(left, right);
      evaluated = true;
    }
    return result;
  }

  protected abstract int combine(Expression left, Expression right);
}
