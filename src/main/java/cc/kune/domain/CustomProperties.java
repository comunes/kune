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

// TODO: Auto-generated Javadoc
/**
 * The Class CustomProperties.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Table(name = "customproperties")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomProperties implements HasId {

  /** The data. */
  @Lob
  private HashMap<Class<?>, Object> data;

  /** The id. */
  @Id
  @GeneratedValue
  private Long id;

  /**
   * Instantiates a new custom properties.
   */
  public CustomProperties() {
    data = new HashMap<Class<?>, Object>();
  }

  /**
   * Gets the data.
   * 
   * @return the data
   */
  public HashMap<Class<?>, Object> getData() {
    return data;
  }

  /**
   * Gets the data.
   * 
   * @param <T>
   *          the generic type
   * @param type
   *          the type
   * @return the data
   */
  @SuppressWarnings("unchecked")
  public <T> T getData(final Class<T> type) {
    return (T) data.get(type);
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
   * Checks for propertie.
   * 
   * @param <T>
   *          the generic type
   * @param type
   *          the type
   * @return true, if successful
   */
  public <T> boolean hasPropertie(final Class<T> type) {
    return data.containsKey(type);
  }

  /**
   * Sets the data.
   * 
   * @param <T>
   *          the generic type
   * @param type
   *          the type
   * @param value
   *          the value
   * @return the t
   */
  @SuppressWarnings("unchecked")
  public <T> T setData(final Class<T> type, final T value) {
    return (T) data.put(type, value);
  }

  /**
   * Sets the data.
   * 
   * @param data
   *          the data
   */
  public void setData(final HashMap<Class<?>, Object> data) {
    this.data = data;
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
}
