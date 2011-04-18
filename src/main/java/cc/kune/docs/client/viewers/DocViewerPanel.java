package cc.kune.docs.client.viewers;

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
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.docs.client.viewers.DocViewerPresenter.DocViewerView;
import cc.kune.gspace.client.GSpaceArmor;
import cc.kune.wave.client.WaveClientManager;
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
import com.google.gwt.user.client.ui.Label;
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
    private IdGenerator idGenerator;
    private final Element loading = new LoadingIndicator().getElement();
    @UiField
    InlineHTML onlyViewPanel;
    private ProfileManager profiles;
    /** The wave panel, if a wave is open. */
    private StagesProvider wave;
    private final WaveClientManager waveClientManager;
    // @UiField
    // FramedPanel waveFrame;
    @UiField
    ImplPanel waveHolder;
    private final WaveStore waveStore = new SimpleWaveStore();
    private final Widget widget;
    private final GSpaceArmor wsArmor;

    @Inject
    public DocViewerPanel(final GSpaceArmor wsArmor, final WaveClientManager waveClientManager) {
        this.wsArmor = wsArmor;
        this.waveClientManager = waveClientManager;
        widget = uiBinder.createAndBindUi(this);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void attach() {
        final ForIsWidget docContainer = wsArmor.getDocContainer();
        final int widgetCount = docContainer.getWidgetCount();
        for (int i = 0; i < widgetCount; i++) {
            docContainer.remove(i);
        }
        docContainer.add(widget);
    }

    @Override
    public void detach() {
        widget.removeFromParent();
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
            profiles = new ProfileManagerImpl();
            idGenerator = ClientIdGenerator.create();
        }
    }

    @Override
    public void setActions(final GuiActionDescCollection actions) {
        wsArmor.getSubheaderToolbar().clear();
        wsArmor.getSubheaderToolbar().addAll(actions);
    }

    @Override
    public void setContent(final StateContentDTO state) {
        final boolean editable = state.getContentRights().isEditable();
        if (editable) {
            // initWaveClientIfNeeded();
            // setEditableWaveContent(state.getWaveRef(), false);
            waveHolder.clear();
            waveHolder.add(new Label(state.getContent() + " (but here goes the Wave editor -we are testing it-)"));
            onlyViewPanel.setHTML(SafeHtmlUtils.fromTrustedString(state.getContent()));
        } else {
            onlyViewPanel.setHTML(SafeHtmlUtils.fromTrustedString(state.getContent()));
        }
        deck.showWidget(editable ? 0 : 1);
    }

    public void setEditableWaveContent(final String waveRefS, final boolean isNewWave) {
        final WaveRef waveRef = getWaveRef(waveRefS);

        if (wave != null) {
            wave.destroy();
            wave = null;
        }

        // Release the display:none.
        // UIObject.setVisible(waveFrame.getElement(), true);
        waveHolder.getElement().appendChild(loading);
        final Element holder = waveHolder.getElement().appendChild(Document.get().createDivElement());
        final StagesProvider wave = new StagesProvider(holder, waveHolder, waveRef, channel, idGenerator, profiles,
                waveStore, isNewWave);
        this.wave = wave;
        wave.load(new Command() {
            @Override
            public void execute() {
                loading.removeFromParent();
            }
        });
    }
}
