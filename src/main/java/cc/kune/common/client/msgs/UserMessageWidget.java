/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.common.client.msgs;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Effects;
import cc.kune.common.client.msgs.resources.UserMessageImagesUtil;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.ui.Animations;
import cc.kune.common.shared.utils.TextUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.query.client.Function;
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

// TODO: Auto-generated Javadoc
/**
 * The Class UserMessageWidget.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserMessageWidget extends Composite implements HasText, UserMessage {

  /**
   * The Interface MsgTemplate.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface MsgTemplate extends SafeHtmlTemplates {
    
    /**
     * Format.
     *
     * @param message the message
     * @return the safe html
     */
    @Template("<span>{0}</span>")
    SafeHtml format(SafeHtml message);
  }

  /**
   * The Interface MsgWithTitleTemplate.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface MsgWithTitleTemplate extends SafeHtmlTemplates {
    
    /**
     * Format.
     *
     * @param title the title
     * @param message the message
     * @return the safe html
     */
    @Template("<span><span class=\"k-msg-title\">{0}</span><p>{1}</p></span>")
    SafeHtml format(SafeHtml title, SafeHtml message);
  }

  /**
   * The Interface UserMessageWidgetUiBinder.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface UserMessageWidgetUiBinder extends UiBinder<Widget, UserMessageWidget> {
  }

  /** The Constant AVATAR_SIZE. */
  private static final String AVATAR_SIZE = "40px";

  /** The close title. */
  private static String closeTitle = "Close";

  /** The fade mills. */
  private static int fadeMills = 5000;

  /** The Constant MSG_NO_TITLE. */
  private static final MsgTemplate MSG_NO_TITLE = GWT.create(MsgTemplate.class);

  /** The Constant MSG_WITH_TITLE. */
  private static final MsgWithTitleTemplate MSG_WITH_TITLE = GWT.create(MsgWithTitleTemplate.class);
  
  /** The ui binder. */
  private static UserMessageWidgetUiBinder uiBinder = GWT.create(UserMessageWidgetUiBinder.class);

  /**
   * Sets the close title.
   *
   * @param title the new close title
   */
  public static void setCloseTitle(final String title) {
    closeTitle = title;
  }

  /**
   * Sets the fade mills.
   *
   * @param mills the new fade mills
   */
  public static void setFadeMills(final int mills) {
    fadeMills = mills;
  }

  /** The close. */
  @UiField
  PushButton close;

  /** The close callback. */
  private final CloseCallback closeCallback;

  /** The icon. */
  @UiField
  Image icon;

  /** The label. */
  @UiField
  InlineHTML label;

  /** The panel. */
  @UiField
  HorizontalPanel panel;

  /** The time. */
  private Timer time;

  /**
   * Instantiates a new user message widget.
   *
   * @param level the level
   * @param title the title
   * @param message the message
   * @param id the id
   * @param closeable the closeable
   * @param closeCallback the close callback
   */
  public UserMessageWidget(final NotifyLevel level, final String title, final String message,
      final String id, final boolean closeable, final CloseCallback closeCallback) {
    this.closeCallback = closeCallback;
    initWidget(uiBinder.createAndBindUi(this));
    getElement().getStyle().setOpacity(0);
    // setVisible(false);
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
      if (Animations.enabled) {
        $(this).as(Effects).fadeTo(200, 1d);
      } else {
        setVisible(true);
      }

    }
  }

  /**
   * Instantiates a new user message widget.
   *
   * @param message the message
   * @param closeCallback the close callback
   */
  public UserMessageWidget(final String message, final CloseCallback closeCallback) {
    this("", message, false, closeCallback);
  }

  /**
   * Instantiates a new user message widget.
   *
   * @param title the title
   * @param message the message
   * @param closeable the closeable
   * @param closeCallback the close callback
   */
  public UserMessageWidget(final String title, final String message, final boolean closeable,
      final CloseCallback closeCallback) {
    this(NotifyLevel.info, title, message, "", closeable, closeCallback);
  }

  /**
   * Instantiates a new user message widget.
   *
   * @param title the title
   * @param message the message
   * @param closeCallback the close callback
   */
  public UserMessageWidget(final String title, final String message, final CloseCallback closeCallback) {
    this(title, message, false, closeCallback);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.dom.client.HasClickHandlers#addClickHandler(com.google.gwt.event.dom.client.ClickHandler)
   */
  @Override
  public HandlerRegistration addClickHandler(final ClickHandler handler) {
    panel.addStyleName("k-pointer");
    return addDomHandler(handler, ClickEvent.getType());
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.msgs.UserMessage#appendMsg(java.lang.String)
   */
  @Override
  public void appendMsg(final String message) {
    resetTimer();
    label.setHTML(sanitize(label.getHTML() + "<p class='k-msg-topsep'>" + message + "</p>"));
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.msgs.UserMessage#close()
   */
  @Override
  public void close() {
    $(this).as(Effects).fadeOut(300, new Function() {
      @Override
      public void f() {
        removeFromParent();
        setVisible(false);
        closeCallback.onClose();
      }
    });

  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.HasText#getText()
   */
  @Override
  public String getText() {
    return label.getText();
  }

  /**
   * Handle click.
   *
   * @param e the e
   */
  @UiHandler("close")
  void handleClick(final ClickEvent e) {
    close();
  }

  /**
   * Reset timer.
   */
  private void resetTimer() {
    if (time != null) {
      // More time to read it!
      time.schedule(fadeMills);
    }
  }

  /**
   * Sanitize.
   *
   * @param message the message
   * @return the safe html
   */
  private SafeHtml sanitize(final String message) {
    return SafeHtmlUtils.fromTrustedString(message);
  }

  /**
   * Sets the icon.
   *
   * @param level the new icon
   */
  private void setIcon(final NotifyLevel level) {
    if (level.equals(NotifyLevel.avatar)) {
      icon.setUrl(level.getUrl());
      icon.setSize(AVATAR_SIZE, AVATAR_SIZE);
    } else {
      icon.setResource(UserMessageImagesUtil.getIcon(level));
    }
  }

  /**
   * Sets the msg.
   *
   * @param title the title
   * @param message the message
   */
  private void setMsg(final String title, final String message) {
    if (TextUtils.notEmpty(title)) {
      label.setHTML(MSG_WITH_TITLE.format(SimpleHtmlSanitizer.sanitizeHtml(title), sanitize(message)));
    } else {
      label.setHTML(MSG_NO_TITLE.format(sanitize(message)));
    }
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.HasText#setText(java.lang.String)
   */
  @Override
  public void setText(final String text) {
    label.setText(text);
  }

}
