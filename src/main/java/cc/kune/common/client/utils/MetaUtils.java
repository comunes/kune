package cc.kune.common.client.utils;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.MetaElement;
import com.google.gwt.dom.client.NodeList;

public class MetaUtils {

  /**
   * Get the value of meta information writen in the html page. The meta
   * information is a html tag with name of meta usually placed inside the the
   * head section with two attributes: id and content. For example:
   * 
   * <code>&lt;meta name="name" value="userName" /&gt;</code>
   * 
   * @param id
   *          the 'id' value of the desired meta tag
   * @return the value of the attribute 'content' or null if not found
   */
  public static String get(final String name) {
    final NodeList<Element> tags = Document.get().getElementsByTagName("meta");
    for (int i = 0; i < tags.getLength(); i++) {
      final MetaElement metaTag = ((MetaElement) tags.getItem(i));
      if (metaTag.getName().equals(name)) {
        return metaTag.getContent();
      }
    }
    return null;
  }
}
