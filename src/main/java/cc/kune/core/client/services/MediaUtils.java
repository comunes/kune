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
package cc.kune.core.client.services;

import cc.kune.core.client.state.Session;
import cc.kune.core.client.ui.ContentPosition;
import cc.kune.core.shared.domain.utils.StateToken;

public class MediaUtils {

  public static final String DOC_URL_TAG = "###DOC_URL###";

  private final Session session;

  private final ClientFileDownloadUtils downloadUtils;

  public MediaUtils(final Session session, final ClientFileDownloadUtils downloadUtils) {
    this.session = session;
    this.downloadUtils = downloadUtils;
  }

  public String getAviEmbed(final StateToken token) {
    return setCenterPosition(session.getInitData().getAviEmbedObject().replace(DOC_URL_TAG,
        session.getSiteUrl() + downloadUtils.getUrl(token)));
  }

  public String getFlvEmbed(final StateToken token) {
    return setCenterPosition(session.getInitData().getFlvEmbedObject().replace(DOC_URL_TAG,
        session.getSiteUrl() + downloadUtils.getUrl(token)));
  }

  public String getMp3Embed(final StateToken token) {
    return setCenterPosition(session.getInitData().getMp3EmbedObject().replace(DOC_URL_TAG,
        session.getSiteUrl() + downloadUtils.getUrl(token)));
  }

  public String getOggEmbed(final StateToken token) {
    return setCenterPosition(session.getInitData().getOggEmbedObject().replace(DOC_URL_TAG,
        session.getSiteUrl() + downloadUtils.getUrl(token)));
  }

  private String setCenterPosition(final String elementCode) {
    return ContentPosition.setCenterPosition(elementCode);
  }

}
