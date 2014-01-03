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
package cc.kune.core.client.services;

import cc.kune.core.client.state.Session;
import cc.kune.core.client.ui.ContentPosition;
import cc.kune.core.shared.domain.utils.StateToken;

// TODO: Auto-generated Javadoc
/**
 * The Class MediaUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class MediaUtils {

  /** The Constant DOC_URL_TAG. */
  public static final String DOC_URL_TAG = "###DOC_URL###";

  /** The download utils. */
  private final ClientFileDownloadUtils downloadUtils;

  /** The session. */
  private final Session session;

  /**
   * Instantiates a new media utils.
   * 
   * @param session
   *          the session
   * @param downloadUtils
   *          the download utils
   */
  public MediaUtils(final Session session, final ClientFileDownloadUtils downloadUtils) {
    this.session = session;
    this.downloadUtils = downloadUtils;
  }

  /**
   * Gets the avi embed.
   * 
   * @param token
   *          the token
   * @return the avi embed
   */
  public String getAviEmbed(final StateToken token) {
    return setCenterPosition(session.getInitData().getAviEmbedObject().replace(DOC_URL_TAG,
        session.getSiteUrl() + downloadUtils.getUrl(token)));
  }

  /**
   * Gets the flv embed.
   * 
   * @param token
   *          the token
   * @return the flv embed
   */
  public String getFlvEmbed(final StateToken token) {
    return setCenterPosition(session.getInitData().getFlvEmbedObject().replace(DOC_URL_TAG,
        session.getSiteUrl() + downloadUtils.getUrl(token)));
  }

  /**
   * Gets the mp3 embed.
   * 
   * @param token
   *          the token
   * @return the mp3 embed
   */
  public String getMp3Embed(final StateToken token) {
    return setCenterPosition(session.getInitData().getMp3EmbedObject().replace(DOC_URL_TAG,
        session.getSiteUrl() + downloadUtils.getUrl(token)));
  }

  /**
   * Gets the ogg embed.
   * 
   * @param token
   *          the token
   * @return the ogg embed
   */
  public String getOggEmbed(final StateToken token) {
    return setCenterPosition(session.getInitData().getOggEmbedObject().replace(DOC_URL_TAG,
        session.getSiteUrl() + downloadUtils.getUrl(token)));
  }

  /**
   * Sets the center position.
   * 
   * @param elementCode
   *          the element code
   * @return the string
   */
  private String setCenterPosition(final String elementCode) {
    return ContentPosition.setCenterPosition(elementCode);
  }

}
