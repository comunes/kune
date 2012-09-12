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

@Entity
@Table(name = "ext_media_descriptors")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExtMediaDescrip implements HasId {

  @Column(nullable = false)
  private String detectRegex;
  @Column(nullable = false)
  @Length(max = 1000)
  private String embedTemplate;
  private int height;
  @Id
  @GeneratedValue
  private Long id;
  @Column(nullable = false)
  private String idRegex;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String siteurl;
  private int width;

  public ExtMediaDescrip() {
    this(null, null, null, null, null, 0, 0);
  }

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

  public String getDetectRegex() {
    return detectRegex;
  }

  public String getEmbedTemplate() {
    return embedTemplate;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public Long getId() {
    return id;
  }

  public String getIdRegex() {
    return idRegex;
  }

  public String getName() {
    return name;
  }

  public String getSiteurl() {
    return siteurl;
  }

  public int getWidth() {
    return width;
  }

  public void setDetectRegex(final String detectRegex) {
    this.detectRegex = detectRegex;
  }

  public void setEmbedTemplate(final String embedTemplate) {
    this.embedTemplate = embedTemplate;
  }

  public void setHeight(final int height) {
    this.height = height;
  }

  @Override
  public void setId(final Long id) {
    this.id = id;
  }

  public void setIdRegex(final String idRegex) {
    this.idRegex = idRegex;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setSiteurl(final String siteurl) {
    this.siteurl = siteurl;
  }

  public void setWidth(final int width) {
    this.width = width;
  }
}
