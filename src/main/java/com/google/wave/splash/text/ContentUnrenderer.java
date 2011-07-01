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


import com.google.common.collect.Maps;
import com.google.wave.api.Annotations;
import com.google.wave.api.Line;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.Map;
import java.util.logging.Logger;

/**
 * @author anthonybaxter@gmail.com (Anthony Baxter)
 */
public class ContentUnrenderer {

  private static final Logger LOG = Logger.getLogger(ContentUnrenderer.class.getName());

  /**
   * Turns a HTML document back into a set of text, elements, annotations.
   */
  public static UnrenderedBlip unrender(String content) {
    StringBuilder sb = new StringBuilder();
    Map<Integer, com.google.wave.api.Element> elements = Maps.newHashMap();
    Annotations annotations = new Annotations();
    Document doc = Jsoup.parse(content);
    unrender(doc.body(), sb, elements, annotations);
    return new UnrenderedBlip(sb.toString(), elements, annotations);
  }

  /**
   * Helper method to recursively parse a HTML element and construct a wave document.
   */
  private static void unrender(Node parent, StringBuilder output, Map<Integer,
      com.google.wave.api.Element> elements, Annotations annotations) {
    for (Node node : parent.childNodes()) {
      if (node instanceof TextNode) {
        output.append(((TextNode)node).text());
      }
      else if (node instanceof Element) {
        int position = output.length();
        Element element = (Element)node;
        if ("p".equals(element.tag().getName())) {
          elements.put(position, new Line());
          // handle any attributes?
        }
        // Additional HTML element tags here.
        unrender(element, output, elements, annotations);
      }
    }

  }

  /**
   * Container for the output of the unrender method.
   */
  public static class UnrenderedBlip {
    public String contents;
    Map<Integer, com.google.wave.api.Element> elements;
    Annotations annotations = new Annotations();

    UnrenderedBlip(String contents, Map<Integer, com.google.wave.api.Element> elements,
        Annotations annotations) {
      this.contents = contents;
      this.elements = elements;
      this.annotations = annotations;
    }
  }
}
