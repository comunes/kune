/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiAddCondition;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The Class IsInDevelopmentCondition is used to include this action only if
 * there is a ?dev=true in the URL, so we can test some functionality in
 * development
 */
@Singleton
public class IsInDevelopmentCondition implements GuiAddCondition {

  private final Session session;

  @Inject
  public IsInDevelopmentCondition(final Session session) {
    this.session = session;
  }

  @Override
  public boolean mustBeAdded(final GuiActionDescrip descr) {
    return session.isGuiInDevelopment();
  }
}
