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
package cc.kune.domain;

import java.util.TimeZone;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import cc.kune.core.shared.CoreConstants;
import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.EmailNotificationFrequency;
import cc.kune.domain.utils.HasId;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Indexed
@Table(name = "kusers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements HasId {

  /** The Constant DEF_EMAIL_FREQ. */
  private static final EmailNotificationFrequency DEF_EMAIL_FREQ = EmailNotificationFrequency.immediately;

  // public static final String PROPS_ID = "userprops";
  // see: http://docs.codehaus.org/display/PICO/Good+Citizen:
  // Never expect or return null
  /** The Constant UNKNOWN_USER. */
  public static final User UNKNOWN_USER = new User();

  /**
   * Checks if is known user.
   * 
   * @param user
   *          the user
   * @return true, if is known user
   */
  public static boolean isKnownUser(final User user) {
    return !user.equals(UNKNOWN_USER);
  }

  /** The country. */
  @ManyToOne
  @NotNull
  private I18nCountry country;

  /** The created on. */
  @org.hibernate.annotations.Index(name = "createdOn")
  @Basic(optional = false)
  private final Long createdOn;

  /** The diggest. */
  @Basic
  @Lob
  @Column(nullable = false)
  private byte[] diggest;

  /** The email. */
  @Column(unique = true, nullable = false)
  @Email
  @Length(min = 1)
  private String email;

  /** The email check date. */
  @Basic(optional = true)
  private Long emailCheckDate;

  // @OneToOne(cascade = CascadeType.REMOVE)
  // private final CustomProperties customProperties;

  /** The email confirm hash. */
  @Column(unique = true, nullable = true)
  private String emailConfirmHash;

  /** The email notif freq. */
  @Enumerated(EnumType.STRING)
  private EmailNotificationFrequency emailNotifFreq;

  /** The email verified. */
  @Basic
  @Column(columnDefinition = "BIT", length = 1)
  private Boolean emailVerified;

  /** The id. */
  @Id
  @DocumentId
  @GeneratedValue
  private Long id;

  /** The language. */
  @ManyToOne
  @NotNull
  private I18nLanguage language;

  /** The last login. */
  @Basic
  private Long lastLogin;

  /** The name. */
  @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
  @Column(nullable = false, unique = true)
  @Length(min = 3, max = CoreConstants.MAX_LONG_NAME_SIZE)
  private String name;

  /** The salt. */
  @Basic
  @Lob
  @Column(nullable = false)
  private byte[] salt;

  /** The short name. */
  @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
  @Column(unique = true)
  // http://www.hibernate.org/hib_docs/validator/reference/en/html/validator-defineconstraints.html
  @Length(min = 3, max = CoreConstants.MAX_SHORT_NAME_SIZE)
  @Pattern(regexp = "^[a-z0-9]+$", message = "The name must be between 3 and "
      + CoreConstants.MAX_SHORT_NAME_SIZE
      + " lowercase characters. It can only contain Western characters, numbers, and dashes")
  private String shortName;

  /** The s net visibility. */
  @Enumerated(EnumType.STRING)
  private UserSNetVisibility sNetVisibility;

  /** The timezone. */
  @NotNull
  private TimeZone timezone;

  /** The user group. */
  @OneToOne(cascade = CascadeType.ALL)
  private Group userGroup;

  /**
   * Instantiates a new user.
   */
  public User() {
    this(null, null, null, null, null, null, null, null);
  }

  /**
   * Instantiates a new user.
   * 
   * @param shortName
   *          the short name
   * @param longName
   *          the long name
   * @param email
   *          the email
   * @param diggets
   *          the diggets
   * @param salt
   *          the salt
   * @param language
   *          the language
   * @param country
   *          the country
   * @param timezone
   *          the timezone
   */
  public User(final String shortName, final String longName, final String email, final byte[] diggets,
      final byte[] salt, final I18nLanguage language, final I18nCountry country, final TimeZone timezone) {
    this.shortName = shortName;
    this.name = longName;
    this.email = email;
    this.diggest = diggets;
    this.salt = salt;
    this.userGroup = null;
    this.language = language;
    this.country = country;
    this.timezone = timezone;
    // customProperties = new CustomProperties();
    sNetVisibility = UserSNetVisibility.anyone;
    this.createdOn = System.currentTimeMillis();
    this.lastLogin = null;
    emailNotifFreq = DEF_EMAIL_FREQ;
    // this.properties = properties;
    emailVerified = false;
  }

  // @OneToOne
  // @OnDelete(action = OnDeleteAction.CASCADE)
  // private Properties properties;

  //
  // public User(final String shortName, final String longName, final String
  // email, final String passwd,
  // final I18nLanguage language, final I18nCountry country, final TimeZone
  // timezone) {
  // this(shortName, longName, email, passwd, language, country, timezone,
  // null);
  // }

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
    final User other = (User) obj;
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
   * Gets the country.
   * 
   * @return the country
   */
  public I18nCountry getCountry() {
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
   * Gets the diggest.
   * 
   * @return the diggest
   */
  public byte[] getDiggest() {
    return diggest;
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
   * Gets the email check date.
   * 
   * @return the email check date
   */
  public Long getEmailCheckDate() {
    return emailCheckDate;
  }

  /**
   * Gets the email confirm hash.
   * 
   * @return the email confirm hash
   */
  public String getEmailConfirmHash() {
    return emailConfirmHash;
  }

  /**
   * Gets the email notif freq.
   * 
   * @return the email notif freq
   */
  public EmailNotificationFrequency getEmailNotifFreq() {
    return emailNotifFreq == null ? DEF_EMAIL_FREQ : emailNotifFreq;
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
    return hasLogo();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.domain.utils.HasId#getId()
   */
  @Override
  public Long getId() {
    return id;
  }

  /**
   * Gets the language.
   * 
   * @return the language
   */
  public I18nLanguage getLanguage() {
    return language;
  }

  /**
   * Gets the last login.
   * 
   * @return the last login
   */
  public Long getLastLogin() {
    return lastLogin;
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
   * Gets the salt.
   * 
   * @return the salt
   */
  public byte[] getSalt() {
    return salt;
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
   * Gets the s net visibility.
   * 
   * @return the s net visibility
   */
  public UserSNetVisibility getSNetVisibility() {
    return sNetVisibility;
  }

  /**
   * Gets the state token.
   * 
   * @return the state token
   */
  @Transient
  public StateToken getStateToken() {
    return userGroup.getStateToken();
  }

  /**
   * Gets the timezone.
   * 
   * @return the timezone
   */
  public TimeZone getTimezone() {
    return timezone;
  }

  /**
   * Gets the user group.
   * 
   * @return the user group
   */
  public Group getUserGroup() {
    return userGroup;
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
    result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
    return result;
  }

  /**
   * Checks for logo.
   * 
   * @return true, if successful
   */
  @Transient
  public boolean hasLogo() {
    return userGroup.hasLogo();
  }

  /**
   * Checks if is email verified.
   * 
   * @return true, if is email verified
   */
  public boolean isEmailVerified() {
    return emailVerified == null ? false : emailVerified;
  }

  /**
   * Sets the country.
   * 
   * @param country
   *          the new country
   */
  public void setCountry(final I18nCountry country) {
    this.country = country;
  }

  /**
   * Sets the diggest.
   * 
   * @param diggest
   *          the new diggest
   */
  public void setDiggest(final byte[] diggest) {
    this.diggest = diggest;
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
   * Sets the email check date.
   * 
   * @param emailCheckDate
   *          the new email check date
   */
  public void setEmailCheckDate(final Long emailCheckDate) {
    this.emailCheckDate = emailCheckDate;
  }

  /**
   * Sets the email confirm hash.
   * 
   * @param emailConfirmHash
   *          the new email confirm hash
   */
  public void setEmailConfirmHash(final String emailConfirmHash) {
    this.emailConfirmHash = emailConfirmHash;
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

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.domain.utils.HasId#setId(java.lang.Long)
   */
  @Override
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Sets the language.
   * 
   * @param language
   *          the new language
   */
  public void setLanguage(final I18nLanguage language) {
    this.language = language;
  }

  /**
   * Sets the last login.
   * 
   * @param lastLogin
   *          the new last login
   */
  public void setLastLogin(final Long lastLogin) {
    this.lastLogin = lastLogin;
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
   * Sets the salt.
   * 
   * @param salt
   *          the new salt
   */
  public void setSalt(final byte[] salt) {
    this.salt = salt;
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
   * Sets the s net visibility.
   * 
   * @param sNetVisibility
   *          the new s net visibility
   */
  public void setSNetVisibility(final UserSNetVisibility sNetVisibility) {
    this.sNetVisibility = sNetVisibility;
  }

  /**
   * Sets the timezone.
   * 
   * @param timezone
   *          the new timezone
   */
  public void setTimezone(final TimeZone timezone) {
    this.timezone = timezone;
  }

  /**
   * Sets the user group.
   * 
   * @param userGroup
   *          the new user group
   */
  public void setUserGroup(final Group userGroup) {
    this.userGroup = userGroup;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "User[" + shortName + "]";
  }

}