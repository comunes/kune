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

import java.util.ArrayList;
import java.util.List;

import cc.kune.core.shared.dto.EmailNotificationFrequency;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Class Addressee is used to make a list of notifications addressees.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class Addressee {

  /**
   * Builds the.
   * 
   * @param lang
   *          the lang
   * @param emails
   *          the emails
   * @return the list
   */
  public static List<Addressee> build(final I18nLanguage lang, final String... emails) {
    final ArrayList<Addressee> list = new ArrayList<Addressee>();
    for (final String email : emails) {
      list.add(Addressee.build(email, lang));
    }
    return list;
  }

  /**
   * Builds and address from a email.
   * 
   * @param email
   *          the email
   * @param lang
   *          the lang
   * @return the addressee
   */
  public static Addressee build(final String email, final I18nLanguage lang) {
    return new Addressee(email, email, EmailNotificationFrequency.immediately, true, lang);
  }

  /**
   * Builds an addressee form a user.
   * 
   * @param user
   *          the user
   * @return the addressee
   */
  public static Addressee build(final User user) {
    return new Addressee(user.getShortName(), user.getEmail(), user.getEmailNotifFreq(),
        user.isEmailVerified(), user.getLanguage());
  }

  /** The address (an email, or a wave participant id). */
  private String address;

  /** The email verified. */
  private Boolean emailVerified;

  /** The freq. */
  private EmailNotificationFrequency freq;

  /** The lang. */
  private I18nLanguage lang;

  /** The shortname. */
  private String shortName;

  /**
   * Instantiates a new addressee.
   * 
   * @param shortName
   *          the short name
   * @param address
   *          the address
   * @param freq
   *          the freq
   * @param emailVerified
   *          the email verified
   * @param lang
   *          the lang
   */
  public Addressee(final String shortName, final String address, final EmailNotificationFrequency freq,
      final Boolean emailVerified, final I18nLanguage lang) {
    this.address = address;
    this.freq = freq;
    this.emailVerified = emailVerified;
    this.shortName = shortName;
    this.lang = lang;
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
    final Addressee other = (Addressee) obj;
    if (address == null) {
      if (other.address != null) {
        return false;
      }
    } else if (!address.equals(other.address)) {
      return false;
    }
    if (shortName == null) {
      if (other.shortName != null) {
        return false;
      }
    } else if (!shortName.equals(other.shortName)) {
      return false;
    }
    return true;
  }

  /**
   * Gets the address.
   * 
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * Gets the email verified.
   * 
   * @return the email verified
   */
  public Boolean getEmailVerified() {
    return emailVerified;
  }

  /**
   * Gets the freq.
   * 
   * @return the freq
   */
  public EmailNotificationFrequency getFreq() {
    return freq;
  }

  /**
   * Gets the lang.
   * 
   * @return the lang
   */
  public I18nLanguage getLang() {
    return lang;
  }

  /**
   * Gets the short name.
   * 
   * @return the short name
   */
  public String getShortName() {
    return shortName;
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
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
    return result;
  }

  /**
   * Checks if is email verified.
   * 
   * @return the boolean
   */
  public Boolean isEmailVerified() {
    return emailVerified;
  }

  /**
   * Sets the address.
   * 
   * @param address
   *          the new address
   */
  public void setAddress(final String address) {
    this.address = address;
  }

  /**
   * Sets the email verified.
   * 
   * @param emailVerified
   *          the new email verified
   */
  public void setEmailVerified(final Boolean emailVerified) {
    this.emailVerified = emailVerified;
  }

  /**
   * Sets the freq.
   * 
   * @param freq
   *          the new freq
   */
  public void setFreq(final EmailNotificationFrequency freq) {
    this.freq = freq;
  }

  /**
   * Sets the lang.
   * 
   * @param lang
   *          the new lang
   */
  public void setLang(final I18nLanguage lang) {
    this.lang = lang;
  }

  /**
   * Sets the short name.
   * 
   * @param shortName
   *          the new short name
   */
  public void setShortName(final String shortName) {
    this.shortName = shortName;
  }

}
