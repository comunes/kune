/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.selenium;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import cc.kune.selenium.login.EntityHeaderPageObject;
import cc.kune.selenium.login.LoginPageObject;
import cc.kune.selenium.login.RegisterPageObject;
import cc.kune.selenium.tools.SeleniumConstants;

import com.google.inject.Singleton;

public class SeleniumModule extends PageObjectModule {

  public SeleniumModule() {
  }

  @Override
  protected void configure() {
    // final RemoteWebDriver driver = createChromeDriver();
    final WebDriver driver = creatFirefoxDriver();
    final EventFiringWebDriver wrap = new EventFiringWebDriver(driver);
    wrap.register(new CustomWebDriverEventListener());

    bind(WebDriver.class).toInstance(wrap);

    bind(ElementLocatorFactory.class).toInstance(
        new AjaxElementLocatorFactory(wrap, SeleniumConstants.TIMEOUT));

    // Page Objects here!
    bind(LoginPageObject.class).in(Singleton.class);
    bind(RegisterPageObject.class).in(Singleton.class);
    bind(EntityHeaderPageObject.class).in(Singleton.class);
  }

  @SuppressWarnings("unused")
  private RemoteWebDriver createChromeDriver() {
    // http://code.google.com/p/selenium/wiki/ChromeDriver
    System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
    final DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    // http://peter.sh/experiments/chromium-command-line-switches/
    capabilities.setCapability("chrome.switches", Arrays.asList("--disable-translate"
    // ,
    // "--load-extension=/home/YOURUSER/.config/chromium/Default/Extensions/jpjpnpmbddbjkfaccnmhnkdgjideieim/1.0.9738_0/"
    // (tests with the gwt extension)
    ));
    capabilities.setCapability("chrome.binary", "/usr/bin/chromium-browser");
    final ChromeDriver driver = new ChromeDriver(capabilities);
    return driver;
  }

  private FirefoxDriver creatFirefoxDriver() {
    // http://code.google.com/p/selenium/wiki/FirefoxDriver
    System.setProperty("webdriver.firefox.useExisting", "true");
    final ProfilesIni allProfiles = new ProfilesIni();
    final FirefoxProfile profile = allProfiles.getProfile(SeleniumConstants.FIREFOX_PROFILE_NAME);
    // final FirefoxProfile profile = allProfiles.getProfile("76tp2vh0.ff5");
    final FirefoxDriver driver = new FirefoxDriver(profile);
    return driver;
  }
}
