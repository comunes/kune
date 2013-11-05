/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasAllKeyHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class SitebarSearchPresenter extends
    Presenter<SitebarSearchPresenter.SitebarSearchView, SitebarSearchPresenter.SitebarSearchProxy> {

  private Timer setsmall;

  @ProxyCodeSplit
  public interface SitebarSearchProxy extends Proxy<SitebarSearchPresenter> {
  }

  public interface SitebarSearchView extends View {

    void clearSearchText();

    void focus();

    HasClickHandlers getButton();

    HasAllFocusHandlers getFocus();

    HasText getTextBox();

    void selectSearchText();

    void setTextSearch(String text);

    void setTextSearchBig();

    void setTextSearchSmall();

    void setDefTextVisible(boolean visible);

    HasAllKeyHandlers getKeyHandler();

    HasClickHandlers getDefLabelFocus();
  }

  @Inject
  public SitebarSearchPresenter(final EventBus eventBus, final SitebarSearchView view,
      final SitebarSearchProxy proxy, final I18nTranslationService i18n) {
    super(eventBus, view, proxy);
    getView().setTextSearchSmall();
    setsmall = new Timer() {
      @Override
      public void run() {
        getView().setTextSearchSmall();
      }
    };
  }

  private void doSearch() {
  }

  public void focus() {
    getView().focus();
  }

  @Override
  protected void onBind() {
    super.onBind();
    getView().getFocus().addBlurHandler(new BlurHandler() {
      @Override
      public void onBlur(final BlurEvent event) {
        onSearchBlur(getView().getTextBox().getText());
      }
    });
    getView().getDefLabelFocus().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        onSearchFocus();
        focus();
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
    getView().getKeyHandler().addKeyUpHandler(new KeyUpHandler() {
      @Override
      public void onKeyUp(final KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          if (getView().getTextBox().getText().length() > 0) {
            NotifyUser.showProgressSearching();
            doSearch();
          }
        }
      }
    });
  }

  public void onSearchFocus() {
    getView().setTextSearchBig();
    getView().setDefTextVisible(false);
  }

  public void onSearchBlur(final String search) {
    if (search.length() == 0) {
      getView().setTextSearchSmall();
      getView().setDefTextVisible(true);
    } else {
      getView().setDefTextVisible(false);
      setsmall.cancel();
      setsmall.schedule(3000);
    }
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

}
