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
package cc.kune.core.client.sitebar;

import br.com.rpa.client._paperelements.PaperIconButton;
import cc.kune.common.client.actions.ui.descrip.ToolbarDescriptor;
import cc.kune.common.client.actions.ui.descrip.WidgetMenuDescriptor;

// TODO: Auto-generated Javadoc
/**
 * The Interface SitebarActions.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface SitebarActions {

  /** The Constant MORE_MENU. */
  static final WidgetMenuDescriptor MORE_MENU = new WidgetMenuDescriptor(
      PaperIconButton.wrap("sitebar_more_icon"));

  /** The Constant RIGHT_TOOLBAR. */
  static final ToolbarDescriptor RIGHT_TOOLBAR = new ToolbarDescriptor();

  /**
   * Refresh actions.
   */
  void refreshActions();

}
