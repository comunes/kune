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
package com.google.wave.splash.web.template;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.waveprotocol.box.server.CoreSettings;

import cc.kune.common.shared.utils.Url;
import cc.kune.common.shared.utils.UrlParam;
import cc.kune.core.shared.FileConstants;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.wave.api.Blip;
import com.google.wave.api.BlipThread;
import com.google.wave.api.Element;
import com.google.wave.api.ParticipantProfile;
import com.google.wave.api.Wavelet;
import com.google.wave.splash.rpc.ClientAction;
import com.google.wave.splash.text.ContentRenderer;
import com.google.wave.splash.text.Markup;

// TODO: Auto-generated Javadoc
/**
 * Does the actual conversion of a wavelet/blipdata tree into html, using the
 * conversation-thread model, optionally falling back to the blip-hierarchy
 * model, if so configured.
 * 
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */
@Singleton
class ThreadedWaveRenderer implements WaveRenderer {
  
  /**
   * The Class PageTracker.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  private class PageTracker {
    
    /** The counter. */
    private int counter;
    // Whether or not we're trying to deliver the first page.
    /** The first page. */
    private final boolean firstPage;
    
    /** The markers. */
    private final List<Integer> markers = Lists.newArrayList();

    /**
     * This is an alternate output string which will wrap all html content that
     * is not immediately displayed. This is useful for inline replies that need
     * to be moved in and out of the appropriate part of the DOM.
     */
    private final StringBuilder purgatory = new StringBuilder();

    // The wavelet we're trying to render in this page.
    /** The wavelet. */
    private final Wavelet wavelet;

    /**
     * Instantiates a new page tracker.
     *
     * @param page the page
     * @param wavelet the wavelet
     */
    public PageTracker(final int page, final Wavelet wavelet) {
      this.wavelet = wavelet;
      firstPage = (page == 0);

      // Start purgatory (will be ended by #render)
      purgatory.append("<div id=\"purgatory\">");
    }

    /**
     * Checks for pages.
     *
     * @return true, if successful
     */
    public boolean hasPages() {
      return !markers.isEmpty();
    }

    /**
     * Marker.
     *
     * @param page the page
     * @return the int
     */
    public int marker(final int page) {
      return markers.get(page);
    }

    /**
     * Purgatory element.
     *
     * @return the string
     */
    public String purgatoryElement() {
      return purgatory.append("</div>").toString();
    }

    /**
     * Returns true if a page boundary was crossed.
     *
     * @param builder the builder
     * @return true, if successful
     */
    public boolean track(final StringBuilder builder) {
      final int length = builder.length();
      if (length - counter >= charsPerPage) {
        markers.add(length);
        counter = length;

        // If this is the first page, short circuit the rendering
        // process so we can deliver the page faster.
        if (firstPage) {
          return true;
        }
      }
      return false;
    }
  }

  /** The Constant HIDDEN_PARTICIPANTS. */
  private static final Set<String> HIDDEN_PARTICIPANTS = ImmutableSet.of("public");

  // TODO(dhanji): This is expensive, see if we can precompute branch size
  // when constructing the thread tree.
  /**
   * Size of thread tree.
   *
   * @param inlineReplyThread the inline reply thread
   * @return the int
   */
  private static int sizeOfThreadTree(final BlipThread inlineReplyThread) {
    final List<Blip> blips = inlineReplyThread.getBlips();
    int size = blips.size();

    for (final Blip blip : blips) {
      for (final BlipThread thread : blip.getReplyThreads()) {
        size += thread.getBlipIds().size();
      }
    }

    return size;
  }
  
  /** The chars per page. */
  private final int charsPerPage;
  // Ugly, but we do this to avoid polluting all the rendering methods. =(
  /** The current page. */
  private final ThreadLocal<PageTracker> currentPage = new ThreadLocal<PageTracker>();
  
  /** The domain. */
  private final String domain;

  /** The is read only. */
  private final boolean isReadOnly;

  /** The renderer. */
  private final ContentRenderer renderer;
  
  /** The templates. */
  private final Templates templates;

  /**
   * Instantiates a new threaded wave renderer.
   *
   * @param templates the templates
   * @param renderer the renderer
   * @param domain the domain
   */
  @Inject
  public ThreadedWaveRenderer(final Templates templates, final ContentRenderer renderer,
      @Named(CoreSettings.WAVE_SERVER_DOMAIN) final String domain) {
    this.templates = templates;
    this.domain = domain;
    // vjrj: manual setted
    this.isReadOnly = true;
    this.charsPerPage = 100000;
    this.renderer = renderer;
  }

  /**
   * Gets the avatar url.
   *
   * @param address the address
   * @return the avatar url
   */
  private String getAvatarUrl(final String address) {
    String avatar = "";
    if (address.contains(domain)) {
      avatar = new Url(FileConstants.LOGODOWNLOADSERVLET, new UrlParam(FileConstants.TOKEN,
          address.split("@")[0]), new UrlParam(FileConstants.ONLY_USERS, true)).toString();
    } else {
      avatar = FileConstants.PERSON_NO_AVATAR_IMAGE;
    }
    return avatar;
  }

  /**
   * Gets the profiles.
   *
   * @param participants the participants
   * @return the profiles
   */
  private Map<String, ParticipantProfile> getProfiles(final Collection<String> participants) {
    final HashMap<String, ParticipantProfile> profiles = new HashMap<String, ParticipantProfile>();
    for (final String address : participants) {
      final ParticipantProfile profile = new ParticipantProfile(address, address.split("@")[0],
          getAvatarUrl(address), "");
      profiles.put(address, profile);
    }

    return profiles;
  }

  /**
   * Load profiles.
   *
   * @param participants the participants
   * @return the list
   */
  List<ParticipantProfile> loadProfiles(final Collection<String> participants) {
    final ImmutableList.Builder<ParticipantProfile> result = ImmutableList.builder();
    final Map<String, ParticipantProfile> profiles = getProfiles(participants);
    for (final String address : participants) {
      result.add(profiles.get(address));
    }
    return result.build();
  }

  /**
   * Render.
   *
   * @param wavelet A wavelet to render as a single html blob.
   * @param page The page number to send back. Use this to implement paging, if you
   * specify page 1, the client action will only contain the second
   * page as computed during the current render.
   * @return the client action.
   */
  @Override
  public ClientAction render(final Wavelet wavelet, final int page) {
    Preconditions.checkState(null == currentPage.get(),
        "A page render is already in progress (this is an algorithm bug)");
    final StringBuilder builder = new StringBuilder();
    final Blip rootBlip = wavelet.getRootBlip();

    // The pagetracker tracks every page worth of HTML rendered.
    final PageTracker pageTracker = new PageTracker(page, wavelet);
    currentPage.set(pageTracker);
    try {
      return renderInternal(wavelet, page, builder, rootBlip, pageTracker);
    } finally {
      currentPage.remove();
    }
  }

  /**
   * Render blip.
   *
   * @param blip the blip
   * @param builder the builder
   * @param title the title
   * @param pageTracker the page tracker
   * @return true, if successful
   */
  private boolean renderBlip(final Blip blip, final StringBuilder builder, final String title,
      final PageTracker pageTracker) {
    builder.append("<div class='blip' id='");
    builder.append(Markup.toDomId(blip.getBlipId()));
    builder.append("'>");
    builder.append(toHtml(blip, title));
    builder.append("</div>");

    // At the end of each blip, see if we've passed a page worth of content.
    return pageTracker.track(builder);
  }

  /**
   * Render blip template.
   *
   * @param blip the blip
   * @return the string
   */
  String renderBlipTemplate(final Map<String, Object> blip) {
    return templates.process(Templates.BLIP_TEMPLATE, blip);
  }

  /**
   * Render content.
   *
   * @param blip the blip
   * @return the string
   */
  private String renderContent(final Blip blip) {
    return renderer.renderHtml(blip.getContent(), blip.getAnnotations(), blip.getElements(),
        blip.getContributors());
  }

  /**
   * Renders the header of a wavelet, including participants.
   * 
   * @param profiles
   *          A list of profiles for each participant in the wave, in correct
   *          order.
   * @return A {@code ClientAction} that inserts the rendered participant list
   *         in the header portion of the DOM.
   */
  @Override
  public ClientAction renderHeader(final List<ParticipantProfile> profiles) {
    final Map<String, Object> context = Maps.newHashMap();
    final int max = Math.min(10, profiles.size());

    final List<ParticipantProfile> renderedParticipants = Lists.newArrayListWithExpectedSize(max);
    for (final ParticipantProfile p : profiles) {
      // TODO: For some reason the address is an empty string, so
      // we use name here instead.
      if (HIDDEN_PARTICIPANTS.contains(p.getName())) {
        continue;
      }
      if (renderedParticipants.size() >= max) {
        break;
      }
      renderedParticipants.add(p);
    }

    context.put("participants", renderedParticipants);
    return new ClientAction("update-header").html(templates.process(Templates.HEADER_TEMPLATE, context));
  }

  /**
   * Renders an inline reply thread at the correct offset location inside a
   * blip.
   *
   * @param element The element representing the position of the offset inline reply
   * @param index the index
   * @param builder The current HTML content StringBuilder of the wave so far
   */
  @Override
  public void renderInlineReply(final Element element, final int index, final StringBuilder builder) {

    final PageTracker pageTracker = currentPage.get();
    final BlipThread inlineReplyThread = pageTracker.wavelet.getThread(element.getProperty("id"));

    // inlineReplyThread can be null if a sub-thread was completely deleted.
    // There's still
    // an entry left behind in the conversation that points to nothing.
    if (inlineReplyThread != null && !inlineReplyThread.getBlipIds().isEmpty()) {
      builder.append(" <span class=\"inline-reply\" ir-id=\"");
      builder.append(Markup.toDomId(inlineReplyThread.getId()));
      builder.append("\"><span class=\"count\" title=\"Click to expand inline replies\"><span class=\"count-inner\">");
      builder.append(sizeOfThreadTree(inlineReplyThread));
      builder.append("</span><span class=\"pointer\"></span></span> ");

      // Render this thread into purgatory, it will be transferred to the
      // appropriate
      // spot by the JS code. This is needed to prevent the browser from trying
      // to pre-emptively "correct" our dom structure (and thus ruin it).
      pageTracker.purgatory.append("<div class=\"inline-reply-content\" id=\"ir-");
      pageTracker.purgatory.append(Markup.toDomId(inlineReplyThread.getId()));
      pageTracker.purgatory.append("\"><div class=\"inline-reply-content-inner\">");
      renderThreads(inlineReplyThread, pageTracker.purgatory, pageTracker);
      pageTracker.purgatory.append("</div></div>");

      builder.append("</span>"); // Close inline-reply
    }
  }

  /**
   * Render internal.
   *
   * @param wavelet the wavelet
   * @param page the page
   * @param builder the builder
   * @param rootBlip the root blip
   * @param pageTracker the page tracker
   * @return the client action
   */
  private ClientAction renderInternal(final Wavelet wavelet, final int page,
      final StringBuilder builder, final Blip rootBlip, final PageTracker pageTracker) {
    final boolean stopRender = renderThreads(wavelet.getRootThread(), builder, pageTracker);
    String html;
    if (page != ALL_PAGES && (stopRender || pageTracker.hasPages())) {
      if (page == 0) {
        builder.append("<img id=\"wave-loading\" src=\"images/wave-loading.gif\">");
        html = builder.toString();
      } else {
        // If this is a request for the rest of the wave, start from end of page
        // 0 and get
        // the rest.
        final int start = pageTracker.marker(0);

        // Append purgatory--which contains all inline reply threads
        builder.append(pageTracker.purgatoryElement());

        html = builder.substring(start);
      }
    } else {
      // Append purgatory--which contains all inline reply threads
      builder.append(pageTracker.purgatoryElement());

      html = builder.toString();
    }

    return new ClientAction("update-wave").version(wavelet.getLastModifiedTime()).html(html);
  }

  /* (non-Javadoc)
   * @see com.google.wave.splash.web.template.WaveRenderer#renderNotFound()
   */
  @Override
  public ClientAction renderNotFound() {
    // TODO: This should be an alert message once that system is
    // implemented.
    return new ClientAction("update-wave").html(templates.process(Templates.WAVE_NOT_FOUND_TEMPLATE,
        ImmutableMap.of()));
  }

  /**
   * This method renders the blip thread hierarchy using the new conversation
   * structure.
   *
   * @param thread the thread
   * @param builder the builder
   * @param pageTracker the page tracker
   * @return true if we should stop rendering because a page boundary was
   * reached.
   */
  boolean renderThreads(final BlipThread thread, final StringBuilder builder,
      final PageTracker pageTracker) {
    builder.append("<div class=\"thread\" id=\"");
    builder.append(Markup.toDomId(thread.getId()));
    builder.append("\">");
    final List<Blip> blipsInThread = thread.getBlips();

    for (final Blip blip : blipsInThread) {
      if (renderBlip(blip, builder, "", pageTracker)) {
        return true;
      }

      // If this blip has any reply threads, they should be rendered indented.
      if (blip.getReplyThreads().size() > 0) {
        builder.append("<div class=\"indent\">");

        for (final BlipThread childThread : blip.getReplyThreads()) {
          if (renderThreads(childThread, builder, pageTracker)) {
            return true;
          }
        }

        builder.append("</div>");
      }
    }
    builder.append("</div>");
    return false;
  }

  /**
   * Renders the content of a blip as html. If a title is specified, renders
   * that specially as the root blip.
   * 
   * @param blipData
   *          The blip whose content you want to render
   * @param title
   *          The title string if this is a root blip or null
   * @return Rendered HTML string with markup
   */
  @Override
  public String toHtml(final Blip blipData, final String title) {
    final List<String> contributors = blipData.getContributors();
    final List<ParticipantProfile> authors = loadProfiles(contributors);

    final Map<String, Object> blip = Maps.newHashMap();
    blip.put("id", Markup.toDomId(blipData.getBlipId()));

    final StringBuilder authorString = new StringBuilder();
    final int numberOfAuthors = authors.size();
    final int len = Math.min(3, numberOfAuthors);
    if (blipData.isRoot()) {
      blip.put("authorCountClass", "author-count-root");
    } else {
      for (int i = 0; i < len; i++) {
        authorString.append("<div class='authorbar ");
        if (i == 0) {
          authorString.append("first");
        }
        authorString.append("'><div class=\"avatar\"><img src=\"");
        final ParticipantProfile author = authors.get(i);
        authorString.append(author.getImageUrl());
        authorString.append("\" alt=\"");

        final String name = author.getName();
        authorString.append(name);
        authorString.append("\"><span class=\"name\" title=\"");
        authorString.append(name);
        authorString.append("\">");
        authorString.append(name);
        authorString.append("</span></div></div>");
      }

      if (numberOfAuthors > 3) {
        authorString.append("<div class=\"authorbar\">");
        authorString.append("<div class=\"author-more\">+");
        authorString.append(numberOfAuthors);
        authorString.append(" others</div>");
        authorString.append("</div>");

        blip.put("authorCountClass", "author-count-many");
      } else {
        blip.put("authorCountClass", "author-count-" + contributors.size());
      }
    }

    blip.put("authorString", authorString.toString());
    blip.put("time", Markup.formatDateTime(blipData.getLastModifiedTime()));
    blip.put("title", Markup.sanitize(title));
    blip.put("content", renderContent(blipData));
    blip.put("readonly", isReadOnly);

    return renderBlipTemplate(blip);
  }
}
