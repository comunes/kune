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
package cc.kune.core.shared.utils;

import cc.kune.common.shared.utils.Url;
import cc.kune.common.shared.utils.UrlParam;
import cc.kune.core.client.state.impl.HistoryUtils;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

// TODO: Auto-generated Javadoc
/**
 * The Class SharedFileDownloadUtils.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class SharedFileDownloadUtils {

  /** The prefix. */
  protected String prefix;
  private final ChangedLogosRegistry recentlyChanged;

  /**
   * Instantiates a new shared file download utils.
   */
  public SharedFileDownloadUtils(final ChangedLogosRegistry recentlyChanged) {
    this.recentlyChanged = recentlyChanged;
    this.prefix = "";
  }

  /**
   * Instantiates a new shared file download utils.
   *
   * @param prefix
   *          the prefix
   */
  public SharedFileDownloadUtils(final String prefix, final ChangedLogosRegistry recentlyChanged) {
    this(recentlyChanged);
    setPrefix(prefix);
  }

  /**
   * Gets the group logo.
   *
   * @param group
   *          the group
   * @return the group logo
   */
  public String getGroupLogo(final GroupDTO group) {
    return prefix
        + (group.hasLogo() ? getLogoImageUrl(group.getShortName(),
            group.getLogoLastModifiedTime().toString())
            : group.isPersonal() ? getLogoImageUrl(group.getShortName()) : "/"
                + FileConstants.GROUP_NO_AVATAR_IMAGE);
  }

  /**
   * Gets the logo avatar html.
   *
   * @param groupName
   *          the group name
   * @param groupHasLogo
   *          the group has logo
   * @param isPersonal
   *          the is personal
   * @param size
   *          the size
   * @param hvspace
   *          the hvspace
   * @return the logo avatar html
   */
  public String getLogoAvatarHtml(final String groupName, final boolean groupHasLogo,
      final boolean isPersonal, final int size, final int hvspace) {
    final String imgUrl = groupHasLogo ? getLogoImageUrl(groupName)
        : isPersonal ? getLogoImageUrl(groupName) : prefix + "/" + FileConstants.GROUP_NO_AVATAR_IMAGE;
    return "<img hspace='" + hvspace + "' vspace='" + hvspace + "' align='left' style='width: " + size
        + "px; height: " + size + "px;' src='" + imgUrl + "'>";
  }

  /**
   * Gets the logo image url.
   *
   * @param groupName
   *          the group name
   * @return the logo image url
   */
  public String getLogoImageUrl(final String groupName) {
    return prefix
        + new Url(FileConstants.LOGODOWNLOADSERVLET, new UrlParam(FileConstants.TOKEN, groupName),
            new UrlParam(FileConstants.ONLY_USERS, false)).toString() + noCacheSuffix(groupName);
  }

  /**
   * Gets the logo image url.
   *
   * @param groupName
   *          the group name
   * @return the logo image url
   */
  public String getLogoImageUrl(final String groupName, final String suffix) {
    return prefix
        + new Url(FileConstants.LOGODOWNLOADSERVLET, new UrlParam(FileConstants.TOKEN, groupName),
            new UrlParam(FileConstants.ONLY_USERS, false)).toString()
        + UrlParam.noCacheStringSuffix(suffix);
  }

  /**
   * Gets the prefix.
   *
   * @return the prefix
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * Gets the url.
   *
   * @param hash
   *          the hash
   * @return the url
   */
  public String getUrl(final String hash) {
    return getPrefix() + "/#" + HistoryUtils.hashbang(hash);
  }

  /**
   * Gets the user avatar.
   *
   * @param username
   *          the username
   * @return the user avatar
   */
  public String getUserAvatar(final String username) {
    return prefix
        + new Url(FileConstants.LOGODOWNLOADSERVLET, new UrlParam(FileConstants.TOKEN, username),
            new UrlParam(FileConstants.ONLY_USERS, true)).toString() + noCacheSuffix(username);
  }

  /**
   * Gets the user avatar.
   *
   * @param user
   *          the user
   * @return the user avatar
   */
  public String getUserAvatar(final UserSimpleDTO user) {
    return prefix + getLogoImageUrl(user.getShortName(), user.getLogoLastModifiedTime().toString());
  }

  public String noCacheSuffix(final String shortName) {
    return recentlyChanged.isRecentlyChanged(shortName) ? UrlParam.noCacheStringSuffix(recentlyChanged.getLastModified(shortName))
        : "";
  }

  public void setPrefix(final String prefix) {
    if (prefix == null) {
      this.prefix = "";
    } else if (prefix.endsWith("/")) {
      final int lastSlash = prefix.lastIndexOf("/");
      this.prefix = prefix.substring(0, lastSlash == -1 ? prefix.length() : lastSlash);
    } else {
      this.prefix = prefix;
    }
  }

}
