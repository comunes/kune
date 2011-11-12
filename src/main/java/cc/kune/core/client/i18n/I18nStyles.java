package cc.kune.core.client.i18n;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.LinkElement;

/**
 * 
 * Some code from the StyleSheetLoader.java GWT's showcase
 * 
 */
public class I18nStyles {

  /**
   * Convenience method for getting the document's head element.
   * 
   * 
   * @return the document's head element
   */
  private static native HeadElement getHeadElement()
  /*-{
		return $doc.getElementsByTagName("head")[0];
  }-*/;

  /**
   * Load a style sheet onto the page.
   * 
   * @param href
   *          the url of the style sheet
   */
  private static void loadStyleSheet(final String href) {
    final LinkElement linkElem = Document.get().createLinkElement();
    linkElem.setRel("stylesheet");
    linkElem.setType("text/css");
    linkElem.setHref(href);
    getHeadElement().appendChild(linkElem);
  }

  public static void setRTL(final boolean isRTL) {
    if (isRTL) {
      loadStyleSheet("ws/kune-common-rtl.css");
      loadStyleSheet("ws/ws-rtl.css");
    }
  }

}