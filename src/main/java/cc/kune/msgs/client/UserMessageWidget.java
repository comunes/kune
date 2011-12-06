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
package cc.kune.msgs.client;

import org.adamtacy.client.ui.effects.events.EffectCompletedEvent;
import org.adamtacy.client.ui.effects.events.EffectCompletedHandler;
import org.adamtacy.client.ui.effects.examples.Fade;
import org.adamtacy.client.ui.effects.examples.Show;

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.msgs.client.resources.UserMessageImagesUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class UserMessageWidget extends Composite implements HasText, UserMessage {

  public interface MsgTemplate extends SafeHtmlTemplates {
    @Template("<span>{0}</span>")
    SafeHtml format(SafeHtml message);
  }

  public interface MsgWithTitleTemplate extends SafeHtmlTemplates {
    @Template("<span><span class=\"k-msg-title\">{0}</span><p>{1}</p></span>")
    SafeHtml format(SafeHtml title, SafeHtml message);
  }

  interface UserMessageWidgetUiBinder extends UiBinder<Widget, UserMessageWidget> {
  }

  private static final String AVATAR_SIZE = "40px";

  private static String closeTitle = "Close";

  private static int fadeMills = 5000;

  private static final MsgTemplate MSG_NO_TITLE = GWT.create(MsgTemplate.class);

  private static final MsgWithTitleTemplate MSG_WITH_TITLE = GWT.create(MsgWithTitleTemplate.class);
  private static UserMessageWidgetUiBinder uiBinder = GWT.create(UserMessageWidgetUiBinder.class);

  public static void setCloseTitle(final String title) {
    closeTitle = title;
  }

  public static void setFadeMills(final int mills) {
    fadeMills = mills;
  }

  @UiField
  PushButton close;

  private final CloseCallback closeCallback;

  @UiField
  Image icon;

  @UiField
  InlineHTML label;

  @UiField
  HorizontalPanel panel;

  private Timer time;

  public UserMessageWidget(final NotifyLevel level, final String title, final String message,
      final String id, final boolean closeable, final CloseCallback closeCallback) {
    this.closeCallback = closeCallback;
    initWidget(uiBinder.createAndBindUi(this));
    if (TextUtils.notEmpty(id)) {
      close.ensureDebugId(id);
    }
    if (TextUtils.notEmpty(message)) {
      setMsg(title, message);
      close.setVisible(closeable);
      close.setTitle(closeTitle);
      if (!closeable) {
        time = new Timer() {
          @Override
          public void run() {
            close();
          }
        };
        time.schedule(fadeMills);
      }
      setIcon(level);
      final Show anim = new Show(this.getElement());
      anim.setDuration(0.5);
      anim.play();
    }
  }

  public UserMessageWidget(final String message, final CloseCallback closeCallback) {
    this("", message, false, closeCallback);
  }

  public UserMessageWidget(final String title, final String message, final boolean closeable,
      final CloseCallback closeCallback) {
    this(NotifyLevel.info, title, message, "", closeable, closeCallback);
  }

  public UserMessageWidget(final String title, final String message, final CloseCallback closeCallback) {
    this(title, message, false, closeCallback);
  }

  @Override
  public HandlerRegistration addClickHandler(final ClickHandler handler) {
    panel.addStyleName("k-pointer");
    return addDomHandler(handler, ClickEvent.getType());
  }

  @Override
  public void appendMsg(final String message) {
    resetTimer();
    label.setHTML(sanitize(label.getHTML() + "<p>" + message + "</p>"));
  }

  @Override
  public void close() {
    final Fade fade = new Fade(this.getElement());
    fade.setDuration(0.7);
    fade.play();
    fade.addEffectCompletedHandler(new EffectCompletedHandler() {

      @Override
      public void onEffectCompleted(final EffectCompletedEvent event) {
        removeFromParent();
        closeCallback.onClose();
      }
    });
  }

  @Override
  public String getText() {
    return label.getText();
  }

  @UiHandler("close")
  void handleClick(final ClickEvent e) {
    close();
  }

  private void resetTimer() {
    if (time != null) {
      // More time to read it!
      time.schedule(fadeMills);
    }
  }

  private SafeHtml sanitize(final String message) {
    return SafeHtmlUtils.fromTrustedString(message);
  }

  private void setIcon(final NotifyLevel level) {
    if (level.equals(NotifyLevel.avatar)) {
      icon.setUrl(level.getUrl());
      icon.setSize(AVATAR_SIZE, AVATAR_SIZE);
    } else {
      icon.setResource(UserMessageImagesUtil.getIcon(level));
    }
  }

  private void setMsg(final String title, final String message) {
    if (TextUtils.notEmpty(title)) {
      label.setHTML(MSG_WITH_TITLE.format(SimpleHtmlSanitizer.sanitizeHtml(title), sanitize(message)));
    } else {
      label.setHTML(MSG_NO_TITLE.format(sanitize(message)));
    }
  }

  @Override
  public void setText(final String text) {
    label.setText(text);
  }

}
