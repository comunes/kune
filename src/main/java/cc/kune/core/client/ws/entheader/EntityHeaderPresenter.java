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
package cc.kune.core.client.ws.entheader;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.client.events.GroupChangedEvent;
import cc.kune.core.client.events.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.gspace.client.events.CurrentEntityChangedEvent;
import cc.kune.gspace.client.events.CurrentEntityChangedEvent.CurrentEntityChangedHandler;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityHeaderPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EntityHeaderPresenter extends
    Presenter<EntityHeaderPresenter.EntityHeaderView, EntityHeaderPresenter.EntityHeaderProxy> implements
    EntityHeader {

  /**
   * The Interface EntityHeaderProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface EntityHeaderProxy extends Proxy<EntityHeaderPresenter> {
  }

  /**
   * The Interface EntityHeaderView.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface EntityHeaderView extends View {

    /**
     * Adds the action.
     * 
     * @param descriptor
     *          the descriptor
     */
    void addAction(GuiActionDescrip descriptor);

    /**
     * Adds the widget.
     * 
     * @param widget
     *          the widget
     */
    void addWidget(IsWidget widget);

    /**
     * Sets the large font.
     */
    void setLargeFont();

    /**
     * Sets the logo image.
     * 
     * @param group
     *          the group
     * @param noCache
     *          the no cache
     */
    void setLogoImage(GroupDTO group, boolean noCache);

    /**
     * Sets the logo image visible.
     * 
     * @param visible
     *          the new logo image visible
     */
    void setLogoImageVisible(boolean visible);

    /**
     * Sets the logo text.
     * 
     * @param groupName
     *          the new logo text
     */
    void setLogoText(final String groupName);

    /**
     * Sets the medium font.
     */
    void setMediumFont();

    /**
     * Sets the online status group.
     * 
     * @param group
     *          the new online status group
     */
    void setOnlineStatusGroup(String group);

    /**
     * Sets the online status visible.
     * 
     * @param visible
     *          the new online status visible
     */
    void setOnlineStatusVisible(boolean visible);

    /**
     * Sets the small font.
     */
    void setSmallFont();

    /**
     * Show def user logo.
     */
    void showDefUserLogo();
  }

  /** The Constant LARGE_NAME_LIMIT. */
  private static final int LARGE_NAME_LIMIT = 17;

  /** The Constant MEDIUM_NAME_LIMIT. */
  private static final int MEDIUM_NAME_LIMIT = 80;

  /**
   * Instantiates a new entity header presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   */
  @Inject
  public EntityHeaderPresenter(final EventBus eventBus, final EntityHeaderView view,
      final EntityHeaderProxy proxy, final StateManager stateManager, final Session session) {
    super(eventBus, view, proxy);
    stateManager.onGroupChanged(true, new GroupChangedHandler() {
      @Override
      public void onGroupChanged(final GroupChangedEvent event) {
        setGroupLogo(session.getCurrentState().getGroup(), false);
      }
    });
    eventBus.addHandler(CurrentEntityChangedEvent.getType(), new CurrentEntityChangedHandler() {
      @Override
      public void onCurrentLogoChanged(final CurrentEntityChangedEvent event) {
        final GroupDTO group = session.getCurrentState().getGroup();
        setGroupLogo(group, true);
        setLogoText(group.getLongName());
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeader#addAction(cc.kune.common.
   * client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public void addAction(final GuiActionDescrip descriptor) {
    getView().addAction(descriptor);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.ws.entheader.EntityHeader#addWidget(com.google.gwt.
   * user.client.ui.IsWidget)
   */
  @Override
  public void addWidget(final IsWidget widget) {
    getView().addWidget(widget);
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
   * Sets the group logo.
   * 
   * @param group
   *          the group
   * @param noCache
   *          the no cache
   */
  void setGroupLogo(final GroupDTO group, final boolean noCache) {
    setLogoText(group.getLongName());
    if (group.hasLogo()) {
      getView().setLogoImage(group, noCache);
      getView().setLogoImageVisible(true);
    } else {
      if (group.isPersonal()) {
        getView().showDefUserLogo();
        getView().setLogoImageVisible(true);
      } else {
        getView().setLogoImageVisible(false);
      }
    }
    if (group.isPersonal()) {
      getView().setOnlineStatusGroup(group.getShortName());
      getView().setOnlineStatusVisible(true);
    } else {
      getView().setOnlineStatusGroup(null);
      getView().setOnlineStatusVisible(false);
    }
  }

  /**
   * Sets the logo text.
   * 
   * @param name
   *          the new logo text
   */
  void setLogoText(final String name) {
    final int length = name.length();
    if (length <= LARGE_NAME_LIMIT) {
      getView().setLargeFont();
    } else if (length <= MEDIUM_NAME_LIMIT) {
      getView().setMediumFont();
    } else {
      getView().setSmallFont();
    }
    getView().setLogoText(name);
  }
}
