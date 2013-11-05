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

import cc.kune.common.client.actions.ui.descrip.SubMenuDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.gspace.client.actions.ContentViewerOptionsMenu;

import com.google.inject.Inject;

/**
 * The Class ContentViewerOptionsShareSubMenu (not used yet)
 */
public class ContentViewerOptionsShareSubMenu extends SubMenuDescriptor {

  public static final String ID = "k-cnt-viewer-share-opt-submenu";

  @Inject
  public ContentViewerOptionsShareSubMenu(final I18nTranslationService i18n, final IconicResources res,
      final ContentViewerOptionsMenu parent) {
    super();
    this.withText(i18n.t("Share")).withIcon(res.add()).withId(ID).withParent(parent, false);
  }

}
