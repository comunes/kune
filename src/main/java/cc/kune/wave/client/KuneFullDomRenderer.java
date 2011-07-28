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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.waveprotocol.wave.client.account.Profile;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.common.safehtml.EscapeUtils;
import org.waveprotocol.wave.client.common.safehtml.SafeHtmlBuilder;
import org.waveprotocol.wave.client.render.RenderingRules;
import org.waveprotocol.wave.client.state.ThreadReadStateMonitor;
import org.waveprotocol.wave.client.uibuilder.HtmlClosure;
import org.waveprotocol.wave.client.uibuilder.HtmlClosureCollection;
import org.waveprotocol.wave.client.uibuilder.UiBuilder;
import org.waveprotocol.wave.client.wavepanel.render.ShallowBlipRenderer;
import org.waveprotocol.wave.client.wavepanel.view.ViewIdMapper;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.AnchorViewBuilder;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.BlipMetaViewBuilder;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.BlipViewBuilder;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.ContinuationIndicatorViewBuilder;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.InlineThreadViewBuilder;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.ParticipantAvatarViewBuilder;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.ParticipantsViewBuilder;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.ReplyBoxViewBuilder;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.RootThreadViewBuilder;
import org.waveprotocol.wave.client.wavepanel.view.dom.full.ViewFactory;
import org.waveprotocol.wave.model.conversation.Conversation;
import org.waveprotocol.wave.model.conversation.ConversationBlip;
import org.waveprotocol.wave.model.conversation.ConversationThread;
import org.waveprotocol.wave.model.conversation.ConversationView;
import org.waveprotocol.wave.model.util.CollectionUtils;
import org.waveprotocol.wave.model.util.IdentityMap;
import org.waveprotocol.wave.model.util.IdentityMap.ProcV;
import org.waveprotocol.wave.model.util.IdentityMap.Reduce;
import org.waveprotocol.wave.model.util.StringMap;
import org.waveprotocol.wave.model.wave.ParticipantId;


/**
 * Renders conversational objects with UiBuilders.
 *
 */
public final class KuneFullDomRenderer implements RenderingRules<UiBuilder> {

  public interface DocRefRenderer {
    DocRefRenderer EMPTY = new DocRefRenderer() {
      @Override
      public UiBuilder render(final ConversationBlip blip,
          final IdentityMap<ConversationThread, UiBuilder> replies) {
        return UiBuilder.Constant.of(EscapeUtils.fromSafeConstant("<div></div>"));
      }
    };

    UiBuilder render(ConversationBlip blip,
        IdentityMap<ConversationThread, UiBuilder> replies);
  }

  public interface ParticipantsRenderer {
    ParticipantsRenderer EMPTY = new ParticipantsRenderer() {
      @Override
      public UiBuilder render(final Conversation c) {
        return UiBuilder.Constant.of(EscapeUtils.fromSafeConstant("<div></div>"));
      }
    };

    UiBuilder render(Conversation c);
  }

  private final ShallowBlipRenderer blipPopulator;
  private final DocRefRenderer docRenderer;
  private final ProfileManager profileManager;
  private final ThreadReadStateMonitor readMonitor;
  private final ViewFactory viewFactory;
  private final ViewIdMapper viewIdMapper;
  private final boolean showParticipantsPanel;

  public KuneFullDomRenderer(final ShallowBlipRenderer blipPopulator, final DocRefRenderer docRenderer,
      final ProfileManager profileManager, final ViewIdMapper viewIdMapper, final ViewFactory viewFactory,
      final ThreadReadStateMonitor readMonitor, boolean showParticipantsPanel) {
    this.blipPopulator = blipPopulator;
    this.docRenderer = docRenderer;
    this.profileManager = profileManager;
    this.viewIdMapper = viewIdMapper;
    this.viewFactory = viewFactory;
    this.readMonitor = readMonitor;
    this.showParticipantsPanel = showParticipantsPanel;
  }

  public UiBuilder getFirstConversation(final IdentityMap<Conversation, UiBuilder> conversations) {
    return conversations.reduce(null, new Reduce<Conversation, UiBuilder, UiBuilder>() {
      @Override
      public UiBuilder apply(final UiBuilder soFar, final Conversation key, final UiBuilder item) {
        // Pick the first rendering (any will do).
        return soFar == null ? item : soFar;
      }
    });
  }

  @Override
  public UiBuilder render(final Conversation conversation, final ParticipantId participant) {
    final Profile profile = profileManager.getProfile(participant);
    final String id = viewIdMapper.participantOf(conversation, participant);
    // Use ParticipantAvatarViewBuilder for avatars.
    // Kune patch
    // final ParticipantNameViewBuilder participantUi = ParticipantNameViewBuilder.create(id);
    final ParticipantAvatarViewBuilder participantUi = ParticipantAvatarViewBuilder.create(id);
    participantUi.setAvatar(profile.getImageUrl());
    participantUi.setName(profile.getFullName());
    return participantUi;
  }

  @Override
  public UiBuilder render(final Conversation conversation, final StringMap<UiBuilder> participantUis) {
    final HtmlClosureCollection participantsUi = new HtmlClosureCollection();
    for (final ParticipantId participant : conversation.getParticipantIds()) {
      participantsUi.add(participantUis.get(participant.getAddress()));
    }
    final String id = viewIdMapper.participantsOf(conversation);
    // Kune patch (but not used)
    return showParticipantsPanel? ParticipantsViewBuilder.create(id, participantsUi): new UiBuilder() {
      @Override
      public void outputHtml(SafeHtmlBuilder arg0) {
        // Don't show nothing
      }};
  }

  @Override
  public UiBuilder render(final Conversation conversation, final UiBuilder participantsUi, final UiBuilder threadUi) {
    final String id = viewIdMapper.conversationOf(conversation);
    final boolean isTop = !conversation.hasAnchor();
    return isTop ? viewFactory.createTopConversationView(id, threadUi, participantsUi)
        : viewFactory.createInlineConversationView(id, threadUi, participantsUi);
  }

  /**
   */
  @Override
  public UiBuilder render(
      final ConversationBlip blip, final IdentityMap<ConversationThread, UiBuilder> replies) {
    return docRenderer.render(blip, replies);
  }

  @Override
  public UiBuilder render(final ConversationBlip blip, final UiBuilder document,
      final IdentityMap<ConversationThread, UiBuilder> anchorUis,
      final IdentityMap<Conversation, UiBuilder> nestedConversations) {
    final UiBuilder threadsUi = new UiBuilder() {
      @Override
      public void outputHtml(final SafeHtmlBuilder out) {
        for (final ConversationThread thread : blip.getReplyThreads()) {
          anchorUis.get(thread).outputHtml(out);
        }
      }
    };

    final UiBuilder convsUi = new UiBuilder() {
      @Override
      public void outputHtml(final SafeHtmlBuilder out) {
        // Order by conversation id. Ideally, the sort key would be creation
        // time, but that is not exposed in the conversation API.
        final List<Conversation> ordered = CollectionUtils.newArrayList();
        nestedConversations.each(new ProcV<Conversation, UiBuilder>() {
          @Override
          public void apply(final Conversation conv, final UiBuilder ui) {
            ordered.add(conv);
          }
        });
        Collections.sort(ordered, new Comparator<Conversation>() {
          @Override
          public int compare(final Conversation o1, final Conversation o2) {
            return o1.getId().compareTo(o2.getId());
          }
        });
        final List<UiBuilder> orderedUis = CollectionUtils.newArrayList();
        for (final Conversation conv : ordered) {
          nestedConversations.get(conv).outputHtml(out);
        }
      }
    };

    final BlipMetaViewBuilder metaUi = BlipMetaViewBuilder.create(viewIdMapper.metaOf(blip), document);
    blipPopulator.render(blip, metaUi);

    return BlipViewBuilder.create(viewIdMapper.blipOf(blip), metaUi, threadsUi, convsUi);
  }

  @Override
  public UiBuilder render(final ConversationThread thread,
      final IdentityMap<ConversationBlip, UiBuilder> blipUis) {
    final HtmlClosure blipsUi = new HtmlClosure() {
      @Override
      public void outputHtml(final SafeHtmlBuilder out) {
        for (final ConversationBlip blip : thread.getBlips()) {
          final UiBuilder blipUi = blipUis.get(blip);
          // Not all blips are rendered.
          if (blipUi != null) {
            blipUi.outputHtml(out);
          }
        }
      }
    };
    final String threadId = viewIdMapper.threadOf(thread);
    final String replyIndicatorId = viewIdMapper.replyIndicatorOf(thread);
    UiBuilder builder = null;
    if (thread.getConversation().getRootThread() == thread) {
      final ReplyBoxViewBuilder replyBoxBuilder =
          ReplyBoxViewBuilder.create(replyIndicatorId);
      builder = RootThreadViewBuilder.create(threadId, blipsUi, replyBoxBuilder);
    } else {
      final ContinuationIndicatorViewBuilder indicatorBuilder = ContinuationIndicatorViewBuilder.create(
          replyIndicatorId);
      final InlineThreadViewBuilder inlineBuilder =
          InlineThreadViewBuilder.create(threadId, blipsUi, indicatorBuilder);
      final int read = readMonitor.getReadCount(thread);
      final int unread = readMonitor.getUnreadCount(thread);
      inlineBuilder.setTotalBlipCount(read + unread);
      inlineBuilder.setUnreadBlipCount(unread);
      builder = inlineBuilder;
    }
    return builder;
  }

  @Override
  public UiBuilder render(final ConversationThread thread, final UiBuilder threadR) {
    final String id = EscapeUtils.htmlEscape(viewIdMapper.defaultAnchorOf(thread));
    return AnchorViewBuilder.create(id, threadR);
  }

  @Override
  public UiBuilder render(final ConversationView wave,
      final IdentityMap<Conversation, UiBuilder> conversations) {
    // return the first conversation in the view.
    // TODO(hearnden): select the 'best' conversation.
    return conversations.isEmpty() ? null : getFirstConversation(conversations);
  }
}
