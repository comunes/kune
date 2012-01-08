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

import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sitebar.search.SitebarSearchPresenter.SitebarSearchView;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.gspace.client.armor.GSpaceArmor;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class SitebarSearchPanel extends ViewImpl implements SitebarSearchView {

  private static final int SEARCH_TEXT_HEIGHT = 13;
  private static final int SEARCH_TEXT_WIDTH_BIG = 160;
  private static final int SEARCH_TEXT_WIDTH_SMALL = 80;
  public static final String SITE_SEARCH_BUTTON = "kune-ssp-searchbt";
  public static final String SITE_SEARCH_TEXTBOX = "kune-ssp-tbox";
  private final PushButton searchButton;
  private final TextBoxBase searchTextBox;
  private final SuggestBox suggestBox;

  @Inject
  public SitebarSearchPanel(final GSpaceArmor gs, final CoreResources img, final Session session,
      final StateManager stateManager, final I18nUITranslationService i18n) {
    searchButton = new PushButton(new Image(img.kuneSearchIco()), new Image(img.kuneSearchIcoPush()));
    searchButton.ensureDebugId(SITE_SEARCH_BUTTON);
    final MultivalueSuggestBox multivalueSBox = SearchBoxFactory.create(i18n, false, true,
        SITE_SEARCH_TEXTBOX, new OnEntitySelectedInSearch() {
          @Override
          public void onSeleted(final String shortName) {
            stateManager.gotoHistoryToken(shortName);
          }
        });
    suggestBox = multivalueSBox.getSuggestBox();
    searchTextBox = suggestBox.getTextBox();
    searchTextBox.addStyleName("k-fr");
    searchTextBox.addStyleName("k-sitebarsearch");
    searchTextBox.addStyleName("k-fr");
    searchButton.addStyleName("k-sitebarsearch-img");
    searchButton.addStyleName("k-fr");
    gs.getSitebar().add(multivalueSBox);
    gs.getSitebar().add(searchButton);
    setTextSearchSmallImpl();
  }

  @Override
  public Widget asWidget() {
    return null;
  }

  @Override
  public void clearSearchText() {
    searchTextBox.setValue("");
  }

  @Override
  public void focus() {
    searchTextBox.setFocus(true);
  }

  @Override
  public HasClickHandlers getButton() {
    return searchButton;
  }

  @Override
  public HasAllFocusHandlers getFocus() {
    return searchTextBox;
  }

  @Override
  public HasText getTextBox() {
    return searchTextBox;
  }

  @Override
  public void selectSearchText() {
    searchTextBox.selectAll();
  }

  @Override
  public void setTextSearch(final String text) {
    suggestBox.setValue(text, false);
  }

  @Override
  public void setTextSearchBig() {
    searchTextBox.setPixelSize(SEARCH_TEXT_WIDTH_BIG, SEARCH_TEXT_HEIGHT);
  }

  @Override
  public void setTextSearchSmall() {
    setTextSearchSmallImpl();
  }

  private void setTextSearchSmallImpl() {
    searchTextBox.setPixelSize(SEARCH_TEXT_WIDTH_SMALL, SEARCH_TEXT_HEIGHT);
  }
}
