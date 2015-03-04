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

import static cc.kune.polymer.client.Layout.*;

import java.util.LinkedList;
import java.util.List;

import org.waveprotocol.box.webclient.search.i18n.DigestDomMessages;
import org.waveprotocol.wave.client.account.Profile;
import org.waveprotocol.wave.client.common.safehtml.SafeHtml;
import org.waveprotocol.wave.client.common.safehtml.SafeHtmlBuilder;
import org.waveprotocol.wave.client.uibuilder.BuilderHelper;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.initials.AvatarComposite;
import cc.kune.initials.AvatarCompositeFactory;
import cc.kune.polymer.client.PolymerUtils;

import com.allen_sauer.gwt.dnd.client.HasDragHandle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public final class CustomDigestDomImpl extends Composite implements DigestView, HasDragHandle {

  interface Binder extends UiBinder<Widget, CustomDigestDomImpl> {
  }
  interface Css extends CssResource {
    String avatar();

    String avatars();

    String digest();

    String info();

    String inner();

    String selected();

    String unread();

    String unreadCount();
  }

  /** Resources used by this widget. */
  interface Resources extends ClientBundle {
    /** CSS */
    @Source("mock/digest.css")
    Css css();
  }
  private final static Binder BINDER = GWT.create(Binder.class);

  /** HTML attribute used to hold an id unique within digest widgets. */
  static String DIGEST_ID_ATTRIBUTE = "di";

  private static int idCounter;

  private static final DigestDomMessages messages = GWT.create(DigestDomMessages.class);
  @UiField
  Element avatarsAndUnreadDiv;
  @UiField
  FlowPanel avatarsDiv;
  private final SearchPanelWidget container;
  private final ClientFileDownloadUtils downUtils;
  private final KuneDragController dragController;
  @UiField
  Image groupAvatar;
  @UiField
  Element main;
  @UiField
  Element mainLeft;
  @UiField
  HTMLPanel mainPanel;
  @UiField
  Element mainRight;
  @UiField
  InlineLabel msgs;
  private final CoreResources res;
  private final Element self;
  @UiField
  InlineLabel snippet;
  @UiField
  InlineLabel time;
  @UiField
  InlineLabel title;
  private final Tooltip tooltip;
  @UiField
  FlowPanel unreadDiv;
  private String waveUri;

  public CustomDigestDomImpl(final SearchPanelWidget container, final KuneDragController dragController,
      final ClientFileDownloadUtils downUtils, final CoreResources res) {
    this.container = container;
    this.dragController = dragController;
    this.downUtils = downUtils;
    this.res = res;

    initWidget(BINDER.createAndBindUi(this));
    self = this.getElement();
    self.setAttribute(BuilderHelper.KIND_ATTRIBUTE, "digest");
    self.setAttribute(DIGEST_ID_ATTRIBUTE, "D" + idCounter++);

    PolymerUtils.addLayout(main, HORIZONTAL, LAYOUT, FLEX);
    PolymerUtils.addLayout(mainLeft, VERTICAL, LAYOUT);
    PolymerUtils.addLayout(mainRight, VERTICAL, LAYOUT, FLEX);
    PolymerUtils.addLayout(avatarsAndUnreadDiv, VERTICAL, LAYOUT);
    PolymerUtils.addLayout(avatarsDiv, HORIZONTAL, JUSTIFIED, LAYOUT);
    tooltip = Tooltip.to(this, "");
    groupAvatar.addLoadHandler(new LoadHandler() {

      @Override
      public void onLoad(final LoadEvent event) {

        if (groupAvatar.getWidth() == 1) {
          // If the server loads an avatar, is already published in a group.
          // if not, the servelt returns a 1x1 transparent pixel, so the wave
          // can be dragged to a folder publish it.

          // if (SessionInstance.get().isNewbie())
          tooltip.setText(I18n.t("Drag and drop into a group or personal folder to publish"));

          // Note: see make not dragable below
          makeDragable();
        }
        groupAvatar.setSize("41px", "41px");
      }
    });
  }

  private DragableImageParticipant createImageDragable(final String imageUrl, final String address) {
    final DragableImageParticipant participant = new DragableImageParticipant(imageUrl, address);
    dragController.makeDraggable(participant);
    return participant;
  }

  private DragableInitialParticipant createInitialDragable(final String address) {
    final DragableInitialParticipant participant = new DragableInitialParticipant(address);
    dragController.makeDraggable(participant);
    return participant;

  }

  @Override
  public void deselect() {
    self.removeClassName("k-digest-selected");
  }

  @Override
  public Widget getDragHandle() {
    return this;
  }

  String getId() {
    return getElement().getAttribute(DIGEST_ID_ATTRIBUTE);
  }

  public String getWaveUri() {
    return waveUri;
  }

  private void makeDragable() {
    dragController.makeDraggable(CustomDigestDomImpl.this, title);
    dragController.makeDraggable(CustomDigestDomImpl.this, snippet);
  }

  public void makeNotDraggable() {
    try {
      dragController.makeNotDraggable(this);
    } catch (final RuntimeException e) {
      // gwt-dd don't have a way to check if some widget is dragable, and as
      // this Digest are reusable, then sometimes are dragables when is not
      // necessary
      Log.debug("We tryied to remove a not (yet) dragable widget");
    }
  }

  @Override
  public void remove() {
    // TODO
    // self.removeFromParent();
    setVisible(false);
    container.onDigestRemoved(this);
  }

  public void removeTooltip() {
    tooltip.setText("");
  }

  private SafeHtml renderReadMessages(final int total) {
    final SafeHtmlBuilder html = new SafeHtmlBuilder();
    html.appendHtmlConstant(String.valueOf(total));
    html.appendHtmlConstant(" " + messages.msgs());
    return html.toSafeHtml();
  }

  private SafeHtml renderUnreadMessages(final int unread, final int total) {
    final SafeHtmlBuilder html = new SafeHtmlBuilder();
    html.appendHtmlConstant("<span class='" + "k-digest-unread-count" + "'>");
    html.appendHtmlConstant(String.valueOf(unread));
    html.appendHtmlConstant("</span>");
    html.appendHtmlConstant(" " + messages.of(total));
    return html.toSafeHtml();
  }

  public void reset() {
    title.setText("");
    snippet.setText("");
    time.setText("");
    // remove avatars
    groupAvatar.setResource(res.clear());
    avatarsDiv.remove(0);
    msgs.setText("");
    self.removeClassName("k-digest-selected");
    removeTooltip();
    makeNotDraggable();
    setVisible(false);
  }

  @Override
  public void select() {
    self.addClassName("k-digest-selected");
  }

  @Override
  public void setAvatars(final List<Profile> profiles) {
    final LinkedList<IsWidget> names = new LinkedList<IsWidget>();
    for (final Profile profile : profiles) {
      final String imageUrl = profile.getImageUrl();
      final String address = profile.getAddress();

      final Widget avatar = imageUrl.contains("iniavatars") ? createInitialDragable(address)
          : createImageDragable(imageUrl, address);
      Tooltip.to(avatar, profile.getFullName());

      // FIXME: seems that this not work for some reason. Maybe some work should
      // be done in AvatarComposite
      // new DragableImageParticipant(imageUrl, address)

      // FIXME: Last connected?
      // Try to use MediumAvatarDecorator or similar
      names.add(avatar);
    }
    final AvatarComposite composite = AvatarCompositeFactory.get40().build(names);
    composite.addStyleName("k-digest-avatar");
    avatarsDiv.insert(composite, 0);
  }

  public void setGroup(final GroupDTO group) {
    groupAvatar.setUrl(downUtils.getGroupLogo(group));
  }

  @Override
  public void setMessageCounts(final int unread, final int total) {
    // TODO Auto-generated method stub
    if (unread == 0) {
      msgs.getElement().setInnerHTML(renderReadMessages(total).asString());
      title.removeStyleName("k-digest-unread");
      time.removeStyleName("k-digest-unread");
    } else {
      msgs.getElement().setInnerHTML(renderUnreadMessages(unread, total).asString());
      title.addStyleName("k-digest-unread");
      time.addStyleName("k-digest-unread");
    }
  }

  @Override
  public void setSnippet(final String text) {
    snippet.setText(text);
  }

  @Override
  public void setTimestamp(final String text) {
    time.setText(text);
  }

  @Override
  public void setTitleText(final String text) {
    title.setText(text);
  }

  public void setWaveUri(final String waveUri) {
    this.waveUri = waveUri;
    groupAvatar.setUrl(downUtils.getGroupLogoFromWaveUri(waveUri));
  }

}
