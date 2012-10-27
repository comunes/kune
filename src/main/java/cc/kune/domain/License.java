/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cc.kune.domain.utils.HasId;

@Entity
@Table(name = "licenses")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class License implements HasId {
  private String description;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String imageUrl;

  @Basic
  @Column(columnDefinition = "BIT", length = 1)
  private boolean isCC;

  @Basic
  @Column(columnDefinition = "BIT", length = 1)
  private boolean isCopyleft;

  @Basic
  @Column(columnDefinition = "BIT", length = 1)
  private boolean isDeprecated;

  @Column(unique = true)
  private String longName;
  private String rdf;
  @Column(unique = true)
  private String shortName;
  @Column(nullable = false)
  private String url;

  public License() {
    this(null, null, null, null, false, false, false, null, null);
  }

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

  public String getDescription() {
    return description;
  }

  @Override
  public Long getId() {
    return id;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getLongName() {
    return longName;
  }

  public String getRdf() {
    return rdf;
  }

  public String getShortName() {
    return shortName;
  }

  public String getUrl() {
    return url;
  }

  public boolean isCC() {
    return isCC;
  }

  public boolean isCopyleft() {
    return isCopyleft;
  }

  public boolean isDeprecated() {
    return isDeprecated;
  }

  public void setCC(final boolean isCC) {
    this.isCC = isCC;
  }

  public void setCopyleft(final boolean isCopyleft) {
    this.isCopyleft = isCopyleft;
  }

  public void setDeprecated(final boolean isDeprecated) {
    this.isDeprecated = isDeprecated;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  public void setId(final Long id) {
    this.id = id;
  }

  public void setImageUrl(final String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void setLongName(final String longName) {
    this.longName = longName;
  }

  public void setRdf(final String rdf) {
    this.rdf = rdf;
  }

  public void setShortName(final String shortName) {
    this.shortName = shortName;
  }

  public void setUrl(final String url) {
    this.url = url;
  }
}
