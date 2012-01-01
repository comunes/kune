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
package cc.kune.core.server.mail;

public interface MailService {

  /**
   * Sends email
   * 
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in text or html format
   * @param from
   *          the sender
   * @param tos
   *          the recipients
   */
  void send(FormatedString subject, FormatedString body, boolean isHtml, String... tos);

  /**
   * Sends email
   * 
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in text or html format
   * @param from
   *          the sender
   * @param tos
   *          the recipients
   */
  void send(String from, FormatedString subject, FormatedString body, boolean isHtml, String... tos);

  /**
   * Sends html email with default site "from"
   * 
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in html format
   * @param tos
   *          the recipients
   */
  void sendHtml(FormatedString subject, FormatedString body, String... tos);

  /**
   * Sends html email
   * 
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in html format
   * @param from
   *          the sender
   * @param tos
   *          the recipients
   */
  void sendHtml(String from, FormatedString subject, FormatedString body, String... tos);

  /**
   * 
   * Sends email with default site "from"
   * 
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in text format
   * @param tos
   *          the recipients
   */
  void sendPlain(FormatedString subject, FormatedString body, String... tos);

  /**
   * Sends plain email
   * 
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in text format
   * @param from
   *          the sender
   * @param tos
   *          the recipients
   */
  void sendPlain(String from, FormatedString subject, FormatedString body, String... tos);

}
