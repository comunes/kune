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
package cc.kune.common.shared.ui;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UploadFile implements IsSerializable {

  public static UploadFile build(final String name, final String type, final Long size, final String data) {
    return new UploadFile(name, type, size, data);
  }

  private String data;
  private String name;
  private Long size;
  private String type;

  public UploadFile() {
  }

  public UploadFile(final String name, final String type, final Long size, final String data) {
    this.name = name;
    this.type = type;
    this.size = size;
    this.data = data;
  }

  public String getData() {
    return data;
  }

  public String getName() {
    return name;
  }

  public Long getSize() {
    return size;
  }

  public String getType() {
    return type;
  }

  public void setData(final String data) {
    this.data = data;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setSize(final Long size) {
    this.size = size;
  }

  public void setType(final String type) {
    this.type = type;
  }

}
