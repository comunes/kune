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
 *
 */

package cc.kune.wave.client;





import java.util.Date;
import java.util.logging.Logger;

import org.waveprotocol.box.webclient.client.ClientEvents;
import org.waveprotocol.box.webclient.client.ClientIdGenerator;
import org.waveprotocol.box.webclient.client.DebugMessagePanel;
import org.waveprotocol.box.webclient.client.HistoryProvider;
import org.waveprotocol.box.webclient.client.HistorySupport;
import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.Session;
import org.waveprotocol.box.webclient.client.SimpleWaveStore;
import org.waveprotocol.box.webclient.client.WaveWebSocketClient;
import org.waveprotocol.box.webclient.client.events.Log;
import org.waveprotocol.box.webclient.client.events.NetworkStatusEvent;
import org.waveprotocol.box.webclient.client.events.NetworkStatusEventHandler;
import org.waveprotocol.box.webclient.client.events.WaveCreationEvent;
import org.waveprotocol.box.webclient.client.events.WaveCreationEventHandler;
import org.waveprotocol.box.webclient.client.events.WaveSelectionEvent;
import org.waveprotocol.box.webclient.client.events.WaveSelectionEventHandler;
import org.waveprotocol.box.webclient.search.RemoteSearchService;
import org.waveprotocol.box.webclient.search.Search;
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
import org.waveprotocol.wave.client.widget.common.ImplPanel;
import org.waveprotocol.wave.client.widget.popup.CenterPopupPositioner;
import org.waveprotocol.wave.client.widget.popup.PopupChrome;
import org.waveprotocol.wave.client.widget.popup.PopupChromeFactory;
import org.waveprotocol.wave.client.widget.popup.PopupFactory;
import org.waveprotocol.wave.client.widget.popup.UniversalPopup;
import org.waveprotocol.wave.model.id.IdGenerator;
import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceConfEvent;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.wave.client.inboxcount.InboxCountPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
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
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
@Singleton
public class WebClient extends  Composite implements WaveClientView {
  interface Binder extends UiBinder<DockLayoutPanel, WebClient> {
  }

  /**
   * An exception handler that reports exceptions using a <em>shiny banner</em>
   * (an alert placed on the top of the screen). Once the stack trace is
   * prepared, it is revealed in the banner via a link.
   */
  public static class ErrorHandler implements UncaughtExceptionHandler {
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

    public static void install() {
    GWT.setUncaughtExceptionHandler(new ErrorHandler(GWT.getUncaughtExceptionHandler()));
    }

    private static String maybe(final int value, final String otherwise) {
      return value != -1 ? String.valueOf(value) : otherwise;
    }

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

    private ErrorHandler(final UncaughtExceptionHandler next) {
      this.next = next;
    }

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
            REMOTE_LOG.severe(message);
            NotifyUser.logError(message);
            NotifyUser.showProgress("Error in editor");
            new Timer() {
              @Override
              public void run() {
                NotifyUser.hideProgress();
              }}.schedule(3000);
          }
        });
      }

      if (next != null) {
        next.onUncaughtException(e);
      }
    }
  }

  interface Style extends CssResource {
  }

  private static final Binder BINDER = GWT.create(Binder.class);
  static Log LOG = Log.get(WebClient.class);

  // Use of GWT logging is only intended for sending exception reports to the
  // server, nothing else in the client should use java.util.logging.
  // Please also see WebClientDemo.gwt.xml.
  private static final Logger REMOTE_LOG = Logger.getLogger("REMOTE_LOG");

  /** Creates a popup that warns about network disconnects. */
  private static UniversalPopup createTurbulencePopup() {
    final PopupChrome chrome = PopupChromeFactory.createPopupChrome();
    final UniversalPopup popup =
        PopupFactory.createPopup(null, new CenterPopupPositioner(), chrome, true);
    popup.add(new HTML("<div style='color: red; padding: 5px; text-align: center;'>"
        + "<b>A turbulence detected!<br></br>"
        + " Please save your last changes to somewhere and reload the wave.</b></div>"));
    return popup;
  }
  private RemoteViewServiceMultiplexer channel;

  private final EventBus eventBus;

  private IdGenerator idGenerator;

  private final InboxCountPresenter inboxCount;

  private final Element loading = new LoadingIndicator().getElement();

  private ParticipantId loggedInUser;
  @UiField
  DebugMessagePanel logPanel;

  private final ProfileManager profiles;

  @UiField(provided = true)
  final SearchPanelWidget searchPanel;

  @UiField
  SplitLayoutPanel splitPanel;

  @UiField
  Style style;

  private final UniversalPopup turbulencePopup = createTurbulencePopup();

  /** The wave panel, if a wave is open. */
  private KuneStagesProvider wave;

  @UiField
  FramedPanel waveFrame;

  ImplPanel waveHolder;

  private final WaveStore waveStore = new SimpleWaveStore();

  /**
   * Create a remote websocket to talk to the server-side FedOne service.
   */
  private WaveWebSocketClient websocket;

  /**
   * This is the entry point method.
   */
  @Inject
  public WebClient(final EventBus eventBus, final KuneWaveProfileManager profiles, final InboxCountPresenter inboxCount, final TokenMatcher tokenMatcher, final cc.kune.core.client.state.Session session) {
    // Window.alert("webclient! " + new Date());
    this.eventBus = eventBus;
    this.profiles = profiles;
    this.inboxCount = inboxCount;
    searchPanel = new SearchPanelWidget(new SearchPanelRenderer(profiles));
    ErrorHandler.install();

    ClientEvents.get().addWaveCreationEventHandler(
        new WaveCreationEventHandler() {

          @Override
          public void onCreateRequest(final WaveCreationEvent event) {
            LOG.info("WaveCreationEvent received");
            if (channel == null) {
              throw new DefaultException("Spaghetti attack.  Create occured before login");
            }

            openWave(WaveRef.of(idGenerator.newWaveId()), true);
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
        if (tokenMatcher.isGroupToken(currentToken) || tokenMatcher.isHomeToken(currentToken)) {
          waveToken = session.getContentState().getWaveRef();
          LOG.info("Kune URL: " + currentToken + " = " + waveToken);
        }
        return waveToken;
      }
    });

    loginImpl();

    setupUi();

    if (tokenMatcher.isWaveToken(History.getToken())) {
      History.fireCurrentHistoryState();
    }
    LOG.info("SimpleWebClient.onModuleLoad() done");
  }

  @Override
  public void clear() {
    // Duplicate below
  if (wave != null) {
    wave.destroy();
    wave = null;
  }
  if (waveHolder.isAttached()) {
    waveHolder.removeFromParent();
  }
}

  private void createWebSocket() {
    websocket = new WaveWebSocketClient(useSocketIO(), getWebSocketBaseUrl(GWT.getModuleBaseURL()));
    websocket.connect();
  }

  @Override
  public RemoteViewServiceMultiplexer getChannel() {
    return channel;
  }

  @Override
  public Element getLoading() {
    return loading;
  }

  @Override
  public ProfileManager getProfiles() {
    return profiles;
  }

  @Override
  public void getStackTraceAsync(final Throwable caught, final Accessor<SafeHtml> accessor) {
    ErrorHandler.getStackTraceAsync(caught, accessor);
  }

  @Override
  public ImplPanel getWaveHolder() {
    return waveHolder;
  }

  @Override
  public WaveWebSocketClient getWebSocket() {
    return websocket;
  }

  /**
   * Returns <code>ws://yourhost[:port]/</code>.
   */
  // XXX check formatting wrt GPE
  private native String getWebSocketBaseUrl(String moduleBase) /*-{
		return "ws" + /:\/\/[^\/]+/.exec(moduleBase)[0] + "/";
  }-*/;

  @Override
  public void login() {
    loginImpl();
  }

  private void loginImpl() {
    createWebSocket();
    if (Session.get().isLoggedIn()) {
      loggedInUser = new ParticipantId(Session.get().getAddress());
      idGenerator = ClientIdGenerator.create();
      loginToServer();
    }
  }

  /**
   */
  private void loginToServer() {
    assert loggedInUser != null;
    channel = new RemoteViewServiceMultiplexer(websocket, loggedInUser.getAddress());
  }

  @Override
  public void logout() {
    loggedInUser = null;
    channel = null;
    idGenerator = null;
    websocket = null;
    clear();
  }

  /**
   * Shows a wave in a wave panel.
   *
   * @param waveRef wave id to open
   * @param isNewWave whether the wave is being created by this client session.
   */
  private void openWave(final WaveRef waveRef, final boolean isNewWave) {
    LOG.info("WebClient.openWave()");

    WaveClientClearEvent.fire(eventBus);
    if (wave != null) {
      wave.destroy();
      wave = null;
    }
    if (waveHolder.isAttached()) {
      waveHolder.removeFromParent();
    }
      waveFrame.remove(waveHolder);
      waveFrame.clear();
    waveFrame.add(waveHolder);

    // Release the display:none.
    UIObject.setVisible(waveFrame.getElement(), true);
    waveHolder.getElement().appendChild(loading);
    final Element holder = waveHolder.getElement().appendChild(Document.get().createDivElement());
    final KuneStagesProvider wave = new KuneStagesProvider(
        holder, waveHolder, waveRef, channel, idGenerator, profiles, waveStore, isNewWave, Session.get().getDomain(), true);
    this.wave = wave;
    wave.load(new Command() {
      @Override
      public void execute() {
        loading.removeFromParent();
      }
    });
    final String encodedToken = History.getToken();
    // Kune patch
    if (encodedToken != null && !encodedToken.isEmpty() && !encodedToken.equals(SiteTokens.WAVEINBOX)) {
      final WaveRef fromWaveRef = HistorySupport.waveRefFromHistoryToken(encodedToken);
      if (waveRef == null) {
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
    final String tokenFromWaveref = HistorySupport.historyTokenFromWaveref(waveRef);
    SpaceConfEvent.fire(eventBus, Space.userSpace, tokenFromWaveref);
    History.newItem(tokenFromWaveref, false);
  }

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
            case RECONNECTING:
              element.setInnerText("Connecting...");
              element.setClassName("connecting");
              break;
          }
        }
      }
    });
  }

  private void setupSearchPanel() {
    // On wave action fire an event.
    final SearchPresenter.WaveActionHandler actionHandler =
        new SearchPresenter.WaveActionHandler() {
          @Override
          public void onCreateWave() {
            ClientEvents.get().fireEvent(WaveCreationEvent.CREATE_NEW_WAVE);
          }

          @Override
          public void onWaveSelected(final WaveId id) {
            ClientEvents.get().fireEvent(new WaveSelectionEvent(WaveRef.of(id)));
          }
        };
    final Search search = SimpleSearch.create(RemoteSearchService.create(), waveStore);
    search.addListener(inboxCount.getSearchListener());
    SearchPresenter.create(search, searchPanel, actionHandler, profiles);
  }
private void setupUi() {
  // Set up UI
  final DockLayoutPanel self = BINDER.createAndBindUi(this);
  // kune-patch
  // RootPanel.get("app").add(self);
  initWidget(self);
  waveHolder = new ImplPanel("");
  waveHolder.addStyleName("k-waveHolder");
  waveFrame.add(waveHolder);
  // DockLayoutPanel forcibly conflicts with sensible layout control, and
  // sticks inline styles on elements without permission. They must be
  // cleared.
  self.getElement().getStyle().clearPosition();
  splitPanel.setWidgetMinSize(searchPanel, 300);

  if (LogLevel.showDebug()) {
    logPanel.enable();
  } else {
    logPanel.removeFromParent();
  }

  setupSearchPanel();
  setupWavePanel();
}
  private void setupWavePanel() {
    // Hide the frame until waves start getting opened.
    UIObject.setVisible(waveFrame.getElement(), false);

    // Handles opening waves.
    ClientEvents.get().addWaveSelectionEventHandler(new WaveSelectionEventHandler() {
      @Override
      public void onSelection(final WaveRef waveRef) {
        openWave(waveRef, false);
      }
    });
  }

  private native boolean useSocketIO() /*-{
		return !window.WebSocket
  }-*/;
}
