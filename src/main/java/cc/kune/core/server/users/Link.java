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
package cc.kune.core.server.users;

import java.util.List;

import cc.kune.domain.Tag;

// TODO: Auto-generated Javadoc
/**
 * The Class Link.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class Link {

  /** The icon url. */
  private String iconUrl;

  /** The link. */
  private String link;

  /** The long name. */
  private String longName;

  /** The rate. */
  private Double rate;

  /** The short name. */
  private String shortName;

  /** The tags. */
  private List<Tag> tags;

  /**
   * Instantiates a new link.
   */
  public Link() {
    this(null, null, null, null);
  }

  /**
   * Instantiates a new link.
   * 
   * @param shortName
   *          the short name
   * @param longName
   *          the long name
   * @param iconUrl
   *          the icon url
   * @param link
   *          the link
   */
  public Link(final String shortName, final String longName, final String iconUrl, final String link) {
    this.shortName = shortName;
    this.longName = longName;
    this.iconUrl = iconUrl;
    this.link = link;
  }

  /**
   * Gets the icon url.
   * 
   * @return the icon url
   */
  public String getIconUrl() {
    return iconUrl;
  }

  /**
   * Gets the link.
   * 
   * @return the link
   */
  public String getLink() {
    return link;
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
   * Gets the rate.
   * 
   * @return the rate
   */
  public Double getRate() {
    return rate;
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
   * Gets the tags.
   * 
   * @return the tags
   */
  public List<Tag> getTags() {
    return tags;
  }

  /**
   * Sets the icon url.
   * 
   * @param iconUrl
   *          the new icon url
   */
  public void setIconUrl(final String iconUrl) {
    this.iconUrl = iconUrl;
  }

  /**
   * Sets the link.
   * 
   * @param link
   *          the new link
   */
  public void setLink(final String link) {
    this.link = link;
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
   * Sets the rate.
   * 
   * @param rate
   *          the new rate
   */
  public void setRate(final Double rate) {
    this.rate = rate;
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
   * Sets the tags.
   * 
   * @param tags
   *          the new tags
   */
  public void setTags(final List<Tag> tags) {
    this.tags = tags;
  }

}
