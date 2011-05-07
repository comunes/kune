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
package org.ourproject.kune.platf.client.ui.dialogs;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.noti.OldNotifyUser;

import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.WindowListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;

public class WizardDialog implements WizardDialogView {

  private final Button backButton;
  private final Button cancelButton;
  private final DeckPanel deck;
  private final BasicDialog dialog;
  private final Button finishButton;
  private final I18nTranslationService i18n;
  private final Panel mainPanel;
  private final Button nextButton;

  public WizardDialog(final String dialogId, final String caption, final boolean modal,
      final boolean minimizable, final int width, final int height, final int minWidth,
      final int minHeight, final String backId, final String nextId, final String finishId,
      final String cancelId, final String closeId, final I18nTranslationService i18n,
      final WizardListener listener) {
    dialog = new BasicDialog(dialogId, caption, modal, false, width, height, minWidth, minHeight);
    this.i18n = i18n;
    dialog.setCollapsible(minimizable);
    dialog.setShadow(true);
    dialog.setPlain(true);
    dialog.setCollapsible(false);
    dialog.setResizable(false);
    dialog.setId(dialogId);

    backButton = new Button(i18n.tWithNT("« Back", "used in button"), new ButtonListenerAdapter() {
      @Override
      public void onClick(final Button button, final EventObject e) {
        listener.onBack();
      }
    });
    backButton.setId(backId);
    dialog.addButton(backButton);

    nextButton = new Button(i18n.tWithNT("Next »", "used in button"), new ButtonListenerAdapter() {
      @Override
      public void onClick(final Button button, final EventObject e) {
        listener.onNext();
      }
    });
    nextButton.setId(nextId);
    dialog.addButton(nextButton);

    cancelButton = new Button(i18n.tWithNT("Cancel", "used in button"), new ButtonListenerAdapter() {
      @Override
      public void onClick(final Button button, final EventObject e) {
        listener.onCancel();
      }
    });
    cancelButton.setId(cancelId);
    dialog.addButton(cancelButton);

    finishButton = new Button(i18n.tWithNT("Finish", "used in button"), new ButtonListenerAdapter() {
      @Override
      public void onClick(final Button button, final EventObject e) {
        listener.onFinish();
      }
    });
    finishButton.setId(finishId);
    dialog.addButton(finishButton);

    dialog.addListener(new WindowListenerAdapter() {
      @Override
      public void onHide(final Component component) {
        listener.onClose();
      }
    });
    mainPanel = new Panel();
    mainPanel.setLayout(new FitLayout());
    deck = new DeckPanel();
    mainPanel.add(deck);
  }

  public WizardDialog(final String dialogId, final String caption, final boolean modal,
      final boolean minimizable, final int width, final int height, final WizardListener listener,
      final I18nTranslationService i18n, final String backId, final String nextId,
      final String finishId, final String cancelId, final String closeId) {
    this(dialogId, caption, modal, minimizable, width, height, width, height, backId, nextId, finishId,
        cancelId, closeId, i18n, listener);
  }

  @Override
  public void add(final View view) {
    deck.add(toWidget(view));
    doLayoutIfNeeded();
  }

  @Override
  public void center() {
    dialog.center();
  }

  @Override
  public void clear() {
    dialog.clear();
  }

  private void doLayoutIfNeeded() {
    if (dialog.isRendered()) {
      dialog.doLayout();
      mainPanel.syncSize();
      mainPanel.doLayout();
    }
  }

  private int getWidgetCount(final View view) {
    if (view instanceof Widget) {
      return deck.getWidgetIndex((Widget) view);
    } else if (view instanceof DefaultForm) {
      return deck.getWidgetIndex(((DefaultForm) view).getFormPanel());
    }
    return -1;
  }

  @Override
  public void hide() {
    dialog.hide();
  }

  @Override
  public boolean isCurrentPage(final View view) {
    final int visibleWidgetIndex = deck.getVisibleWidget();
    if (visibleWidgetIndex == -1) {
      return false;
    } else {
      return deck.getWidget(visibleWidgetIndex).equals(toWidget(view));
    }
  }

  @Override
  public void mask(final String message) {
    dialog.getEl().mask(message, "x-mask-loading");
  }

  @Override
  public void maskProcessing() {
    mask(i18n.t("Processing"));
  }

  @Override
  public void remove(final View view) {
    final int count = getWidgetCount(view);
    if (count != -1) {
      deck.remove(count);
    }
  }

  public void setBottomToolbar1(final Toolbar toolbar) {
    dialog.setBottomToolbar(toolbar);
  }

  @Override
  public void setEnabled(final boolean back, final boolean next, final boolean cancel,
      final boolean finish) {
    setEnabledBackButton(back);
    setEnabledNextButton(next);
    setEnabledCancelButton(cancel);
    setEnabledFinishButton(finish);
  }

  @Override
  public void setEnabledBackButton(final boolean enabled) {
    if (enabled) {
      backButton.enable();
    } else {
      backButton.disable();
    }
  }

  @Override
  public void setEnabledCancelButton(final boolean enabled) {
    if (enabled) {
      cancelButton.enable();
    } else {
      cancelButton.disable();
    }
  }

  @Override
  public void setEnabledFinishButton(final boolean enabled) {
    if (enabled) {
      finishButton.enable();
    } else {
      finishButton.disable();
    }
  }

  @Override
  public void setEnabledNextButton(final boolean enabled) {
    if (enabled) {
      nextButton.enable();
    } else {
      nextButton.disable();
    }
  }

  @Override
  public void setFinishText(final String text) {
    finishButton.setText(text);
  }

  public void setIconCls(final String iconCls) {
    dialog.setIconCls(iconCls);
  }

  @Override
  public void setVisible(final boolean back, final boolean next, final boolean cancel,
      final boolean finish) {
    setVisibleBackButton(back);
    setVisibleNextButton(next);
    setVisibleCancelButton(cancel);
    setVisibleFinishButton(finish);
  }

  @Override
  public void setVisibleBackButton(final boolean visible) {
    backButton.setVisible(visible);
  }

  @Override
  public void setVisibleCancelButton(final boolean visible) {
    cancelButton.setVisible(visible);
  }

  @Override
  public void setVisibleFinishButton(final boolean visible) {
    finishButton.setVisible(visible);
  }

  @Override
  public void setVisibleNextButton(final boolean visible) {
    nextButton.setVisible(visible);
  }

  @Override
  public void show() {
    dialog.show();
  }

  @Override
  public void show(final View view) {
    final int count = getWidgetCount(view);
    if (count != -1) {
      deck.showWidget(count);
      dialog.add(mainPanel);
      doLayoutIfNeeded();
    } else {
      OldNotifyUser.error("Widget not found in deck of WizardDialog");
    }

  }

  private Widget toWidget(final View view) {
    if (view instanceof Widget) {
      return (Widget) view;
    } else if (view instanceof DefaultForm) {
      return ((DefaultForm) view).getFormPanel();
    } else {
      OldNotifyUser.error("Trying to add a unknown element in WizardDialog");
      return null;
    }
  }

  @Override
  public void unMask() {
    dialog.getEl().unmask();
  }
}
