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

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import cc.kune.core.shared.domain.UserSNetVisibility;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.EmailNotificationFrequency;
import cc.kune.domain.utils.HasId;

@Entity
@Indexed
@Table(name = "kusers")
public class User implements HasId {

  private static final EmailNotificationFrequency DEF_EMAIL_FREQ = EmailNotificationFrequency.immediately;

  // public static final String PROPS_ID = "userprops";
  // see: http://docs.codehaus.org/display/PICO/Good+Citizen:
  // Never expect or return null
  public static final User UNKNOWN_USER = new User();

  public static boolean isKnownUser(final User user) {
    return !user.equals(UNKNOWN_USER);
  }

  @ManyToOne
  @NotNull
  private I18nCountry country;

  @Basic(optional = false)
  private final Long createdOn;

  @Basic
  @Lob
  @Column(nullable = false)
  private byte[] diggest;

  @Column(unique = true, nullable = false)
  @Email
  @Length(min = 1)
  private String email;

  @Basic(optional = true)
  private Long emailCheckDate;

  // @OneToOne(cascade = CascadeType.REMOVE)
  // private final CustomProperties customProperties;

  @Column(unique = true, nullable = true)
  private String emailConfirmHash;

  @Enumerated(EnumType.STRING)
  private EmailNotificationFrequency emailNotifFreq;

  private Boolean emailVerified;

  @Id
  @DocumentId
  @GeneratedValue
  private Long id;

  @ManyToOne
  @NotNull
  private I18nLanguage language;

  @Basic
  private Long lastLogin;

  @Field(index = Index.YES, store = Store.NO)
  @Column(nullable = false, unique = true)
  @Length(min = 3, max = 50)
  private String name;

  @Basic
  @Lob
  @Column(nullable = false)
  private byte[] salt;

  @Field(index = Index.YES, store = Store.NO)
  @Column(unique = true)
  // http://www.hibernate.org/hib_docs/validator/reference/en/html/validator-defineconstraints.html
  @Length(min = 3, max = 15)
  @Pattern(regexp = "^[a-z0-9]+$", message = "The name must be between 3 and 15 lowercase characters. It can only contain Western characters, numbers, and dashes")
  private String shortName;

  @Enumerated(EnumType.STRING)
  private UserSNetVisibility sNetVisibility;

  @NotNull
  private TimeZone timezone;

  @OneToOne(cascade = CascadeType.ALL)
  private Group userGroup;

  public User() {
    this(null, null, null, null, null, null, null, null);
  }

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

  public I18nCountry getCountry() {
    return country;
  }

  public Long getCreatedOn() {
    return createdOn;
  }

  public byte[] getDiggest() {
    return diggest;
  }

  // public CustomProperties getCustomProperties() {
  // return customProperties;
  // }

  public String getEmail() {
    return email;
  }

  public Long getEmailCheckDate() {
    return emailCheckDate;
  }

  public String getEmailConfirmHash() {
    return emailConfirmHash;
  }

  public EmailNotificationFrequency getEmailNotifFreq() {
    return emailNotifFreq == null ? DEF_EMAIL_FREQ : emailNotifFreq;
  }

  public boolean getEmailVerified() {
    return isEmailVerified();
  }

  public boolean getHasLogo() {
    return hasLogo();
  }

  @Override
  public Long getId() {
    return id;
  }

  // public Properties getProperties() {
  // return properties;
  // }

  public I18nLanguage getLanguage() {
    return language;
  }

  public Long getLastLogin() {
    return lastLogin;
  }

  public String getName() {
    return name;
  }

  public byte[] getSalt() {
    return salt;
  }

  public String getShortName() {
    return shortName;
  }

  public UserSNetVisibility getSNetVisibility() {
    return sNetVisibility;
  }

  @Transient
  public StateToken getStateToken() {
    return userGroup.getStateToken();
  }

  public TimeZone getTimezone() {
    return timezone;
  }

  public Group getUserGroup() {
    return userGroup;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
    return result;
  }

  @Transient
  public boolean hasLogo() {
    return getUserGroup().hasLogo();
  }

  public boolean isEmailVerified() {
    return emailVerified == null ? false : emailVerified;
  }

  public void setCountry(final I18nCountry country) {
    this.country = country;
  }

  public void setDiggest(final byte[] diggest) {
    this.diggest = diggest;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public void setEmailCheckDate(final Long emailCheckDate) {
    this.emailCheckDate = emailCheckDate;
  }

  public void setEmailConfirmHash(final String emailConfirmHash) {
    this.emailConfirmHash = emailConfirmHash;
  }

  public void setEmailNotifFreq(final EmailNotificationFrequency emailNotifFreq) {
    this.emailNotifFreq = emailNotifFreq;
  }

  public void setEmailVerified(final boolean emailVerified) {
    this.emailVerified = emailVerified;
  }

  @Override
  public void setId(final Long id) {
    this.id = id;
  }

  public void setLanguage(final I18nLanguage language) {
    this.language = language;
  }

  public void setLastLogin(final Long lastLogin) {
    this.lastLogin = lastLogin;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setSalt(final byte[] salt) {
    this.salt = salt;
  }

  public void setShortName(final String shortName) {
    this.shortName = shortName;
  }

  public void setSNetVisibility(final UserSNetVisibility sNetVisibility) {
    this.sNetVisibility = sNetVisibility;
  }

  public void setTimezone(final TimeZone timezone) {
    this.timezone = timezone;
  }

  public void setUserGroup(final Group userGroup) {
    this.userGroup = userGroup;
  }

  @Override
  public String toString() {
    return "User[" + shortName + "]";
  }
}
