package tokenizer;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.NoSuchElementException;

public class TokenIteratorTest {

  @Test
  public void hasOneElementTest() {
    TokenIterator t = new TokenIterator("1");
    assertTrue(t.hasNext());
  }

  @Test
  public void isOneElementTest() {
    TokenIterator t = new TokenIterator("1");
    Token tok = t.next();
    assertEquals(tok.getValue(), "1");
    assertTrue(tok.sameType(Token.Type.NUM));
  }

  @Test
  public void hasManyElementsTest() {
    TokenIterator t = new TokenIterator("1+(4-22)*51");
    int count = 0;
    while (t.hasNext()) {
      t.next();
      count++;
    }
    assertEquals(count, 9);
  }

  @Test
  public void isManyElementsTest() {
    TokenIterator t = new TokenIterator("1+(4-22)*51");
    int count = 0;
    assertEquals(t.next().getValue(), "1");
    assertEquals(t.next().getValue(), "+");
    assertEquals(t.next().getValue(), "(");
    assertEquals(t.next().getValue(), "4");
    assertEquals(t.next().getValue(), "-");
    assertEquals(t.next().getValue(), "22");
    assertEquals(t.next().getValue(), ")");
    assertEquals(t.next().getValue(), "*");
    assertEquals(t.next().getValue(), "51");
  }

  @Test
  public void hasSimpleDiceTest() {
    TokenIterator t = new TokenIterator("2d6");
    assertTrue(t.hasNext());
  }

  @Test
  public void isSimpleDiceTest() {
    TokenIterator t = new TokenIterator("2d6");
    Token tok = t.next();
    assertEquals(tok.getValue(), "2d6");
    assertTrue(tok.sameType(Token.Type.DICE));
  }

  @Test
  public void hasManyDiceTest() {
    TokenIterator t = new TokenIterator("(2d6+2)*2-10d20");
    int count = 0;
    while (t.hasNext()) {
      t.next();
      count++;
    }
    assertEquals(count, 9);
  }

  @Test
  public void isManyDiceTest() {
    TokenIterator t = new TokenIterator("(2d6+2)*2-10d20");
    assertEquals(t.next().getValue(), "(");
    assertEquals(t.next().getValue(), "2d6");
    assertEquals(t.next().getValue(), "+");
    assertEquals(t.next().getValue(), "2");
    assertEquals(t.next().getValue(), ")");
    assertEquals(t.next().getValue(), "*");
    assertEquals(t.next().getValue(), "2");
    assertEquals(t.next().getValue(), "-");
    assertEquals(t.next().getValue(), "10d20");
  }

  @Test
  public void failTest() {
    TokenIterator t = new TokenIterator("1+2+3+1d6");
    while (t.hasNext()) {
      t.next();
    }

    try {
      t.next();
      fail("Should return NoSuchElementException.");
    } catch(NoSuchElementException e) {
      // Success
    }
  }
}
