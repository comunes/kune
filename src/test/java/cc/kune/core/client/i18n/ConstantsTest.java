package cc.kune.core.client.i18n;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

public class ConstantsTest {
  private ResourceBundle english;
  private ResourceBundle spanish;

  @Test
  public void basicEnglishFound() {
    assertEquals("Are you Sure?", english.getString(I18nUtils.convertMethodName("Are you Sure?")));
  }

  @Test(expected = MissingResourceException.class)
  public void basicEnglishNotFound() {
    english.getString(I18nUtils.convertMethodName("Other thing"));
  }

  @Test
  public void basicFound() {
    assertEquals("¡Por favor confirma!",
        spanish.getString(I18nUtils.convertMethodName("Please confirm!")));
  }

  @Test(expected = MissingResourceException.class)
  public void basicNotFound() {
    assertTrue(english.containsKey(I18nUtils.convertMethodName("Something not translated")));
    assertFalse(spanish.containsKey(I18nUtils.convertMethodName("Something not translated")));
    assertEquals("Something not translated",
        spanish.getString(I18nUtils.convertMethodName("Something not translated")));
  }

  @Before
  public void before() {
    english = ResourceBundle.getBundle("ConstantsTest", Locale.ENGLISH);
    spanish = ResourceBundle.getBundle("ConstantsTest", new Locale("es"));
    Locale.setDefault(Locale.ENGLISH);
  }

  @Test
  public void multipleLines() {
    assertEquals("some multiple lines",
        english.getString(I18nUtils.convertMethodName("some multiple lines")));
  }
}
