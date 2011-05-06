package cc.kune.gspace.client.viewers;

import org.waveprotocol.box.webclient.client.ClientIdGenerator;
import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.SimpleWaveStore;
import org.waveprotocol.box.webclient.client.StagesProvider;
import org.waveprotocol.box.webclient.search.WaveStore;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.widget.common.ImplPanel;
import org.waveprotocol.wave.model.id.IdGenerator;
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
import cc.kune.gspace.client.viewers.ContentViewerPresenter.ContentViewerView;
import cc.kune.wave.client.WaveClientClearEvent;
import cc.kune.wave.client.WaveClientManager;
import cc.kune.wave.client.WebClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class ContentViewerPanel extends ViewImpl implements ContentViewerView {
  interface DocsViewerPanelUiBinder extends UiBinder<Widget, ContentViewerPanel> {
  }

  private static final RemoteViewServiceMultiplexer NO_CHANNEL = null;

  private static DocsViewerPanelUiBinder uiBinder = GWT.create(DocsViewerPanelUiBinder.class);

  private final ContentCapabilitiesRegistry capabilitiesRegistry;
  private RemoteViewServiceMultiplexer channel;
  private final ContentTitleWidget contentTitle;
  @UiField
  DeckPanel deck;
  private final GSpaceArmor gsArmor;
  private IdGenerator idGenerator;
  private Element loading;
  @UiField
  InlineHTML onlyViewPanel;
  private ProfileManager profiles;
  /** The wave panel, if a wave is open. */
  private StagesProvider wave;
  private final WaveClientManager waveClient;
  private ImplPanel waveHolder;

  @UiField
  VerticalPanel waveHolderParent;

  private final WaveStore waveStore = new SimpleWaveStore();

  private WebClient webClient;

  private final Widget widget;

  @Inject
  public ContentViewerPanel(final GSpaceArmor wsArmor, final WaveClientManager waveClient,
      final ContentCapabilitiesRegistry capabilitiesRegistry, final I18nTranslationService i18n,
      final EventBus eventBus) {
    this.gsArmor = wsArmor;
    this.waveClient = waveClient;
    this.capabilitiesRegistry = capabilitiesRegistry;
    widget = uiBinder.createAndBindUi(this);
    contentTitle = new ContentTitleWidget(i18n, gsArmor, capabilitiesRegistry.getIconsRegistry());
    eventBus.addHandler(WaveClientClearEvent.getType(),
        new WaveClientClearEvent.WaveClientClearHandler() {
          @Override
          public void onWaveClientClear(final WaveClientClearEvent event) {
            waveClear();
          }
        });
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
    waveClear();
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
      webClient = waveClient.getWebClient();
      loading = webClient.getLoading();
      waveHolder = webClient.getWaveHolder();
      channel = webClient.getChannel();
      profiles = webClient.getProfiles();
      idGenerator = ClientIdGenerator.create();
      loading.addClassName("kune-Margin-40-tb");
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
    setEditableWaveContent(state.getWaveRef(), false);
    deck.showWidget(0);
  }

  @Override
  public void setEditableTitle(final String title) {
    contentTitle.setText(title);
  }

  private void setEditableWaveContent(final String waveRefS, final boolean isNewWave) {
    final WaveRef waveRef = getWaveRef(waveRefS);

    initWaveClientIfNeeded();

    webClient.clear();
    waveClear();

    if (waveHolder.isAttached()) {
      waveHolder.removeFromParent();
      waveHolderParent.remove(waveHolder);
    }
    waveHolderParent.add(waveHolder);

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

  @Override
  public void signIn() {
    // Do nothing (now)
    // initWaveClientIfNeeded();
  }

  @Override
  public void signOut() {
    channel = NO_CHANNEL;
  }

  private void waveClear() {
    if (wave != null) {
      wave.destroy();
      wave = null;
    }
  }

}
