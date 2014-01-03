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
package cc.kune.pspace.client;

import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.events.AppStartEvent;
import cc.kune.core.client.events.StateChangedEvent;
import cc.kune.core.client.events.StateChangedEvent.StateChangedHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.StateTokenUtils;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupListDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class PSpacePresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class PSpacePresenter extends Presenter<PSpacePresenter.PSpaceView, PSpacePresenter.PSpaceProxy> {

  /**
   * The Interface PSpaceProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface PSpaceProxy extends Proxy<PSpacePresenter> {
  }

  /**
   * The Interface PSpaceView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface PSpaceView extends View {

    /**
     * Gets the action panel.
     * 
     * @return the action panel
     */
    IsActionExtensible getActionPanel();

    /**
     * Gets the description.
     * 
     * @return the description
     */
    HasText getDescription();

    /**
     * Gets the title.
     * 
     * @return the title
     */
    HasText getTitle();

    /**
     * Sets the content goto public url.
     * 
     * @param publicUrl
     *          the new content goto public url
     */
    void setContentGotoPublicUrl(String publicUrl);
  }

  /** The i18n. */
  private final I18nTranslationService i18n;

  /**
   * Instantiates a new p space presenter.
   * 
   * @param session
   *          the session
   * @param stateManager
   *          the state manager
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param i18n
   *          the i18n
   */
  @Inject
  public PSpacePresenter(final Session session, final StateManager stateManager,
      final EventBus eventBus, final PSpaceView view, final PSpaceProxy proxy,
      final I18nTranslationService i18n) {
    super(eventBus, view, proxy);
    this.i18n = i18n;
    session.onAppStart(true, new AppStartEvent.AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        stateManager.onStateChanged(true, new StateChangedHandler() {
          @Override
          public void onStateChanged(final StateChangedEvent event) {
            setState(event.getState());
          }
        });
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.PresenterWidget#onReveal()
   */
  @Override
  protected void onReveal() {
    super.onReveal();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  /**
   * Sets the content not public.
   */
  private void setContentNotPublic() {
    getView().getTitle().setText(
        i18n.t("Not Published yet") + i18n.tWithNT(TextUtils.IN_DEVELOPMENT_P, "with Brackets"));
    getView().getDescription().setText(
        i18n.t("This is only a preview of how this webpage would look like to the general public on the internet."));
  }

  /**
   * Sets the content not publicable.
   */
  private void setContentNotPublicable() {
    getView().getTitle().setText(
        i18n.t("Not Publishable") + i18n.tWithNT(TextUtils.IN_DEVELOPMENT_P, "with Brackets"));
    getView().getDescription().setText(i18n.t("This page is not publishable"));
    getView().setContentGotoPublicUrl("about:blank");
  }

  /**
   * Sets the content public.
   */
  private void setContentPublic() {
    getView().getTitle().setText(
        i18n.t("Preview") + i18n.tWithNT(TextUtils.IN_DEVELOPMENT_P, "with Brackets"));
    getView().getDescription().setText(
        i18n.t("This is only a preview of how this page would look like to the general public on the internet."));
  }

  /**
   * Sets the state.
   * 
   * @param state
   *          the new state
   */
  public void setState(final StateAbstractDTO state) {
    if (state instanceof StateContainerDTO) {
      final StateToken token = state.getStateToken();
      if (((StateContainerDTO) state).getAccessLists().getViewers().getMode().equals(
          GroupListDTO.EVERYONE)) {
        @SuppressWarnings("unused")
        final String publicUrl = StateTokenUtils.getPublicSpaceUrl(token);
        // getView().setContentGotoPublicUrl(publicUrl);
        getView().setContentGotoPublicUrl("about:blank");
        if (state instanceof StateContentDTO) {
          final StateContentDTO content = (StateContentDTO) state;
          if (content.getStatus().equals(ContentStatus.publishedOnline)) {
            setContentPublic();
          } else {
            setContentNotPublic();
          }
        } else {
          setContentPublic();
        }
      } else {
        setContentNotPublic();
      }
    } else {
      setContentNotPublicable();
    }
  }

}
