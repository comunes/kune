// @formatter:off
/**
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package cc.kune.wave.client;

import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.common.safehtml.SafeHtmlBuilder;
import org.waveprotocol.wave.client.render.ReductionBasedRenderer;
import org.waveprotocol.wave.client.render.RenderingRules;
import org.waveprotocol.wave.client.render.WaveRenderer;
import org.waveprotocol.wave.client.state.ThreadReadStateMonitor;
import org.waveprotocol.wave.client.uibuilder.UiBuilder;
import org.waveprotocol.wave.client.wavepanel.render.ShallowBlipRenderer;
import org.waveprotocol.wave.client.wavepanel.view.ViewIdMapper;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.BlipQueueRenderer;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.DomRenderer;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.ViewFactory;
import org.waveprotocol.wave.model.conversation.Conversation;
import org.waveprotocol.wave.model.conversation.ConversationBlip;
import org.waveprotocol.wave.model.conversation.ConversationThread;
import org.waveprotocol.wave.model.conversation.ConversationView;
import org.waveprotocol.wave.model.util.IdentityMap;
import org.waveprotocol.wave.model.wave.ParticipantId;

import com.google.gwt.dom.client.Element;

/**
 * Renders waves into HTML DOM, given a renderer that renders waves as HTML
 * closures.
 *
 */
public final class KuneFullDomWaveRendererImpl implements DomRenderer {

  public static DomRenderer create(final ConversationView wave, final ProfileManager profileManager,
      final ShallowBlipRenderer shallowRenderer, final ViewIdMapper idMapper, final BlipQueueRenderer pager,
      final ThreadReadStateMonitor readMonitor, final ViewFactory views, final boolean showParticipantsPanel) {
    // Kune patch
    final KuneFullDomRenderer.DocRefRenderer docRenderer = new KuneFullDomRenderer.DocRefRenderer() {

      @Override
      public UiBuilder render(
        final ConversationBlip blip, final IdentityMap<ConversationThread, UiBuilder> replies) {
         // Documents are rendered blank, and filled in later when they get paged
          // in.
          pager.add(blip);
          return KuneFullDomRenderer.DocRefRenderer.EMPTY.render(blip, replies);
      }
    };
    // Kune patch
    final RenderingRules<UiBuilder> rules = new KuneFullDomRenderer(
        shallowRenderer, docRenderer, profileManager, idMapper, views, readMonitor, showParticipantsPanel);
    return new KuneFullDomWaveRendererImpl(ReductionBasedRenderer.of(rules, wave));
  }

  private final WaveRenderer<UiBuilder> driver;

  private KuneFullDomWaveRendererImpl(final WaveRenderer<UiBuilder> driver) {
    this.driver = driver;
  }

  //
  // Temporary invokers. Anti-parser API will remove these methods.
  //

  /** Turns a UiBuilder rendering into a DOM element. */
  private Element parseHtml(final UiBuilder ui) {
    if (ui == null) {
      return null;
    }
    final SafeHtmlBuilder html = new SafeHtmlBuilder();
    ui.outputHtml(html);
    final Element div = com.google.gwt.dom.client.Document.get().createDivElement();
    div.setInnerHTML(html.toSafeHtml().asString());
    final Element ret = div.getFirstChildElement();
    // Detach, in order that this element looks free-floating (required by some
    // preconditions).
    ret.removeFromParent();
    return ret;
  }

  @Override
  public Element render(final Conversation conversation) {
    return parseHtml(driver.render(conversation));
  }

  @Override
  public Element render(final Conversation conversation, final ParticipantId participant) {
    return parseHtml(driver.render(conversation, participant));
  }

  @Override
  public Element render(final ConversationBlip blip) {
    return parseHtml(driver.render(blip));
  }

  @Override
  public Element render(final ConversationThread thread) {
    return parseHtml(driver.render(thread));
  }

  @Override
  public Element render(final ConversationView wave) {
    return parseHtml(driver.render(wave));
  }
}
