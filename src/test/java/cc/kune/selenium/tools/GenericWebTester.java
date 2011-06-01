package cc.kune.selenium.tools;

import org.openqa.selenium.WebDriver;

import com.google.inject.Inject;

public class GenericWebTester extends AbstractWebTester {

  @Inject
  public GenericWebTester(final WebDriver driver) {
    super(driver, "http://kune.beta.iepala.es/ws/?locale=en#");
  }

}
