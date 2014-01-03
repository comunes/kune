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

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.server.i18n.I18nTranslationServiceMultiLang;
import cc.kune.core.server.mail.MailService;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.server.utils.FormattedString;
import cc.kune.core.server.xmpp.XmppManager;
import cc.kune.core.shared.dto.EmailNotificationFrequency;
import cc.kune.wave.server.kspecific.KuneWaveService;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class NotificationSenderDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class NotificationSenderDefault implements NotificationSender {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(NotificationSenderDefault.class);

  /** The email template. */
  private final String emailTemplate;

  /** The i18n. */
  @SuppressWarnings("unused")
  private final I18nTranslationServiceMultiLang i18n;

  /** The mail service. */
  private final MailService mailService;

  /** The site name. */
  private final String siteName;

  /** The users online. */
  private final UsersOnline usersOnline;

  /** The wave service. */
  private final KuneWaveService waveService;

  /** The xmpp manager. */
  private final XmppManager xmppManager;

  /**
   * Instantiates a new notification sender default.
   * 
   * @param mailService
   *          the mail service
   * @param waveService
   *          the wave service
   * @param xmppManager
   *          the xmpp manager
   * @param i18n
   *          the i18n
   * @param usersOnline
   *          the users online
   * @param kuneProperties
   *          the kune properties
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  @Inject
  public NotificationSenderDefault(final MailService mailService, final KuneWaveService waveService,
      final XmppManager xmppManager, final I18nTranslationServiceMultiLang i18n,
      final UsersOnline usersOnline, final KuneProperties kuneProperties) throws IOException {
    this.mailService = mailService;
    this.waveService = waveService;
    this.xmppManager = xmppManager;
    this.i18n = i18n;
    this.usersOnline = usersOnline;
    emailTemplate = FileUtils.readFileToString(new File(
        kuneProperties.get(KuneProperties.SITE_EMAIL_TEMPLATE)));
    Preconditions.checkNotNull(emailTemplate);
    Preconditions.checkArgument(TextUtils.notEmpty(emailTemplate));
    siteName = kuneProperties.get(KuneProperties.SITE_NAME);
  }

  /**
   * Adds the braquet.
   * 
   * @param subjectPrefix
   *          the subject prefix
   * @return the string
   */
  private String addBraquet(final String subjectPrefix) {
    return new StringBuffer("[").append(subjectPrefix).append("] ").toString();
  }

  /**
   * No online.
   * 
   * @param username
   *          the username
   * @return true, if successful
   */
  private boolean noOnline(final String username) {
    final boolean isOnline = usersOnline.isOnline(username);
    LOG.debug(String.format("User '%s' is online for notifications? %s", username, isOnline));
    return !isOnline;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.notifier.NotificationSender#send(cc.kune.core.server
   * .notifier.PendingNotification,
   * cc.kune.core.shared.dto.EmailNotificationFrequency)
   */
  @Override
  public void send(final PendingNotification notification, final EmailNotificationFrequency withFrequency) {
    final FormattedString subject = notification.getSubject().copy();
    final FormattedString body = notification.getBody();
    final NotificationType notifyType = notification.getNotifyType();
    final boolean forceSend = notification.isForceSend();
    final boolean isHtml = notification.isHtml();
    final String subjectPrefix = notification.getSubjectPrefix();
    final String subjectWithoutBra = subjectPrefix.equals(PendingNotification.SITE_DEFAULT_SUBJECT_PREFIX) ? siteName
        : subjectPrefix;
    subject.setTemplate(addBraquet(subjectWithoutBra) + subject.getTemplate());
    if (subject.shouldBeTranslated()) {
      // Translate per recipient language
      // final String subjectTranslation = i18n.tWithNT(user.getLanguage(),
      // subject.getTemplate(), "");
      // if (subjectTranslation != null) {
      // Right now commented because we are only testing
      // subject.setTemplate(subjectTranslation);
      // }
    }
    if (body.shouldBeTranslated()) {
      // final String bodyTranslation = i18n.tWithNT(user.getLanguage(),
      // body.getTemplate(), "");
      // if (bodyTranslation != null) {
      // Right now commented because we are only testing
      // body.setTemplate(bodyTranslation);
      // }
    }

    for (final Addressee user : notification.getDestProvider().getDest()) {
      final String username = user.getShortName();

      switch (notifyType) {
      case chat:
        // FIXME seems that html is not sending correctly... check server specs
        xmppManager.sendMessage(username,
            String.format("<b>%s</b>%s", subject.getString(), body.getString()));
        break;
      case email:
        if (forceSend
            || (user.isEmailVerified() && noOnline(username) && withFrequency == user.getFreq())) {
          // we'll send this notification if is mandatory or this user is not
          // only and has this freq configured
          mailService.send(subject,
              FormattedString.build(emailTemplate.replace("%s", body.getString())), isHtml,
              user.getAddress());
        }
        break;
      case wave:
        if (isHtml) {
          LOG.error("Wave html messages not supported yet");
        }
        waveService.createWave(subject.getString(), body.getString(), KuneWaveService.DO_NOTHING_CBACK,
            username);
        break;
      }
    }

  }
}
