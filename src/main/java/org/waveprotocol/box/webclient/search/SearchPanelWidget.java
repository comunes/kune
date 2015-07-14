// @formatter:off
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.waveprotocol.box.webclient.search;

import org.gwtbootstrap3.client.ui.base.button.CustomButton;
import org.waveprotocol.wave.client.common.util.LinkedSequence;
import org.waveprotocol.wave.client.uibuilder.BuilderHelper;
import org.waveprotocol.wave.client.widget.common.ImplPanel;
import org.waveprotocol.wave.client.widget.toolbar.ToplevelToolbarWidget;
import org.waveprotocol.wave.model.util.CollectionUtils;
import org.waveprotocol.wave.model.util.StringMap;
import org.waveprotocol.wave.model.waveref.WaveRef;
import org.waveprotocol.wave.util.escapers.GwtWaverefEncoder;

import com.google.common.base.Preconditions;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.InlineLabel;

import br.com.rpa.client._paperelements.PaperIconButton;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.WrappedFlowPanel;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.core.client.dnd.InboxToContainerHelper;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.polymer.client.PolymerId;

/**
 * View interface for the search panel.
 *
 * @author hearnden@google.com (David Hearnden)
 */
public class SearchPanelWidget extends Composite implements SearchPanelView {

  /** Resources used by this widget. */
  interface Resources extends ClientBundle {
    @Source("images/toolbar_empty.png")
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    ImageResource emptyToolbar();

    /** CSS */
    @Source("SearchPanel.css")
    Css css();
  }

  interface Css extends CssResource {
    String self();

    String search();

    String toolbar();

    String list();

    String showMore();
  }

  /**
   * Positioning constants for components of this panel.
   */
  static class CssConstants {
    private static int SEARCH_HEIGHT_PX = 0; // To match wave panel (0 == notVisible).
    private static int TOOLBAR_HEIGHT_PX = 0;
        // SearchPanelResourceLoader.getPanel().emptyToolbar().getHeight();
    private static int TOOLBAR_TOP_PX = 0 + SEARCH_HEIGHT_PX;
    private static int LIST_TOP_PX = TOOLBAR_TOP_PX + TOOLBAR_HEIGHT_PX;

    // CSS constants exported to .css files
    static String SEARCH_HEIGHT = SEARCH_HEIGHT_PX + "px";
    static String TOOLBAR_TOP = TOOLBAR_TOP_PX + "px";
    static String LIST_TOP = LIST_TOP_PX + "px";
  }

  @UiField(provided = true)
  static Css css = SearchPanelResourceLoader.getPanel().css();

  interface Binder extends UiBinder<FocusPanel, SearchPanelWidget> {
  }

  private final static Binder BINDER = GWT.create(Binder.class);

  @UiField
  SearchWidget search;
  @UiField
  ToplevelToolbarWidget toolbar;
  @UiField
  FlowPanel list;
  @UiField
  CustomButton showMore;
  Element showMoreElement;
  @UiField
  ImplPanel self;
  @UiField
  FocusPanel focus;

  private final LinkedSequence<DigestDomImpl> digests = LinkedSequence.create();
  private final StringMap<DigestDomImpl> byId = CollectionUtils.createStringMap();
  private final SearchPanelRenderer renderer;
  private final Pool<DigestDomImpl> digestPool =
      ToppingUpPool.create(new ToppingUpPool.Factory<DigestDomImpl>() {
        @Override
        public DigestDomImpl create() {
          return new DigestDomImpl(SearchPanelWidget.this, dragController, downUtils);
        }
      }, 20);
  private Listener listener;

  private InlineLabel inboxTitle;

  private KuneDragController dragController;

  private ClientFileDownloadUtils downUtils;

  private PaperIconButton shareBtn;

  private DigestView digestToPublish;

  private InboxToContainerHelper inboxToContainerHelper;

  public SearchPanelWidget(SearchPanelRenderer renderer, KuneDragController dragController, ClientFileDownloadUtils downUtils, GSpaceArmor armor, InboxToContainerHelper inboxToContainerHelper) {
    this.dragController = dragController;
    this.downUtils = downUtils;
    this.inboxToContainerHelper = inboxToContainerHelper;
    initWidget(BINDER.createAndBindUi(this));
    // showMore.setVisible(false);
    showMore.setText(I18n.t("Show more results"));
    showMore.setIcon(KuneIcon.ADD);
    showMore.addStyleName("btn-lg");
    showMore.addStyleName("btn-block");
    WrappedFlowPanel inboxTitleFlow = armor.wrapDiv(PolymerId.INBOX_TITLE);
    shareBtn = PaperIconButton.wrap(PolymerId.INBOX_SHARE_ICON.getId());
    shareBtn.setEnabled(false);
    shareBtn.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        onSharedClicked();
      }});
    Tooltip.to(shareBtn,
        I18n.t("To publish the selected document, move to some group folder (or your public profile), select the document in your Inbox and press this button"));
    inboxTitle = new InlineLabel(I18n.t("Inbox"));
    inboxTitleFlow.add(inboxTitle);
    showMoreElement = showMore.getElement();
    toolbar.setVisible(false);
    search.setVisible(false);
    this.renderer = renderer;
  }

  protected void onSharedClicked() {
    inboxToContainerHelper.publishDigest(digestToPublish);
  }

  @Override
  public void init(Listener listener) {
    Preconditions.checkState(this.listener == null);
    Preconditions.checkArgument(listener != null);
    this.listener = listener;
  }

  @Override
  public void reset() {
    Preconditions.checkState(listener != null);
    listener = null;
  }

  void onDigestRemoved(DigestDomImpl digestUi) {
    digests.remove(digestUi);
    byId.remove(digestUi.getId());

    // Restore blank state and recycle.
    digestUi.reset();
    digestPool.recycle(digestUi);
  }

  @Override
  public void setTitleText(String text) {
    // Commented because is very geek
    // inboxTitle.setText(text);
  }

  @Override
  public SearchWidget getSearch() {
    return search;
  }

  public ToplevelToolbarWidget getToolbar() {
    return toolbar;
  }

  @Override
  public DigestDomImpl getFirst() {
    return digests.getFirst();
  }

  @Override
  public DigestDomImpl getLast() {
    return digests.getLast();
  }

  @Override
  public DigestDomImpl getNext(DigestView ref) {
    return digests.getNext(narrow(ref));
  }

  @Override
  public DigestDomImpl getPrevious(DigestView ref) {
    return digests.getPrevious(narrow(ref));
  }

  @Override
  public DigestDomImpl insertBefore(DigestView ref, Digest digest) {
    DigestDomImpl digestUi = digestPool.get();
    setWaveUri(digest, digestUi);
    renderer.render(digest, digestUi);

    DigestDomImpl refDomImpl = narrow(ref);
    Element refElement = refDomImpl != null ? refDomImpl.getElement() : showMoreElement;
    byId.put(digestUi.getId(), digestUi);
    digests.insertBefore(refDomImpl, digestUi);
    list.getElement().insertBefore(digestUi.getElement(), refElement);
    return digestUi;
  }

  @Override
  public DigestDomImpl insertAfter(DigestView ref, Digest digest) {
    DigestDomImpl digestUi = digestPool.get();
    setWaveUri(digest, digestUi);
    renderer.render(digest, digestUi);

    DigestDomImpl refDomImpl = narrow(ref);
    Element refElement = refDomImpl != null ? refDomImpl.getElement() : showMoreElement;
    byId.put(digestUi.getId(), digestUi);
    if (refElement != showMoreElement) {
      digests.insertAfter(refDomImpl, digestUi);
      list.getElement().insertAfter(digestUi.getElement(), refElement);
    } else {
      digests.insertBefore(refDomImpl, digestUi);
      list.getElement().insertBefore(digestUi.getElement(), refElement);
    }
    return digestUi;
  }

  private void setWaveUri(final Digest digest, final DigestDomImpl digestUi) {
    final String waveUri = GwtWaverefEncoder.encodeToUriPathSegment(WaveRef.of(digest.getWaveId()));
    digestUi.setWaveUri(waveUri);
  }

  @Override
  public void clearDigests() {
    while (!digests.isEmpty()) {
      digests.getFirst().remove(); // onDigestRemoved removes it from digests.
    }
    assert digests.isEmpty();
  }

  @Override
  public void setShowMoreVisible(boolean visible) {
    // In order to keep the padding effect, the button always need to be present
    // in order to affect layout.  Just make it invisible and non-clickable.
    if (visible) {
      showMoreElement.getStyle().clearVisibility();
    } else {
      showMoreElement.getStyle().setVisibility(Visibility.HIDDEN);
    }
  }

  @UiHandler("self")
  void handleClick(ClickEvent e) {
    Element target = e.getNativeEvent().getEventTarget().cast();
    Element top = self.getElement();
    while (!top.equals(target)) {
      if ("digest".equals(target.getAttribute(BuilderHelper.KIND_ATTRIBUTE))) {
        handleClick(byId.get(target.getAttribute(DigestDomImpl.DIGEST_ID_ATTRIBUTE)));
        e.stopPropagation();
        return;
      } else if (showMoreElement.equals(target)) {
        handleShowMoreClicked();
      }
      target = target.getParentElement();
    }
  }

  private void handleClick(DigestDomImpl digestUi) {
    if (digestUi == null) {
      // Error - there's an element in the DOM that looks like a digest, but
      // it's not in the digest map.
      // TODO(hearnden): log.
    } else {
      if (listener != null) {
        listener.onClicked(digestUi);
      }
    }
  }

  private void handleShowMoreClicked() {
    if (listener != null) {
      listener.onShowMoreClicked();
    }
  }

  private static DigestDomImpl narrow(DigestView digestUi) {
    return (DigestDomImpl) digestUi;
  }

  public void setShareIconEnabled(boolean enabled) {
    shareBtn.setEnabled(enabled);
  }

  public void setDigestToPublish(DigestView digestUi) {
    this.digestToPublish = digestUi;
  }

}
