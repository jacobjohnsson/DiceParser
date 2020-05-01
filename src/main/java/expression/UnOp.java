package expression;

public abstract class UnOp implements Expression {
  private Expression exp;

  public UnOp(Expression exp) {
    this.exp = exp;
  }

  public int eval() {
    return action(exp);
  }

  protected abstract int action(Expression exp);
}
