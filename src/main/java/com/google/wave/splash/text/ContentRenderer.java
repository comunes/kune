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

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

import org.waveprotocol.wave.client.editor.content.paragraph.DefaultParagraphHtmlRenderer;
import org.waveprotocol.wave.client.editor.content.paragraph.Paragraph;
import org.waveprotocol.wave.client.editor.content.paragraph.Paragraph.Alignment;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.inject.Inject;
import com.google.wave.api.Annotation;
import com.google.wave.api.Annotations;
import com.google.wave.api.Attachment;
import com.google.wave.api.Element;
import com.google.wave.api.ElementType;
import com.google.wave.api.Gadget;
import com.google.wave.api.Line;
import com.google.wave.splash.web.template.WaveRenderer;

/**
 * A utility class that converts blip content into html.
 * 
 * @author David Byttow
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
public class ContentRenderer {

  /**
   * Represents a marker in content that is either the start or end of an
   * annotation or a single element.
   */
  static class Marker implements Comparable<Marker> {
    static Marker fromAnnotation(final Annotation annotation, final int index, final boolean isEnd) {
      return new Marker(annotation, index, isEnd);
    }

    static Marker fromElement(final Element element, final int index) {
      return new Marker(element, index);
    }

    private final Annotation annotation;
    private final Element element;
    private final int index;
    private boolean isEnd;

    private Marker(final Annotation annotation, final int index, final boolean isEnd) {
      this.annotation = annotation;
      this.element = null;
      this.index = index;
      this.isEnd = isEnd;
    }

    private Marker(final Element element, final int index) {
      this.element = element;
      this.annotation = null;
      this.index = index;
    }

    @Override
    public int compareTo(final Marker that) {
      final int value = Integer.signum(this.index - that.index);
      if (value == 0) {
        if (this.isElement() != that.isElement()) {
          // At boundaries, annotations should wrap elements.
          final Marker annotation = this.isAnnotation() ? this : that;
          return annotation.isEnd ? -1 : 1;
        }
      }
      return value;
    }

    boolean isAnnotation() {
      return this.annotation != null;
    }

    boolean isElement() {
      return this.element != null;
    }
  }

  private final GadgetRenderer gadgetRenderer;
  private boolean identing;
  private boolean inAlign = false;
  private boolean inheader = false;
  private final WaveRenderer waveRenderer;

  @Inject
  public ContentRenderer(final GadgetRenderer gadgetRenderer, final WaveRenderer waveRenderer) {
    this.gadgetRenderer = gadgetRenderer;
    this.waveRenderer = waveRenderer;
  }

  private void closeIndentIfNecessary(final StringBuilder builder) {
    if (identing) {
      // Close identations
      identing = false;
      builder.append("</li>");
    }
  }

  private void emitAnnotation(final Marker marker, final StringBuilder builder) {
    if (marker.annotation.getName().startsWith("link")) {
      if (marker.isEnd) {
        builder.append("</a>");
      } else {
        builder.append("<a href=\"" + marker.annotation.getValue() + "\" target=\"_blank\">");
      }
    } else if (marker.isEnd) {
      // if (marker.annotation.getName().)
      builder.append("</span>");
    } else {
      // Transform name into dash-separated css property rather than lower camel
      // case.
      String name = Markup.sanitize(marker.annotation.getName());
      final String value = Markup.sanitize(marker.annotation.getValue());

      // Title annotations are translated as bold.
      name = name.substring(marker.annotation.getName().indexOf("/") + 1);
      name = Markup.toDashedStyle(name);

      builder.append("<span style='");
      builder.append(name);
      builder.append(':');
      builder.append(value);
      builder.append("'>");
    }
  }

  private void renderElement(final Element element, final int index, final List<String> contributors,
      final StringBuilder builder) {
    final ElementType type = element.getType();
    switch (type) {
    case LINE:
      final String t = element.getProperty(Line.LINE_TYPE);
      final String i = element.getProperty(Line.INDENT);
      final String a = element.getProperty(Line.ALIGNMENT);
      final String d = element.getProperty(Line.DIRECTION);
      // For direction stuff (RTL etc) see DefaultParagraphHtml
      if (inAlign) {
        // Close identations
        inAlign = false;
        builder.append("</div><!-- end of align -->");
      }
      final Integer ident = i != null ? Integer.valueOf(i) : 0;
      if (inheader) {
        // New line, we close previous header
        builder.append("</div> <!-- end h1/h2... header -->");
        inheader = false;
      }

      if (t != null && t.equals(Paragraph.LIST_TYPE)) {
        // type-0 to 2, margin 22px * i <li class="bullet-type-0"
        // style="margin-left: 88px;">
        // See DefaultParagraphHtml
        builder.append("<li class=\"bullet-type-").append(ident % 3).append("\" style=\"margin-left: ").append(
            (ident + 1) * 22).append("px;\">");
        identing = true;
      } else if (ident > 0) {
        builder.append("<li style=\"margin-left: ").append(ident * 22).append("px;\">");
        identing = true;
      } else {
        closeIndentIfNecessary(builder);
      }
      if (a != null) {
        builder.append("<div style=\"text-align:" + Alignment.fromValue(a).cssValue() + ";\">");
        inAlign = true;
      }
      if (t != null && t.startsWith("h")) {
        // See DefaultParagraphHtml
        final String fontWeight = FontWeight.BOLD.getCssName();
        final double headingNum = Integer.parseInt(t.substring(1));
        // Do this with CSS instead.
        // h1 -> 1.75, h4 -> 1, others linearly in between.
        final double factor = 1 - (headingNum - 1) / (Paragraph.NUM_HEADING_SIZES - 1);
        final double fontSize = DefaultParagraphHtmlRenderer.MIN_HEADING_SIZE_EM
            + factor
            * (DefaultParagraphHtmlRenderer.MAX_HEADING_SIZE_EM - DefaultParagraphHtmlRenderer.MIN_HEADING_SIZE_EM);
        builder.append("<div style=\"font-size: ").append(fontSize).append("em; font-weight: ").append(
            fontWeight).append(";\">");
        inheader = true;
      }
      // TODO(anthonybaxter): need to handle <line t="li"> and <line t="li"
      // i="3">
      // TODO(anthonybaxter): also handle H1 &c
      // Special case: If this is the first LINE element at position 0,
      // ignore it because we've already appended the first <p> tag.

      if (index > 0) {
        builder.append("</p><p>");
      } else {
        // We build "<li>" with a main wrapper ul
        // FIXME, close finally the ul
        builder.append("<ul style=\"padding: 0px; margin: 0px;\">");
      }
      break;
    case ATTACHMENT:
      final Attachment attachment = (Attachment) element;
      final String url = Markup.sanitizeAndEncode(attachment.getAttachmentUrl());
      String caption = Markup.sanitize(element.getProperty("caption"));
      if (caption == null) {
        caption = "";
      }
      // TODO: Revisit this questionable html.
      builder.append("<table class=\"attachment-element\"><tr><td>").append(
          "<a class=\"lightbox\" title=\"").append(caption).append("\" href=\"").append(url).append(
          "\"><img src=\"").append(url).append(
          "\"/></a></td></tr></td></tr><tr><td><div class=\"caption\">").append(caption).append(
          "</div></td></tr></table>");
      break;
    case IMAGE:
      String imageUrl = element.getProperty("url");

      if (Markup.isTrustedImageUrl(imageUrl)) {
        imageUrl = Markup.sanitizeAndEncode(imageUrl);
        builder.append("<img src=\"").append(imageUrl).append("\"/>");
      }
      break;
    case GADGET:
      gadgetRenderer.render((Gadget) element, contributors, builder);
      break;
    case INLINE_BLIP:
      waveRenderer.renderInlineReply(element, index, builder);

      break;
    default:
      // Ignore all others.
    }
  }

  /**
   * Takes content and applies style and formatting to it based on its
   * annotations and elements.
   * 
   */
  public String renderHtml(final String content, final Annotations annotations,
      final SortedMap<Integer, Element> elements, final List<String> contributors) {
    final StringBuilder builder = new StringBuilder();

    // NOTE(dhanji): This step is enormously important!
    final char[] raw = content.toCharArray();

    final SortedSet<Marker> markers = new TreeSet<Marker>();

    // First add annotations sorted by range.
    for (final Annotation annotation : annotations.asList()) {
      // Ignore anything but style or title annotations.
      final String annotationName = annotation.getName();
      if (annotationName.startsWith("style")) {
        markers.add(Marker.fromAnnotation(annotation, annotation.getRange().getStart(), false));
        markers.add(Marker.fromAnnotation(annotation, annotation.getRange().getEnd(), true));
      } else if (annotationName.startsWith("link")) {
        markers.add(Marker.fromAnnotation(annotation, annotation.getRange().getStart(), false));
        markers.add(Marker.fromAnnotation(annotation, annotation.getRange().getEnd(), true));
      } else if ("conv/title".equals(annotationName)) {
        // Find the first newline and make sure the annotation only gets to that
        // point.
        final int start = annotation.getRange().getStart();
        final int from = raw[0] == '\n' ? 1 : 0;
        final int end = content.indexOf('\n', from);
        if (end > start && start < end) {
          // Commented (vjrj)
          // final Annotation title = new Annotation(Annotation.FONT_WEIGHT,
          // "bold", start, end);
          // markers.add(Marker.fromAnnotation(title, start, false));
          // markers.add(Marker.fromAnnotation(title, end, true));
        } else {
          // LOG?
        }
      }
    }

    // Now add elements sorted by index.
    for (final Map.Entry<Integer, Element> entry : elements.entrySet()) {
      markers.add(Marker.fromElement(entry.getValue(), entry.getKey()));
    }

    builder.append("<p>");
    int cursor = 0;
    for (final Marker marker : markers) {
      if (marker.index > cursor) {
        final int to = Math.min(raw.length, marker.index);
        builder.append(Markup.sanitize(new String(raw, cursor, to - cursor)));
      }

      cursor = marker.index;

      if (marker.isElement()) {
        renderElement(marker.element, marker.index, contributors, builder);
      } else {
        emitAnnotation(marker, builder);
      }
    }

    // add any tail bits
    if (cursor < raw.length - 1) {
      builder.append(Markup.sanitize(new String(raw, cursor, raw.length - cursor)));
    }

    // Replace empty paragraphs. (TODO expensive and silly)
    return builder.append("</ul>").toString().replace("<p>\n</p>", "<p><br/></p>");
  }
}
