/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

package cc.kune.wave.client;

import static com.google.gwt.query.client.GQuery.$;
import br.com.rpa.client._paperelements.PaperFab;
import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.polymer.client.PolymerId;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CustomEditToolbar {

  public interface Listener {
    void onEdit();

    void onEditDone();

    void onReply();
  }

  final ClickHandler editDoneHandler = new ClickHandler() {
    @Override
    public void onClick(final ClickEvent event) {
      onClickEditDone();
    }
  };

  private final PaperFab editDoneInGroup;

  private final PaperFab editDoneInInbox;
  final ClickHandler editHandler = new ClickHandler() {
    @Override
    public void onClick(final ClickEvent event) {
      onClickEdit();
    }
  };
  private final PaperFab editInGroup;

  private final PaperFab editInInbox;

  private Listener listener;
  final ClickHandler replyHandler = new ClickHandler() {
    @Override
    public void onClick(final ClickEvent event) {
      onClickReply();
    }
  };
  private final PaperFab replyInGroup;
  private final PaperFab replyInInbox;

  @Inject
  public CustomEditToolbar(final GlobalShortcutRegister shorcutRegister) {
    // KeyStroke ctrlE = Shortcut.getShortcut(true,'E');
    // shorcutRegister.put(ctrlE, new AbstractAction() {
    // @Override
    // public void actionPerformed(ActionEvent event) {
    // onClickEdit();
    // }
    // });
    //
    // DEFAULT_BINDINGS.put(KeyCombo.CTRL_E, Action.EDIT_BLIP);
    //
    // DEFAULT_BINDINGS.put(KeyCombo.CTRL_R, Action.REPLY_TO_BLIP);
    // DEFAULT_BINDINGS.put(KeyCombo.CTRL_ENTER, Action.REPLY_TO_BLIP);
    // DEFAULT_BINDINGS.put(KeyCombo.ENTER, Action.REPLY_TO_BLIP);
    //
    // DEFAULT_BINDINGS.put(KeyCombo.SHIFT_ENTER, Action.CONTINUE_THREAD);
    // DEFAULT_BINDINGS.put(KeyCombo.SHIFT_DELETE, Action.DELETE_BLIP);

    final String editText = I18n.t("Edit") + " (Ctrl-E)";
    editInGroup = wrapBtn(editHandler, PolymerId.EDIT_DOCGROUP_FAB.getId(), editText);
    editInInbox = wrapBtn(editHandler, PolymerId.EDIT_INBOX_FAB.getId(), editText);

    final String editDoneText = I18n.t("Done");
    editDoneInGroup = wrapBtn(editDoneHandler, PolymerId.EDITDONE_GROUP_FAB.getId(), editDoneText);
    editDoneInInbox = wrapBtn(editDoneHandler, PolymerId.EDITDONE_INBOX_FAB.getId(), editDoneText);

    final String replyText = I18n.t("Reply" + " (Ctrl-R)");
    replyInGroup = wrapBtn(replyHandler, PolymerId.REPLY_GROUP_FAB.getId(), replyText);
    replyInInbox = wrapBtn(replyHandler, PolymerId.REPLY_INBOX_FAB.getId(), replyText);

    setEditAndReplyVisible(false);
    setEditDoneVisible(false);
  }

  public boolean isEditEnable() {
    return editInGroup.isEnabled();
  }

  public void onClickEdit() {
    if (listener != null) {
      listener.onEdit();
    }
  }

  private void onClickEditDone() {
    if (listener != null) {
      listener.onEditDone();
    }
  }

  private void onClickReply() {
    if (listener != null) {
      listener.onReply();
    }
  }

  public void setEditAndReplyVisible(final boolean visible) {
    editInGroup.setVisible(visible);
    editInInbox.setVisible(visible);
    replyInGroup.setVisible(visible);
    replyInInbox.setVisible(visible);
    // Dirty workaround while we hide all edit links

    $(".org-waveprotocol-wave-client-wavepanel-view-dom-full-BlipViewBuilder-Css-menu").hide();
  }

  public void setEditDoneVisible(final boolean visible) {
    editDoneInGroup.setVisible(visible);
    editDoneInInbox.setVisible(visible);
  }

  public void setEnable(final boolean enable) {
    editInGroup.setEnabled(enable);
    editInInbox.setEnabled(enable);
    replyInGroup.setEnabled(enable);
    replyInInbox.setEnabled(enable);
  }

  public void setListener(final Listener listener) {
    this.listener = listener;
  }

  private PaperFab wrapBtn(final ClickHandler clickHandler, final String id, final String text) {
    final PaperFab btn = PaperFab.wrap(id);
    Tooltip.to(btn, text);
    btn.addStyleName(ActionStyles.BTN_EDIT);
    btn.addClickHandler(clickHandler);
    return btn;
  }
}
