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

import org.waveprotocol.box.webclient.widget.frame.FramedPanel;
import org.waveprotocol.wave.client.common.util.LinkedSequence;
import org.waveprotocol.wave.client.uibuilder.BuilderHelper;
import org.waveprotocol.wave.client.widget.common.ImplPanel;
import org.waveprotocol.wave.client.widget.toolbar.ToplevelToolbarWidget;
import org.waveprotocol.wave.model.util.CollectionUtils;
import org.waveprotocol.wave.model.util.StringMap;

import cc.kune.common.client.log.Log;
import cc.kune.core.client.dnd.KuneDragController;

import com.google.common.base.Preconditions;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.MouseEvent;
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
import com.google.gwt.user.client.ui.Widget;

/**
 * View interface for the search panel.
 *
 * @author hearnden@google.com (David Hearnden)
 */
public class SearchPanelWidget extends Composite implements SearchPanelView {

  interface Binder extends UiBinder<FramedPanel, SearchPanelWidget> {
  }

  interface Css extends CssResource {
    String list();

    String search();

    String self();

    String showMore();

    String toolbar();
  }

  /**
   * Positioning constants for components of this panel.
   */
// @formatter:off
  static class CssConstants {
    private static int SEARCH_HEIGHT_PX = 51; // To match wave panel.
    private static int TOOLBAR_TOP_PX = 0 + SEARCH_HEIGHT_PX;
    static String TOOLBAR_TOP = TOOLBAR_TOP_PX + "px";
    private static int TOOLBAR_HEIGHT_PX =
        SearchPanelResourceLoader.getPanel().emptyToolbar().getHeight();
    private static int LIST_TOP_PX = TOOLBAR_TOP_PX + TOOLBAR_HEIGHT_PX;
    static String LIST_TOP = LIST_TOP_PX + "px";
    // CSS constants exported to .css files
    static String SEARCH_HEIGHT = SEARCH_HEIGHT_PX + "px";

  }

  /** Resources used by this widget. */
  interface Resources extends ClientBundle {
    /** CSS */
    @Source("SearchPanel.css")
    Css css();

    @Source("images/toolbar_empty.png")
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    ImageResource emptyToolbar();
  }

  private final static Binder BINDER = GWT.create(Binder.class);

  @UiField(provided = true)
  static Css css = SearchPanelResourceLoader.getPanel().css();

  private static CustomDigestDomImpl narrow(final DigestView digestUi) {
    return (CustomDigestDomImpl) digestUi;
  }
  private final StringMap<CustomDigestDomImpl> byId = CollectionUtils.createStringMap();
  private final Pool<CustomDigestDomImpl> digestPool = ToppingUpPool.create(
      new ToppingUpPool.Factory<CustomDigestDomImpl>() {
        @Override
        public CustomDigestDomImpl create() {
          final CustomDigestDomImpl digest = new CustomDigestDomImpl(SearchPanelWidget.this, dragController);
          return digest;
        }
      }, 20);
  private final LinkedSequence<CustomDigestDomImpl> digests = LinkedSequence.create();
  private final FramedPanel frame;
  @UiField
  FlowPanel list;
  private Listener listener;
  private final SearchPanelRenderer renderer;
  @UiField
  SearchWidget search;
  @UiField
  ImplPanel self;
  @UiField
  FlowPanel showMore;

  @UiField
  ToplevelToolbarWidget toolbar;

  private final KuneDragController dragController;

  public SearchPanelWidget(final SearchPanelRenderer renderer, final KuneDragController dragController) {
    this.dragController = dragController;
    initWidget(frame = BINDER.createAndBindUi(this));
    showMore.setVisible(false);
    this.renderer = renderer;
  }

  @Override
  public void clearDigests() {
    while (!digests.isEmpty()) {
      digests.getFirst().remove(); // onDigestRemoved removes it from digests.
    }
    assert digests.isEmpty();
  }

  @Override
  public CustomDigestDomImpl getFirst() {
    return digests.getFirst();
  }

  @Override
  public CustomDigestDomImpl getLast() {
    return digests.getLast();
  }

  @Override
  public CustomDigestDomImpl getNext(final DigestView ref) {
    return digests.getNext(narrow(ref));
  }

  @Override
  public CustomDigestDomImpl getPrevious(final DigestView ref) {
    return digests.getPrevious(narrow(ref));
  }

  @Override
  public SearchWidget getSearch() {
    return search;
  }

  @Override
  public ToplevelToolbarWidget getToolbar() {
    return toolbar;
  }
  @UiHandler("self")
  void handleClick(final DoubleClickEvent e) {
    final Element target = e.getNativeEvent().getEventTarget().cast();
    Log.info("Double click in wave");
    handleClicksEvent(e, target);
  }

  @UiHandler("self")
  void handleClick(final ClickEvent e) {
    final Element target = e.getNativeEvent().getEventTarget().cast();
    handleClicksEvent(e, target);
  }

  private void handleClicksEvent(final MouseEvent e, Element target) {
    final Element top = self.getElement();
    while (!top.equals(target)) {
      if ("digest".equals(target.getAttribute(BuilderHelper.KIND_ATTRIBUTE))) {
        handleClick(byId.get(target.getAttribute(CustomDigestDomImpl.DIGEST_ID_ATTRIBUTE)));
        e.stopPropagation();
        return;
      } else if (showMore.equals(target)) {
        handleShowMoreClicked();
      }
      target = target.getParentElement();
    }
  }

  private void handleClick(final CustomDigestDomImpl digestUi) {
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

  @Override
  public void init(final Listener listener) {
    Preconditions.checkState(this.listener == null);
    Preconditions.checkArgument(listener != null);
    this.listener = listener;
  }

  @Override
  public CustomDigestDomImpl insertAfter(final DigestView ref, final Digest digest) {
    final CustomDigestDomImpl digestUi = digestPool.get();
    renderer.render(digest, digestUi);

    final Widget refDomImpl =  narrow(ref);
    final Widget refElement = refDomImpl != null ? refDomImpl : showMore;
    byId.put(digestUi.getId(), digestUi);
    int position = list.getWidgetIndex(refElement);
    if (!refElement.equals(showMore)) {
      digests.insertAfter((CustomDigestDomImpl) refDomImpl, digestUi);
      list.insert(digestUi, position + 1);
    } else {
      digests.insertBefore((CustomDigestDomImpl) refDomImpl, digestUi);
      list.insert(digestUi, position);
    }
    return digestUi;
  }

  @Override
  public CustomDigestDomImpl insertBefore(final DigestView ref, final Digest digest) {
    final CustomDigestDomImpl digestUi = digestPool.get();
    renderer.render(digest, digestUi);

    final Widget refDomImpl = narrow(ref);
    final Widget refElement = refDomImpl != null ? refDomImpl : showMore;
    int position = list.getWidgetIndex(refElement);
    byId.put(digestUi.getId(), digestUi);
    digests.insertBefore((CustomDigestDomImpl) refDomImpl, digestUi);
    list.insert(digestUi, position);    

    return digestUi;
  }

  void onDigestRemoved(final CustomDigestDomImpl digestUi) {
    digests.remove(digestUi);
    byId.remove(digestUi.getId());

    // Restore blank state and recycle.
    digestUi.reset();
    digestPool.recycle(digestUi);
  }

  @Override
  public void reset() {
    Preconditions.checkState(listener != null);
    listener = null;
  }

  @Override
  public void setShowMoreVisible(final boolean visible) {
    // In order to keep the padding effect, the button always need to be present
    // in order to affect layout. Just make it invisible and non-clickable.
    showMore.setVisible(visible);
  }

  @Override
  public void setTitleText(final String text) {
    frame.setTitleText(text);
  }
}
