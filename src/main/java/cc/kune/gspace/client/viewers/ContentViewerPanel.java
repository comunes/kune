/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.gspace.client.viewers;

import org.waveprotocol.box.webclient.client.ClientIdGenerator;
import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.SimpleWaveStore;
import org.waveprotocol.box.webclient.search.WaveStore;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.widget.common.ImplPanel;
import org.waveprotocol.wave.model.id.IdGenerator;
import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.model.waveref.WaveRef;
import org.waveprotocol.wave.util.escapers.GwtWaverefEncoder;

import cc.kune.common.client.actions.BeforeActionListener;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.common.client.ui.UiUtils;
import cc.kune.core.client.registry.ContentCapabilitiesRegistry;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;
import cc.kune.gspace.client.viewers.ContentViewerPresenter.ContentViewerView;
import cc.kune.wave.client.KuneStagesProvider;
import cc.kune.wave.client.WaveClientClearEvent;
import cc.kune.wave.client.WaveClientProvider;
import cc.kune.wave.client.WaveClientView;
import cc.kune.wave.client.WebClientMock;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class ContentViewerPanel extends ViewImpl implements ContentViewerView {
  interface ContentViewerPanelUiBinder extends UiBinder<Widget, ContentViewerPanel> {
  }

  private static final RemoteViewServiceMultiplexer NO_CHANNEL = null;

  private static ContentViewerPanelUiBinder uiBinder = GWT.create(ContentViewerPanelUiBinder.class);

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
  private KuneStagesProvider wave;
  private final WaveClientProvider waveClientProv;

  private ImplPanel waveHolder;

  @UiField
  ImplPanel waveHolderParent;

  private final WaveStore waveStore = new SimpleWaveStore();

  private final Widget widget;

  @Inject
  public ContentViewerPanel(final GSpaceArmor wsArmor, final WaveClientProvider waveClient,
      final ContentCapabilitiesRegistry capabilitiesRegistry, final I18nTranslationService i18n,
      final EventBus eventBus, final StateManager stateManager) {
    this.gsArmor = wsArmor;
    this.waveClientProv = waveClient;
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
    stateManager.addBeforeStateChangeListener(new BeforeActionListener() {
      @Override
      public boolean beforeAction() {
        // This fix lot of problems when you are editing and move to other
        // location (without stop editing)
        waveClear();
        return true;
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
  public void blinkTitle() {
    contentTitle.highlightTitle();
  }

  @Override
  public void clear() {
    onlyViewPanel.setHTML("");
    gsArmor.getSubheaderToolbar().clear();
    gsArmor.getDocFooterToolbar().clear();
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
      final WaveClientView webClient = waveClientProv.get();
      loading = webClient.getLoading();
      waveHolder = webClient.getWaveHolder();
      channel = webClient.getChannel();
      profiles = webClient.getProfiles();
      idGenerator = ClientIdGenerator.create();
    }
  }

  @Override
  public void setContent(final StateContentDTO state) {
    final boolean editable = state.getContentRights().isEditable();
    gsArmor.enableCenterScroll(true);
    setTitle(state, editable);
    onlyViewPanel.setHTML(SafeHtmlUtils.fromTrustedString(state.getContent()));
    deck.showWidget(1);
  }

  @Override
  public void setEditableContent(final StateContentDTO state) {
    gsArmor.enableCenterScroll(false);
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
    waveClientProv.get().clear();
    waveClear();

    waveHolderParent.add(waveHolder);

    if (waveClientProv.get() instanceof WebClientMock) {
      // do nothing;
    } else {
      // real wave client
      // Release the display:none.
      // UIObject.setVisible(waveFrame.getElement(), true);
      waveHolder.getElement().appendChild(loading);
      final Element holder = waveHolder.getElement().appendChild(Document.get().createDivElement());
      final KuneStagesProvider wave = new KuneStagesProvider(holder, waveHolder, waveRef, channel,
          idGenerator, profiles, waveStore, isNewWave,
          org.waveprotocol.box.webclient.client.Session.get().getDomain(), true);
      this.wave = wave;
      wave.load(new Command() {
        @Override
        public void execute() {
          loading.removeFromParent();
        }
      });
    }
  }

  @Override
  public void setFooterActions(final GuiActionDescCollection actions) {
    setToolbarActions(actions, gsArmor.getDocFooterToolbar());
  }

  @Override
  public void setSubheaderActions(final GuiActionDescCollection actions) {
    setToolbarActions(actions, gsArmor.getSubheaderToolbar());
  }

  private void setTitle(final StateContentDTO state, final boolean editable) {
    contentTitle.setTitle(state.getTitle(), state.getTypeId(), state.getMimeType(), editable
        && capabilitiesRegistry.isRenamable(state.getTypeId()));
    Window.setTitle(state.getGroup().getLongName() + ": " + state.getTitle());
  }

  private void setToolbarActions(final GuiActionDescCollection actions, final IsActionExtensible toolbar) {
    toolbar.clear();
    toolbar.addAll(actions);
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
      // try {
      wave.destroy();
      // } catch (final RuntimeException e) {
      // When editing: java.lang.RuntimeException: Component not found: MENU
      // }
      wave = null;
    }
    if (waveHolder != null && waveHolder.isAttached()) {
      waveHolder.removeFromParent();
      waveHolderParent.remove(waveHolder);
    }
  }

}
