package cc.kune.selenium;

import org.testng.Assert;

public class SeleniumUtils {

  public static void sleep(final int milliseconds) {
    try {
      Thread.sleep(milliseconds);
    } catch (final InterruptedException e) {
      Assert.fail("Exception in sleep method", e);
    }
  }

}
