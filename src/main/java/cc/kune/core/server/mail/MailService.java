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
package cc.kune.core.server.mail;

import cc.kune.common.shared.utils.AbstractFormattedString;

// TODO: Auto-generated Javadoc
/**
 * The Interface MailService.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface MailService {

  /**
   * Sends email.
   * 
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in text or html format
   * @param isHtml
   *          the is html
   * @param tos
   *          the recipients
   */
  void send(AbstractFormattedString subject, AbstractFormattedString body, boolean isHtml, String... tos);

  /**
   * Sends email.
   * 
   * @param from
   *          the sender
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in text or html format
   * @param isHtml
   *          the is html
   * @param tos
   *          the recipients
   */
  void send(String from, AbstractFormattedString subject, AbstractFormattedString body, boolean isHtml,
      String... tos);

  /**
   * Sends html email with default site "from".
   * 
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in html format
   * @param tos
   *          the recipients
   */
  void sendHtml(AbstractFormattedString subject, AbstractFormattedString body, String... tos);

  /**
   * Sends html email.
   * 
   * @param from
   *          the sender
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in html format
   * @param tos
   *          the recipients
   */
  void sendHtml(String from, AbstractFormattedString subject, AbstractFormattedString body,
      String... tos);

  /**
   * Sends email with default site "from".
   * 
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in text format
   * @param tos
   *          the recipients
   */
  void sendPlain(AbstractFormattedString subject, AbstractFormattedString body, String... tos);

  /**
   * Sends plain email.
   * 
   * @param from
   *          the sender
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in text format
   * @param tos
   *          the recipients
   */
  void sendPlain(String from, AbstractFormattedString subject, AbstractFormattedString body,
      String... tos);

}
