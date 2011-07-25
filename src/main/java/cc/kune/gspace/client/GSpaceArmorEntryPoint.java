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

import cc.kune.gspace.client.resources.GSpaceArmorResources;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

@Deprecated
public class GSpaceArmorEntryPoint implements EntryPoint {
  @Override
  public void onModuleLoad() {
    final GSpaceArmorResources resources = GWT.create(GSpaceArmorResources.class);
    resources.style().ensureInjected();

    final RootLayoutPanel rootPanel = RootLayoutPanel.get();
    final GSpaceArmorImpl armor = new GSpaceArmorImpl(null);
    final InlineLabel icons = new InlineLabel("Icons");
    final InlineLabel login = new InlineLabel("Login");
    final InlineLabel logo = new InlineLabel("Logo");
    icons.setStyleName(resources.style().floatLeft());
    login.setStyleName(resources.style().floatRight());
    logo.setStyleName(resources.style().floatRight());
    armor.getSitebar().add(icons);
    armor.getSitebar().add(logo);
    armor.getSitebar().add(login);
    armor.getEntityToolsNorth().add(new InlineLabel("Group members"));
    armor.getEntityToolsCenter().add(new InlineLabel("Documents"));
    armor.getEntityToolsSouth().add(new InlineLabel("Group tags"));
    armor.getEntityHeader().add(new InlineLabel("Name of the Initiative"));
    armor.getDocHeader().add(new InlineLabel("Some doc title"));
    final InlineLabel editors = new InlineLabel("Editors:");
    editors.addStyleName(resources.style().docSubheaderLeft());
    armor.getDocSubheader().add(editors);
    armor.getDocFooter().add(new InlineLabel("Tags:"));
    armor.getEntityFooter().add(new InlineLabel("Rate it:"));
    rootPanel.add(armor);
  }

}
