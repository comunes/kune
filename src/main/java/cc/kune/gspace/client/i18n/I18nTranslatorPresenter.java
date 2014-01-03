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
package cc.kune.gspace.client.i18n;

import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialog;
import cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;
import cc.kune.core.shared.dto.I18nLanguageDTO;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;
import cc.kune.gspace.client.i18n.I18nTranslatorPresenter.I18nTranslatorView;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nTranslatorPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class I18nTranslatorPresenter extends
    AbstractTabbedDialogPresenter<I18nTranslatorView, I18nTranslatorPresenter.I18nTranslatorProxy>
    implements AbstractTabbedDialog, I18nTranslator {

  /**
   * The Interface I18nTranslatorProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface I18nTranslatorProxy extends Proxy<I18nTranslatorPresenter> {
  }

  /**
   * The Interface I18nTranslatorView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface I18nTranslatorView extends
      cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter.AbstractTabbedDialogView {

    /**
     * Inits the.
     */
    void init();

    /**
     * Sets the language.
     * 
     * @param currentLanguage
     *          the new language
     */
    void setLanguage(I18nLanguageSimpleDTO currentLanguage);

  }

  /** The session. */
  private final Session session;

  /**
   * Instantiates a new i18n translator presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param proxy
   *          the proxy
   * @param session
   *          the session
   * @param i18n
   *          the i18n
   * @param view
   *          the view
   */
  @Inject
  public I18nTranslatorPresenter(final EventBus eventBus, final I18nTranslatorProxy proxy,
      final Session session, final I18nUITranslationService i18n, final I18nTranslatorView view) {
    super(eventBus, view, proxy);
    this.session = session;
  }

  /**
   * Do close.
   */
  public void doClose() {
    getView().hide();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter#getView
   * ()
   */
  @Override
  public I18nTranslatorView getView() {
    return (I18nTranslatorView) super.getView();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter#hide()
   */
  @Override
  public void hide() {
    getView().hide();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
   */
  @Override
  protected void onBind() {
    super.onBind();
    getView().init();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter#show()
   */
  @Override
  public void show() {
    final I18nLanguageDTO userLang = session.getCurrentLanguage();
    if (!userLang.getCode().equals("en")) {
      getView().setLanguage(new I18nLanguageSimpleDTO(userLang.getCode(), userLang.getEnglishName()));
    }
    getView().show();
  }
}
