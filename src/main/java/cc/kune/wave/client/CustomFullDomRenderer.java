// @formatter:off
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
import org.waveprotocol.wave.client.wavepanel.view.dom.full.ParticipantNameViewBuilder;
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

import cc.kune.common.client.utils.WindowUtils;
import cc.kune.core.client.state.SiteParameters;


// TODO: Auto-generated Javadoc
/**
 * Renders conversational objects with UiBuilders.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class CustomFullDomRenderer implements RenderingRules<UiBuilder> {

  /**
   * The Interface DocRefRenderer.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface DocRefRenderer {
    
    /**
     * Render.
     *
     * @param blip the blip
     * @param replies the replies
     * @return the ui builder
     */
    UiBuilder render(ConversationBlip blip,
        IdentityMap<ConversationThread, UiBuilder> replies);

    /** The empty. */
    DocRefRenderer EMPTY = new DocRefRenderer() {
      @Override
      public UiBuilder render(ConversationBlip blip,
          IdentityMap<ConversationThread, UiBuilder> replies) {
        return UiBuilder.Constant.of(EscapeUtils.fromSafeConstant("<div></div>"));
      }
    };
  }

  /**
   * The Interface ParticipantsRenderer.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface ParticipantsRenderer {
    
    /**
     * Render.
     *
     * @param c the c
     * @return the ui builder
     */
    UiBuilder render(Conversation c);

    /** The empty. */
    ParticipantsRenderer EMPTY = new ParticipantsRenderer() {
      @Override
      public UiBuilder render(Conversation c) {
        return UiBuilder.Constant.of(EscapeUtils.fromSafeConstant("<div></div>"));
      }
    };
  }

  /** The blip populator. */
  private final ShallowBlipRenderer blipPopulator;
  
  /** The doc renderer. */
  private final DocRefRenderer docRenderer;
  
  /** The view id mapper. */
  private final ViewIdMapper viewIdMapper;
  
  /** The view factory. */
  private final ViewFactory viewFactory;
  
  /** The profile manager. */
  private final ProfileManager profileManager;
  
  /** The read monitor. */
  private final ThreadReadStateMonitor readMonitor;
  
  /** The show participants panel. */
  private final boolean showParticipantsPanel;

  /**
   * Instantiates a new custom full dom renderer.
   *
   * @param blipPopulator the blip populator
   * @param docRenderer the doc renderer
   * @param profileManager the profile manager
   * @param viewIdMapper the view id mapper
   * @param viewFactory the view factory
   * @param readMonitor the read monitor
   * @param showParticipantsPanel the show participants panel
   */
  public CustomFullDomRenderer(ShallowBlipRenderer blipPopulator, DocRefRenderer docRenderer,
      ProfileManager profileManager, ViewIdMapper viewIdMapper, ViewFactory viewFactory,
      ThreadReadStateMonitor readMonitor, boolean showParticipantsPanel) {
    this.blipPopulator = blipPopulator;
    this.docRenderer = docRenderer;
    this.profileManager = profileManager;
    this.viewIdMapper = viewIdMapper;
    this.viewFactory = viewFactory;
    this.readMonitor = readMonitor;
    this.showParticipantsPanel = showParticipantsPanel;
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.render.RenderingRules#render(org.waveprotocol.wave.model.conversation.ConversationView, org.waveprotocol.wave.model.util.IdentityMap)
   */
  @Override
  public UiBuilder render(ConversationView wave,
      IdentityMap<Conversation, UiBuilder> conversations) {
    // return the first conversation in the view.
    // TODO(hearnden): select the 'best' conversation.
    return conversations.isEmpty() ? null : getFirstConversation(conversations);
  }

  /**
   * Gets the first conversation.
   *
   * @param conversations the conversations
   * @return the first conversation
   */
  public UiBuilder getFirstConversation(IdentityMap<Conversation, UiBuilder> conversations) {
    return conversations.reduce(null, new Reduce<Conversation, UiBuilder, UiBuilder>() {
      @Override
      public UiBuilder apply(UiBuilder soFar, Conversation key, UiBuilder item) {
        // Pick the first rendering (any will do).
        return soFar == null ? item : soFar;
      }
    });
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.render.RenderingRules#render(org.waveprotocol.wave.model.conversation.Conversation, java.lang.Object, java.lang.Object)
   */
  @Override
  public UiBuilder render(Conversation conversation, UiBuilder participantsUi, UiBuilder threadUi) {
    String id = viewIdMapper.conversationOf(conversation);
    boolean isTop = !conversation.hasAnchor();
    return isTop ? viewFactory.createTopConversationView(id, threadUi, participantsUi)
        : viewFactory.createInlineConversationView(id, threadUi, participantsUi);
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.render.RenderingRules#render(org.waveprotocol.wave.model.conversation.Conversation, org.waveprotocol.wave.model.util.StringMap)
   */
  @Override
  public UiBuilder render(Conversation conversation, StringMap<UiBuilder> participantUis) {
    HtmlClosureCollection participantsUi = new HtmlClosureCollection();
    for (ParticipantId participant : conversation.getParticipantIds()) {
      participantsUi.add(participantUis.get(participant.getAddress()));
    }
    String id = viewIdMapper.participantsOf(conversation);
    // Kune patch (but not used)
    return showParticipantsPanel? ParticipantsViewBuilder.create(id, participantsUi): new UiBuilder() {
      @Override
      public void outputHtml(SafeHtmlBuilder arg0) {
        // Don't show nothing
      }};
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.render.RenderingRules#render(org.waveprotocol.wave.model.conversation.Conversation, org.waveprotocol.wave.model.wave.ParticipantId)
   */
  @Override
  public UiBuilder render(Conversation conversation, ParticipantId participant) {
    Profile profile = profileManager.getProfile(participant);
    String id = viewIdMapper.participantOf(conversation, participant);
    // Use ParticipantAvatarViewBuilder for avatars.

    if (WindowUtils.getParameter(SiteParameters.WAVE_AVATARS_DISABLED) != null) {
      ParticipantNameViewBuilder participantUi = ParticipantNameViewBuilder.create(id);
      participantUi.setAvatar(profile.getImageUrl());
      participantUi.setName(profile.getFullName());
      return participantUi;
    } else {
      ParticipantAvatarViewBuilder participantUi = ParticipantAvatarViewBuilder.create(id);
      participantUi.setAvatar(profile.getImageUrl());
      participantUi.setName(profile.getFullName());
      return participantUi;
    }
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.render.RenderingRules#render(org.waveprotocol.wave.model.conversation.ConversationThread, org.waveprotocol.wave.model.util.IdentityMap)
   */
  @Override
  public UiBuilder render(final ConversationThread thread,
      final IdentityMap<ConversationBlip, UiBuilder> blipUis) {
    HtmlClosure blipsUi = new HtmlClosure() {
      @Override
      public void outputHtml(SafeHtmlBuilder out) {
        for (ConversationBlip blip : thread.getBlips()) {
          UiBuilder blipUi = blipUis.get(blip);
          // Not all blips are rendered.
          if (blipUi != null) {
            blipUi.outputHtml(out);
          }
        }
      }
    };
    String threadId = viewIdMapper.threadOf(thread);
    String replyIndicatorId = viewIdMapper.replyIndicatorOf(thread);
    UiBuilder builder = null;
    if (thread.getConversation().getRootThread() == thread) {
      ReplyBoxViewBuilder replyBoxBuilder =
          ReplyBoxViewBuilder.create(replyIndicatorId);
      builder = RootThreadViewBuilder.create(threadId, blipsUi, replyBoxBuilder);
    } else {
      ContinuationIndicatorViewBuilder indicatorBuilder = ContinuationIndicatorViewBuilder.create(
          replyIndicatorId);
      InlineThreadViewBuilder inlineBuilder =
          InlineThreadViewBuilder.create(threadId, blipsUi, indicatorBuilder);
      int read = readMonitor.getReadCount(thread);
      int unread = readMonitor.getUnreadCount(thread);
      inlineBuilder.setTotalBlipCount(read + unread);
      inlineBuilder.setUnreadBlipCount(unread);
      builder = inlineBuilder;
    }
    return builder;
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.render.RenderingRules#render(org.waveprotocol.wave.model.conversation.ConversationBlip, java.lang.Object, org.waveprotocol.wave.model.util.IdentityMap, org.waveprotocol.wave.model.util.IdentityMap)
   */
  @Override
  public UiBuilder render(final ConversationBlip blip, UiBuilder document,
      final IdentityMap<ConversationThread, UiBuilder> anchorUis,
      final IdentityMap<Conversation, UiBuilder> nestedConversations) {
    UiBuilder threadsUi = new UiBuilder() {
      @Override
      public void outputHtml(SafeHtmlBuilder out) {
        for (ConversationThread thread : blip.getReplyThreads()) {
          anchorUis.get(thread).outputHtml(out);
        }
      }
    };

    UiBuilder convsUi = new UiBuilder() {
      @Override
      public void outputHtml(SafeHtmlBuilder out) {
        // Order by conversation id. Ideally, the sort key would be creation
        // time, but that is not exposed in the conversation API.
        final List<Conversation> ordered = CollectionUtils.newArrayList();
        nestedConversations.each(new ProcV<Conversation, UiBuilder>() {
          @Override
          public void apply(Conversation conv, UiBuilder ui) {
            ordered.add(conv);
          }
        });
        Collections.sort(ordered, new Comparator<Conversation>() {
          @Override
          public int compare(Conversation o1, Conversation o2) {
            return o1.getId().compareTo(o2.getId());
          }
        });
        // List<UiBuilder> orderedUis = CollectionUtils.newArrayList();
        for (Conversation conv : ordered) {
          nestedConversations.get(conv).outputHtml(out);
        }
      }
    };

    BlipMetaViewBuilder metaUi = BlipMetaViewBuilder.create(viewIdMapper.metaOf(blip), document);
    if (blip.isRoot())
      metaUi.disable(BlipMetaViewBuilder.DELETE_MENU_OPTIONS_SET);
    blipPopulator.render(blip, metaUi);

    return BlipViewBuilder.create(viewIdMapper.blipOf(blip), metaUi, threadsUi, convsUi, blip.isRoot());
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.render.RenderingRules#render(org.waveprotocol.wave.model.conversation.ConversationBlip, org.waveprotocol.wave.model.util.IdentityMap)
   */
  @Override
  public UiBuilder render(
      ConversationBlip blip, IdentityMap<ConversationThread, UiBuilder> replies) {
    return docRenderer.render(blip, replies);
  }

  /* (non-Javadoc)
   * @see org.waveprotocol.wave.client.render.RenderingRules#render(org.waveprotocol.wave.model.conversation.ConversationThread, java.lang.Object)
   */
  @Override
  public UiBuilder render(ConversationThread thread, UiBuilder threadR) {
    String id = EscapeUtils.htmlEscape(viewIdMapper.defaultAnchorOf(thread));
    return AnchorViewBuilder.create(id, threadR);
  }
}
