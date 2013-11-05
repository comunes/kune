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

import cc.kune.common.client.utils.ClientFormattedString;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.state.Session;

import com.google.gwt.http.client.URL;
import com.google.inject.Inject;

public class ShareInFacebookMenuItem extends AbstractShareInSocialNetMenuItem {
  private static final String URL_TEMPLATE = "https://www.facebook.com/dialog/feed?"
      + "app_id=SOMETHINGHERE&" +
      // LINK
      "link=%s&" +
      // IMAGE
      "picture=%s&" +
      // NAME
      "name=%s&" +
      // CAPTION
      "caption=Reference%20Documentation&" +
      // DESCRIPTION
      "description=%s&" +
      // REDIRECT
      "redirect_uri=%s";

  @Inject
  public ShareInFacebookMenuItem(final AbstractShareInSocialNetAction action,
      final IconicResources iconic, final Session session, final ContentViewerShareMenu menu,
      final I18nUITranslationService i18n, final ClientFileDownloadUtils downUtils) {
    // THIS DOES NOT WORK (we need to study how the API works better).
    super(action, session, menu, i18n.t("Share this in facebook"), iconic.facebook(),
        ClientFormattedString.build(
            false,
            URL_TEMPLATE,
            URL.encode(getCurrentUrl(session)),
            URL.encodeQueryString(downUtils.getGroupLogo(session.getCurrentState().getGroup())),
            URL.encode(getTitle(session)),
            URL.encode(i18n.tWithNT("via [%s]", "used in references 'something via @someone'",
                i18n.getSiteCommonName())), getCurrentUrl(session)));
  }
}
