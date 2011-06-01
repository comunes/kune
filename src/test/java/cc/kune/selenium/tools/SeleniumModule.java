package cc.kune.selenium.tools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import cc.kune.selenium.login.LoginPageObject;

import com.google.inject.Singleton;

public class SeleniumModule extends PageObjectModule {

  public SeleniumModule() {
  }

  @Override
  protected void configure() {

    // bind(WebDriver.class).toInstance(creatFirefoxDriver());
    final ChromeDriver driver = createChromeDriver();
    bind(WebDriver.class).toInstance(driver);

    bind(GenericWebTester.class).in(Singleton.class);

    // "http://127.0.0.1:8888/ws/?locale=en&log_level=INFO&gwt.codesvr=127.0.0.1:9997#");

    bind(ElementLocatorFactory.class).toInstance(
        new AjaxElementLocatorFactory(driver, SeleniumConstants.TIMEOUT));

    // Page Objects here!
    bind(LoginPageObject.class).in(Singleton.class);
  }

  private ChromeDriver createChromeDriver() {
    final ChromeDriver driver = new ChromeDriver();
    return driver;
  }

  @SuppressWarnings("unused")
  private FirefoxDriver creatFirefoxDriver() {
    // final FirefoxProfile profile = new FirefoxProfile();
    // profile.setPreference("webdriver.firefox.profile",
    // SeleniumConstants.FIREFOX_PROFILE_NAME);
    // profile.setPreference("webdriver.firefox.useExisting",true);
    final ProfilesIni allProfiles = new ProfilesIni();
    final FirefoxProfile profile = allProfiles.getProfile(SeleniumConstants.FIREFOX_PROFILE_NAME);
    // profile.setPreferences("foo.bar", 23);
    final FirefoxDriver driver = new FirefoxDriver(profile);
    return driver;
  }
}
