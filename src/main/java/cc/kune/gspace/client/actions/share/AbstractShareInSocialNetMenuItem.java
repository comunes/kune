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
 */

package cc.kune.gspace.client.actions.share;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.common.client.utils.ClientFormattedString;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateTokenUtils;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.gwt.resources.client.ImageResource;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractShareInSocialNetMenuItem.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AbstractShareInSocialNetMenuItem extends MenuItemDescriptor {

  /**
   * The Class AbstractShareInSocialNetAction.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class AbstractShareInSocialNetAction extends AbstractExtendedAction {

    /** The url. */
    private ClientFormattedString url;

    /**
     * Instantiates a new abstract share in social net action.
     */
    public AbstractShareInSocialNetAction() {

    }

    /* (non-Javadoc)
     * @see cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common.client.actions.ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent event) {
      KuneWindowUtils.open(url.getString());
    }

    /**
     * Sets the url.
     *
     * @param url the new url
     */
    public void setUrl(final ClientFormattedString url) {
      this.url = url;
    }

  }

  /**
   * Gets the current url.
   *
   * @param session the session
   * @return the current url
   */
  protected static String getCurrentUrl(final Session session) {
    return StateTokenUtils.getGroupSpaceUrl(session.getCurrentState().getStateToken());
  }

  /**
   * Gets the title.
   *
   * @param session the session
   * @return the title
   */
  protected static String getTitle(final Session session) {
    final StateAbstractDTO state = session.getCurrentState();
    final String prefix = session.getCurrentGroupShortName() + ", ";
    if (!(state instanceof StateContentDTO)) {
      return prefix
          + (((StateContainerDTO) state).getContainer().isRoot() ? I18n.t(state.getTitle())
              : state.getTitle());
    } else {
      return prefix + session.getCurrentState().getTitle();
    }
  }

  /** The session. */
  protected final Session session;

  /**
   * Instantiates a new abstract share in social net menu item.
   *
   * @param action the action
   * @param session the session
   * @param menu the menu
   * @param text the text
   * @param icon the icon
   * @param url the url
   */
  public AbstractShareInSocialNetMenuItem(final AbstractShareInSocialNetAction action,
      final Session session, final ContentViewerShareMenu menu, final String text,
      final ImageResource icon, final ClientFormattedString url) {
    super(action);
    this.session = session;
    withText(text).withIcon(icon).withParent(menu, false);
    action.setUrl(url);
    setEnabled(session.getCurrentState().getGroupRights().isVisible());
  }

}