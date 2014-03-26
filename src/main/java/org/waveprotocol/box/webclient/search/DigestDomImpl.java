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


import java.util.LinkedList;
import java.util.List;

import org.waveprotocol.box.webclient.search.i18n.DigestDomMessages;
import org.waveprotocol.wave.client.account.Profile;
import org.waveprotocol.wave.client.common.safehtml.SafeHtml;
import org.waveprotocol.wave.client.common.safehtml.SafeHtmlBuilder;
import org.waveprotocol.wave.client.uibuilder.BuilderHelper;

import cc.kune.initials.AvatarComposite;
import cc.kune.initials.AvatarCompositeFactory;
import cc.kune.initials.InitialLabel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * DOM implementation of a digest view.
 *
 * @author hearnden@google.com (David Hearnden)
 */
public final class DigestDomImpl implements DigestView {
  interface Binder extends UiBinder<Widget, DigestDomImpl> {
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

  @UiField(provided = true)
  final static Css css = SearchPanelResourceLoader.getDigest().css();

  /** HTML attribute used to hold an id unique within digest widgets. */
  static String DIGEST_ID_ATTRIBUTE = "di";

  private static int idCounter;
  private static final DigestDomMessages messages = GWT.create(DigestDomMessages.class);

  @UiField
  SimplePanel avatars;
  private final SearchPanelWidget container;
  @UiField
  Element msgs;
  private final Element self;
  @UiField
  Element snippet;
  @UiField
  Element time;
  @UiField
  Element title;

  DigestDomImpl(final SearchPanelWidget container) {
    this.container = container;
    self = BINDER.createAndBindUi(this).getElement();
    self.setAttribute(BuilderHelper.KIND_ATTRIBUTE, "digest");
    self.setAttribute(DIGEST_ID_ATTRIBUTE, "D" + idCounter++);
  }

  @Override
  public void deselect() {
    self.removeClassName(css.selected());
  }

  Element getElement() {
    return self;
  }

  /** @return an id of this widget, unique within the space of digest widgets. */
  String getId() {
    return self.getAttribute(DIGEST_ID_ATTRIBUTE);
  }

  @Override
  public void remove() {
    self.removeFromParent();
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
    html.appendHtmlConstant("<span class='" + css.unreadCount() + "'>");
    html.appendHtmlConstant(String.valueOf(unread));
    html.appendHtmlConstant("</span>");
    html.appendHtmlConstant(" " + messages.of(total));
    return html.toSafeHtml();
  }

  /** Restores this object to a post-constructor state. */
  void reset() {
    avatars.clear();
    title.setInnerText("");
    snippet.setInnerText("");
    time.setInnerText("");
    msgs.setInnerHTML("");
    self.removeClassName(css.selected());
  }

  @Override
  public void select() {
    self.addClassName(css.selected());
  }

  @Override
  public void setAvatars(final List<Profile> profiles) {
    final LinkedList<IsWidget> names = new LinkedList<IsWidget>();
    for (final Profile profile : profiles) {
      final String imageUrl = profile.getImageUrl();
      final Widget avatar = imageUrl.contains("iniavatars")?
      new InitialLabel(profile.getAddress()) : new Image(imageUrl);
      avatar.setTitle(profile.getFullName());
      // Try to use MediumAvatarDecorator or similar
      names.add(avatar);
    }
    final AvatarComposite composite = AvatarCompositeFactory.get40().build(names);
    avatars.add(composite);
  }

  @Override
  public void setMessageCounts(final int unread, final int total) {
    if (unread == 0) {
      msgs.setInnerHTML(renderReadMessages(total).asString());
      title.removeClassName(css.unread());
      time.removeClassName(css.unread());
    } else {
      msgs.setInnerHTML(renderUnreadMessages(unread, total).asString());
      title.addClassName(css.unread());
      time.addClassName(css.unread());
    }
  }

  @Override
  public void setSnippet(final String snippet) {
    this.snippet.setInnerText(snippet);
  }

  @Override
  public void setTimestamp(final String time) {
    this.time.setInnerText(time);
  }

  @Override
  public void setTitleText(final String title) {
    this.title.setInnerText(title);
  }
}
