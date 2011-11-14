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

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.server.properties.KuneProperties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MailServiceDefault implements MailService {

  public static class FormatedString {

    private final String string;

    public FormatedString(final String template, final Object... args) {
      string = String.format(template, args);
    }

    public String getString() {
      return string;
    }
  }

  Log log = LogFactory.getLog(MailServiceDefault.class);
  private final Properties props;
  private final String smtpDefaultFrom;
  private final boolean smtpSkip;

  @Inject
  public MailServiceDefault(final KuneProperties kuneProperties) {
    final String smtpServer = kuneProperties.get(KuneProperties.SITE_SMTP_HOST);
    smtpDefaultFrom = kuneProperties.get(KuneProperties.SITE_SMTP_DEFAULT_FROM);
    smtpSkip = kuneProperties.getBoolean(KuneProperties.SITE_SMTP_SKIP);
    props = System.getProperties();
    props.put("mail.smtp.host", smtpServer);
  }

  private void send(final FormatedString subject, final FormatedString body, final boolean isHtml,
      final String from, final String... tos) {
    if (smtpSkip) {
      return;
    }

    // Get session
    final Session session = Session.getDefaultInstance(props, null);

    // Define message
    final MimeMessage message = new MimeMessage(session);
    try {
      message.setFrom(new InternetAddress(from));
      for (final String to : tos) {
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      }
      final String formatedSubject = subject.getString();
      message.setSubject(formatedSubject);
      final String formatedBody = body.getString();
      if (isHtml) {
        message.setContent(formatedBody, "text/html");
      } else {
        message.setText(formatedBody);
      }
      // Send message
      Transport.send(message);
    } catch (final AddressException e) {
    } catch (final MessagingException e) {
      log.error("Error sending the email", e);
      throw new DefaultException("Error sending an email");
    }
  }

  @Override
  public void sendHtml(final FormatedString subject, final FormatedString body, final String to) {
    send(subject, body, true, smtpDefaultFrom, to);
  }

  @Override
  public void sendHtml(final FormatedString subject, final FormatedString body, final String from,
      final String... tos) {
    send(subject, body, true, from, tos);
  }

  @Override
  public void sendPlain(final FormatedString subject, final FormatedString body, final String to) {
    send(subject, body, false, smtpDefaultFrom, to);
  }

  @Override
  public void sendPlain(final FormatedString subject, final FormatedString body, final String from,
      final String... tos) {
    send(subject, body, false, from, tos);
  }
}
