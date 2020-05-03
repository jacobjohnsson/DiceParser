package expression;

import java.util.Random;

public class Dice implements Expression {
  private final int sides;
  private final int nbr;
  private int result = 0;

  public Dice(int nbr, int sides) {
    this.sides = sides;
    this.nbr = nbr;
  }

  public int eval() {
    if (result != 0) {
      return result;
    } else {
      Random r = new Random();
      for (int i = 0; i < nbr; i++) {
        result += r.nextInt(sides) + 1;
      }
    }
    return result;
  }
}
