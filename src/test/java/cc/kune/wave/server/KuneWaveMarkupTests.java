package cc.kune.wave.server;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.waveprotocol.wave.model.document.util.XmlStringBuilder;

import cc.kune.core.server.manager.impl.ContentConstants;

import com.google.wave.api.Markup;

public class KuneWaveMarkupTests {

  private static final String FOO_MARKUP = "<b>Foo</b><br/>";

  @Test
  public void testMarkup() {
    final Markup markup = Markup.of(FOO_MARKUP);
    assertNotNull(markup.getMarkup());
    assertNotNull(markup.getText());
    assertNotNull(markup.getText().contains("\n"));
  }

  @Test
  public void testXmlStringBuilderOfMarkup() {
    XmlStringBuilder builder = XmlStringBuilder.createFromXmlString(FOO_MARKUP);
    assertTrue(builder.getLength() > 0);
    builder = XmlStringBuilder.createFromXmlString(ContentConstants.INITIAL_CONTENT);
    assertTrue(builder.getLength() > 0);
    builder = XmlStringBuilder.createFromXmlString(ContentConstants.WELCOME_WAVE_CONTENT);
    assertTrue(builder.getLength() > 0);
  }
}
