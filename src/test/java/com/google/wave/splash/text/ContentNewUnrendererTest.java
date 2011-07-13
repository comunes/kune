package com.google.wave.splash.text;

import org.junit.Ignore;
import org.junit.Test;
import org.waveprotocol.wave.client.editor.content.CMutableDocument;
import org.waveprotocol.wave.client.editor.content.ContentDocument;
import org.waveprotocol.wave.model.document.indexed.IndexedDocument;
import org.waveprotocol.wave.model.document.operation.Nindo;
import org.waveprotocol.wave.model.document.raw.impl.Element;
import org.waveprotocol.wave.model.document.raw.impl.Node;
import org.waveprotocol.wave.model.document.raw.impl.Text;
import org.waveprotocol.wave.model.document.util.DocProviders;
import org.waveprotocol.wave.model.operation.OperationSequencer;

public class ContentNewUnrendererTest {

  @Ignore
  @Test
  public void testBasicUnrender() {
    final IndexedDocument<Node, Element, Text> result = ContentNewUnrenderer.unrender("<b>Some <em>bold</em></b>");
    // final UnrenderedBlip result =
    // ContentUnrenderer.unrender("<p></p><p><b>Some <em>bold</em></b></p>");
    // assertEquals("", result.elements.toString());

    final OperationSequencer<Nindo> sequencer = DocProviders.createCopyingSequencer(result);
    final CMutableDocument mutable = new ContentDocument(null).createSequencedDocumentWrapper(sequencer);
    // WaveletDataUtil.createEmptyWavelet(null, null, null, 0)
  }
}
