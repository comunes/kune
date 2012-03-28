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
package cc.kune.core.server.notifier;

import cc.kune.core.server.mail.FormatedString;
import cc.kune.core.server.utils.AbsoluteFileDownloadUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The Class NotifyHtmlHelper is used to get html snippets for email
 * notifications.
 */
@Singleton
public class NotificationHtmlHelper {

  /** The template used from messages snippets from a group */
  private static String GROUP_TEMPLATE = "<table style=\"FIXME\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\" width=\"100%%\">"
      + "<tbody><tr>"
      + "<td style=\"\" valign=\"top\"><a href=\"%s\">%s</a></td>"
      + "<td style=\"\" valign=\"top\" width=\"100%%\">"
      + "<a href=\"%s\">%s</a><br>"
      + "%s"
      + "</td></tr></tbody></table>";

  private final AbsoluteFileDownloadUtils fileDownloadUtils;

  /**
   * Instantiates a new notify html helper.
   * 
   * @param fileDownloadUtils
   *          the file download utils
   */
  @Inject
  public NotificationHtmlHelper(final AbsoluteFileDownloadUtils fileDownloadUtils) {
    this.fileDownloadUtils = fileDownloadUtils;
  }

  private FormatedString entityNotification(final String groupName, final boolean hasLogo,
      final String message, final boolean isPersonal) {
    final String groupUrl = fileDownloadUtils.getUrl(groupName);
    return FormatedString.build(false, GROUP_TEMPLATE, groupUrl,
        fileDownloadUtils.getLogoAvatarHtml(groupName, hasLogo, isPersonal, 50, 5), groupUrl, groupName,
        message);
  }

  /**
   * Generates a group notification in html like [logo|message]
   * 
   * @param groupName
   *          the group name you want to get the notification
   * @param hasLogo
   *          the has logo?
   * @param message
   *          the message to show close to the logo
   * @param readMoreMsg
   *          the read more msg
   * @return the html string
   */
  public FormatedString groupNotification(final String groupName, final boolean hasLogo,
      final String message) {
    return entityNotification(groupName, hasLogo, message, false);
  }

  /**
   * Generates a user notification in html like [logo|message]
   * 
   * @param userName
   *          the user name you want to get the notification
   * @param hasLogo
   *          the has logo?
   * @param message
   *          the message to show close to the logo
   * @param readMoreMsg
   *          the read more msg
   * @return the html string
   */
  public FormatedString userNotification(final String userName, final boolean hasLogo,
      final String message) {
    return entityNotification(userName, hasLogo, message, true);
  }

  /**
   * Format a user notification with an additional link. The first an unique %s
   * in body is changed by the site name.
   * 
   * @param body
   *          the body
   * @param hash
   *          the hash
   * @return the formated string
   */
  public FormatedString userNotification(final String body, final String hash) {
    final String hashUrl = fileDownloadUtils.getUrl(hash);
    return FormatedString.build(false, body + "<br><a href='%s'>%s</a>",
        fileDownloadUtils.getSiteCommonName(), hashUrl, hashUrl);
  }
}
