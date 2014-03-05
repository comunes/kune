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

package cc.kune.lists.client.actions;

import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.state.Session;
import cc.kune.gspace.client.actions.IsInDevelopmentCondition;
import cc.kune.gspace.client.actions.share.ShareDialogAction;
import cc.kune.gspace.client.share.ShareDialog;

import com.google.inject.Inject;

public class ConfigureListMenuItem extends ButtonDescriptor {

  @Inject
  public ConfigureListMenuItem(final ShareDialog shareDialog, final ShareDialogAction shareAction,
      final IsInDevelopmentCondition isInDevAddCondition, final IconicResources icons,
      final Session session) {
    super(shareAction);
    withText(session.isNewbie() ? I18n.t("Configure") : "");
    withToolTip(I18n.t("Configure this list: manage members, etc"));
    withIcon(icons.prefs());
    withAddCondition(isInDevAddCondition);
  }

}
