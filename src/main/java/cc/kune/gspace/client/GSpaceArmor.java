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
 */
package cc.kune.gspace.client;

import cc.kune.common.client.actions.ui.IsActionExtensible;

import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;

public interface GSpaceArmor {

  void clearBackImage();

  void enableCenterScroll(boolean enable);

  ForIsWidget getDocContainer();

  ForIsWidget getDocFooter();

  ForIsWidget getDocHeader();

  ForIsWidget getDocSubheader();

  ForIsWidget getEntityFooter();

  ForIsWidget getEntityHeader();

  ForIsWidget getEntityToolsCenter();

  ForIsWidget getEntityToolsNorth();

  ForIsWidget getEntityToolsSouth();

  IsActionExtensible getFooterToolbar();

  IsActionExtensible getHeaderToolbar();

  SimplePanel getHomeSpace();

  IsWidget getMainpanel();

  SimplePanel getPublicSpace();

  ForIsWidget getSitebar();

  IsActionExtensible getSubheaderToolbar();

  IsActionExtensible getToolsSouthToolbar();

  ForIsWidget getUserSpace();

  void selectGroupSpace();

  void selectHomeSpace();

  void selectPublicSpace();

  void selectUserSpace();

  void setBackImage(String url);

  void setContentVisible(boolean visible);
}