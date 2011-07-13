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
