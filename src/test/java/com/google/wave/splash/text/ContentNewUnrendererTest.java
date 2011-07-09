package com.google.wave.splash.text;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.google.wave.splash.text.ContentUnrenderer.UnrenderedBlip;

public class ContentNewUnrendererTest {

  @Ignore
  @Test
  public void testBasicUnrender() {
    // final IndexedDocument<Node, Element, Text> result =
    // ContentNewUnrenderer.unrender("<b>Some <em>bold</em></b>");
    final UnrenderedBlip result = ContentUnrenderer.unrender("<p></p><p><b>Some <em>bold</em></b></p>");
    assertEquals("", result.elements.toString());
  }
}
