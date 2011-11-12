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
package cc.kune.common.client.ui.dialogs.tabbed;

import cc.kune.common.client.ProvidersCollection;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.client.ui.dialogs.BasicTopDialog.Builder;
import cc.kune.common.client.ui.dialogs.MessageToolbar;
import cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter.AbstractTabbedDialogView;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public abstract class AbstractTabbedDialogPanel extends ViewImpl implements AbstractTabbedDialogView {
  private static final int NO_SIZE = -1;
  private final boolean autohide;
  private BasicTopDialog dialog;
  private final String dialogId;
  private final Direction direction;
  private final String errorLabelId;
  private final String firstBtnId;
  private final String firstBtnTitle;
  private int height = NO_SIZE;
  private ImageResource icon;
  private String iconCls;
  private final NotifyLevelImages images;
  private MessageToolbar messageErrorBar;
  private final boolean modal;
  private final ProvidersCollection provCollection;
  private final String sndBtnId;
  private final String sndBtnTitle;
  private TabLayoutPanel tabPanel;
  private String title;
  private int width = NO_SIZE;

  public AbstractTabbedDialogPanel(final String dialogId, final String title, final boolean modal,
      final boolean autoHide, final NotifyLevelImages images, final String errorLabelId,
      final String firstBtnTitle, final String firstBtnId, final String sndBtnTitle,
      final String sndBtnId, final ProvidersCollection provCollection, final Direction direction) {
    this.dialogId = dialogId;
    this.title = title;
    this.modal = modal;
    this.images = images;
    this.errorLabelId = errorLabelId;
    this.firstBtnTitle = firstBtnTitle;
    this.firstBtnId = firstBtnId;
    this.sndBtnTitle = sndBtnTitle;
    this.sndBtnId = sndBtnId;
    this.provCollection = provCollection;
    this.autohide = autoHide;
    this.direction = direction;
  }

  public AbstractTabbedDialogPanel(final String dialogId, final String title, final boolean modal,
      final NotifyLevelImages images, final String errorLabelId, final String firstBtnTitle,
      final String firstBtnId, final String sndBtnTitle, final String sndBtnId,
      final ProvidersCollection provCollection, final Direction direction) {
    this(dialogId, title, NO_SIZE, NO_SIZE, modal, false, images, errorLabelId, firstBtnTitle,
        firstBtnId, sndBtnTitle, sndBtnId, provCollection, direction);
  }

  public AbstractTabbedDialogPanel(final String dialogId, final String title, final int width,
      final int height, final boolean modal, final boolean autoHide, final NotifyLevelImages images,
      final String errorLabelId, final String firstBtnTitle, final String firstBtnId,
      final String sndBtnTitle, final String sndBtnId, final ProvidersCollection provCollection,
      final Direction direction) {
    this(dialogId, title, modal, autoHide, images, errorLabelId, firstBtnTitle, firstBtnId, sndBtnTitle,
        sndBtnId, provCollection, direction);
    this.width = width;
    this.height = height;
  }

  public AbstractTabbedDialogPanel(final String dialogId, final String title, final int width,
      final int height, final boolean modal, final NotifyLevelImages images, final String errorLabelId,
      final String firstBtnTitle, final String firstBtnId, final String sndBtnTitle,
      final String sndBtnId, final ProvidersCollection provCollection, final Direction direction) {
    this(dialogId, title, width, height, modal, false, images, errorLabelId, firstBtnTitle, firstBtnId,
        sndBtnTitle, sndBtnId, provCollection, direction);
  }

  @Override
  public void activateTab(final int index) {
    createDialogIfNecessary();
    tabPanel.selectTab(index);
  }

  @Override
  public void addTab(final IsWidget view, final IsWidget tabTitle) {
    createDialogIfNecessary();
    tabPanel.add(view, tabTitle);
    setPositions();
  }

  @Override
  public Widget asWidget() {
    createDialogIfNecessary();
    return dialog;
  }

  private void createDialog() {
    final Builder builder = new BasicTopDialog.Builder(dialogId, autohide, modal, direction).autoscroll(
        true).icon(iconCls).firstButtonId(firstBtnId).firstButtonTitle(firstBtnTitle).sndButtonId(
        dialogId).sndButtonTitle(sndBtnTitle).sndButtonId(sndBtnId).title(title);
    if (width != NO_SIZE) {
      builder.width(String.valueOf(width + 20) + "px");
    }
    if (height != NO_SIZE) {
      builder.height(String.valueOf(height + 20) + "px");
    }
    dialog = builder.build();
    if (icon != null) {
      dialog.setTitleIcon(icon);
    }
    messageErrorBar = new MessageToolbar(images, errorLabelId);
    tabPanel = new TabLayoutPanel(25, Unit.PX);
    // tabPanel.addStyleName("oc-noselect");
    dialog.getInnerPanel().add(tabPanel);
    tabPanel.addSelectionHandler(new SelectionHandler<Integer>() {
      @Override
      public void onSelection(final SelectionEvent<Integer> event) {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
          @Override
          public void execute() {
            dialog.showCentered();
          }
        });
      }
    });
    provCollection.createAll();
    if (width != NO_SIZE) {
      tabPanel.setWidth(String.valueOf(width) + "px");
    }
    if (height != NO_SIZE) {
      tabPanel.setHeight(String.valueOf(height) + "px");
    }
    tabPanel.addStyleName("k-tabpanel-aditionalpadding");
    tabPanel.addStyleName("k-tabs");
    dialog.getFirstBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        hide();
      }
    });
  }

  private void createDialogIfNecessary() {
    if (dialog == null) {
      createDialog();
    }
  }

  @Override
  public void destroy() {
    if (dialog != null) {
      dialog.removeFromParent();
      dialog = null;
    }
  }

  public HasCloseHandlers<?> getClose() {
    createDialogIfNecessary();
    return dialog.getClose();
  }

  public HasClickHandlers getFirstBtn() {
    createDialogIfNecessary();
    return dialog.getFirstBtn();
  }

  public ForIsWidget getInnerPanel() {
    createDialogIfNecessary();
    return dialog.getInnerPanel();
  }

  public HasClickHandlers getSecondBtn() {
    createDialogIfNecessary();
    return dialog.getSecondBtn();
  }

  @Override
  public void hide() {
    if (dialog != null) {
      if (dialog.isVisible()) {
        dialog.hide();
      }
    }
  }

  @Override
  public void hideMessages() {
    if (dialog != null) {
      messageErrorBar.hideErrorMessage();
    }
  }

  @Override
  public void insertTab(final IsWidget tab, final IsWidget tabTitle, final int index) {
    createDialogIfNecessary();
    tabPanel.insert(tab, tabTitle, index);
    setPositions();
  }

  public boolean isVisible() {
    createDialogIfNecessary();
    return dialog.isVisible();
  }

  @Override
  public void setErrorMessage(final String message, final NotifyLevel level) {
    messageErrorBar.setErrorMessage(message, level);
  }

  public void setFirstTabActive() {
    tabPanel.selectTab(0);
  }

  public void setIcon(final ImageResource icon) {
    this.icon = icon;
  }

  public void setIconCls(final String iconCls) {
    this.iconCls = iconCls;
    if (dialog != null) {
      dialog.setTitleIcon(iconCls);
    }
  }

  // private void setPosition(final IsWidget widget) {
  // // DOM.setStyleAttribute(widget.asWidget().getElement(), "position",
  // // "relative");
  // //
  // (widget.asWidget()).getParent().getElement().getStyle().setPosition(Position.RELATIVE);
  // }

  private void setPositions() {
    for (int i = 0; i < tabPanel.getWidgetCount(); i++) {
      final Widget widget = tabPanel.getWidget(i);
      DOM.setStyleAttribute(widget.getElement(), "position", "relative");

      final Element parent = DOM.getParent(widget.getElement());
      DOM.setStyleAttribute(parent, "overflowX", "visible");
      DOM.setStyleAttribute(parent, "overflowY", "visible");
      DOM.setStyleAttribute(parent, "position", "relative");
    }
  }

  public void setTitle(final String title) {
    this.title = title;
    if (dialog != null) {
      dialog.getTitleText().setText(title, direction);
    }
  }

  @Override
  public void show() {
    showImpl();
    setFirstTabActive();
  }

  private void showImpl() {
    createDialogIfNecessary();
    hideMessages();
    dialog.showCentered();
  }

}
