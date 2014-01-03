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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import cc.kune.domain.utils.HasId;

// TODO: Auto-generated Javadoc
/**
 * The Class ExtMediaDescrip.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Table(name = "ext_media_descriptors")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExtMediaDescrip implements HasId {

  /** The detect regex. */
  @Column(nullable = false)
  private String detectRegex;

  /** The embed template. */
  @Column(nullable = false, columnDefinition = "LONGTEXT")
  @Length(max = 1000)
  private String embedTemplate;

  /** The height. */
  private int height;

  /** The id. */
  @Id
  @GeneratedValue
  private Long id;

  /** The id regex. */
  @Column(nullable = false)
  private String idRegex;

  /** The name. */
  @Column(nullable = false)
  private String name;

  /** The siteurl. */
  @Column(nullable = false)
  private String siteurl;

  /** The width. */
  private int width;

  /**
   * Instantiates a new ext media descrip.
   */
  public ExtMediaDescrip() {
    this(null, null, null, null, null, 0, 0);
  }

  /**
   * Instantiates a new ext media descrip.
   * 
   * @param name
   *          the name
   * @param siteurl
   *          the siteurl
   * @param detectRegex
   *          the detect regex
   * @param idRegex
   *          the id regex
   * @param embedTemplate
   *          the embed template
   * @param defWidth
   *          the def width
   * @param defHeight
   *          the def height
   */
  public ExtMediaDescrip(final String name, final String siteurl, final String detectRegex,
      final String idRegex, final String embedTemplate, final int defWidth, final int defHeight) {
    this.name = name;
    this.siteurl = siteurl;
    this.detectRegex = detectRegex;
    this.idRegex = idRegex;
    this.embedTemplate = embedTemplate;
    width = defWidth;
    height = defHeight;
  }

  /**
   * Gets the detect regex.
   * 
   * @return the detect regex
   */
  public String getDetectRegex() {
    return detectRegex;
  }

  /**
   * Gets the embed template.
   * 
   * @return the embed template
   */
  public String getEmbedTemplate() {
    return embedTemplate;
  }

  /**
   * Gets the height.
   * 
   * @return the height
   */
  public int getHeight() {
    return height;
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
   * Gets the id regex.
   * 
   * @return the id regex
   */
  public String getIdRegex() {
    return idRegex;
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
   * Gets the siteurl.
   * 
   * @return the siteurl
   */
  public String getSiteurl() {
    return siteurl;
  }

  /**
   * Gets the width.
   * 
   * @return the width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Sets the detect regex.
   * 
   * @param detectRegex
   *          the new detect regex
   */
  public void setDetectRegex(final String detectRegex) {
    this.detectRegex = detectRegex;
  }

  /**
   * Sets the embed template.
   * 
   * @param embedTemplate
   *          the new embed template
   */
  public void setEmbedTemplate(final String embedTemplate) {
    this.embedTemplate = embedTemplate;
  }

  /**
   * Sets the height.
   * 
   * @param height
   *          the new height
   */
  public void setHeight(final int height) {
    this.height = height;
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
   * Sets the id regex.
   * 
   * @param idRegex
   *          the new id regex
   */
  public void setIdRegex(final String idRegex) {
    this.idRegex = idRegex;
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
   * Sets the siteurl.
   * 
   * @param siteurl
   *          the new siteurl
   */
  public void setSiteurl(final String siteurl) {
    this.siteurl = siteurl;
  }

  /**
   * Sets the width.
   * 
   * @param width
   *          the new width
   */
  public void setWidth(final int width) {
    this.width = width;
  }
}
