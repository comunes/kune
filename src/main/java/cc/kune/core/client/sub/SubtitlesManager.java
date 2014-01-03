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
package cc.kune.core.client.sub;

import cc.kune.common.client.utils.Base64Utils;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.state.StateManager;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class SubtitlesManager.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SubtitlesManager extends
    Presenter<SubtitlesManager.SubtitlesView, SubtitlesManager.SubtitlesProxy> {

  /**
   * The Interface SubtitlesProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface SubtitlesProxy extends Proxy<SubtitlesManager> {
  }

  /**
   * The Interface SubtitlesView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface SubtitlesView extends View {

    /**
     * Sets the description.
     * 
     * @param descr
     *          the new description
     */
    void setDescription(String descr);

    /**
     * Sets the title text.
     * 
     * @param text
     *          the new title text
     */
    void setTitleText(String text);

    /**
     * Show.
     * 
     * @param atShowEnd
     *          the at show end
     */
    void show(final SimpleCallback atShowEnd);

  }

  /** The state manager. */
  private final StateManager stateManager;

  /**
   * Instantiates a new subtitles manager.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param stateManager
   *          the state manager
   */
  @Inject
  public SubtitlesManager(final EventBus eventBus, final SubtitlesView view, final SubtitlesProxy proxy,
      final StateManager stateManager) {
    super(eventBus, view, proxy);
    this.stateManager = stateManager;
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
   * Show.
   * 
   * @param token
   *          the token
   */
  public void show(final String token) {
    final String[] params = token.split("\\|");
    final int len = params.length;
    getView().setTitleText(new String(Base64Utils.fromBase64(params[0])));
    if (len > 1) {
      getView().setDescription(new String(Base64Utils.fromBase64(params[1])));
    }
    getView().show(new SimpleCallback() {
      @Override
      public void onCallback() {
        if (len > 2 && TextUtils.notEmpty(params[2])) {
          stateManager.gotoHistoryToken(params[2]);
        } else {
          stateManager.gotoHistoryToken("");
        }
      }
    });
  }
}
