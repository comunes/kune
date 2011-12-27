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
package cc.kune.core.shared.dto;

import cc.kune.core.shared.domain.dto.EmailNotificationFrequency;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserDTO implements IsSerializable {
  private String avatar;
  private String chatColor;
  private I18nCountryDTO country;
  private String email;
  private EmailNotificationFrequency emailNotifFreq;
  private Long id;
  private I18nLanguageDTO language;
  private String name;
  private String password;
  private boolean publishRoster;
  private String shortName;
  private SubscriptionMode subscriptionMode;
  private TimeZoneDTO timezone;

  public UserDTO() {
    this(null, null, null, null, null, null, null, null, true, null, null);
  }

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

  public String getAvatar() {
    return avatar;
  }

  public String getChatColor() {
    return chatColor;
  }

  public I18nCountryDTO getCountry() {
    return country;
  }

  public String getEmail() {
    return email;
  }

  public EmailNotificationFrequency getEmailNotifFreq() {
    return emailNotifFreq;
  }

  public Long getId() {
    return id;
  }

  public I18nLanguageDTO getLanguage() {
    return language;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public String getShortName() {
    return shortName;
  }

  public SubscriptionMode getSubscriptionMode() {
    return subscriptionMode;
  }

  public TimeZoneDTO getTimezone() {
    return timezone;
  }

  public boolean isPublishRoster() {
    return publishRoster;
  }

  public void setAvatar(final String avatar) {
    this.avatar = avatar;
  }

  public void setChatColor(final String chatColor) {
    this.chatColor = chatColor;
  }

  public void setCountry(final I18nCountryDTO country) {
    this.country = country;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public void setEmailNotifFreq(final EmailNotificationFrequency emailNotifFreq) {
    this.emailNotifFreq = emailNotifFreq;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public void setLanguage(final I18nLanguageDTO language) {
    this.language = language;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public void setPublishRoster(final boolean publishRoster) {
    this.publishRoster = publishRoster;
  }

  public void setShortName(final String shortName) {
    this.shortName = shortName;
  }

  public void setSubscriptionMode(final SubscriptionMode subscriptionMode) {
    this.subscriptionMode = subscriptionMode;
  }

  public void setTimezone(final TimeZoneDTO timezone) {
    this.timezone = timezone;
  }

  @Override
  public String toString() {
    return "UserDTO[" + shortName + "]";
  }

}
