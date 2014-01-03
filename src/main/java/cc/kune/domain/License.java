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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cc.kune.domain.utils.HasId;

// TODO: Auto-generated Javadoc
/**
 * The Class License.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Table(name = "licenses")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class License implements HasId {

  /** The description. */
  private String description;

  /** The id. */
  @Id
  @GeneratedValue
  private Long id;

  /** The image url. */
  @Column(nullable = false)
  private String imageUrl;

  /** The is cc. */
  @org.hibernate.annotations.Index(name = "isCC")
  @Basic
  @Column(columnDefinition = "BIT", length = 1)
  private boolean isCC;

  /** The is copyleft. */
  @Basic
  @Column(columnDefinition = "BIT", length = 1)
  private boolean isCopyleft;

  /** The is deprecated. */
  @Basic
  @Column(columnDefinition = "BIT", length = 1)
  private boolean isDeprecated;

  /** The long name. */
  @Column(unique = true)
  private String longName;

  /** The rdf. */
  private String rdf;

  /** The short name. */
  @org.hibernate.annotations.Index(name = "shortName")
  @Column(unique = true)
  private String shortName;

  /** The url. */
  @Column(nullable = false)
  private String url;

  /**
   * Instantiates a new license.
   */
  public License() {
    this(null, null, null, null, false, false, false, null, null);
  }

  /**
   * Instantiates a new license.
   * 
   * @param shortName
   *          the short name
   * @param longName
   *          the long name
   * @param description
   *          the description
   * @param url
   *          the url
   * @param isCC
   *          the is cc
   * @param isCopyleft
   *          the is copyleft
   * @param isDeprecated
   *          the is deprecated
   * @param rdf
   *          the rdf
   * @param imageUrl
   *          the image url
   */
  public License(final String shortName, final String longName, final String description,
      final String url, final boolean isCC, final boolean isCopyleft, final boolean isDeprecated,
      final String rdf, final String imageUrl) {
    this.shortName = shortName;
    this.longName = longName;
    this.description = description;
    this.url = url;
    this.isCC = isCC;
    this.isCopyleft = isCopyleft;
    this.isDeprecated = isDeprecated;
    this.rdf = rdf;
    this.imageUrl = imageUrl;
  }

  /**
   * Gets the description.
   * 
   * @return the description
   */
  public String getDescription() {
    return description;
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
   * Gets the image url.
   * 
   * @return the image url
   */
  public String getImageUrl() {
    return imageUrl;
  }

  /**
   * Gets the long name.
   * 
   * @return the long name
   */
  public String getLongName() {
    return longName;
  }

  /**
   * Gets the rdf.
   * 
   * @return the rdf
   */
  public String getRdf() {
    return rdf;
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
   * Gets the url.
   * 
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Checks if is cc.
   * 
   * @return true, if is cc
   */
  public boolean isCC() {
    return isCC;
  }

  /**
   * Checks if is copyleft.
   * 
   * @return true, if is copyleft
   */
  public boolean isCopyleft() {
    return isCopyleft;
  }

  /**
   * Checks if is deprecated.
   * 
   * @return true, if is deprecated
   */
  public boolean isDeprecated() {
    return isDeprecated;
  }

  /**
   * Sets the cc.
   * 
   * @param isCC
   *          the new cc
   */
  public void setCC(final boolean isCC) {
    this.isCC = isCC;
  }

  /**
   * Sets the copyleft.
   * 
   * @param isCopyleft
   *          the new copyleft
   */
  public void setCopyleft(final boolean isCopyleft) {
    this.isCopyleft = isCopyleft;
  }

  /**
   * Sets the deprecated.
   * 
   * @param isDeprecated
   *          the new deprecated
   */
  public void setDeprecated(final boolean isDeprecated) {
    this.isDeprecated = isDeprecated;
  }

  /**
   * Sets the description.
   * 
   * @param description
   *          the new description
   */
  public void setDescription(final String description) {
    this.description = description;
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

  /**
   * Sets the image url.
   * 
   * @param imageUrl
   *          the new image url
   */
  public void setImageUrl(final String imageUrl) {
    this.imageUrl = imageUrl;
  }

  /**
   * Sets the long name.
   * 
   * @param longName
   *          the new long name
   */
  public void setLongName(final String longName) {
    this.longName = longName;
  }

  /**
   * Sets the rdf.
   * 
   * @param rdf
   *          the new rdf
   */
  public void setRdf(final String rdf) {
    this.rdf = rdf;
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
   * Sets the url.
   * 
   * @param url
   *          the new url
   */
  public void setUrl(final String url) {
    this.url = url;
  }
}
