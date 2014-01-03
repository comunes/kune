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

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserDTO implements IsSerializable {

  /** The avatar. */
  private String avatar;

  /** The chat color. */
  private String chatColor;

  /** The country. */
  private I18nCountryDTO country;

  /** The email. */
  private String email;

  /** The email notif freq. */
  private EmailNotificationFrequency emailNotifFreq;

  /** The id. */
  private Long id;

  /** The language. */
  private I18nLanguageDTO language;

  /** The name. */
  private String name;

  /** The password. */
  private String password;

  /** The publish roster. */
  private boolean publishRoster;

  /** The short name. */
  private String shortName;

  /** The subscription mode. */
  private SubscriptionMode subscriptionMode;

  /** The timezone. */
  private TimeZoneDTO timezone;

  /**
   * Instantiates a new user dto.
   */
  public UserDTO() {
    this(null, null, null, null, null, null, null, null, true, null, null);
  }

  /**
   * Instantiates a new user dto.
   * 
   * @param shortName
   *          the short name
   * @param name
   *          the name
   * @param password
   *          the password
   * @param email
   *          the email
   * @param language
   *          the language
   * @param country
   *          the country
   * @param timezone
   *          the timezone
   * @param avatar
   *          the avatar
   * @param publishRoster
   *          the publish roster
   * @param subscriptionMode
   *          the subscription mode
   * @param chatColor
   *          the chat color
   */
  public UserDTO(final String shortName, final String name, final String password, final String email,
      final I18nLanguageDTO language, final I18nCountryDTO country, final TimeZoneDTO timezone,
      final String avatar, final boolean publishRoster, final SubscriptionMode subscriptionMode,
      final String chatColor) {
    this.name = name;
    this.shortName = shortName;
    this.password = password;
    this.email = email;
    this.language = language;
    this.country = country;
    this.timezone = timezone;
    this.avatar = avatar;
    this.publishRoster = publishRoster;
    this.subscriptionMode = subscriptionMode;
    this.chatColor = chatColor;
  }

  /**
   * Gets the avatar.
   * 
   * @return the avatar
   */
  public String getAvatar() {
    return avatar;
  }

  /**
   * Gets the chat color.
   * 
   * @return the chat color
   */
  public String getChatColor() {
    return chatColor;
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
   * Gets the password.
   * 
   * @return the password
   */
  public String getPassword() {
    return password;
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
   * Gets the subscription mode.
   * 
   * @return the subscription mode
   */
  public SubscriptionMode getSubscriptionMode() {
    return subscriptionMode;
  }

  /**
   * Gets the timezone.
   * 
   * @return the timezone
   */
  public TimeZoneDTO getTimezone() {
    return timezone;
  }

  /**
   * Checks if is publish roster.
   * 
   * @return true, if is publish roster
   */
  public boolean isPublishRoster() {
    return publishRoster;
  }

  /**
   * Sets the avatar.
   * 
   * @param avatar
   *          the new avatar
   */
  public void setAvatar(final String avatar) {
    this.avatar = avatar;
  }

  /**
   * Sets the chat color.
   * 
   * @param chatColor
   *          the new chat color
   */
  public void setChatColor(final String chatColor) {
    this.chatColor = chatColor;
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
   * Sets the password.
   * 
   * @param password
   *          the new password
   */
  public void setPassword(final String password) {
    this.password = password;
  }

  /**
   * Sets the publish roster.
   * 
   * @param publishRoster
   *          the new publish roster
   */
  public void setPublishRoster(final boolean publishRoster) {
    this.publishRoster = publishRoster;
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
   * Sets the subscription mode.
   * 
   * @param subscriptionMode
   *          the new subscription mode
   */
  public void setSubscriptionMode(final SubscriptionMode subscriptionMode) {
    this.subscriptionMode = subscriptionMode;
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
    return "UserDTO[" + shortName + "]";
  }

}
