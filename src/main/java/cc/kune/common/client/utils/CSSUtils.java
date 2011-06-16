package cc.kune.common.client.utils;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.LinkElement;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.dom.client.StyleInjector;

public class CSSUtils {

  public static StyleElement addCss(final String cssContents) {
    // final StyleElement style = Document.get().createStyleElement();
    // style.setPropertyString("language", "text/css");
    // style.setInnerText(cssContents);

    return StyleInjector.injectStylesheetAtEnd(cssContents);
  }

  public static LinkElement setCss(final String cssUrl) {
    final Element head = Document.get().getElementsByTagName("head").getItem(0);
    final LinkElement link = Document.get().createLinkElement();
    link.setType("text/css");
    link.setRel("stylesheet");
    link.setHref(cssUrl);
    link.setMedia("screen");
    link.setTitle("dynamicLoadedSheet");
    head.appendChild(link);
    // you can use removeFromParent
    return link;
  }

}
