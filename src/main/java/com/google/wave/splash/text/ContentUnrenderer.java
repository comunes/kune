/**
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.google.wave.splash.text;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.safety.Whitelist;

import com.google.common.collect.Maps;
import com.google.wave.api.Annotations;
import com.google.wave.api.Line;

public class ContentUnrenderer {

  /**
   * Container for the output of the unrender method.
   */
  public static class UnrenderedBlip {
    Annotations annotations = new Annotations();
    public String contents;
    Map<Integer, com.google.wave.api.Element> elements;

    UnrenderedBlip(final String contents, final Map<Integer, com.google.wave.api.Element> elements,
        final Annotations annotations) {
      this.contents = contents;
      this.elements = elements;
      this.annotations = annotations;
    }
  }

  // private static final Logger LOG =
  // Logger.getLogger(ContentUnrenderer.class.getName());

  /**
   * Helper method to recursively parse a HTML element and construct a wave
   * document.
   */
  private static void unrender(final Node parent, final StringBuilder output,
      final Map<Integer, com.google.wave.api.Element> elements, final Annotations annotations) {
    for (final Node node : parent.childNodes()) {
      if (node instanceof TextNode) {
        output.append(((TextNode) node).text());
      } else if (node instanceof Element) {
        final int position = output.length();
        final Element element = (Element) node;
        final String name = element.tag().getName();
        if ("p".equalsIgnoreCase(name)) {
          elements.put(position, new Line());
          // handle any attributes?
        }
        // Additional HTML element tags here.
        unrender(element, output, elements, annotations);
      }
    }

  }

  /**
   * Turns a HTML document back into a set of text, elements, annotations.
   */
  public static UnrenderedBlip unrender(final String content) {
    final StringBuilder sb = new StringBuilder();
    final Map<Integer, com.google.wave.api.Element> elements = Maps.newHashMap();
    final Annotations annotations = new Annotations();
    // Sanitized
    final String safe = Jsoup.clean(content, Whitelist.basic());
    final Document doc = Jsoup.parse(safe);
    unrender(doc.body(), sb, elements, annotations);
    return new UnrenderedBlip(sb.toString(), elements, annotations);
  }
}
