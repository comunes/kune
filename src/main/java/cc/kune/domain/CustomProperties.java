/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
 \*/
package cc.kune.domain;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cc.kune.domain.utils.HasId;

@Entity
@Table(name = "customproperties")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomProperties implements HasId {

  @Id
  @GeneratedValue
  private Long id;

  @Lob
  private HashMap<Class<?>, Object> data;

  public CustomProperties() {
    data = new HashMap<Class<?>, Object>();
  }

  public HashMap<Class<?>, Object> getData() {
    return data;
  }

  @SuppressWarnings("unchecked")
  public <T> T getData(final Class<T> type) {
    return (T) data.get(type);
  }

  public Long getId() {
    return id;
  }

  public <T> boolean hasPropertie(final Class<T> type) {
    return data.containsKey(type);
  }

  @SuppressWarnings("unchecked")
  public <T> T setData(final Class<T> type, final T value) {
    return (T) data.put(type, value);
  }

  public void setData(final HashMap<Class<?>, Object> data) {
    this.data = data;
  }

  public void setId(final Long id) {
    this.id = id;
  }
}
