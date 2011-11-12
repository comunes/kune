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
 \*/
package cc.kune.core.client.sitebar.search;

import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.CoreResources;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * This panel is used to search for users to add as a buddies or to add to
 * groups as collaborators
 * 
 */
public abstract class EntitySearchPanel {

  public static final String DIALOG_ID = "entity-search-panel-diag";
  public static final String ENTITY_SEARCH_TEXTBOX = "kune-ssp-tbox";
  public static final String OK_ID = "entity-search-panel-ok-id";
  private static final int SEARCH_TEXT_HEIGHT = 13;
  private static final int SEARCH_TEXT_WIDTH_BIG = 160;
  private static final int SEARCH_TEXT_WIDTH_SMALL = 120;
  private OnEntitySelectedInSearch callback;
  private final BasicTopDialog dialog;
  private final I18nUITranslationService i18n;
  private boolean searchOnlyUsers;
  private TextBoxBase searchTextBox;
  private SuggestBox suggestBox;

  public EntitySearchPanel(final CoreResources img, final I18nUITranslationService i18n) {
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

  public void clearSearchText() {
    searchTextBox.setValue("");
  }

  private void create() {
    dialog.getTitleText().setText(
        i18n.t(searchOnlyUsers ? "Type the name of the user and select him/her:"
            : "Type the name of the user or group and select it:"), i18n.getDirection());
    final MultivalueSuggestBox multivalueSBox = SearchBoxFactory.create(i18n, searchOnlyUsers, callback);
    suggestBox = multivalueSBox.getSuggestBox();
    searchTextBox = suggestBox.getTextBox();
    searchTextBox.ensureDebugId(ENTITY_SEARCH_TEXTBOX);
    dialog.getInnerPanel().add(multivalueSBox);
    setTextSearchSmallImpl();
  }

  public void focus() {
    searchTextBox.setFocus(true);
  }

  public HasAllFocusHandlers getFocus() {
    return searchTextBox;
  }

  public HasText getTextBox() {
    return searchTextBox;
  }

  public void init(final OnEntitySelectedInSearch callback, final boolean searchOnlyUsers) {
    this.callback = callback;
    this.searchOnlyUsers = searchOnlyUsers;
    create();
  }

  public void selectSearchText() {
    searchTextBox.selectAll();
  }

  public void setTextSearch(final String text) {
    suggestBox.setValue(text, false);
  }

  public void setTextSearchBig() {
    searchTextBox.setPixelSize(SEARCH_TEXT_WIDTH_BIG, SEARCH_TEXT_HEIGHT);
  }

  private void setTextSearchSmallImpl() {
    searchTextBox.setPixelSize(SEARCH_TEXT_WIDTH_SMALL, SEARCH_TEXT_HEIGHT);
  }

  public void show() {
    dialog.showCentered();
  }
}
