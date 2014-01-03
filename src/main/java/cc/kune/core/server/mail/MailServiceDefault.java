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

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.common.shared.utils.AbstractFormattedString;
import cc.kune.core.server.LogThis;
import cc.kune.core.server.properties.KuneProperties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class MailServiceDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
@LogThis
public class MailServiceDefault implements MailService {

  /** The log. */
  Log log = LogFactory.getLog(MailServiceDefault.class);

  /** The props. */
  private final Properties props;

  /** The smtp default from. */
  private final String smtpDefaultFrom;

  /** The smtp skip. */
  private final boolean smtpSkip;

  /**
   * Instantiates a new mail service default.
   * 
   * @param kuneProperties
   *          the kune properties
   */
  @Inject
  public MailServiceDefault(final KuneProperties kuneProperties) {
    final String smtpServer = kuneProperties.get(KuneProperties.SITE_SMTP_HOST);
    smtpDefaultFrom = kuneProperties.get(KuneProperties.SITE_SMTP_DEFAULT_FROM);
    smtpSkip = kuneProperties.getBoolean(KuneProperties.SITE_SMTP_SKIP);
    props = System.getProperties();
    props.put("mail.smtp.host", smtpServer);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.mail.MailService#send(cc.kune.common.shared.utils.
   * AbstractFormattedString,
   * cc.kune.common.shared.utils.AbstractFormattedString, boolean,
   * java.lang.String[])
   */
  @Override
  public void send(final AbstractFormattedString subject, final AbstractFormattedString body,
      final boolean isHtml, final String... tos) {
    send(smtpDefaultFrom, subject, body, isHtml, tos);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.mail.MailService#send(java.lang.String,
   * cc.kune.common.shared.utils.AbstractFormattedString,
   * cc.kune.common.shared.utils.AbstractFormattedString, boolean,
   * java.lang.String[])
   */
  @Override
  public void send(final String from, final AbstractFormattedString subject,
      final AbstractFormattedString body, final boolean isHtml, final String... tos) {
    if (smtpSkip) {
      return;
    }

    // Get session
    final Session session = Session.getDefaultInstance(props, null);

    // Define message
    final MimeMessage message = new MimeMessage(session);
    for (final String to : tos) {
      try {
        message.setFrom(new InternetAddress(from));
        // In case we should use utf8 also in address:
        // http://stackoverflow.com/questions/2656478/send-javax-mail-internet-mimemessage-to-a-recipient-with-non-ascii-name
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        // If additional header should be added
        // message.addHeader(name, MimeUtility.encodeText(value, "utf-8", "B"));
        final String formatedSubject = subject.getString();
        message.setSubject(formatedSubject, "utf-8");
        final String formatedBody = body.getString();
        if (isHtml) {
          // message.setContent(formatedBody, "text/html");
          message.setText(formatedBody, "UTF-8", "html");
        } else {
          message.setText(formatedBody, "UTF-8");
        }
        // Send message
        Transport.send(message);
      } catch (final AddressException e) {
      } catch (final MessagingException e) {
        final String error = String.format(
            "Error sendind an email to %s, with subject: %s, and body: %s", from, subject, to);
        log.error(error, e);
        // Better not to throw exceptions because users emails can be wrong...
        // throw new DefaultException(error, e);
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.mail.MailService#sendHtml(cc.kune.common.shared.utils
   * .AbstractFormattedString,
   * cc.kune.common.shared.utils.AbstractFormattedString, java.lang.String[])
   */
  @Override
  public void sendHtml(final AbstractFormattedString subject, final AbstractFormattedString body,
      final String... tos) {
    send(smtpDefaultFrom, subject, body, true, tos);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.mail.MailService#sendHtml(java.lang.String,
   * cc.kune.common.shared.utils.AbstractFormattedString,
   * cc.kune.common.shared.utils.AbstractFormattedString, java.lang.String[])
   */
  @Override
  public void sendHtml(final String from, final AbstractFormattedString subject,
      final AbstractFormattedString body, final String... tos) {
    send(from, subject, body, true, tos);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.mail.MailService#sendPlain(cc.kune.common.shared.utils
   * .AbstractFormattedString,
   * cc.kune.common.shared.utils.AbstractFormattedString, java.lang.String[])
   */
  @Override
  public void sendPlain(final AbstractFormattedString subject, final AbstractFormattedString body,
      final String... tos) {
    send(smtpDefaultFrom, subject, body, false, tos);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.mail.MailService#sendPlain(java.lang.String,
   * cc.kune.common.shared.utils.AbstractFormattedString,
   * cc.kune.common.shared.utils.AbstractFormattedString, java.lang.String[])
   */
  @Override
  public void sendPlain(final String from, final AbstractFormattedString subject,
      final AbstractFormattedString body, final String... tos) {
    send(from, subject, body, false, tos);
  }
}
