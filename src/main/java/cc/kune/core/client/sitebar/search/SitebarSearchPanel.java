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

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sitebar.search.SitebarSearchPresenter.SitebarSearchView;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.SearcherConstants;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class SitebarSearchPanel extends ViewImpl implements SitebarSearchView {
  protected interface OnExactMatch {
    void onExactMatch(String march);
  }
  private static final int SEARCH_TEXT_HEIGHT = 13;
  private static final int SEARCH_TEXT_WIDTH_BIG = 160;
  private static final int SEARCH_TEXT_WIDTH_SMALL = 120;
  public static final String SITE_SEARCH_BUTTON = "kune-ssp-searchbt";

  public static final String SITE_SEARCH_TEXTBOX = "kune-ssp-tbox";
  private final PushButton searchButton;
  private final TextBoxBase searchTextBox;
  private final SuggestBox suggestBox;

  @Inject
  public SitebarSearchPanel(final GSpaceArmor gs, final CoreResources img,
      final StateManager stateManager, final I18nTranslationService i18n) {
    searchButton = new PushButton(new Image(img.kuneSearchIco()), new Image(img.kuneSearchIcoPush()));
    searchButton.ensureDebugId(SITE_SEARCH_BUTTON);
    final MultivalueSuggestBox multivalueSBox = new MultivalueSuggestBox(
        SearcherConstants.GROUP_DATA_PROXY_URL, false, new OnExactMatch() {

          @Override
          public void onExactMatch(final String match) {
            NotifyUser.info(match);
            NotifyUser.info("kk");
          }
        }) {

      @Override
      public void onSelection(
          final com.google.gwt.event.logical.shared.SelectionEvent<com.google.gwt.user.client.ui.SuggestOracle.Suggestion> event) {
        super.onSelection(event);
        final Suggestion suggestion = event.getSelectedItem();
        if (suggestion instanceof OptionSuggestion) {
          final OptionSuggestion osugg = (OptionSuggestion) suggestion;
          final String value = osugg.getValue();
          if (!OptionSuggestion.NEXT_VALUE.equals(value)
              && !OptionSuggestion.PREVIOUS_VALUE.equals(value)) {
            stateManager.gotoHistoryToken(value);
            setTextSearch("");
          }
        }
      };
    };
    suggestBox = multivalueSBox.getSuggestBox();
    Tooltip.to(suggestBox, i18n.t("Type something to search users and groups in this site"));
    searchTextBox = suggestBox.getTextBox();
    searchTextBox.ensureDebugId(SITE_SEARCH_TEXTBOX);
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
