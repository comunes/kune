/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

public class WizardDialog implements WizardDialogView {

  private final Button backButton;
  protected final DeckPanel deck;
  private final BasicTopDialog dialog;
  private final I18nTranslationService i18n;
  private WizardListener listener;
  private final MaskWidget maskWidget;
  private final Button nextButton;

  public WizardDialog(final String dialogId, final String header, final boolean modal,
      final boolean minimizable, final String width, final String height, final String backId,
      final String nextId, final String finishId, final String cancelId, final String closeId,
      final I18nTranslationService i18n, final MaskWidget maskWidget) {
    this(dialogId, header, modal, minimizable, width, height, backId, nextId, finishId, cancelId,
        closeId, i18n, maskWidget, null);
  }

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

  @Override
  public void add(final IsWidget view) {
    deck.add(toWidget(view));
  }

  @Override
  public void clear() {
  }

  protected int getWidgetCount(final IsWidget view) {
    if (view instanceof Widget) {
      return deck.getWidgetIndex((Widget) view);
    }
    return -1;
  }

  @Override
  public void hide() {
    dialog.hide();
  }

  @Override
  public boolean isCurrentPage(final IsWidget view) {
    final int visibleWidgetIndex = deck.getVisibleWidget();
    if (visibleWidgetIndex == -1) {
      return false;
    } else {
      return deck.getWidget(visibleWidgetIndex).equals(toWidget(view));
    }
  }

  @Override
  public void mask(final String message) {
    maskWidget.mask(dialog, message);
  }

  @Override
  public void maskProcessing() {
    maskWidget.mask(dialog, i18n.t("Processing"));
  }

  @Override
  public void remove(final IsWidget view) {
    final int count = getWidgetCount(view);
    if (count != -1) {
      deck.remove(count);
    }
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
    backButton.setEnabled(enabled);
  }

  @Override
  public void setEnabledCancelButton(final boolean enabled) {
    dialog.setFirstBtnEnabled(enabled);
  }

  @Override
  public void setEnabledFinishButton(final boolean enabled) {
    dialog.setSecondBtnEnabled(enabled);
  }

  @Override
  public void setEnabledNextButton(final boolean enabled) {
    nextButton.setEnabled(enabled);
  }

  @Override
  public void setFinishText(final String text) {
    dialog.setSecondBtnText(text);
  }

  public void setIcon(final ImageResource icon) {
    dialog.setTitleIcon(icon);
  }

  @Override
  public void setListener(final WizardListener listener) {
    this.listener = listener;
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
    dialog.setFirstBtnVisible(visible);
  }

  @Override
  public void setVisibleFinishButton(final boolean visible) {
    dialog.setSecondBtnVisible(visible);
  }

  @Override
  public void setVisibleNextButton(final boolean visible) {
    nextButton.setVisible(visible);
  }

  @Override
  public void show() {
    dialog.showCentered();
  }

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

  protected Widget toWidget(final IsWidget view) {
    if (view instanceof Widget) {
      return (Widget) view;
    } else {
      NotifyUser.error("Trying to add a unknown element in WizardDialog");
      return null;
    }
  }

  @Override
  public void unMask() {
    maskWidget.unMask();
  }
}
