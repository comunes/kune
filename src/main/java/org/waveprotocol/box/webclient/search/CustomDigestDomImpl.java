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


import static cc.kune.polymer.client.Layout.*;

import java.util.LinkedList;
import java.util.List;

import org.waveprotocol.box.webclient.search.i18n.DigestDomMessages;
import org.waveprotocol.wave.client.account.Profile;
import org.waveprotocol.wave.client.common.safehtml.SafeHtml;
import org.waveprotocol.wave.client.common.safehtml.SafeHtmlBuilder;
import org.waveprotocol.wave.client.uibuilder.BuilderHelper;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.initials.AvatarComposite;
import cc.kune.initials.AvatarCompositeFactory;
import cc.kune.initials.InitialLabel;
import cc.kune.polymer.client.PolymerUtils;

import com.allen_sauer.gwt.dnd.client.HasDragHandle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
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
  private final Element self;
  @UiField
  InlineLabel snippet;
  @UiField
  InlineLabel time;
  @UiField
  InlineLabel title;
  @UiField
  FlowPanel unreadDiv;

  public CustomDigestDomImpl(final SearchPanelWidget container, final KuneDragController dragController) {
    this.container = container;

    initWidget(BINDER.createAndBindUi(this));
    self = this.getElement();
    self.setAttribute(BuilderHelper.KIND_ATTRIBUTE, "digest");
    self.setAttribute(DIGEST_ID_ATTRIBUTE, "D" + idCounter++);

    PolymerUtils.addLayout(main, HORIZONTAL, LAYOUT, FLEX);
    PolymerUtils.addLayout(mainLeft, VERTICAL, LAYOUT);
    PolymerUtils.addLayout(mainRight, VERTICAL, LAYOUT, FLEX);

    PolymerUtils.addLayout(avatarsAndUnreadDiv, VERTICAL, LAYOUT);
    PolymerUtils.addLayout(avatarsDiv, HORIZONTAL, LAYOUT);

    // FIXME if not in groups make it draggable
    dragController.makeDraggable(this, title);
    dragController.makeDraggable(this, snippet);
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

  @Override
  public void remove() {
    // TODO
    // self.removeFromParent();
    setVisible(false);
    container.onDigestRemoved(this);
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
    avatarsDiv.clear();
    msgs.setText("");
    self.removeClassName("k-digest-selected");
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
      final Widget avatar = imageUrl.contains("iniavatars")?
          new InitialLabel(profile.getAddress()) : new Image(imageUrl);
          Tooltip.to(avatar,profile.getFullName());

          // FIXME: Last connected?
          // Try to use MediumAvatarDecorator or similar
          names.add(avatar);
    }
    final AvatarComposite composite = AvatarCompositeFactory.get40().build(names);
    composite.addStyleName("k-digest-avatar");
    avatarsDiv.add(composite);
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
    // Rendering
    setVisible(true);
  }

  @Override
  public void setTitleText(final String text) {
    title.setText(text);
  }


}
