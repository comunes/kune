package cc.kune.gspace.client.viewers;

import org.waveprotocol.box.webclient.client.ClientIdGenerator;
import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.Session;
import org.waveprotocol.box.webclient.client.SimpleWaveStore;
import org.waveprotocol.box.webclient.client.StagesProvider;
import org.waveprotocol.box.webclient.client.WaveWebSocketClient;
import org.waveprotocol.box.webclient.search.WaveStore;
import org.waveprotocol.box.webclient.widget.frame.FramedPanel;
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
import cc.kune.common.client.ui.EditableLabel;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.common.client.ui.UiUtils;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.registry.IconsRegistry;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;
import cc.kune.gspace.client.viewers.DocViewerPresenter.DocViewerView;
import cc.kune.wave.client.WebClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class DocViewerPanel extends ViewImpl implements DocViewerView {
  interface DocsViewerPanelUiBinder extends UiBinder<Widget, DocViewerPanel> {
  }

  private static DocsViewerPanelUiBinder uiBinder = GWT.create(DocsViewerPanelUiBinder.class);

  private RemoteViewServiceMultiplexer channel;
  @UiField
  DeckPanel deck;
  private final EditableLabel editableTitle;
  private final GSpaceArmor gsArmor;
  private final IconsRegistry iconRegistry;
  private IdGenerator idGenerator;
  private final Element loading = new LoadingIndicator().getElement();
  @UiField
  InlineHTML onlyViewPanel;
  private ProfileManager profiles;
  private final FlowPanel title;
  private final Image titleIcon;
  /** The wave panel, if a wave is open. */
  private StagesProvider wave;
  @UiField
  FramedPanel waveFrame;
  @UiField
  ImplPanel waveHolder;
  private final WaveStore waveStore = new SimpleWaveStore();
  private final Widget widget;

  @Inject
  public DocViewerPanel(final GSpaceArmor wsArmor,
      final ContentCapabilitiesRegistry capabilitiesRegistry, final I18nTranslationService i18n) {
    this.gsArmor = wsArmor;
    this.iconRegistry = capabilitiesRegistry.getIconsRegistry();
    widget = uiBinder.createAndBindUi(this);
    title = new FlowPanel();
    titleIcon = new Image();
    editableTitle = new EditableLabel();
    editableTitle.setTooltip(i18n.t("Click to edit"));
    title.add(titleIcon);
    title.add(editableTitle);
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
    return editableTitle;
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
    if (editable) {
      initWaveClientIfNeeded();
      setEditableWaveContent(state.getWaveRef(), false);
      // waveHolder.clear();
      // waveHolder.add(new Label(state.getContent()
      // + " (but here goes the Wave editor -we are testing it-)"));
      // onlyViewPanel.setHTML(SafeHtmlUtils.fromTrustedString(state.getContent()));
    } else {
      onlyViewPanel.setHTML(SafeHtmlUtils.fromTrustedString(state.getContent()));
    }
    // deck.showWidget(editable ? 0 : 1);
    deck.showWidget(1);
  }

  @Override
  public void setEditableTitle(final String title) {
    editableTitle.setText(title);
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
    final ForIsWidget docHeader = gsArmor.getDocHeader();
    UiUtils.clear(docHeader);
    final ImageResource resource = (ImageResource) iconRegistry.getContentTypeIcon(state.getTypeId(),
        state.getMimeType());
    final boolean hasIcon = resource != null;
    if (hasIcon) {
      titleIcon.setResource(resource);
    }
    titleIcon.setVisible(hasIcon);
    editableTitle.setText(state.getTitle());
    editableTitle.setEditable(editable);
    docHeader.add(title);
  }
}
