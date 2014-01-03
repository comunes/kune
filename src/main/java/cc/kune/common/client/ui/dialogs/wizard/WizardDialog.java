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
package cc.kune.common.client.ui.dialogs.wizard;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.MaskWidget;
import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.client.ui.dialogs.BasicTopDialog.Builder;
import cc.kune.common.shared.i18n.I18nTranslationService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class WizardDialog.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class WizardDialog implements WizardDialogView {

  /** The back button. */
  private final Button backButton;
  
  /** The deck. */
  protected final DeckPanel deck;
  
  /** The dialog. */
  private final BasicTopDialog dialog;
  
  /** The i18n. */
  private final I18nTranslationService i18n;
  
  /** The listener. */
  private WizardListener listener;
  
  /** The mask widget. */
  private final MaskWidget maskWidget;
  
  /** The next button. */
  private final Button nextButton;

  /**
   * Instantiates a new wizard dialog.
   *
   * @param dialogId the dialog id
   * @param header the header
   * @param modal the modal
   * @param minimizable the minimizable
   * @param width the width
   * @param height the height
   * @param backId the back id
   * @param nextId the next id
   * @param finishId the finish id
   * @param cancelId the cancel id
   * @param closeId the close id
   * @param i18n the i18n
   * @param maskWidget the mask widget
   */
  public WizardDialog(final String dialogId, final String header, final boolean modal,
      final boolean minimizable, final String width, final String height, final String backId,
      final String nextId, final String finishId, final String cancelId, final String closeId,
      final I18nTranslationService i18n, final MaskWidget maskWidget) {
    this(dialogId, header, modal, minimizable, width, height, backId, nextId, finishId, cancelId,
        closeId, i18n, maskWidget, null);
  }

  /**
   * Instantiates a new wizard dialog.
   *
   * @param dialogId the dialog id
   * @param header the header
   * @param modal the modal
   * @param minimizable the minimizable
   * @param width the width
   * @param height the height
   * @param backId the back id
   * @param nextId the next id
   * @param finishId the finish id
   * @param cancelId the cancel id
   * @param closeId the close id
   * @param i18n the i18n
   * @param maskWidget the mask widget
   * @param listener the listener
   */
  public WizardDialog(final String dialogId, final String header, final boolean modal,
      final boolean minimizable, final String width, final String height, final String backId,
      final String nextId, final String finishId, final String cancelId, final String closeId,
      final I18nTranslationService i18n, final MaskWidget maskWidget, final WizardListener listener) {
    this.maskWidget = maskWidget;
    this.listener = listener;
    this.i18n = i18n;
    final Builder dialogBuilder = new BasicTopDialog.Builder(dialogId, false, modal, i18n.getDirection()).width(
        width).height(height).firstButtonId(cancelId).sndButtonId(finishId).title(header);
    dialog = dialogBuilder.build();
    dialog.setFirstBtnText(i18n.tWithNT("Cancel", "used in button"));
    dialog.getFirstBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        WizardDialog.this.listener.onCancel();
      }
    });

    nextButton = new Button(i18n.tWithNT("Next »", "used in button"), new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        WizardDialog.this.listener.onNext();
      }
    });
    nextButton.ensureDebugId(nextId);
    nextButton.addStyleName("k-dialog-btn");
    nextButton.addStyleName("k-dialog-secondBtn");
    nextButton.addStyleName("k-5corners");
    nextButton.addStyleName("k-button");
    nextButton.addStyleName("kune-Margin-Medium-l");
    dialog.getBtnPanel().add(nextButton);

    dialog.getClose().addCloseHandler(new CloseHandler<PopupPanel>() {

      @Override
      public void onClose(final CloseEvent<PopupPanel> event) {
        WizardDialog.this.listener.onClose();
      }
    });

    backButton = new Button(i18n.tWithNT("« Back", "used in button"), new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        WizardDialog.this.listener.onBack();
      }
    });
    backButton.ensureDebugId(backId);
    backButton.addStyleName("k-dialog-btn");
    backButton.addStyleName("k-dialog-secondBtn");
    backButton.addStyleName("k-5corners");
    backButton.addStyleName("k-button");
    backButton.addStyleName("kune-Margin-Medium-l");
    dialog.getBtnPanel().add(backButton);

    dialog.setSecondBtnId(finishId);
    dialog.setSecondBtnText(i18n.tWithNT("Finish", "used in button"));
    dialog.getSecondBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        WizardDialog.this.listener.onFinish();
      }
    });

    deck = new DeckPanel();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#add(com.google.gwt.user.client.ui.IsWidget)
   */
  @Override
  public void add(final IsWidget view) {
    deck.add(toWidget(view));
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#clear()
   */
  @Override
  public void clear() {
  }

  /**
   * Gets the widget count.
   *
   * @param view the view
   * @return the widget count
   */
  protected int getWidgetCount(final IsWidget view) {
    if (view instanceof Widget) {
      return deck.getWidgetIndex((Widget) view);
    }
    return -1;
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#hide()
   */
  @Override
  public void hide() {
    dialog.hide();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#isCurrentPage(com.google.gwt.user.client.ui.IsWidget)
   */
  @Override
  public boolean isCurrentPage(final IsWidget view) {
    final int visibleWidgetIndex = deck.getVisibleWidget();
    if (visibleWidgetIndex == -1) {
      return false;
    } else {
      return deck.getWidget(visibleWidgetIndex).equals(toWidget(view));
    }
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#mask(java.lang.String)
   */
  @Override
  public void mask(final String message) {
    maskWidget.mask(dialog, message);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#maskProcessing()
   */
  @Override
  public void maskProcessing() {
    maskWidget.mask(dialog, i18n.t("Processing"));
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#remove(com.google.gwt.user.client.ui.IsWidget)
   */
  @Override
  public void remove(final IsWidget view) {
    final int count = getWidgetCount(view);
    if (count != -1) {
      deck.remove(count);
    }
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#setEnabled(boolean, boolean, boolean, boolean)
   */
  @Override
  public void setEnabled(final boolean back, final boolean next, final boolean cancel,
      final boolean finish) {
    setEnabledBackButton(back);
    setEnabledNextButton(next);
    setEnabledCancelButton(cancel);
    setEnabledFinishButton(finish);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#setEnabledBackButton(boolean)
   */
  @Override
  public void setEnabledBackButton(final boolean enabled) {
    backButton.setEnabled(enabled);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#setEnabledCancelButton(boolean)
   */
  @Override
  public void setEnabledCancelButton(final boolean enabled) {
    dialog.setFirstBtnEnabled(enabled);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#setEnabledFinishButton(boolean)
   */
  @Override
  public void setEnabledFinishButton(final boolean enabled) {
    dialog.setSecondBtnEnabled(enabled);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#setEnabledNextButton(boolean)
   */
  @Override
  public void setEnabledNextButton(final boolean enabled) {
    nextButton.setEnabled(enabled);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#setFinishText(java.lang.String)
   */
  @Override
  public void setFinishText(final String text) {
    dialog.setSecondBtnText(text);
  }

  /**
   * Sets the icon.
   *
   * @param icon the new icon
   */
  public void setIcon(final ImageResource icon) {
    dialog.setTitleIcon(icon);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#setListener(cc.kune.common.client.ui.dialogs.wizard.WizardListener)
   */
  @Override
  public void setListener(final WizardListener listener) {
    this.listener = listener;
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#setVisible(boolean, boolean, boolean, boolean)
   */
  @Override
  public void setVisible(final boolean back, final boolean next, final boolean cancel,
      final boolean finish) {
    setVisibleBackButton(back);
    setVisibleNextButton(next);
    setVisibleCancelButton(cancel);
    setVisibleFinishButton(finish);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#setVisibleBackButton(boolean)
   */
  @Override
  public void setVisibleBackButton(final boolean visible) {
    backButton.setVisible(visible);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#setVisibleCancelButton(boolean)
   */
  @Override
  public void setVisibleCancelButton(final boolean visible) {
    dialog.setFirstBtnVisible(visible);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#setVisibleFinishButton(boolean)
   */
  @Override
  public void setVisibleFinishButton(final boolean visible) {
    dialog.setSecondBtnVisible(visible);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#setVisibleNextButton(boolean)
   */
  @Override
  public void setVisibleNextButton(final boolean visible) {
    nextButton.setVisible(visible);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#show()
   */
  @Override
  public void show() {
    dialog.showCentered();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#show(com.google.gwt.user.client.ui.IsWidget)
   */
  @Override
  public void show(final IsWidget view) {
    final int count = getWidgetCount(view);
    if (count != -1) {
      deck.showWidget(count);
      dialog.getInnerPanel().add(deck);
    } else {
      NotifyUser.error("Widget not found in deck of WizardDialog");
    }

  }

  /**
   * To widget.
   *
   * @param view the view
   * @return the widget
   */
  protected Widget toWidget(final IsWidget view) {
    if (view instanceof Widget) {
      return (Widget) view;
    } else {
      NotifyUser.error("Trying to add a unknown element in WizardDialog");
      return null;
    }
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.wizard.WizardDialogView#unMask()
   */
  @Override
  public void unMask() {
    maskWidget.unMask();
  }
}
