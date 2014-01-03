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

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;

// TODO: Auto-generated Javadoc
/**
 * The Class Rate.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Table(name = "rates", uniqueConstraints = { @UniqueConstraint(columnNames = { "content_id", "rater_id" }) })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rate {

  /** The content. */
  @ManyToOne
  Content content;

  /** The created on. */
  @Basic(optional = false)
  private final Long createdOn;

  /** The id. */
  @Id
  @GeneratedValue
  Long id;

  /** The rater. */
  @ManyToOne
  User rater;

  /** The value. */
  @Range(min = 0, max = 5)
  Double value;

  /**
   * Instantiates a new rate.
   */
  public Rate() {
    this(null, null, null);
  }

  /**
   * Instantiates a new rate.
   * 
   * @param rater
   *          the rater
   * @param content
   *          the content
   * @param value
   *          the value
   */
  public Rate(final User rater, final Content content, final Double value) {
    this.rater = rater;
    this.content = content;
    this.value = value;
    this.createdOn = System.currentTimeMillis();
  }

  /**
   * Gets the content.
   * 
   * @return the content
   */
  public Content getContent() {
    return content;
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
   * Gets the id.
   * 
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the rater.
   * 
   * @return the rater
   */
  public User getRater() {
    return rater;
  }

  /**
   * Gets the value.
   * 
   * @return the value
   */
  public Double getValue() {
    return value;
  }

  /**
   * Sets the content.
   * 
   * @param content
   *          the new content
   */
  public void setContent(final Content content) {
    this.content = content;
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
   * Sets the rater.
   * 
   * @param rater
   *          the new rater
   */
  public void setRater(final User rater) {
    this.rater = rater;
  }

  /**
   * Sets the value.
   * 
   * @param value
   *          the new value
   */
  public void setValue(final Double value) {
    this.value = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Rate[" + getRater() + " to " + content.getStateTokenEncoded() + "rated: " + getValue() + "]";
  }
}
