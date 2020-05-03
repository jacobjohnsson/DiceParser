package tokenizer;

public class Token {

  public enum Type {LPAR,
                    RPAR,
                    BINOP,
                    NUM,
                    DICE,
                    SENTINEL,
                    END,
                    UNKNOWN};

  private final String value;
  private final Type type;

  public Token(String value, Type type) {
    this.value = value;
    this.type = type;
  }

  public Boolean isType(Token.Type t) {
    return type == t;
  }

  public String getValue() {
    return value;
  }

  public Boolean sameType(Token other) {
    return this.type == other.type;
  }

  public Boolean sameType(Token.Type otherType) {
    return this.type == otherType;
  }

  public String toString() {
    String result = "";
    switch (type) {
      case LPAR: result = "LPAR: " + value;
        break;
      case RPAR: result = "RPAR: " + value;
        break;
      case BINOP: result = "BINOP: " + value;
        break;
      case NUM: result = "NUM: " + value;
        break;
      case DICE: result = "DICE: " + value;
        break;
      case SENTINEL: result = "SENTINEL: " + value;
        break;
      case END: result = "END: " + value;
        break;
      case UNKNOWN: result = "UNKNOWN: " + value;
        break;
      default:
    }
    return result;
  }

}
