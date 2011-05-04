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

import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class SitebarSearchPresenter extends
    Presenter<SitebarSearchPresenter.SitebarSearchView, SitebarSearchPresenter.SitebarSearchProxy> {

  @ProxyCodeSplit
  public interface SitebarSearchProxy extends Proxy<SitebarSearchPresenter> {
  }

  public interface SitebarSearchView extends View {

    void clearSearchText();

    HasClickHandlers getButton();

    HasAllFocusHandlers getFocus();

    HasText getTextBox();

    void selectSearchText();

    void setTextSearch(String text);

    void setTextSearchBig();

    void setTextSearchSmall();
  }

  private final String defaultSearchText;

  @Inject
  public SitebarSearchPresenter(final EventBus eventBus, final SitebarSearchView view,
      final SitebarSearchProxy proxy, final I18nTranslationService i18n) {
    super(eventBus, view, proxy);
    defaultSearchText = i18n.t("Search");
    setDefText();
    getView().setTextSearchSmall();
  }

  private void doSearch() {
    doSearch(getView().getTextBox().getText());
  }

  public void doSearch(final String termToSearch) {
    getView().setTextSearchSmall();
  }

  @Override
  protected void onBind() {
    super.onBind();
    getView().getFocus().addBlurHandler(new BlurHandler() {
      @Override
      public void onBlur(final BlurEvent event) {
        onSearchLostFocus(getView().getTextBox().getText());
      }
    });
    getView().getFocus().addFocusHandler(new FocusHandler() {
      @Override
      public void onFocus(final FocusEvent event) {
        onSearchFocus();
      }
    });
    getView().getButton().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        doSearch();
      }

    });
    // getView().getKeyUp().addKeyUpHandler(new KeyUpHandler() {
    // @Override
    // public void onKeyUp(final KeyUpEvent event) {
    // if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
    // if (getView().getTextBox().getText().length() > 0) {
    // NotifyUser.showProgressProcessing();
    // doSearch();
    // }
    // }
    // }
    // });
  }

  public void onSearchFocus() {
    if (getView().getTextBox().getText().equals(defaultSearchText)) {
      getView().setTextSearchBig();
      getView().selectSearchText();
    }
  }

  public void onSearchLostFocus(final String search) {
    if (search.length() == 0 || search.equals(defaultSearchText)) {
      getView().setTextSearchSmall();
    }
    if (search.length() == 0) {
      setDefText();
    }
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  private void setDefText() {
    getView().setTextSearch(defaultSearchText);
  }
}
