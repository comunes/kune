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

import org.waveprotocol.wave.model.document.indexed.IndexedDocument;
import org.waveprotocol.wave.model.document.operation.Nindo;
import org.waveprotocol.wave.model.document.raw.RawDocumentProviderImpl;
import org.waveprotocol.wave.model.document.raw.impl.Element;
import org.waveprotocol.wave.model.document.raw.impl.Node;
import org.waveprotocol.wave.model.document.raw.impl.RawDocumentImpl;
import org.waveprotocol.wave.model.document.raw.impl.Text;
import org.waveprotocol.wave.model.document.util.DocProviders;
import org.waveprotocol.wave.model.document.util.Point;
import org.waveprotocol.wave.model.document.util.RawElementStyleView;
import org.waveprotocol.wave.model.operation.OperationException;
import org.waveprotocol.wave.model.richtext.RichTextMutationBuilder;
import org.waveprotocol.wave.model.richtext.RichTextTokenizer;
import org.waveprotocol.wave.model.richtext.RichTextTokenizerImpl;
import org.waveprotocol.wave.model.util.Pair;
import org.waveprotocol.wave.model.util.ReadableStringSet;

import cc.kune.core.client.errors.DefaultException;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentNewUnrenderer.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ContentNewUnrenderer {

  // private static final Logger LOG =
  // Logger.getLogger(ContentNewUnrenderer.class.getName());

  /**
   * Apply tokens to empty doc.
   *
   * @param tokens the tokens
   * @return the pair
   */
  private static Pair<Nindo, IndexedDocument<Node, Element, Text>> applyTokensToEmptyDoc(
      final RichTextTokenizer tokens) {
    // final IndexedDocument<Node, Element, Text> doc =
    // DocProviders.POJO.parse("<body><line/></body>");
    final IndexedDocument<Node, Element, Text> doc = DocProviders.POJO.parse("<line/>");
    // final Point<Node> insertAt = doc.locate(3);
    final Point<Node> insertAt = doc.locate(1);

    final Nindo.Builder builder = new Nindo.Builder();
    builder.skip(1);
    // builder.skip(3);
    @SuppressWarnings("unused")
    final ReadableStringSet mutations = new RichTextMutationBuilder().applyMutations(tokens, builder,
        doc, insertAt.getContainer());

    final Nindo nindo = builder.build();
    try {
      doc.consumeAndReturnInvertible(nindo);
    } catch (final OperationException e) {
      throw new DefaultException("Operation Exception " + e);
    }
    doc.asOperation();
    return new Pair<Nindo, IndexedDocument<Node, Element, Text>>(nindo, doc);
  }

  /**
   * Unrender.
   *
   * @param content the content
   * @return the indexed document
   */
  public static IndexedDocument<Node, Element, Text> unrender(final String content) {
    final RawElementStyleView rawEl = new RawElementStyleView(RawDocumentProviderImpl.create(
        RawDocumentImpl.BUILDER).parse("<div>" + content + "</div>"));
    final RichTextTokenizerImpl<Node, Element, Text> tokenizer = new RichTextTokenizerImpl<Node, Element, Text>(
        rawEl);
    final Pair<Nindo, IndexedDocument<Node, Element, Text>> result = applyTokensToEmptyDoc(tokenizer);
    return result.getSecond();
  }

}
