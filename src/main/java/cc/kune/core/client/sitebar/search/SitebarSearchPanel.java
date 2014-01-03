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

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sitebar.search.SitebarSearchPresenter.SitebarSearchView;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.SessionConstants;
import cc.kune.gspace.client.armor.GSpaceArmor;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasAllKeyHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class SitebarSearchPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SitebarSearchPanel extends ViewImpl implements SitebarSearchView {

  /** The Constant SEARCH_TEXT_HEIGHT. */
  private static final int SEARCH_TEXT_HEIGHT = 13;

  /** The Constant SEARCH_TEXT_WIDTH_BIG. */
  private static final int SEARCH_TEXT_WIDTH_BIG = 160;

  /** The Constant SEARCH_TEXT_WIDTH_SMALL. */
  private static final int SEARCH_TEXT_WIDTH_SMALL = 80;

  /** The Constant SITE_SEARCH_BUTTON. */
  public static final String SITE_SEARCH_BUTTON = "kune-ssp-searchbt";

  /** The Constant SITE_SEARCH_TEXTBOX. */
  public static final String SITE_SEARCH_TEXTBOX = "kune-ssp-tbox";

  /** The def search label. */
  private final Label defSearchLabel;

  /** The search button. */
  private final PushButton searchButton;

  /** The search text box. */
  private final TextBoxBase searchTextBox;

  /** The suggest box. */
  private final SuggestBox suggestBox;

  /**
   * Instantiates a new sitebar search panel.
   * 
   * @param gs
   *          the gs
   * @param img
   *          the img
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param i18n
   *          the i18n
   */
  @Inject
  public SitebarSearchPanel(final GSpaceArmor gs, final CoreResources img,
      final SessionConstants session, final StateManager stateManager, final I18nTranslationService i18n) {
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
    defSearchLabel = new Label(i18n.t("Search"));
    defSearchLabel.addStyleName("k-fr");
    defSearchLabel.addStyleName("k-sitebarsearch-deftext");

    gs.getSitebar().add(multivalueSBox);
    gs.getSitebar().add(searchButton);
    gs.getSitebar().add(defSearchLabel);
    defSearchLabel.getElement().getStyle().setLeft(defSearchLabel.getOffsetWidth() + 27, Unit.PX);
    setTextSearchSmallImpl();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.search.SitebarSearchPresenter.SitebarSearchView
   * #clearSearchText()
   */
  @Override
  public void clearSearchText() {
    searchTextBox.setValue("");
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.search.SitebarSearchPresenter.SitebarSearchView
   * #focus()
   */
  @Override
  public void focus() {
    searchTextBox.setFocus(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.search.SitebarSearchPresenter.SitebarSearchView
   * #getButton()
   */
  @Override
  public HasClickHandlers getButton() {
    return searchButton;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.search.SitebarSearchPresenter.SitebarSearchView
   * #getDefLabelFocus()
   */
  @Override
  public HasClickHandlers getDefLabelFocus() {
    return defSearchLabel;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.search.SitebarSearchPresenter.SitebarSearchView
   * #getFocus()
   */
  @Override
  public HasAllFocusHandlers getFocus() {
    return searchTextBox;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.search.SitebarSearchPresenter.SitebarSearchView
   * #getKeyHandler()
   */
  @Override
  public HasAllKeyHandlers getKeyHandler() {
    return suggestBox;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.search.SitebarSearchPresenter.SitebarSearchView
   * #getTextBox()
   */
  @Override
  public HasText getTextBox() {
    return searchTextBox;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.search.SitebarSearchPresenter.SitebarSearchView
   * #selectSearchText()
   */
  @Override
  public void selectSearchText() {
    searchTextBox.selectAll();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.search.SitebarSearchPresenter.SitebarSearchView
   * #setDefTextVisible(boolean)
   */
  @Override
  public void setDefTextVisible(final boolean visible) {
    defSearchLabel.setVisible(visible);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.search.SitebarSearchPresenter.SitebarSearchView
   * #setTextSearch(java.lang.String)
   */
  @Override
  public void setTextSearch(final String text) {
    suggestBox.setValue(text, false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.search.SitebarSearchPresenter.SitebarSearchView
   * #setTextSearchBig()
   */
  @Override
  public void setTextSearchBig() {
    searchTextBox.setPixelSize(SEARCH_TEXT_WIDTH_BIG, SEARCH_TEXT_HEIGHT);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sitebar.search.SitebarSearchPresenter.SitebarSearchView
   * #setTextSearchSmall()
   */
  @Override
  public void setTextSearchSmall() {
    setTextSearchSmallImpl();
  }

  /**
   * Sets the text search small impl.
   */
  private void setTextSearchSmallImpl() {
    searchTextBox.setPixelSize(SEARCH_TEXT_WIDTH_SMALL, SEARCH_TEXT_HEIGHT);
  }
}
