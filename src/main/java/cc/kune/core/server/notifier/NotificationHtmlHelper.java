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
package cc.kune.core.server.notifier;

import cc.kune.core.server.utils.AbsoluteFileDownloadUtils;
import cc.kune.core.server.utils.FormattedString;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class NotifyHtmlHelper is used to get html snippets for email
 * notifications.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class NotificationHtmlHelper {

  /** The template used from messages snippets from a group. */
  private static String GROUP_TEMPLATE = "<table style=\"FIXME\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\" width=\"100%%\">"
      + "<tbody><tr>"
      + "<td style=\"\" valign=\"top\"><a href=\"%s\">%s</a></td>"
      + "<td style=\"\" valign=\"top\" width=\"100%%\">"
      + "<a href=\"%s\">%s</a><br>"
      + "%s"
      + "</td></tr></tbody></table>";

  /** The file download utils. */
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
  @Deprecated
  public FormattedString createBodyWithEndLink(final String body, final String hash) {
    final String hashUrl = fileDownloadUtils.getUrl(hash);
    // FIXME %s aditional in body? dirttyyyyy
    return FormattedString.build(false, body + "<br><a href='%s'>%s</a>",
        fileDownloadUtils.getSiteCommonName(), hashUrl, hashUrl);
  }

  /**
   * Creates the link.
   * 
   * @param hash
   *          the hash
   * @return the string
   */
  public String createLink(final String hash) {
    final String hashUrl = fileDownloadUtils.getUrl(hash);
    return FormattedString.build(false, "<a href='%s'>%s</a>", hashUrl, hashUrl).getString();
  }

  /**
   * Entity notification.
   * 
   * @param groupName
   *          the group name
   * @param hasLogo
   *          the has logo
   * @param message
   *          the message
   * @param isPersonal
   *          the is personal
   * @return the formatted string
   */
  private FormattedString entityNotification(final String groupName, final boolean hasLogo,
      final String message, final boolean isPersonal) {
    final String groupUrl = fileDownloadUtils.getUrl(groupName);
    return FormattedString.build(false, GROUP_TEMPLATE, groupUrl,
        fileDownloadUtils.getLogoAvatarHtml(groupName, hasLogo, isPersonal, 50, 5), groupUrl, groupName,
        message);
  }

  /**
   * Generates a group notification in html like [logo|message].
   * 
   * @param groupName
   *          the group name you want to get the notification
   * @param hasLogo
   *          the has logo?
   * @param message
   *          the message to show close to the logo
   * @return the html string
   */
  public FormattedString groupNotification(final String groupName, final boolean hasLogo,
      final String message) {
    return entityNotification(groupName, hasLogo, message, false);
  }

  /**
   * Generates a user notification in html like [logo|message].
   * 
   * @param userName
   *          the user name you want to get the notification
   * @param hasLogo
   *          the has logo?
   * @param message
   *          the message to show close to the logo
   * @return the html string
   */
  public FormattedString userNotification(final String userName, final boolean hasLogo,
      final String message) {
    return entityNotification(userName, hasLogo, message, true);
  }
}
