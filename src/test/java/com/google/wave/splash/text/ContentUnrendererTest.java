package com.google.wave.splash.text;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.waveprotocol.wave.model.document.indexed.IndexedDocument;
import org.waveprotocol.wave.model.document.raw.impl.Element;
import org.waveprotocol.wave.model.document.raw.impl.Node;
import org.waveprotocol.wave.model.document.raw.impl.Text;

public class ContentUnrendererTest {

  @Ignore
  @Test
  public void testBasicUnrender() {
    final IndexedDocument<Node, Element, Text> unrender = ContentNewUnrenderer.unrender("<b>foo</b>");
    assertEquals("", unrender.toXmlString());
    // assertTrue(blip.annotations.size() > 0);
  }

}
