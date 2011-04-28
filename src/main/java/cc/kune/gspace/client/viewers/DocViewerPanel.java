package cc.kune.gspace.client.viewers;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.client.ui.UiUtils;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.registry.IconsRegistry;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.gspace.client.GSpaceArmor;
import cc.kune.gspace.client.viewers.DocViewerPresenter.DocViewerView;
import cc.kune.wave.client.WaveClientManager;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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

  // private RemoteViewServiceMultiplexer channel;
  @UiField
  DeckPanel deck;
  private final GSpaceArmor gsArmor;
  private final IconsRegistry iconRegistry;
  // private IdGenerator idGenerator;
  // private final Element loading = new LoadingIndicator().getElement();
  @UiField
  InlineHTML onlyViewPanel;
  // private ProfileManager profiles;
  /** The wave panel, if a wave is open. */
  // private StagesProvider wave;
  // private final WaveClientManager waveClientManager;
  // @UiField
  // FramedPanel waveFrame;
  // @UiField
  // ImplPanel waveHolder;
  // private final WaveStore waveStore = new SimpleWaveStore();
  private final Widget widget;

  @Inject
  public DocViewerPanel(final GSpaceArmor wsArmor, final WaveClientManager waveClientManager,
      final ContentCapabilitiesRegistry capabilitiesRegistry) {
    this.gsArmor = wsArmor;
    this.iconRegistry = capabilitiesRegistry.getIconsRegistry();
    // this.waveClientManager = waveClientManager;
    widget = uiBinder.createAndBindUi(this);
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

  // private WaveRef getWaveRef(final String waveRefS) {
  // try {
  // return GwtWaverefEncoder.decodeWaveRefFromPath(waveRefS);
  // } catch (final InvalidWaveRefException e) {
  // throw new UIException("Invalid waveref: " + waveRefS);
  // }
  // }
  //
  // private void initWaveClientIfNeeded() {
  // if (channel == null) {
  // final WaveWebSocketClient webSocket = new
  // WaveWebSocketClient(WebClient.useSocketIO(),
  // WebClient.getWebSocketBaseUrl(GWT.getModuleBaseURL()));
  // webSocket.connect();
  // channel = new RemoteViewServiceMultiplexer(webSocket,
  // new ParticipantId(Session.get().getAddress()).getAddress());
  // profiles = new ProfileManagerImpl();
  // idGenerator = ClientIdGenerator.create();
  // }
  // }

  @Override
  public void setActions(final GuiActionDescCollection actions) {
    gsArmor.getSubheaderToolbar().clear();
    gsArmor.getSubheaderToolbar().addAll(actions);
  }

  @Override
  public void setContent(final StateContentDTO state) {
    final ForIsWidget docHeader = gsArmor.getDocHeader();
    UiUtils.clear(docHeader);
    // docHeader.add(new InlineLabel(state.getTitle()));
    docHeader.add(new IconLabel((ImageResource) iconRegistry.getContentTypeIcon(state.getTypeId(),
        state.getMimeType()), state.getTitle()));
    final boolean editable = state.getContentRights().isEditable();
    if (editable) {
      // initWaveClientIfNeeded();
      // setEditableWaveContent(state.getWaveRef(), false);
      // waveHolder.clear();
      // waveHolder.add(new Label(state.getContent() +
      // " (but here goes the Wave editor -we are testing it-)"));
      onlyViewPanel.setHTML(SafeHtmlUtils.fromTrustedString(state.getContent()));
    } else {
      onlyViewPanel.setHTML(SafeHtmlUtils.fromTrustedString(state.getContent()));
    }
    // deck.showWidget(editable ? 0 : 1);
    deck.showWidget(1);
  }
  //
  // private void setEditableWaveContent(final String waveRefS, final boolean
  // isNewWave) {
  // final WaveRef waveRef = getWaveRef(waveRefS);
  //
  // if (wave != null) {
  // wave.destroy();
  // wave = null;
  // }
  //
  // // Release the display:none.
  // // UIObject.setVisible(waveFrame.getElement(), true);
  // waveHolder.getElement().appendChild(loading);
  // final Element holder =
  // waveHolder.getElement().appendChild(Document.get().createDivElement());
  // final StagesProvider wave = new StagesProvider(holder, waveHolder,
  // waveRef, channel, idGenerator, profiles,
  // waveStore, isNewWave);
  // this.wave = wave;
  // wave.load(new Command() {
  // @Override
  // public void execute() {
  // loading.removeFromParent();
  // }
  // });
  // }
}
