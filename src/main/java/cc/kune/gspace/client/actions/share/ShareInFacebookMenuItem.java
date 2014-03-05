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

package cc.kune.gspace.client.actions.share;

import cc.kune.common.client.utils.ClientFormattedString;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;

/**
 * The Class ShareInFacebookMenuItem.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ShareInFacebookMenuItem extends AbstractShareInSocialNetMenuItem {

  /** The Constant URL_TEMPLATE. */
  private static final String URL_TEMPLATE = "https://www.facebook.com/login.php?next=http%3A%2F%2Fwww.facebook.com%2Fsharer%2Fsharer.php%3Fu%3D%s%26t%3D%s&display=popup";

  /**
   * Instantiates a new share in facebook menu item.
   * 
   * @param action
   *          the action
   * @param iconic
   *          the iconic
   * @param session
   *          the session
   * @param menu
   *          the menu
   * @param i18n
   *          the i18n
   * @param downUtils
   *          the down utils
   */
  @Inject
  public ShareInFacebookMenuItem(final AbstractShareInSocialNetAction action,
      final IconicResources iconic, final Session session, final ShareMenu menu,
      final I18nTranslationService i18n, final ClientFileDownloadUtils downUtils) {
    super(action, session, menu, i18n.t("Share this in facebook"), iconic.facebook(),
        ClientFormattedString.build(false, URL_TEMPLATE, ShareInHelper.getCommonUrl(),
            ShareInHelper.getCommonText()));
  }
}
