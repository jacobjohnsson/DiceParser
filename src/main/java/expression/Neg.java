package expression;

public class Neg extends UnOp {
  public Neg(Expression exp) {
    super(exp);
  }

  public int action(Expression exp) {
    return exp.eval() * -1;
  }

}
