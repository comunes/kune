/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
import java.util.Locale;
import java.util.ResourceBundle;

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

import cc.kune.selenium.chat.ChatPageObject;
import cc.kune.selenium.general.EntityHeaderPageObject;
import cc.kune.selenium.login.LoginPageObject;
import cc.kune.selenium.login.RegisterPageObject;
import cc.kune.selenium.spaces.SitePageObject;

import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class SeleniumModule.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SeleniumModule extends PageObjectModule {

  /*
   * (non-Javadoc)
   * 
   * @see com.google.inject.AbstractModule#configure()
   */
  @Override
  protected void configure() {
    WebDriver driver;
    switch (SeleniumConf.DRIVER) {
    case chrome:
      driver = createChromeDriver();
      break;
    case firefox:
    default:
      driver = creatFirefoxDriver();
      break;
    }
    final EventFiringWebDriver wrap = new EventFiringWebDriver(driver);
    wrap.register(new CustomWebDriverEventListener());

    bind(WebDriver.class).toInstance(wrap);

    bind(ElementLocatorFactory.class).toInstance(
        new AjaxElementLocatorFactory(wrap, SeleniumConf.TIMEOUT));

    // Page Objects here!
    bind(LoginPageObject.class).in(Singleton.class);
    bind(RegisterPageObject.class).in(Singleton.class);
    bind(EntityHeaderPageObject.class).in(Singleton.class);
    bind(ChatPageObject.class).in(Singleton.class);
    bind(SitePageObject.class).in(Singleton.class);

    final ResourceBundle english = ResourceBundle.getBundle("TestConstants", Locale.ENGLISH);
    final ResourceBundle spanish = ResourceBundle.getBundle("TestConstants", new Locale("es"));
    Locale.setDefault(Locale.ENGLISH);
    switch (SeleniumConf.LANG) {
    case es:
      bind(ResourceBundle.class).toInstance(spanish);
      break;
    case en:
    default:
      bind(ResourceBundle.class).toInstance(english);
      break;
    }

  }

  /**
   * Creates the chrome driver.
   * 
   * @return the remote web driver
   */
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
    capabilities.setCapability("chrome.binary", "/usr/lib/chromium-browser/chromium-browser");
    final ChromeDriver driver = new ChromeDriver(capabilities);
    return driver;
  }

  /**
   * Creat firefox driver.
   * 
   * @return the firefox driver
   */
  private FirefoxDriver creatFirefoxDriver() {
    // http://code.google.com/p/selenium/wiki/FirefoxDriver
    System.setProperty("webdriver.firefox.useExisting", "true");

    final ProfilesIni allProfiles = new ProfilesIni();
    final FirefoxProfile profile = allProfiles.getProfile(SeleniumConstants.FIREFOX_PROFILE_NAME);
    // final FirefoxProfile profile = allProfiles.getProfile("76tp2vh0.ff5");

    // http://code.google.com/p/selenium/wiki/AdvancedUserInteractions#Mouse
    // profile.setEnableNativeEvents(true);

    final FirefoxDriver driver = new FirefoxDriver(profile);
    return driver;
  }
}
