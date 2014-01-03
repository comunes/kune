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
package cc.kune.core.client.i18n;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.HeadElement;
import com.google.gwt.dom.client.LinkElement;

// TODO: Auto-generated Javadoc
/**
 * Some code from the StyleSheetLoader.java GWT's showcase
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class I18nStyles {

  /**
   * Convenience method for getting the document's head element.
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

  /**
   * Sets the rtl.
   * 
   * @param isRTL
   *          the new rtl
   */
  public static void setRTL(final boolean isRTL) {
    // FIXME: This is added at the beginning (by getHeadElement) so ws.css
    // overwrite this
    if (isRTL) {
      loadStyleSheet("ws/kune-common-rtl.css");
      loadStyleSheet("ws/ws-rtl.css");
    }
  }

}
