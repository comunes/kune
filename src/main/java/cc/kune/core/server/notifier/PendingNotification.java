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

import cc.kune.core.server.utils.FormattedString;

// TODO: Auto-generated Javadoc
/**
 * The Class PendingNotification is used to store and send notifications (for
 * instance email) via cron.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class PendingNotification {

  /**
   * The Constant NONE is used when for instance, all the destinations are not
   * local, so, we should not notify them by email.
   */
  public static final PendingNotification NONE = new PendingNotification(null, null, null, null, false,
      false, null);

  /** The Constant SITE_DEFAULT_SUBJECT_PREFIX. */
  public static final String SITE_DEFAULT_SUBJECT_PREFIX = new String();

  /** The body. */
  private final FormattedString body;

  /** The dest provider. */
  private final DestinationProvider destProvider;

  /** The force send. */
  private final boolean forceSend;

  /** The is html. */
  private final boolean isHtml;

  /** The notify type. */
  private final NotificationType notifyType;

  /** The subject. */
  private final FormattedString subject;

  /** The subject prefix [sitename]. */
  private final String subjectPrefix;

  /**
   * Instantiates a new pending notification.
   * 
   * @param notifyType
   *          the notify type
   * @param subject
   *          the subject
   * @param body
   *          the body
   * @param isHtml
   *          the is html
   * @param forceSend
   *          the force send
   * @param destProvider
   *          the dest provider
   */
  public PendingNotification(final NotificationType notifyType, final FormattedString subject,
      final FormattedString body, final boolean isHtml, final boolean forceSend,
      final DestinationProvider destProvider) {
    this(notifyType, SITE_DEFAULT_SUBJECT_PREFIX, subject, body, isHtml, forceSend, destProvider);
  }

  /**
   * Instantiates a new pending notification.
   * 
   * @param notifyType
   *          the notify type
   * @param subjectPrefix
   *          the subject prefix
   * @param subject
   *          the subject
   * @param body
   *          the body
   * @param isHtml
   *          the is html
   * @param forceSend
   *          the force send
   * @param destProvider
   *          the dest provider
   */
  public PendingNotification(final NotificationType notifyType, final String subjectPrefix,
      final FormattedString subject, final FormattedString body, final boolean isHtml,
      final boolean forceSend, final DestinationProvider destProvider) {
    this.notifyType = notifyType;
    this.subjectPrefix = subjectPrefix;
    this.subject = subject;
    this.body = body;
    this.isHtml = isHtml;
    this.forceSend = forceSend;
    this.destProvider = destProvider;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final PendingNotification other = (PendingNotification) obj;
    if (body == null) {
      if (other.body != null) {
        return false;
      }
    } else if (!body.equals(other.body)) {
      return false;
    }
    if (destProvider == null) {
      if (other.destProvider != null) {
        return false;
      }
    } else if (!destProvider.equals(other.destProvider)) {
      return false;
    }
    if (forceSend != other.forceSend) {
      return false;
    }
    if (isHtml != other.isHtml) {
      return false;
    }
    if (notifyType != other.notifyType) {
      return false;
    }
    if (subject == null) {
      if (other.subject != null) {
        return false;
      }
    } else if (!subject.equals(other.subject)) {
      return false;
    }
    return true;
  }

  /**
   * Gets the body.
   * 
   * @return the body
   */
  public FormattedString getBody() {
    return body;
  }

  /**
   * Gets the dest provider.
   * 
   * @return the dest provider
   */
  public DestinationProvider getDestProvider() {
    return destProvider;
  }

  /**
   * Gets the notify type.
   * 
   * @return the notify type
   */
  public NotificationType getNotifyType() {
    return notifyType;
  }

  /**
   * Gets the subject.
   * 
   * @return the subject
   */
  public FormattedString getSubject() {
    return subject;
  }

  /**
   * Gets the subject prefix.
   * 
   * @return the subject prefix
   */
  public String getSubjectPrefix() {
    return subjectPrefix;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((body == null) ? 0 : body.hashCode());
    result = prime * result + ((destProvider == null) ? 0 : destProvider.hashCode());
    result = prime * result + (forceSend ? 1231 : 1237);
    result = prime * result + (isHtml ? 1231 : 1237);
    result = prime * result + ((notifyType == null) ? 0 : notifyType.hashCode());
    result = prime * result + ((subject == null) ? 0 : subject.hashCode());
    return result;
  }

  /**
   * Checks if is force send.
   * 
   * @return true, if is force send
   */
  public boolean isForceSend() {
    return forceSend;
  }

  /**
   * Checks if is html.
   * 
   * @return true, if is html
   */
  public boolean isHtml() {
    return isHtml;
  }

}
