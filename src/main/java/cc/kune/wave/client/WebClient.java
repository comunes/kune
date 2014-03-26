// @formatter:off
/**
 * Copyright 2010, 2012 Google Inc.
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
 *
 */

package cc.kune.wave.client;


import java.util.Date;
import java.util.Set;
import java.util.logging.Logger;

import org.waveprotocol.box.webclient.client.ClientIdGenerator;
import org.waveprotocol.box.webclient.client.DebugMessagePanel;
import org.waveprotocol.box.webclient.client.HistoryProvider;
import org.waveprotocol.box.webclient.client.HistorySupport;
import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.Session;
import org.waveprotocol.box.webclient.client.SimpleWaveStore;
import org.waveprotocol.box.webclient.client.WaveWebSocketClient;
import org.waveprotocol.box.webclient.search.RemoteSearchService;
import org.waveprotocol.box.webclient.search.SearchPanelRenderer;
import org.waveprotocol.box.webclient.search.SearchPanelWidget;
import org.waveprotocol.box.webclient.search.SearchPresenter;
import org.waveprotocol.box.webclient.search.SimpleSearch;
import org.waveprotocol.box.webclient.search.WaveStore;
import org.waveprotocol.box.webclient.widget.frame.FramedPanel;
import org.waveprotocol.box.webclient.widget.loading.LoadingIndicator;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.common.safehtml.SafeHtml;
import org.waveprotocol.wave.client.common.safehtml.SafeHtmlBuilder;
import org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor;
import org.waveprotocol.wave.client.debug.logger.LogLevel;
import org.waveprotocol.wave.client.doodad.attachment.AttachmentManagerImpl;
import org.waveprotocol.wave.client.doodad.attachment.AttachmentManagerProvider;
import org.waveprotocol.wave.client.events.ClientEvents;
import org.waveprotocol.wave.client.events.Log;
import org.waveprotocol.wave.client.events.NetworkStatusEvent;
import org.waveprotocol.wave.client.events.NetworkStatusEventHandler;
import org.waveprotocol.wave.client.events.WaveCreationEvent;
import org.waveprotocol.wave.client.events.WaveCreationEventHandler;
import org.waveprotocol.wave.client.events.WaveSelectionEvent;
import org.waveprotocol.wave.client.events.WaveSelectionEventHandler;
import org.waveprotocol.wave.client.widget.common.ImplPanel;
import org.waveprotocol.wave.client.widget.popup.CenterPopupPositioner;
import org.waveprotocol.wave.client.widget.popup.PopupChrome;
import org.waveprotocol.wave.client.widget.popup.PopupChromeFactory;
import org.waveprotocol.wave.client.widget.popup.PopupFactory;
import org.waveprotocol.wave.client.widget.popup.UniversalPopup;
import org.waveprotocol.wave.model.id.IdGenerator;
import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.model.waveref.WaveRef;
import org.waveprotocol.wave.util.escapers.GwtWaverefEncoder;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.events.StackErrorEvent;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceConfEvent;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.core.client.state.impl.HistoryUtils;
import cc.kune.initials.InitialsResources;
import cc.kune.wave.client.kspecific.AfterOpenWaveEvent;
import cc.kune.wave.client.kspecific.AurorisColorPicker;
import cc.kune.wave.client.kspecific.BeforeOpenWaveEvent;
import cc.kune.wave.client.kspecific.OnWaveClientStartEvent;
import cc.kune.wave.client.kspecific.WaveClientClearEvent;
import cc.kune.wave.client.kspecific.WaveClientUtils;
import cc.kune.wave.client.kspecific.WaveClientView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 *
 */
@Singleton
public class WebClient extends Composite implements WaveClientView {

  /**
   * The Interface Binder.
   *   */
  interface Binder extends UiBinder<DockLayoutPanel, WebClient> {
  }

  /**
   * An exception handler that reports exceptions using a <em>shiny banner</em>
   * (an alert placed on the top of the screen). Once the stack trace is
   * prepared, it is revealed in the banner via a link.
   *
   */
  public static class ErrorHandler implements UncaughtExceptionHandler {

    /**
     * Gets the stack trace async.
     *
     * @param t the t
     * @param whenReady the when ready
     * @return the stack trace async
     */
    public static void getStackTraceAsync(final Throwable t, final Accessor<SafeHtml> whenReady) {
      // TODO: Request stack-trace de-obfuscation. For now, just use the
      // javascript stack trace.
      //
      // Use minimal services here, in order to avoid the chance that reporting
      // the error produces more errors. In particular, do not use WIAB's
      // scheduler to run this command.
      // Also, this code could potentially be put behind a runAsync boundary, to
      // save whatever dependencies it uses from the initial download.
      new Timer() {
        @Override
        public void run() {
          final SafeHtmlBuilder stack = new SafeHtmlBuilder();

          Throwable error = t;
          while (error != null) {
            final String token = String.valueOf((new Date()).getTime());
            stack.appendHtmlConstant("Token:  " + token + "<br> ");
            stack.appendEscaped(String.valueOf(error.getMessage())).appendHtmlConstant("<br>");
            for (final StackTraceElement elt : error.getStackTrace()) {
              stack.appendHtmlConstant("  ")
                  .appendEscaped(maybe(elt.getClassName(), "??")).appendHtmlConstant(".") //
                  .appendEscaped(maybe(elt.getMethodName(), "??")).appendHtmlConstant(" (") //
                  .appendEscaped(maybe(elt.getFileName(), "??")).appendHtmlConstant(":") //
                  .appendEscaped(maybe(elt.getLineNumber(), "??")).appendHtmlConstant(")") //
                  .appendHtmlConstant("<br>");
            }
            error = error.getCause();
            if (error != null) {
              stack.appendHtmlConstant("Caused by: ");
            }
          }

          whenReady.use(stack.toSafeHtml());
        }
      }.schedule(1);
    }

    /**
     * Install.
     */
    public static void install() {
    GWT.setUncaughtExceptionHandler(new ErrorHandler(GWT.getUncaughtExceptionHandler()));
    }

    /**
     * Maybe.
     *
     * @param value the value
     * @param otherwise the otherwise
     * @return the string
     */
    private static String maybe(final int value, final String otherwise) {
      return value != -1 ? String.valueOf(value) : otherwise;
    }

    /**
     * Maybe.
     *
     * @param value the value
     * @param otherwise the otherwise
     * @return the string
     */
    private static String maybe(final String value, final String otherwise) {
      return value != null ? value : otherwise;
    }

    /**
     * Indicates whether an error has already been reported (at most one error
     * is ever reported by this handler).
     */
    private boolean hasFired;

    /** Next handler in the handler chain. */
    private final UncaughtExceptionHandler next;

    /**
     * Instantiates a new error handler.
     *
     * @param next the next
     */
    private ErrorHandler(final UncaughtExceptionHandler next) {
      this.next = next;
    }

    /* (non-Javadoc)
     * @see com.google.gwt.core.client.GWT.UncaughtExceptionHandler#onUncaughtException(java.lang.Throwable)
     */
    @Override
    public void onUncaughtException(final Throwable e) {
      if (!hasFired) {
        hasFired = true;
      //  final ErrorIndicatorPresenter error =
        //    ErrorIndicatorPresenter.create(RootPanel.get("banner"));
        getStackTraceAsync(e, new Accessor<SafeHtml>() {
          @Override
          public void use(final SafeHtml stack) {
          //  error.addDetail(stack, null);
            // REMOTE_LOG.severe(stack.asString().replace("<br>", "\n"));
            final String message = stack.asString().replace("<br>", "\n");
            NotifyUser.logError(message);
            NotifyUser.showProgress("Error");
            REMOTE_LOG.severe(message);
            new Timer() {
              @Override
              public void run() {
                NotifyUser.hideProgress();
              }}.schedule(5000);
          }
        });
      }
      if (next != null) {
        next.onUncaughtException(e);
      }
    }
  }

  /**
   * The Interface Style.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface Style extends CssResource {
  }

  /** The Constant BINDER. */
  private static final Binder BINDER = GWT.create(Binder.class);

  /** The log. */
  static Log LOG = Log.get(WebClient.class);

  // Use of GWT logging is only intended for sending exception reports to the
  // server, nothing else in the client should use java.util.logging.
  // Please also see WebClientDemo.gwt.xml.
  /** The Constant REMOTE_LOG. */
  private static final Logger REMOTE_LOG = Logger.getLogger("REMOTE_LOG");

  /**
   * Creates a popup that warns about network disconnects.
   *
   * @return the universal popup
   */
  private static UniversalPopup createTurbulencePopup() {
    final PopupChrome chrome = PopupChromeFactory.createPopupChrome();
    final UniversalPopup popup =
        PopupFactory.createPopup(null, new CenterPopupPositioner(), chrome, true);
    popup.add(new HTML("<div style='color: red; padding: 5px; text-align: center;'>"
        + "<b>A turbulence detected!<br></br>"
        + " Please save your last changes to somewhere and reload the wave.</b></div>"));
    return popup;
  }

  /** The bottom bar. */
  @UiField
  SimplePanel bottomBar;


  /** The channel. */
  private RemoteViewServiceMultiplexer channel;

  /** The color picker. */
  private Provider<AurorisColorPicker> colorPicker;

  /** The content service. */
  private final ContentServiceAsync contentService;

  /** The event bus. */
  private final EventBus eventBus;

  /** The id generator. */
  private IdGenerator idGenerator;

  /** The kune session. */
  private final cc.kune.core.client.state.Session kuneSession;

  /** The loading. */
  private final Element loading = new LoadingIndicator().getElement();

  /** The logged in user. */
  private ParticipantId loggedInUser;

  /** The log panel. */
  @UiField
  DebugMessagePanel logPanel;

  /** The profiles. */
  private final ProfileManager profiles;

  /** The right panel. */
  @UiField
  DockLayoutPanel rightPanel;

  private SimpleSearch search;

  /** The search panel. */
  @UiField(provided = true)
  final SearchPanelWidget searchPanel;

  /** The split panel. */
  @UiField(provided=true)
  SplitLayoutPanel splitPanel;

  /** The style. */
  @UiField
  Style style;


  /** The turbulence popup. */
  private final UniversalPopup turbulencePopup = createTurbulencePopup();

  /** The wave panel, if a wave is open. */
  private CustomStagesProvider wave;

  /** The wave frame. */
  @UiField
  FramedPanel waveFrame;


  //FIXME UiField???
  /** The wave holder. */
  ImplPanel waveHolder;

  /** The wave store. */
  private final WaveStore waveStore = new SimpleWaveStore();

  /** The wave unsaved indicator. */
  private final CustomSavedStateIndicator waveUnsavedIndicator;

  /**
   * Create a remote websocket to talk to the server-side FedOne service.
   */
  private WaveWebSocketClient websocket;

  /**
   * This is the entry point method.
   *
   * @param eventBus the event bus
   * @param profiles the profiles
   * @param tokenMatcher the token matcher
   * @param kuneSession the kune session
   * @param waveUnsavedIndicator the wave unsaved indicator
   * @param contentService the content service
   * @param colorPicker the color picker
   */
  @Inject
  public WebClient(final EventBus eventBus, final KuneWaveProfileManager profiles,final cc.kune.core.client.state.Session kuneSession, final CustomSavedStateIndicator waveUnsavedIndicator, final ContentServiceAsync contentService, final Provider<AurorisColorPicker> colorPicker) {
    this.eventBus = eventBus;
    this.profiles = profiles;
    this.kuneSession = kuneSession;
    this.waveUnsavedIndicator = waveUnsavedIndicator;
    this.contentService = contentService;
    this.colorPicker = colorPicker;
    searchPanel = new SearchPanelWidget(new SearchPanelRenderer(profiles));
    ErrorHandler.install();
    eventBus.addHandler(StackErrorEvent.getType(), new StackErrorEvent.StackErrorHandler() {
      @Override
      public void onStackError(final StackErrorEvent event) {
        getStackTraceAsync(event.getException(), new Accessor<SafeHtml>() {
          @Override
          public void use(final SafeHtml stack) {
            final String stackAsString = stack.asString().replace("<br>", "\n");
            NotifyUser.logError(stackAsString);
            cc.kune.common.client.log.Log.error("Stack: " + stackAsString);
          }
        });
      }
    });

    ClientEvents.get().addWaveCreationEventHandler(
        new WaveCreationEventHandler() {

          @Override
          public void onCreateRequest(final WaveCreationEvent event, final Set<ParticipantId> participantSet) {
            LOG.info("WaveCreationEvent received");
            if (channel == null) {
              throw new DefaultException("Spaghetti attack.  Create occured before login");
            }
            openWave(WaveRef.of(idGenerator.newWaveId()), true, participantSet);
          }
        });

    //setupConnectionIndicator();

    // Done in StateManager
    HistorySupport.init(new HistoryProvider() {
      @Override
      public String getToken() {
        final String currentToken = History.getToken();
        String waveToken = currentToken;
        // FIXME what about preview?
        if (TokenMatcher.isGroupToken(currentToken) || TokenMatcher.isHomeToken(currentToken)) {
          waveToken = kuneSession.getContentState().getWaveRef();
          LOG.info("Kune URL: " + currentToken + " = " + waveToken);
        }
        return waveToken;
      }
    });

    loginImpl();

    setupUi();

    //WaveClientUtils.addListener(stateManager, wave, waveHolder, waveFrame);

    if (TokenMatcher.isWaveToken(History.getToken())) {
      History.fireCurrentHistoryState();
    }
    LOG.info("SimpleWebClient.onModuleLoad() done");

    eventBus.fireEvent(new OnWaveClientStartEvent(this));
  }

  @Override
  public void addToBottonBar(final IsWidget widget) {
    bottomBar.add(widget);
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.client.kspecific.WaveClientView#clear()
   */
  @Override
  public void clear() {
  WaveClientUtils.clear(wave, waveHolder, waveFrame);
  waveFrame.clear();
}

  /**
   * Creates the web socket.
   */
  private void createWebSocket() {
    websocket = new WaveWebSocketClient(useSocketIO(), getWebSocketBaseUrl());
    websocket.connect();
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.client.kspecific.WaveClientView#getChannel()
   */
  @Override
  public RemoteViewServiceMultiplexer getChannel() {
    return channel;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.client.kspecific.WaveClientView#getLoading()
   */
  @Override
  public Element getLoading() {
    return loading;
  }


  /* (non-Javadoc)
   * @see cc.kune.wave.client.kspecific.WaveClientView#getProfiles()
   */
  @Override
  public ProfileManager getProfiles() {
    return profiles;
  }

  @Override
  public SimpleSearch getSearch() {
    return search;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.client.kspecific.WaveClientView#getStackTraceAsync(java.lang.Throwable, org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor)
   */
  @Override
  public void getStackTraceAsync(final Throwable caught, final Accessor<SafeHtml> accessor) {
    ErrorHandler.getStackTraceAsync(caught, accessor);
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.client.kspecific.WaveClientView#getWaveHolder()
   */
  @Override
  public ImplPanel getWaveHolder() {
    return waveHolder;
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.client.kspecific.WaveClientView#getWebSocket()
   */
  @Override
  public WaveWebSocketClient getWebSocket() {
    return websocket;
  }

  /**
   * Returns <code>ws(s)://yourhost[:port]/</code>.
   *
   * @return the web socket base url
   */
  // XXX check formatting wrt GPE
  private native String getWebSocketBaseUrl() /*-{
		return ((window.location.protocol == "https:") ? "wss" : "ws") + "://"
				+ $wnd.__websocket_address + "/";
  }-*/;

  /**
   * Hide bottom toolbar.
   */
  @Override
  public void hideBottomToolbar() {
    rightPanel.setWidgetSize(bottomBar, 0);
    bottomBar.getParent().getElement().getStyle().setBottom(0d, Unit.PX);
  }

  /* (non-Javadoc)
   * @see cc.kune.wave.client.kspecific.WaveClientView#login()
   */
  @Override
  public void login() {
    loginImpl();
  }

  /**
   * Login impl.
   */
  private void loginImpl() {
    createWebSocket();
    if (Session.get().isLoggedIn()) {
      loggedInUser = new ParticipantId(Session.get().getAddress());
      idGenerator = ClientIdGenerator.create();
      loginToServer();
    }
  }

  /**
   * Login to server.
   */
  private void loginToServer() {
    assert loggedInUser != null;
    channel = new RemoteViewServiceMultiplexer(websocket, loggedInUser.getAddress());
  }


  /* (non-Javadoc)
   * @see cc.kune.wave.client.kspecific.WaveClientView#logout()
   */
  @Override
  public void logout() {
    loggedInUser = null;
    channel = null;
    idGenerator = null;
    websocket = null;
    clear();
  }

  /**
   * Open wave.
   *
   * @param waveRef the wave ref
   * @param isNewWave the is new wave
   * @param participants the participants
   */
  private void openWave(final WaveRef waveRef, final boolean isNewWave, final Set<ParticipantId> participants) {
    waveUnsavedIndicator.onNewHistory(History.getToken(), new SimpleResponseCallback () {
      @Override
      public void onCancel() {
        // Do nothing
      }

      @Override
      public void onSuccess() {
        openWaveImpl(waveRef, isNewWave, participants);
      }});
  }

  /**
   * Shows a wave in a wave panel.
   *
   * @param waveRef wave id to open
   * @param isNewWave whether the wave is being created by this client session.
   * @param participants the participants to add to the newly created wave.
   *        {@code null} if only the creator should be added
   */
  private void openWaveImpl(final WaveRef waveRef, final boolean isNewWave, final Set<ParticipantId> participants) {
    LOG.info("WebClient.openWave()");

    WaveClientClearEvent.fire(eventBus);
    clear();
    eventBus.fireEvent(new BeforeOpenWaveEvent());;

    waveFrame.add(waveHolder);

    // Release the display:none.
    UIObject.setVisible(waveFrame.getElement(), true);
    waveHolder.getElement().appendChild(loading);
    final Element holder = waveHolder.getElement().appendChild(Document.get().createDivElement());
    final CustomStagesProvider wave = new CustomStagesProvider(
        holder, waveUnsavedIndicator, waveHolder, waveFrame, waveRef, channel, idGenerator, profiles, waveStore, isNewWave, Session.get().getDomain(), participants, eventBus, colorPicker);
    this.wave = wave;
    wave.load(new Command() {
      @Override
      public void execute() {
        loading.removeFromParent();
      }
    });

    final String waveUri = GwtWaverefEncoder.encodeToUriPathSegment(waveRef);

    eventBus.fireEvent(new AfterOpenWaveEvent(waveUri));;

    final String encodedToken = HistoryUtils.undoHashbang(History.getToken());
    // Kune patch
    if (encodedToken != null && !encodedToken.isEmpty() && !TokenMatcher.isInboxToken(encodedToken)) {
      WaveRef fromWaveRef;
      try {
        fromWaveRef = GwtWaverefEncoder.decodeWaveRefFromPath(encodedToken);
      } catch (final InvalidWaveRefException e) {
        LOG.info("History token contains invalid path: " + encodedToken);
        return;
      }
      if (fromWaveRef.getWaveId().equals(waveRef.getWaveId())) {
        // History change was caused by clicking on a link, it's already
        // updated by browser.
        SpaceConfEvent.fire(eventBus, Space.userSpace, encodedToken);
        return;
      }
    }
    final String tokenFromWaveref = HistoryUtils.hashbang(waveUri);
    SpaceConfEvent.fire(eventBus, Space.userSpace, tokenFromWaveref);
    History.newItem(tokenFromWaveref, false);
  }

  /* (non-Javadoc)
   * @see cc.kune.gspace.client.maxmin.IsMaximizable#setMaximized(boolean)
   */
  @Override
  public void setMaximized(final boolean maximized) {
    splitPanel.setWidgetSize(searchPanel, maximized ? 0 : 400);
  }

  /**
   * Setup connection indicator.
   */
  private void setupConnectionIndicator() {
    ClientEvents.get().addNetworkStatusEventHandler(new NetworkStatusEventHandler() {

      boolean isTurbulenceDetected = false;

      @Override
      public void onNetworkStatus(final NetworkStatusEvent event) {
        final Element element = Document.get().getElementById("netstatus");
        if (element != null) {
          switch (event.getStatus()) {
            case CONNECTED:
            case RECONNECTED:
              element.setInnerText("Online");
              element.setClassName("online");
              isTurbulenceDetected = false;
              turbulencePopup.hide();
              break;
            case DISCONNECTED:
              element.setInnerText("Offline");
              element.setClassName("offline");
              if (!isTurbulenceDetected) {
                isTurbulenceDetected = true;
                turbulencePopup.show();
              }
              break;
            case NEVER_CONNECTED:
            case RECONNECTING:
              element.setInnerText("Connecting...");
              element.setClassName("connecting");
              break;
          }
        }
      }
    });
  }

  /**
   * Setup search panel.
   */
  private void setupSearchPanel() {
    if (kuneSession.isEmbedded()) {
      // We don't use search in embed system
      return;
    }
    // On wave action fire an event.
    final SearchPresenter.WaveActionHandler actionHandler =
        new SearchPresenter.WaveActionHandler() {
          @Override
          public void onCreateWave() {
            ClientEvents.get().fireEvent(new WaveCreationEvent());
          }

          @Override
          public void onWaveSelected(final WaveId id) {
            ClientEvents.get().fireEvent(new WaveSelectionEvent(WaveRef.of(id)));
          }
        };
    search = SimpleSearch.create(RemoteSearchService.create(), waveStore);
    SearchPresenter.create(search, searchPanel, actionHandler, profiles);
  }

  /**
   * Setup ui.
   */
  private void setupUi() {
    // Set up UI
    splitPanel = new SplitLayoutPanel(2);
    final DockLayoutPanel self = BINDER.createAndBindUi(this);
    // kune-patch
    // RootPanel.get("app").add(self);
    InitialsResources.INS.css().ensureInjected();
    initWidget(self);
    waveHolder = new ImplPanel("");
    waveHolder.addStyleName("k-waveHolder");
    waveFrame.add(waveHolder);
    // DockLayoutPanel forcibly conflicts with sensible layout control, and
    // sticks inline styles on elements without permission. They must be
    // cleared.
    self.getElement().getStyle().clearPosition();
    splitPanel.setWidgetMinSize(searchPanel, 300);
    AttachmentManagerProvider.init(AttachmentManagerImpl.getInstance());

    if (LogLevel.showDebug()) {
      logPanel.enable();
    } else {
      logPanel.removeFromParent();
    }

    setupSearchPanel();
    setupWavePanel();
  }

  /**
   * Setup wave panel.
   */
  private void setupWavePanel() {
    // Hide the frame until waves start getting opened.
    UIObject.setVisible(waveFrame.getElement(), false);

    // Handles opening waves.
    ClientEvents.get().addWaveSelectionEventHandler(new WaveSelectionEventHandler() {
      @Override
      public void onSelection(final WaveRef waveRef) {
        openWave(waveRef, false, null);
      }
    });
  }

  @Override
  public void showBottomToolbar() {
    rightPanel.setWidgetSize(bottomBar, 26);
    bottomBar.getParent().getElement().getStyle().setBottom(12d, Unit.PX);
  }

  /**
   * Use socket io.
   *
   * @return true, if successful
   */
  private native boolean useSocketIO() /*-{
		return !window.WebSocket
  }-*/;
}
