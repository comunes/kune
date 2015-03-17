/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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

public class MotdDTO implements IsSerializable {
  private String closeBtnText;
  private String cookieName;
  private String message;
  private String okBtnText;
  private String okBtnUrl;
  private int shouldRemember;
  private String title;

  public MotdDTO() {
  }

  public String getCloseBtnText() {
    return closeBtnText;
  }

  public String getCookieName() {
    return cookieName;
  }

  public String getMessage() {
    return message;
  }

  public String getOkBtnText() {
    return okBtnText;
  }

  public String getOkBtnUrl() {
    return okBtnUrl;
  }

  public int getShouldRemember() {
    return shouldRemember;
  }

  public String getTitle() {
    return title;
  }

  public void setCloseBtnText(final String closeBtnText) {
    this.closeBtnText = closeBtnText;
  }

  public void setCookieName(final String cookieName) {
    this.cookieName = cookieName;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public void setOkBtnText(final String okBtnText) {
    this.okBtnText = okBtnText;
  }

  public void setOkBtnUrl(final String okBtnUrl) {
    this.okBtnUrl = okBtnUrl;
  }

  public void setShouldRemember(final int shouldRemember) {
    this.shouldRemember = shouldRemember;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

}
