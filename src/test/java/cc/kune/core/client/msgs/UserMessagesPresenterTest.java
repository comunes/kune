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
package cc.kune.core.client.msgs;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.common.client.msgs.CloseCallback;
import cc.kune.common.client.msgs.UserMessage;
import cc.kune.common.client.msgs.UserMessagesPresenter;
import cc.kune.common.client.msgs.UserMessagesPresenter.UserMessagesView;
import cc.kune.common.client.notify.NotifyLevel;

// TODO: Auto-generated Javadoc
/**
 * The Class UserMessagesPresenterTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserMessagesPresenterTest {

  /** The Constant ID_1. */
  private static final String ID_1 = "id1";

  /** The Constant ID_2. */
  private static final String ID_2 = "id2";

  /** The Constant MESSAGE_1. */
  private static final String MESSAGE_1 = "message 1";

  /** The Constant MESSAGE_2. */
  private static final String MESSAGE_2 = "message 2";

  /** The Constant TITLE_1. */
  private static final String TITLE_1 = "title 1";

  /** The Constant TITLE_2. */
  private static final String TITLE_2 = "title 2";

  /** The close callback. */
  private CloseCallback closeCallback;

  /** The msg. */
  private UserMessage msg;

  /** The presenter. */
  private UserMessagesPresenter presenter;

  /** The view. */
  private UserMessagesView view;

  /**
   * Basic msg.
   */
  @Test
  public void basicMsg() {
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false,
        closeCallback);
  }

  /**
   * Before.
   */
  @Before
  public void before() {
    presenter = new UserMessagesPresenter();
    view = Mockito.mock(UserMessagesView.class);
    closeCallback = Mockito.mock(CloseCallback.class);
    presenter.init(view);
    msg = Mockito.mock(UserMessage.class);
    // Mockito.when(msg.getText()).thenReturn(MESSAGE_1);
    Mockito.when(
        view.add((NotifyLevel) Mockito.anyObject(), Mockito.anyString(), Mockito.anyString(),
            Mockito.anyString(), Mockito.anyBoolean(), (CloseCallback) Mockito.anyObject())).thenReturn(
        msg);
  }

  /**
   * Two basic avatar msg.
   */
  @Test
  public void twoBasicAvatarMsg() {
    final NotifyLevel avatar = NotifyLevel.avatar.url("image1.png");
    presenter.add(avatar, TITLE_1, MESSAGE_1, null, false, closeCallback);
    Mockito.when(msg.isAttached()).thenReturn(true);
    presenter.add(avatar, TITLE_1, MESSAGE_2, null, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(avatar, TITLE_1, MESSAGE_1, null, false, closeCallback);
    Mockito.verify(msg, Mockito.times(1)).appendMsg(MESSAGE_2);
  }

  /**
   * Two basic diff avatar msg.
   */
  @Test
  public void twoBasicDiffAvatarMsg() {
    final NotifyLevel avatar1 = NotifyLevel.avatar.url("image1.png");
    presenter.add(avatar1, TITLE_1, MESSAGE_1, ID_1, false, closeCallback);
    final NotifyLevel avatar2 = NotifyLevel.avatar.url("image2.png");
    presenter.add(avatar2, TITLE_1, MESSAGE_1, ID_1, true, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(avatar1, TITLE_1, MESSAGE_1, ID_1, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(avatar2, TITLE_1, MESSAGE_1, ID_1, true, closeCallback);
    Mockito.verify(msg, Mockito.never()).appendMsg(MESSAGE_1);
  }

  /**
   * Two basic diff closeable msg.
   */
  @Test
  public void twoBasicDiffCloseableMsg() {
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_1, false, closeCallback);
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_1, true, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_1, false,
        closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_1, true,
        closeCallback);
    Mockito.verify(msg, Mockito.never()).appendMsg(MESSAGE_1);
  }

  /**
   * Two basic diff id msg.
   */
  @Test
  public void twoBasicDiffIdMsg() {
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_1, false, closeCallback);
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_2, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_1, false,
        closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, ID_2, false,
        closeCallback);
    Mockito.verify(msg, Mockito.never()).appendMsg(MESSAGE_1);
  }

  /**
   * Two basic diff level msg.
   */
  @Test
  public void twoBasicDiffLevelMsg() {
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false, closeCallback);
    presenter.add(NotifyLevel.error, TITLE_1, MESSAGE_2, null, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false,
        closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.error, TITLE_1, MESSAGE_2, null, false,
        closeCallback);
    Mockito.verify(msg, Mockito.never()).appendMsg(MESSAGE_2);
  }

  /**
   * Two basic diff title msg.
   */
  @Test
  public void twoBasicDiffTitleMsg() {
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false, closeCallback);
    presenter.add(NotifyLevel.info, TITLE_2, MESSAGE_1, null, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false,
        closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_2, MESSAGE_1, null, false,
        closeCallback);
    Mockito.verify(msg, Mockito.never()).appendMsg(MESSAGE_1);
  }

  /**
   * Two basic msg.
   */
  @Test
  public void twoBasicMsg() {
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false, closeCallback);
    Mockito.when(msg.isAttached()).thenReturn(true);
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_2, null, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false,
        closeCallback);
    Mockito.verify(msg, Mockito.times(1)).appendMsg(MESSAGE_2);
  }

  /**
   * Two basic msg but after first closed.
   */
  @Test
  public void twoBasicMsgButAfterFirstClosed() {
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false, closeCallback);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false,
        closeCallback);
    presenter.add(NotifyLevel.info, TITLE_1, MESSAGE_2, null, false, closeCallback);
    Mockito.when(msg.isAttached()).thenReturn(false);
    Mockito.verify(view, Mockito.times(1)).add(NotifyLevel.info, TITLE_1, MESSAGE_1, null, false,
        closeCallback);
    Mockito.verify(msg, Mockito.never()).appendMsg(MESSAGE_2);
  }

}
