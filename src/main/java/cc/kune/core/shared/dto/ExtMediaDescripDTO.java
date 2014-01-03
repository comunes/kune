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
package cc.kune.core.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ExtMediaDescripDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ExtMediaDescripDTO implements IsSerializable {

  /** The Constant HEIGHT. */
  public static final String HEIGHT = "###HEIGHT###";

  /** The Constant URL. */
  public static final String URL = "###URL###";

  /** The Constant WIDTH. */
  public static final String WIDTH = "###WIDTH###";

  /** The detect regex. */
  private String detectRegex;

  /** The embed template. */
  private String embedTemplate;

  /** The height. */
  private int height;

  /** The id regex. */
  private String idRegex;

  /** The name. */
  private String name;

  /** The siteurl. */
  private String siteurl;

  /** The width. */
  private int width;

  /**
   * Instantiates a new ext media descrip dto.
   */
  public ExtMediaDescripDTO() {
    this(null, null, null, null, null, 0, 0);
  }

  /**
   * Instantiates a new ext media descrip dto.
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
  public ExtMediaDescripDTO(final String name, final String siteurl, final String detectRegex,
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
   * Gets the embed.
   * 
   * @param url
   *          the url
   * @return the embed
   */
  public String getEmbed(final String url) {
    final String id = getId(url);
    String result = embedTemplate.replaceAll(URL, id);
    result = result.replaceAll(HEIGHT, "" + height);
    result = result.replaceAll(WIDTH, "" + width);
    return result;
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

  /**
   * Gets the id.
   * 
   * @param url
   *          the url
   * @return the id
   */
  public String getId(final String url) {
    final String id = url.replaceFirst(idRegex, "$1");
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
   * Checks if is.
   * 
   * @param url
   *          the url
   * @return true, if successful
   */
  public boolean is(final String url) {
    return url.matches(detectRegex);
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
