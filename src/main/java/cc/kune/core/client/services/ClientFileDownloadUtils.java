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
 \*/
package cc.kune.core.client.services;

import cc.kune.common.shared.utils.Url;
import cc.kune.common.shared.utils.UrlParam;
import cc.kune.core.client.state.SessionInstance;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.utils.SharedFileDownloadUtils;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;

/**
 * The Class ClientFileDownloadUtils.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ClientFileDownloadUtils extends SharedFileDownloadUtils {

  @Inject
  public ClientFileDownloadUtils() {
  }

  /**
   * Calculate url.
   * 
   * @param token
   *          the token
   * @param download
   *          the download
   * @param useHash
   *          the use hash
   * @return the string
   */
  private String calculateUrl(final StateToken token, final boolean download, final boolean useHash) {
    final Url url = new Url(prefix + FileConstants.DOWNLOADSERVLET, new UrlParam(FileConstants.TOKEN,
        token.toString()));
    if (download) {
      url.add(new UrlParam(FileConstants.DOWNLOAD, download));
    }
    if (useHash) {
      final String hash = SessionInstance.get().getUserHash();
      if (hash != null) {
        url.add(new UrlParam(FileConstants.HASH, hash));
      }
    }
    return url.toString();
  }

  /**
   * Download file.
   * 
   * @param token
   *          the token
   */
  public void downloadFile(final StateToken token) {
    final String url = calculateUrl(token, true, true);
    DOM.setElementAttribute(RootPanel.get("__download").getElement(), "src", url);
  }

  /**
   * Gets the background image url.
   * 
   * @param token
   *          the token
   * @param noCache
   *          the no cache
   * @return the background image url
   */
  public String getBackgroundImageUrl(final StateToken token, final boolean noCache) {
    return new Url(prefix + FileConstants.BACKDOWNLOADSERVLET, new UrlParam(FileConstants.TOKEN,
        token.toString())).toString() + getCacheSuffix(noCache);
  }

  /**
   * Gets the background resized url.
   * 
   * @param token
   *          the token
   * @param imageSize
   *          the image size
   * @return the background resized url
   */
  public String getBackgroundResizedUrl(final StateToken token, final ImageSize imageSize) {
    return new Url(prefix + FileConstants.BACKDOWNLOADSERVLET, new UrlParam(FileConstants.TOKEN,
        token.toString()), new UrlParam(FileConstants.IMGSIZE, imageSize.toString())).toString()
        + getCacheSuffix(true);
  }

  /**
   * Gets the image resized url.
   * 
   * @param token
   *          the token
   * @param imageSize
   *          the image size
   * @return the image resized url
   */
  public String getImageResizedUrl(final StateToken token, final ImageSize imageSize) {
    return calculateUrl(token, false, true) + "&"
        + new UrlParam(FileConstants.IMGSIZE, imageSize.toString());
  }

  /**
   * Gets the image url.
   * 
   * @param token
   *          the token
   * @return the image url
   */
  public String getImageUrl(final StateToken token) {
    return calculateUrl(token, false, true);
  }

  /**
   * Gets the url.
   * 
   * @param token
   *          the token
   * @return the url
   */
  public String getUrl(final StateToken token) {
    return calculateUrl(token, false, false);
  }

}
