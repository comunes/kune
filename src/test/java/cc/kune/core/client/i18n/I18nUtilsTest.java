package cc.kune.core.client.i18n;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class I18nUtilsTest {

  @Test
  public void basicLongMessage() {
    assertEquals(
        "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789",
        I18nUtils.convertMethodName("0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 "));
  }

  @Test
  public void basicMultipleReservedChars() {
    assertEquals("yesOrNoMaybe", I18nUtils.convertMethodName("Yes, or; & no? maybe"));
    assertEquals("yesOrNoMaybe", I18nUtils.convertMethodName("Yes, or; & no? (maybe)"));
    assertEquals("yesOrNoMaybeGiveSomeAnyPerCent",
        I18nUtils.convertMethodName("Yes, or; & no?! (maybe): 'give' \"some/any\" % per-cent"));
  }

  @Test
  public void basicTests() {
    assertEquals("yesOrNo", I18nUtils.convertMethodName("yes, or & no?"));
    assertEquals("yesOrNo", I18nUtils.convertMethodName("yes or no"));
    assertEquals("yesOrNo", I18nUtils.convertMethodName("yes   or   no"));
    assertEquals("newYesNoMaybeVoting", I18nUtils.convertMethodName("New Yes/No/Maybe/+ Voting"));
  }

  @Test
  public void carriageReturn() {
    assertEquals("yesNo", I18nUtils.convertMethodName("yes\nno"));
  }

  @Test
  public void testParams() {
    assertEquals("nOfParamItems", I18nUtils.convertMethodName("[%d] of [%s] items"));
    assertEquals("nOfNItems", I18nUtils.convertMethodName("[%d] of [%d] items"));
  }

  @Test
  public void testParent() {
    assertEquals("ofItems", I18nUtils.convertMethodName("() of [] {} items"));
  }
}
