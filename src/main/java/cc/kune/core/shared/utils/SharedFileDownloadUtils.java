/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.shared.utils;

import cc.kune.common.shared.utils.Url;
import cc.kune.common.shared.utils.UrlParam;
import cc.kune.core.client.state.impl.HistoryUtils;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

public class SharedFileDownloadUtils {

  private final String prefix;

  public SharedFileDownloadUtils() {
    this.prefix = "";
  }

  public SharedFileDownloadUtils(final String prefix) {
    if (prefix.endsWith("/")) {
      final int lastSlash = prefix.lastIndexOf("/");
      this.prefix = prefix.substring(0, lastSlash == -1 ? prefix.length() : lastSlash);
    } else {
      this.prefix = prefix;
    }
  }

  public String getCacheSuffix(final boolean noCache) {
    return noCache ? UrlParam.noCacheStringSuffix() : "";
  }

  public String getGroupLogo(final GroupDTO group) {
    return prefix
        + (group.hasLogo() ? getLogoImageUrl(group.getShortName()) : group.isPersonal() ? "/"
            + FileConstants.PERSON_NO_AVATAR_IMAGE : "/" + FileConstants.GROUP_NO_AVATAR_IMAGE);
  }

  public String getLogoAvatarHtml(final String groupName, final boolean groupHasLogo,
      final boolean isPersonal, final int size, final int hvspace) {
    final String imgUrl = groupHasLogo ? getLogoImageUrl(groupName) : isPersonal ? prefix + "/"
        + FileConstants.PERSON_NO_AVATAR_IMAGE : prefix + "/" + FileConstants.GROUP_NO_AVATAR_IMAGE;
    return "<img hspace='" + hvspace + "' vspace='" + hvspace + "' align='left' style='width: " + size
        + "px; height: " + size + "px;' src='" + imgUrl + "'>";
  }

  public String getLogoImageUrl(final String groupName) {
    return getLogoImageUrl(groupName, false);
  }

  public String getLogoImageUrl(final String groupName, final boolean noCache) {
    return prefix
        + new Url(FileConstants.LOGODOWNLOADSERVLET, new UrlParam(FileConstants.TOKEN, groupName),
            new UrlParam(FileConstants.ONLY_USERS, false)).toString() + getCacheSuffix(noCache);
  }

  public String getPrefix() {
    return prefix;
  }

  public String getUrl(final String hash) {
    return getPrefix() + "/#" + HistoryUtils.hashbang(hash);
  }

  public String getUserAvatar(final String username) {
    return getUserAvatar(username, false);
  }

  public String getUserAvatar(final String username, final boolean noCache) {
    return prefix
        + new Url(FileConstants.LOGODOWNLOADSERVLET, new UrlParam(FileConstants.TOKEN, username),
            new UrlParam(FileConstants.ONLY_USERS, true)).toString() + getCacheSuffix(noCache);
  }

  public String getUserAvatar(final UserSimpleDTO user) {
    return prefix
        + (user.hasLogo() ? getLogoImageUrl(user.getShortName()) : FileConstants.PERSON_NO_AVATAR_IMAGE);
  }

}
