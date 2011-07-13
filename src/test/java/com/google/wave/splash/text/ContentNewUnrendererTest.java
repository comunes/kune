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
