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
package cc.kune.core.shared.dto;

import cc.kune.core.shared.domain.utils.StateToken;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSimpleDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserSimpleDTO implements IsSerializable {

  /** The compound name. */
  private String compoundName;

  /** The country. */
  private I18nCountryDTO country;

  /** The created on. */
  private Long createdOn;

  /** The email. */
  private String email;

  /** The email notif freq. */
  private EmailNotificationFrequency emailNotifFreq;

  /** The email verified. */
  private boolean emailVerified;

  /** The has logo. */
  private boolean hasLogo;

  /** The id. */
  private Long id;

  /** The language. */
  private I18nLanguageDTO language;

  /** The name. */
  private String name;

  /** The short name. */
  private String shortName;

  /** The state token. */
  private StateToken stateToken;

  /** The timezone. */
  private TimeZoneDTO timezone;

  /**
   * Instantiates a new user simple dto.
   */
  public UserSimpleDTO() {
    this(null, null, null, null, null);
  }

  /**
   * Instantiates a new user simple dto.
   * 
   * @param name
   *          the name
   * @param shortName
   *          the short name
   * @param language
   *          the language
   * @param country
   *          the country
   * @param timezone
   *          the timezone
   */
  public UserSimpleDTO(final String name, final String shortName, final I18nLanguageDTO language,
      final I18nCountryDTO country, final TimeZoneDTO timezone) {
    this.name = name;
    this.shortName = shortName;
    this.language = language;
    this.country = country;
    this.timezone = timezone;
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
    final UserSimpleDTO other = (UserSimpleDTO) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
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
   * Gets the compound name.
   * 
   * @return the compound name
   */
  public String getCompoundName() {
    if (compoundName == null) {
      compoundName = !name.equals(shortName) ? name + " (" + shortName + ")" : shortName;
    }
    return compoundName;
  }

  /**
   * Gets the country.
   * 
   * @return the country
   */
  public I18nCountryDTO getCountry() {
    return country;
  }

  /**
   * Gets the created on.
   * 
   * @return the created on
   */
  public Long getCreatedOn() {
    return createdOn;
  }

  /**
   * Gets the email.
   * 
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Gets the email notif freq.
   * 
   * @return the email notif freq
   */
  public EmailNotificationFrequency getEmailNotifFreq() {
    return emailNotifFreq;
  }

  /**
   * Gets the email verified.
   * 
   * @return the email verified
   */
  public boolean getEmailVerified() {
    return isEmailVerified();
  }

  /**
   * Gets the checks for logo.
   * 
   * @return the checks for logo
   */
  public boolean getHasLogo() {
    return hasLogo;
  }

  /**
   * Gets the id.
   * 
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the language.
   * 
   * @return the language
   */
  public I18nLanguageDTO getLanguage() {
    return language;
  }

  /**
   * Gets the name.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the short name.
   * 
   * @return the short name
   */
  public String getShortName() {
    return shortName;
  }

  /**
   * Gets the state token.
   * 
   * @return the state token
   */
  public StateToken getStateToken() {
    return stateToken;
  }

  /**
   * Gets the timezone.
   * 
   * @return the timezone
   */
  public TimeZoneDTO getTimezone() {
    return timezone;
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
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
    return result;
  }

  /**
   * Checks for logo.
   * 
   * @return true, if successful
   */
  public boolean hasLogo() {
    return hasLogo;
  }

  /**
   * Checks if is email verified.
   * 
   * @return true, if is email verified
   */
  public boolean isEmailVerified() {
    return emailVerified;
  }

  /**
   * Sets the country.
   * 
   * @param country
   *          the new country
   */
  public void setCountry(final I18nCountryDTO country) {
    this.country = country;
  }

  /**
   * Sets the created on.
   * 
   * @param createdOn
   *          the new created on
   */
  public void setCreatedOn(final Long createdOn) {
    this.createdOn = createdOn;
  }

  /**
   * Sets the email.
   * 
   * @param email
   *          the new email
   */
  public void setEmail(final String email) {
    this.email = email;
  }

  /**
   * Sets the email notif freq.
   * 
   * @param emailNotifFreq
   *          the new email notif freq
   */
  public void setEmailNotifFreq(final EmailNotificationFrequency emailNotifFreq) {
    this.emailNotifFreq = emailNotifFreq;
  }

  /**
   * Sets the email verified.
   * 
   * @param emailVerified
   *          the new email verified
   */
  public void setEmailVerified(final boolean emailVerified) {
    this.emailVerified = emailVerified;
  }

  /**
   * Sets the checks for logo.
   * 
   * @param hasLogo
   *          the new checks for logo
   */
  public void setHasLogo(final boolean hasLogo) {
    this.hasLogo = hasLogo;
  }

  /**
   * Sets the id.
   * 
   * @param id
   *          the new id
   */
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Sets the language.
   * 
   * @param language
   *          the new language
   */
  public void setLanguage(final I18nLanguageDTO language) {
    this.language = language;
  }

  /**
   * Sets the name.
   * 
   * @param name
   *          the new name
   */
  public void setName(final String name) {
    this.name = name;
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

  /**
   * Sets the state token.
   * 
   * @param stateToken
   *          the new state token
   */
  public void setStateToken(final StateToken stateToken) {
    this.stateToken = stateToken;
  }

  /**
   * Sets the timezone.
   * 
   * @param timezone
   *          the new timezone
   */
  public void setTimezone(final TimeZoneDTO timezone) {
    this.timezone = timezone;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "UserSimpleDTO(" + shortName + ")";
  }

}
