package expression;

import java.util.Random;

public class Dice implements Expression {
  private int sides;
  private int result = 0;

  public Dice(int sides) {
    this.sides = sides;
  }

  public int eval() {
    if (result != 0) {
      return result;
    } else {
      Random r = new Random();
      result = r.nextInt(sides) + 1;
    }

    return result;

  }
}
