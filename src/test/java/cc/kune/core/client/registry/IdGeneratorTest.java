package cc.kune.core.client.registry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IdGeneratorTest {

  @Test
  public void testBasic() {
    assertEquals("", IdGenerator.generate("", ""));
    assertEquals("", IdGenerator.generate(null, ""));
    assertEquals("", IdGenerator.generate("", null));
    assertEquals("", IdGenerator.generate(null, null));
    assertEquals("a", IdGenerator.generate("a", null));
    assertEquals("a", IdGenerator.generate("a", ""));
    assertEquals("b", IdGenerator.generate("", "b"));
    assertEquals("b", IdGenerator.generate(null, "b"));
    assertEquals("a" + IdGenerator.SEPARATOR + "b", IdGenerator.generate("a", "b"));
  }
}
