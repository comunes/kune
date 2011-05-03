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

import org.apache.jasper.compiler.ErrorDispatcher;
import org.waveprotocol.box.webclient.client.ClientEvents;
import org.waveprotocol.box.webclient.client.ClientIdGenerator;
import org.waveprotocol.box.webclient.client.DebugMessagePanel;
import org.waveprotocol.box.webclient.client.HistorySupport;
import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.Session;
import org.waveprotocol.box.webclient.client.SimpleWaveStore;
import org.waveprotocol.box.webclient.client.StagesProvider;
import org.waveprotocol.box.webclient.client.WaveWebSocketClient;
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
import org.waveprotocol.box.webclient.util.Log;
import org.waveprotocol.box.webclient.widget.frame.FramedPanel;
import org.waveprotocol.box.webclient.widget.loading.LoadingIndicator;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.account.impl.ProfileManagerImpl;
import org.waveprotocol.wave.client.common.safehtml.SafeHtml;
import org.waveprotocol.wave.client.common.safehtml.SafeHtmlBuilder;
import org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor;
import org.waveprotocol.wave.client.debug.logger.LogLevel;
import org.waveprotocol.wave.client.widget.common.ImplPanel;
import org.waveprotocol.wave.model.id.IdGenerator;
import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.sitebar.ErrorsDialog;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceConfEvent;
import cc.kune.core.client.state.SiteTokens;

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
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.inject.Inject;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WebClient extends Composite {
  interface Binder extends UiBinder<DockLayoutPanel, WebClient> {
  }

  interface Style extends CssResource {
  }

  private static final Binder BINDER = GWT.create(Binder.class);

  static final Log LOG = Log.get(WebClient.class);
  // Use of GWT logging is only intended for sending exception reports to the
  // server, nothing else in the client should use java.util.logging.
  // Please also see WebClientDemo.gwt.xml.
  private static final Logger REMOTE_LOG = Logger.getLogger("REMOTE_LOG");

  private final ProfileManager profiles = new ProfileManagerImpl(Session.get().getDomain());

  @UiField
  SplitLayoutPanel splitPanel;

  @UiField
  Style style;

  @UiField
  FramedPanel waveFrame;

  @UiField
  ImplPanel waveHolder;
  private final Element loading = new LoadingIndicator().getElement();

  @UiField(provided = true)
  final SearchPanelWidget searchPanel = new SearchPanelWidget(new SearchPanelRenderer(profiles));

  @UiField
  DebugMessagePanel logPanel;

  /** The wave panel, if a wave is open. */
  private StagesProvider wave;

  private final WaveStore waveStore = new SimpleWaveStore();

  /**
   * Create a remote websocket to talk to the server-side FedOne service.
   */
  private WaveWebSocketClient websocket;

  private ParticipantId loggedInUser;

  private IdGenerator idGenerator;

  private RemoteViewServiceMultiplexer channel;

private final EventBus eventBus;

  /**
   * This is the entry point method.
   */
  @Inject
  public WebClient(EventBus eventBus) {

    this.eventBus = eventBus;
    ErrorHandler.install();

    ClientEvents.get().addWaveCreationEventHandler(
        new WaveCreationEventHandler() {

          @Override
          public void onCreateRequest(WaveCreationEvent event) {
            LOG.info("WaveCreationEvent received");
            if (channel == null) {
              throw new DefaultException("Spaghetti attack.  Create occured before login");
            }

            openWave(WaveRef.of(idGenerator.newWaveId()), true);
          }
        });

    setupConnectionIndicator();

    HistorySupport.init();

    websocket = new WaveWebSocketClient(useSocketIO(), getWebSocketBaseUrl(GWT.getModuleBaseURL()));
    websocket.connect();

    if (Session.get().isLoggedIn()) {
      loggedInUser = new ParticipantId(Session.get().getAddress());
      idGenerator = ClientIdGenerator.create();
      loginToServer();
    }

    setupUi();

    History.fireCurrentHistoryState();
    LOG.info("SimpleWebClient.onModuleLoad() done");
  }

  private void setupUi() {
    // Set up UI
    DockLayoutPanel self = BINDER.createAndBindUi(this);
    // kune-patch
    // RootPanel.get("app").add(self);
    initWidget(self);
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

  private void setupSearchPanel() {
    // On wave selection, fire an event.
    SearchPresenter.WaveSelectionHandler selectHandler =
        new SearchPresenter.WaveSelectionHandler() {
          @Override
          public void onWaveSelected(WaveId id) {
            ClientEvents.get().fireEvent(new WaveSelectionEvent(WaveRef.of(id)));
          }
        };
    Search search = SimpleSearch.create(RemoteSearchService.create(), waveStore);
    SearchPresenter.create(search, searchPanel, selectHandler);
  }

  private void setupWavePanel() {
    // Hide the frame until waves start getting opened.
    UIObject.setVisible(waveFrame.getElement(), false);

    // Handles opening waves.
    ClientEvents.get().addWaveSelectionEventHandler(new WaveSelectionEventHandler() {
      @Override
      public void onSelection(WaveRef waveRef) {
        openWave(waveRef, false);
      }
    });
  }

  private void setupConnectionIndicator() {
    ClientEvents.get().addNetworkStatusEventHandler(new NetworkStatusEventHandler() {
      @Override
      public void onNetworkStatus(NetworkStatusEvent event) {
        Element element = Document.get().getElementById("netstatus");
        if (element != null) {
          switch (event.getStatus()) {
            case CONNECTED:
            case RECONNECTED:
              element.setInnerText("Online");
              element.setClassName("online");
              break;
            case DISCONNECTED:
              element.setInnerText("Offline");
              element.setClassName("offline");
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

  /**
   * Returns <code>ws://yourhost[:port]/</code>.
   */
  // XXX check formatting wrt GPE
  public native static String getWebSocketBaseUrl(String moduleBase) /*-{
	return "ws" + /:\/\/[^\/]+/.exec(moduleBase)[0] + "/";
}-*/;

  public native static boolean useSocketIO() /*-{
	return !!$wnd.__useSocketIO
}-*/;

  /**
   */
  private void loginToServer() {
    assert loggedInUser != null;
    channel = new RemoteViewServiceMultiplexer(websocket, loggedInUser.getAddress());
  }

  /**
   * Shows a wave in a wave panel.
   *
   * @param waveRef wave id to open
   * @param isNewWave whether the wave is being created by this client session.
   */
  private void openWave(WaveRef waveRef, boolean isNewWave) {
    LOG.info("WebClient.openWave()");

    if (wave != null) {
      wave.destroy();
      wave = null;
    }

    // Release the display:none.
    UIObject.setVisible(waveFrame.getElement(), true);
    waveHolder.getElement().appendChild(loading);
    Element holder = waveHolder.getElement().appendChild(Document.get().createDivElement());
    StagesProvider wave = new StagesProvider(
        holder, waveHolder, waveRef, channel, idGenerator, profiles, waveStore, isNewWave);
    this.wave = wave;
    wave.load(new Command() {
      @Override
      public void execute() {
        loading.removeFromParent();
      }
    });
    String encodedToken = History.getToken();
    // NotifyUser.info("Open Wave: " + encodedToken + " waveRef: " + waveRef.getWaveId(), true);
    // Kune patch
    if (encodedToken != null && !encodedToken.isEmpty() && !encodedToken.equals(SiteTokens.WAVEINBOX)) {
      WaveRef fromWaveRef = HistorySupport.waveRefFromHistoryToken(encodedToken);
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
    String tokenFromWaveref = HistorySupport.historyTokenFromWaveref(waveRef);
    SpaceConfEvent.fire(eventBus, Space.userSpace, tokenFromWaveref);
    History.newItem(tokenFromWaveref, false);
  }

  /**
   * An exception handler that reports exceptions using a <em>shiny banner</em>
   * (an alert placed on the top of the screen). Once the stack trace is
   * prepared, it is revealed in the banner via a link.
   */
  static class ErrorHandler implements UncaughtExceptionHandler {
    /** Next handler in the handler chain. */
    private final UncaughtExceptionHandler next;

    /**
     * Indicates whether an error has already been reported (at most one error
     * is ever reported by this handler).
     */
    private boolean hasFired;

    private ErrorHandler(UncaughtExceptionHandler next) {
      this.next = next;
    }

    public static void install() {
    GWT.setUncaughtExceptionHandler(new ErrorHandler(GWT.getUncaughtExceptionHandler()));
    }

    @Override
    public void onUncaughtException(Throwable e) {
      if (!hasFired) {
        hasFired = true;
      //  final ErrorIndicatorPresenter error =
        //    ErrorIndicatorPresenter.create(RootPanel.get("banner"));
        getStackTraceAsync(e, new Accessor<SafeHtml>() {
          @Override
          public void use(SafeHtml stack) {
              NotifyUser.error("Oops! Something has gone wrong. Please contact the site admins with <em>more details</em>", true);
          //  error.addDetail(stack, null);
            String message = stack.asString().replace("<br>", "\n");
            REMOTE_LOG.severe(message);
            NotifyUser.logError(message);
          }
        });
      }

      if (next != null) {
        next.onUncaughtException(e);
      }
    }

    private void getStackTraceAsync(final Throwable t, final Accessor<SafeHtml> whenReady) {
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
          SafeHtmlBuilder stack = new SafeHtmlBuilder();

          Throwable error = t;
          while (error != null) {
            String token = String.valueOf((new Date()).getTime());
            stack.appendHtmlConstant("Token:  " + token + "<br> ");
            stack.appendEscaped(String.valueOf(error.getMessage())).appendHtmlConstant("<br>");
            for (StackTraceElement elt : error.getStackTrace()) {
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

    private static String maybe(String value, String otherwise) {
      return value != null ? value : otherwise;
    }

    private static String maybe(int value, String otherwise) {
      return value != -1 ? String.valueOf(value) : otherwise;
    }
  }
}
