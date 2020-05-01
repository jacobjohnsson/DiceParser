package tokenizer;

import java.util.Enumeration;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Tokenizer implements Enumeration<String> {

  private String input;
  private int index;
  private final String DICE = "^[1-9]+d[1-9]+";
  private final String DIGIT = "^\\d+";
  private final String OPERATOR = "^[+-@\\*/]"; // @\\* => *

  public Tokenizer(String s) {
    this.input = s;
    index = 0;
  }

  public boolean hasMoreElements() {
    return index < input.length();
  }

  public String nextElement() {
    return findDice();
  }

  private void consume(int x) {
    index = index + x;
  }

  private String findDice() {
    String result = "";
    Pattern p = Pattern.compile(DICE);
    Matcher m = p.matcher(input.substring(index));
    System.out.println("search space: " + input.substring(index));
    if (m.find()) {
      System.out.println("Found Dice");
      result = m.group();
      consume(result.length());
    } else {
      result = findDigit();
    }
    return result;
  }

  private String findDigit() {
    String result = "";
    Pattern p = Pattern.compile(DIGIT);
    Matcher m = p.matcher(input.substring(index));
    if (m.find()) {
      System.out.println("Found Digit");
      result = m.group();
      consume(result.length());
    } else {
      result = findOperator();
    }
    return result;
  }

  private String findOperator() {
    String result = "";
    Pattern p = Pattern.compile(OPERATOR);
    Matcher m = p.matcher(input.substring(index));
    if (m.find()) {
      System.out.println("Found Operator");
      result = m.group();
      consume(result.length());
    } else {
      result = findTrash();
    }
    return result;
  }

  private String findTrash() {
    String result = input.substring(index, index + 1);
    consume(1);
    return result;
  }
}
