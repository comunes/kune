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
package cc.kune.core.client.ui.dialogs.tabbed;

import cc.kune.common.client.ProvidersCollection;
import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.client.ui.dialogs.BasicTopDialog.Builder;
import cc.kune.common.client.ui.dialogs.MessageToolbar;
import cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter.AbstractTabbedDialogView;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
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

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractTabbedDialogPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractTabbedDialogPanel extends ViewImpl implements AbstractTabbedDialogView {

  /** The Constant NO_SIZE. */
  private static final int NO_SIZE = -1;

  /** The autohide. */
  private final boolean autohide;

  /** The dialog. */
  private BasicTopDialog dialog;

  /** The dialog id. */
  private final String dialogId;

  /** The direction. */
  private final Direction direction;

  /** The error label id. */
  private final String errorLabelId;

  /** The first btn id. */
  private final String firstBtnId;

  /** The first btn title. */
  private final String firstBtnTitle;

  /** The height. */
  private int height = NO_SIZE;

  /** The icon. */
  private ImageResource icon;

  /** The icon cls. */
  private String iconCls;

  /** The images. */
  private final NotifyLevelImages images;

  /** The message error bar. */
  private MessageToolbar messageErrorBar;

  /** The modal. */
  private final boolean modal;

  /** The prov collection. */
  private final ProvidersCollection provCollection;

  /** The snd btn id. */
  private final String sndBtnId;

  /** The snd btn title. */
  private final String sndBtnTitle;

  /** The tab panel. */
  private TabLayoutPanel tabPanel;

  /** The title. */
  private String title;

  /** The width. */
  private int width = NO_SIZE;

  /**
   * Instantiates a new abstract tabbed dialog panel.
   * 
   * @param dialogId
   *          the dialog id
   * @param title
   *          the title
   * @param modal
   *          the modal
   * @param autoHide
   *          the auto hide
   * @param images
   *          the images
   * @param errorLabelId
   *          the error label id
   * @param firstBtnTitle
   *          the first btn title
   * @param firstBtnId
   *          the first btn id
   * @param sndBtnTitle
   *          the snd btn title
   * @param sndBtnId
   *          the snd btn id
   * @param provCollection
   *          the prov collection
   * @param direction
   *          the direction
   */
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

  /**
   * Instantiates a new abstract tabbed dialog panel.
   * 
   * @param dialogId
   *          the dialog id
   * @param title
   *          the title
   * @param modal
   *          the modal
   * @param images
   *          the images
   * @param errorLabelId
   *          the error label id
   * @param firstBtnTitle
   *          the first btn title
   * @param firstBtnId
   *          the first btn id
   * @param sndBtnTitle
   *          the snd btn title
   * @param sndBtnId
   *          the snd btn id
   * @param provCollection
   *          the prov collection
   * @param direction
   *          the direction
   */
  public AbstractTabbedDialogPanel(final String dialogId, final String title, final boolean modal,
      final NotifyLevelImages images, final String errorLabelId, final String firstBtnTitle,
      final String firstBtnId, final String sndBtnTitle, final String sndBtnId,
      final ProvidersCollection provCollection, final Direction direction) {
    this(dialogId, title, NO_SIZE, NO_SIZE, modal, false, images, errorLabelId, firstBtnTitle,
        firstBtnId, sndBtnTitle, sndBtnId, provCollection, direction);
  }

  /**
   * Instantiates a new abstract tabbed dialog panel.
   * 
   * @param dialogId
   *          the dialog id
   * @param title
   *          the title
   * @param width
   *          the width
   * @param height
   *          the height
   * @param modal
   *          the modal
   * @param autoHide
   *          the auto hide
   * @param images
   *          the images
   * @param errorLabelId
   *          the error label id
   * @param firstBtnTitle
   *          the first btn title
   * @param firstBtnId
   *          the first btn id
   * @param sndBtnTitle
   *          the snd btn title
   * @param sndBtnId
   *          the snd btn id
   * @param provCollection
   *          the prov collection
   * @param direction
   *          the direction
   */
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

  /**
   * Instantiates a new abstract tabbed dialog panel.
   * 
   * @param dialogId
   *          the dialog id
   * @param title
   *          the title
   * @param width
   *          the width
   * @param height
   *          the height
   * @param modal
   *          the modal
   * @param images
   *          the images
   * @param errorLabelId
   *          the error label id
   * @param firstBtnTitle
   *          the first btn title
   * @param firstBtnId
   *          the first btn id
   * @param sndBtnTitle
   *          the snd btn title
   * @param sndBtnId
   *          the snd btn id
   * @param provCollection
   *          the prov collection
   * @param direction
   *          the direction
   */
  public AbstractTabbedDialogPanel(final String dialogId, final String title, final int width,
      final int height, final boolean modal, final NotifyLevelImages images, final String errorLabelId,
      final String firstBtnTitle, final String firstBtnId, final String sndBtnTitle,
      final String sndBtnId, final ProvidersCollection provCollection, final Direction direction) {
    this(dialogId, title, width, height, modal, false, images, errorLabelId, firstBtnTitle, firstBtnId,
        sndBtnTitle, sndBtnId, provCollection, direction);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter.
   * AbstractTabbedDialogView#activateTab(int)
   */
  @Override
  public void activateTab(final int index) {
    createDialogIfNecessary();
    tabPanel.selectTab(index);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter.
   * AbstractTabbedDialogView#addTab(com.google.gwt.user.client.ui.IsWidget,
   * com.google.gwt.user.client.ui.IsWidget)
   */
  @Override
  public void addTab(final IsWidget view, final IsWidget tabTitle) {
    createDialogIfNecessary();
    tabPanel.add(view, tabTitle);
    setPositions();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    createDialogIfNecessary();
    return dialog;
  }

  /**
   * Creates the dialog.
   */
  private void createDialog() {
    final Builder builder = new BasicTopDialog.Builder(dialogId, autohide, modal, direction).autoscroll(
        true).icon(iconCls).firstButtonId(firstBtnId).firstButtonTitle(firstBtnTitle).sndButtonTitle(
        sndBtnTitle).sndButtonId(sndBtnId).title(title);
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

  /**
   * Creates the dialog if necessary.
   */
  private void createDialogIfNecessary() {
    if (dialog == null) {
      createDialog();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter.
   * AbstractTabbedDialogView#destroy()
   */
  @Override
  public void destroy() {
    if (dialog != null) {
      dialog.removeFromParent();
      dialog = null;
    }
  }

  /**
   * Gets the close.
   * 
   * @return the close
   */
  public HasCloseHandlers<?> getClose() {
    createDialogIfNecessary();
    return dialog.getClose();
  }

  /**
   * Gets the first btn.
   * 
   * @return the first btn
   */
  public HasClickHandlers getFirstBtn() {
    createDialogIfNecessary();
    return dialog.getFirstBtn();
  }

  /**
   * Gets the inner panel.
   * 
   * @return the inner panel
   */
  public ForIsWidget getInnerPanel() {
    createDialogIfNecessary();
    return dialog.getInnerPanel();
  }

  /**
   * Gets the second btn.
   * 
   * @return the second btn
   */
  public HasClickHandlers getSecondBtn() {
    createDialogIfNecessary();
    return dialog.getSecondBtn();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter.
   * AbstractTabbedDialogView#hide()
   */
  @Override
  public void hide() {
    if (dialog != null) {
      if (dialog.isVisible()) {
        dialog.hide();
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter.
   * AbstractTabbedDialogView#hideMessages()
   */
  @Override
  public void hideMessages() {
    if (dialog != null) {
      messageErrorBar.hideErrorMessage();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter.
   * AbstractTabbedDialogView#insertTab(com.google.gwt.user.client.ui.IsWidget,
   * com.google.gwt.user.client.ui.IsWidget, int)
   */
  @Override
  public void insertTab(final IsWidget tab, final IsWidget tabTitle, final int index) {
    createDialogIfNecessary();
    tabPanel.insert(tab, tabTitle, index);
    setPositions();
  }

  /**
   * Checks if is visible.
   * 
   * @return true, if is visible
   */
  public boolean isVisible() {
    createDialogIfNecessary();
    return dialog.isVisible();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter.
   * AbstractTabbedDialogView#setErrorMessage(java.lang.String,
   * cc.kune.common.client.notify.NotifyLevel)
   */
  @Override
  public void setErrorMessage(final String message, final NotifyLevel level) {
    messageErrorBar.setErrorMessage(message, level);
  }

  /**
   * Sets the first tab active.
   */
  public void setFirstTabActive() {
    tabPanel.selectTab(0);
  }

  /**
   * Sets the icon.
   * 
   * @param icon
   *          the new icon
   */
  public void setIcon(final ImageResource icon) {
    this.icon = icon;
  }

  /**
   * Sets the icon cls.
   * 
   * @param iconCls
   *          the new icon cls
   */
  public void setIconCls(final String iconCls) {
    this.iconCls = iconCls;
    if (dialog != null) {
      dialog.setTitleIcon(iconCls);
    }
  }

  // Workaround from:
  // http://stackoverflow.com/questions/5170324/tablayoutpanel-dynamic-resizing
  /**
   * Sets the positions.
   */
  private void setPositions() {
    for (int i = 0; i < tabPanel.getWidgetCount(); i++) {
      final Element element = tabPanel.getWidget(i).getElement();
      element.getStyle().setPosition(Position.RELATIVE);
      element.getStyle().setOverflow(Overflow.VISIBLE);

      final Element parent = DOM.getParent(element);
      parent.getStyle().setPosition(Position.RELATIVE);
      parent.getStyle().setOverflow(Overflow.VISIBLE);

      final Element grand = DOM.getParent(parent);
      grand.getStyle().setPosition(Position.RELATIVE);
      grand.getStyle().setOverflow(Overflow.VISIBLE);

      final Element pGrand = DOM.getParent(grand);
      pGrand.getStyle().setPosition(Position.RELATIVE);
      pGrand.getStyle().setOverflow(Overflow.VISIBLE);
    }
  }

  /**
   * Sets the title.
   * 
   * @param title
   *          the new title
   */
  public void setTitle(final String title) {
    this.title = title;
    if (dialog != null) {
      dialog.getTitleText().setText(title, direction);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter.
   * AbstractTabbedDialogView#show()
   */
  @Override
  public void show() {
    showImpl();
    setFirstTabActive();
  }

  /**
   * Show impl.
   */
  private void showImpl() {
    createDialogIfNecessary();
    hideMessages();
    dialog.showCentered();
  }

}
