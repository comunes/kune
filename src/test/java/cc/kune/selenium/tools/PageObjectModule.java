package cc.kune.selenium.tools;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import com.google.inject.AbstractModule;

public abstract class PageObjectModule extends AbstractModule {
  protected <T> void registerPageObject(final Class<T> componentType, final T object,
      final ElementLocatorFactory locator) {
    bind(componentType).toInstance(object);
    PageFactory.initElements(locator, object);

  }
}
