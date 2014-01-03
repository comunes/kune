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
package cc.kune.common.client.utils;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.MetaElement;
import com.google.gwt.dom.client.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class MetaUtils.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class MetaUtils {

  /**
   * Get the value of meta information writen in the html page. The meta
   * information is a html tag with name of meta usually placed inside the the
   * head section with two attributes: id and content. For example:
   * 
   * <code>&lt;meta name="name" value="userName" /&gt;</code>
   *
   * @param name the name
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
