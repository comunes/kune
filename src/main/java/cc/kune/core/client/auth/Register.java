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
package cc.kune.core.client.auth;

// TODO: Auto-generated Javadoc
/**
 * The Interface Register.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public interface Register {

  /** The welcome id. */
  String WELCOME_ID = "k-user-reg-welcome-msg-id";

  /**
   * Do register.
   * 
   * @param token
   *          to go when success registered
   */
  void doRegister(String token);

  /**
   * Hide.
   */
  void hide();

  /**
   * Sets the goto token on cancel.
   * 
   * @param gotoTokenOnCancel
   *          the new goto token on cancel
   */
  void setGotoTokenOnCancel(String gotoTokenOnCancel);

}
