package cc.kune.gspace.client.viewers;

import org.waveprotocol.box.webclient.client.ClientIdGenerator;
import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.Session;
import org.waveprotocol.box.webclient.client.SimpleWaveStore;
import org.waveprotocol.box.webclient.client.StagesProvider;
import org.waveprotocol.box.webclient.client.WaveWebSocketClient;
import org.waveprotocol.box.webclient.search.WaveStore;
import org.waveprotocol.box.webclient.widget.loading.LoadingIndicator;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.account.impl.ProfileManagerImpl;
import org.waveprotocol.wave.client.widget.common.ImplPanel;
import org.waveprotocol.wave.model.id.IdGenerator;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.model.waveref.WaveRef;
import org.waveprotocol.wave.util.escapers.GwtWaverefEncoder;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.common.client.ui.UiUtils;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;
import cc.kune.gspace.client.viewers.DocViewerPresenter.DocViewerView;
import cc.kune.wave.client.WebClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class DocViewerPanel extends ViewImpl implements DocViewerView {
  interface DocsViewerPanelUiBinder extends UiBinder<Widget, DocViewerPanel> {
  }

  private static DocsViewerPanelUiBinder uiBinder = GWT.create(DocsViewerPanelUiBinder.class);

  private final ContentCapabilitiesRegistry capabilitiesRegistry;
  private RemoteViewServiceMultiplexer channel;
  private final ContentTitleWidget contentTitle;
  @UiField
  DeckPanel deck;
  private final GSpaceArmor gsArmor;
  private IdGenerator idGenerator;
  private final Element loading = new LoadingIndicator().getElement();
  @UiField
  InlineHTML onlyViewPanel;
  private ProfileManager profiles;
  /** The wave panel, if a wave is open. */
  private StagesProvider wave;
  @UiField
  ImplPanel waveHolder;
  private final WaveStore waveStore = new SimpleWaveStore();

  private final Widget widget;

  @Inject
  public DocViewerPanel(final GSpaceArmor wsArmor,
      final ContentCapabilitiesRegistry capabilitiesRegistry, final I18nTranslationService i18n) {
    this.gsArmor = wsArmor;
    this.capabilitiesRegistry = capabilitiesRegistry;
    widget = uiBinder.createAndBindUi(this);
    contentTitle = new ContentTitleWidget(i18n, gsArmor, capabilitiesRegistry.getIconsRegistry());
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

  @Override
  public void attach() {
    final ForIsWidget docContainer = gsArmor.getDocContainer();
    docContainer.add(widget);
  }

  @Override
  public void clear() {
    onlyViewPanel.setHTML("");
    gsArmor.getSubheaderToolbar().clear();
    UiUtils.clear(gsArmor.getDocContainer());
    UiUtils.clear(gsArmor.getDocHeader());
  }

  @Override
  public void detach() {
    clear();
  }

  @Override
  public HasEditHandler getEditTitle() {
    return contentTitle.getEditableTitle();
  }

  private WaveRef getWaveRef(final String waveRefS) {
    try {
      return GwtWaverefEncoder.decodeWaveRefFromPath(waveRefS);
    } catch (final InvalidWaveRefException e) {
      throw new UIException("Invalid waveref: " + waveRefS);
    }
  }

  private void initWaveClientIfNeeded() {
    if (channel == null) {
      final WaveWebSocketClient webSocket = new WaveWebSocketClient(WebClient.useSocketIO(),
          WebClient.getWebSocketBaseUrl(GWT.getModuleBaseURL()));
      webSocket.connect();
      channel = new RemoteViewServiceMultiplexer(webSocket,
          new ParticipantId(Session.get().getAddress()).getAddress());
      profiles = new ProfileManagerImpl(Session.get().getDomain());
      idGenerator = ClientIdGenerator.create();
    }
  }

  @Override
  public void setActions(final GuiActionDescCollection actions) {
    gsArmor.getSubheaderToolbar().clear();
    gsArmor.getSubheaderToolbar().addAll(actions);
  }

  @Override
  public void setContent(final StateContentDTO state) {
    final boolean editable = state.getContentRights().isEditable();
    setTitle(state, editable);
    onlyViewPanel.setHTML(SafeHtmlUtils.fromTrustedString(state.getContent()));
    deck.showWidget(1);
  }

  @Override
  public void setEditableContent(final StateContentDTO state) {
    setTitle(state, true);
    initWaveClientIfNeeded();
    setEditableWaveContent(state.getWaveRef(), false);
    deck.showWidget(0);
  }

  @Override
  public void setEditableTitle(final String title) {
    contentTitle.setText(title);
  }

  private void setEditableWaveContent(final String waveRefS, final boolean isNewWave) {
    final WaveRef waveRef = getWaveRef(waveRefS);

    if (wave != null) {
      wave.destroy();
      wave = null;
    }

    // Release the display:none.
    // UIObject.setVisible(waveFrame.getElement(), true);
    waveHolder.getElement().appendChild(loading);
    final Element holder = waveHolder.getElement().appendChild(Document.get().createDivElement());
    final StagesProvider wave = new StagesProvider(holder, waveHolder, waveRef, channel, idGenerator,
        profiles, waveStore, isNewWave);
    this.wave = wave;
    wave.load(new Command() {
      @Override
      public void execute() {
        loading.removeFromParent();
      }
    });
  }

  private void setTitle(final StateContentDTO state, final boolean editable) {
    contentTitle.setTitle(state.getTitle(), state.getTypeId(), state.getMimeType(), editable
        && capabilitiesRegistry.isRenamable(state.getTypeId()));
  }

}
