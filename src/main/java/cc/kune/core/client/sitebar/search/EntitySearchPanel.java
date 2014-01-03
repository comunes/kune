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
 \*/
package cc.kune.core.client.sitebar.search;

import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.CoreResources;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBoxBase;

// TODO: Auto-generated Javadoc
/**
 * This panel is used to search for users to add as a buddies or to add to
 * groups as collaborators.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class EntitySearchPanel {

  /** The Constant DIALOG_ID. */
  public static final String DIALOG_ID = "entity-search-panel-diag";

  /** The Constant OK_ID. */
  public static final String OK_ID = "entity-search-panel-ok-id";

  /** The Constant SEARCH_TEXT_HEIGHT. */
  private static final int SEARCH_TEXT_HEIGHT = 13;

  /** The Constant SEARCH_TEXT_WIDTH_BIG. */
  private static final int SEARCH_TEXT_WIDTH_BIG = 160;

  /** The Constant SEARCH_TEXT_WIDTH_SMALL. */
  private static final int SEARCH_TEXT_WIDTH_SMALL = 120;

  /** The callback. */
  private OnEntitySelectedInSearch callback;

  /** The dialog. */
  private final BasicTopDialog dialog;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The search only users. */
  private boolean searchOnlyUsers;

  /** The search text box. */
  private TextBoxBase searchTextBox;

  /** The suggest box. */
  private SuggestBox suggestBox;

  /**
   * Instantiates a new entity search panel.
   * 
   * @param img
   *          the img
   * @param i18n
   *          the i18n
   */
  public EntitySearchPanel(final CoreResources img, final I18nTranslationService i18n) {
    this.i18n = i18n;
    dialog = new BasicTopDialog.Builder(DIALOG_ID, false, true, i18n.getDirection()).autoscroll(false).firstButtonId(
        OK_ID).tabIndexStart(1).build();
    dialog.setFirstBtnText(i18n.t("Close"));
    dialog.setSecondBtnVisible(false);
    dialog.getFirstBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        dialog.hide();
      }
    });
  }

  /**
   * Clear search text.
   */
  public void clearSearchText() {
    searchTextBox.setValue("");
  }

  /**
   * Creates the.
   * 
   * @param id
   *          the id
   */
  private void create(final String id) {
    dialog.getTitleText().setText(
        i18n.t(searchOnlyUsers ? "Type the name of the user and select him/her:"
            : "Type the name of the user or group and select it:"), i18n.getDirection());
    final MultivalueSuggestBox multivalueSBox = SearchBoxFactory.create(i18n, searchOnlyUsers, true, id,
        callback);
    suggestBox = multivalueSBox.getSuggestBox();
    searchTextBox = suggestBox.getTextBox();
    dialog.getInnerPanel().add(multivalueSBox);
    setTextSearchSmallImpl();
  }

  /**
   * Focus.
   */
  public void focus() {
    searchTextBox.setFocus(true);
  }

  /**
   * Gets the focus.
   * 
   * @return the focus
   */
  public HasAllFocusHandlers getFocus() {
    return searchTextBox;
  }

  /**
   * Gets the text box.
   * 
   * @return the text box
   */
  public HasText getTextBox() {
    return searchTextBox;
  }

  /**
   * Inits the.
   * 
   * @param searchOnlyUsers
   *          the search only users
   * @param id
   *          the id
   * @param callback
   *          the callback
   */
  public void init(final boolean searchOnlyUsers, final String id,
      final OnEntitySelectedInSearch callback) {
    this.callback = callback;
    this.searchOnlyUsers = searchOnlyUsers;
    create(id);
  }

  /**
   * Select search text.
   */
  public void selectSearchText() {
    searchTextBox.selectAll();
  }

  /**
   * Sets the text search.
   * 
   * @param text
   *          the new text search
   */
  public void setTextSearch(final String text) {
    suggestBox.setValue(text, false);
  }

  /**
   * Sets the text search big.
   */
  public void setTextSearchBig() {
    searchTextBox.setPixelSize(SEARCH_TEXT_WIDTH_BIG, SEARCH_TEXT_HEIGHT);
  }

  /**
   * Sets the text search small impl.
   */
  private void setTextSearchSmallImpl() {
    searchTextBox.setPixelSize(SEARCH_TEXT_WIDTH_SMALL, SEARCH_TEXT_HEIGHT);
  }

  /**
   * Show.
   */
  public void show() {
    dialog.showCentered();
  }
}
