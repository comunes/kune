/**
 * Copyright 2008 Google Inc.
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

import java.util.Collections;
import java.util.Map;

import org.waveprotocol.wave.model.document.raw.impl.Element;
import org.waveprotocol.wave.model.document.raw.impl.Node;
import org.waveprotocol.wave.model.document.raw.impl.Text;
import org.waveprotocol.wave.model.document.util.ElementStyleView;
import org.waveprotocol.wave.model.document.util.Point;

// TODO: Auto-generated Javadoc
/**
 * Presents a simple view of a HTML document where you can walk through every
 * node. It is rooted at a specific element (i.e. it can be a subtree of a full
 * document). However the results of attempting to traverse outside the document
 * are undefined.
 * 
 * @author danilatos@google.com (Daniel Danilatos)
 * 
 *         Adapted to be used in served side by vjrj@ourproject.org
 * 
 */
public class HtmlRawViewImpl implements ElementStyleView<Node, Element, Text> { // HTMLView

  /**
   * Singleton instance, if you don't care about not having a document element
   * defined.
   */
  public static final HtmlRawViewImpl INSTANCE = new HtmlRawViewImpl(null);

  /** The document element. */
  Element documentElement;

  /**
   * Instantiates a new html raw view impl.
   *
   * @param documentElement Root element for this "document"
   */
  public HtmlRawViewImpl(final Element documentElement) {
    this.documentElement = documentElement;
  }

  /** {@inheritDoc} */
  @Override
  public Element asElement(final Node node) {
    return node.asElement();
  }

  /** {@inheritDoc} */
  @Override
  public Text asText(final Node node) {
    return node.asText();
  }

  /** {@inheritDoc} */
  @Override
  public String getAttribute(final Element element, final String name) {
    // FIXME: test!
    return element.getAttribute(name);
    // return name.equals("class") ? element.getClassName() :
    // element.getAttribute(name);
  }

  /** {@inheritDoc} */
  @Override
  public Map<String, String> getAttributes(final Element element) {
    return Collections.emptyMap();
  }

  /** {@inheritDoc} */
  @Override
  public String getData(final Text textNode) {
    return textNode.getData();
  }

  /** {@inheritDoc} */
  @Override
  public Element getDocumentElement() {
    return documentElement;
  }

  /** {@inheritDoc} */
  @Override
  public Node getFirstChild(final Node node) {
    return node.getFirstChild();
  }

  /** {@inheritDoc} */
  @Override
  public Node getLastChild(final Node node) {
    return node.getLastChild();
  }

  /** {@inheritDoc} */
  @Override
  public int getLength(final Text textNode) {
    return textNode.getLength();
  }

  /** {@inheritDoc} */
  @Override
  public Node getNextSibling(final Node node) {
    return node.getNextSibling();
  }

  /** {@inheritDoc} */
  @Override
  public short getNodeType(final Node node) { // NOPMD by vjrj on 4/07/11 20:55
    return node.getNodeType();
  }

  /** {@inheritDoc} */
  @Override
  public Element getParentElement(final Node node) {
    return node.getParentElement();
  }

  /** {@inheritDoc} */
  @Override
  public Node getPreviousSibling(final Node node) {
    return node.getPreviousSibling();
  }

  /** {@inheritDoc} */
  @Override
  public String getStylePropertyValue(final Element element, final String name) {
    return getStylePropertyValue(element.getAttribute("style"), name);
  }

  /**
   * Gets the style property value.
   *
   * @param styles the styles
   * @param name the name
   * @return the style property value
   */
  private String getStylePropertyValue(final String styles, final String name) {
    if (styles != null && styles.contains(name)) {
      for (final String stylePair : styles.split(";")) {
        final int index = stylePair.indexOf(':');
        if (index >= 0 && index < stylePair.length() - 1) {
          final String key = stylePair.substring(0, index).trim();
          final String value = stylePair.substring(index + 1);
          if (key.equalsIgnoreCase(name)) {
            return value.trim();
          }
        }
      }
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public String getTagName(final Element element) {
    return element.getTagName();
  }

  /** {@inheritDoc} */
  @Override
  public Node getVisibleNode(final Node node) {
    return node;
  }

  /** {@inheritDoc} */
  @Override
  public Node getVisibleNodeFirst(final Node node) {
    return node;
  }

  /** {@inheritDoc} */
  @Override
  public Node getVisibleNodeLast(final Node node) {
    return node;
  }

  /** {@inheritDoc} */
  @Override
  public Node getVisibleNodeNext(final Node node) {
    return node;
  }

  /** {@inheritDoc} */
  @Override
  public Node getVisibleNodePrevious(final Node node) {
    return node;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isSameNode(final Node node, final Node other) {
    // TODO(danilatos): Use .equals or isSameNode for nodelets in nodemanager,
    // typing extractor, etc.
    return node == other || (node != null && node.equals(other));
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.model.document.util.ReadableDocumentView#onBeforeFilter(org.waveprotocol.wave.model.document.util.Point)
   */
  @Override
  public void onBeforeFilter(final Point<Node> at) {
  }
}
