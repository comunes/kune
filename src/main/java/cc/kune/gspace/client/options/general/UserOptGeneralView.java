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
package cc.kune.gspace.client.options.general;

import cc.kune.core.shared.dto.EmailNotificationFrequency;
import cc.kune.core.shared.dto.I18nLanguageSimpleDTO;

import com.google.gwt.event.dom.client.HasClickHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserOptGeneralView.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface UserOptGeneralView extends EntityOptGeneralView {

  /**
   * Gets the email.
   * 
   * @return the email
   */
  String getEmail();

  /**
   * Gets the email notif.
   * 
   * @return the email notif
   */
  EmailNotificationFrequency getEmailNotif();

  /**
   * Gets the language.
   * 
   * @return the language
   */
  I18nLanguageSimpleDTO getLanguage();

  /**
   * Gets the long name.
   * 
   * @return the long name
   */
  String getLongName();

  /**
   * Gets the resend email verif.
   * 
   * @return the resend email verif
   */
  HasClickHandlers getResendEmailVerif();

  /**
   * Sets the email.
   * 
   * @param email
   *          the new email
   */
  void setEmail(String email);

  /**
   * Sets the email notif checked.
   * 
   * @param freq
   *          the new email notif checked
   */
  void setEmailNotifChecked(EmailNotificationFrequency freq);

  /**
   * Sets the email verified.
   * 
   * @param verified
   *          the new email verified
   */
  void setEmailVerified(boolean verified);

  /**
   * Sets the language.
   * 
   * @param language
   *          the new language
   */
  void setLanguage(I18nLanguageSimpleDTO language);

  /**
   * Sets the long name.
   * 
   * @param longName
   *          the new long name
   */
  void setLongName(String longName);

  /**
   * Sets the resend email verif enabled.
   * 
   * @param enabled
   *          the new resend email verif enabled
   */
  void setResendEmailVerifEnabled(boolean enabled);

}
