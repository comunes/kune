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

import javax.persistence.Embeddable;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

// TODO: Auto-generated Javadoc
/**
 * The Class BasicMimeType.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Embeddable
public class BasicMimeType {

  /** The mimesubtype. */
  @Field(index = Index.YES, store = Store.NO)
  private String mimesubtype;

  /** The mimetype. */
  @Field(index = Index.YES, store = Store.NO)
  private String mimetype;

  /**
   * Instantiates a new basic mime type.
   */
  public BasicMimeType() {
    this(null, null);
  }

  /**
   * Instantiates a new basic mime type.
   * 
   * @param mimetype
   *          the mimetype
   */
  public BasicMimeType(final String mimetype) {
    if (mimetype != null) {
      final String[] split = mimetype.split("/", 2);
      this.mimetype = split[0];
      if (split.length > 1 && split[1].length() > 0) {
        this.mimesubtype = split[1];
      }
    }
  }

  /**
   * Instantiates a new basic mime type.
   * 
   * @param type
   *          the type
   * @param subtype
   *          the subtype
   */
  public BasicMimeType(final String type, final String subtype) {
    this.mimetype = type;
    this.mimesubtype = subtype;
  }

  /**
   * Gets the subtype.
   * 
   * @return the subtype
   */
  public String getSubtype() {
    return mimesubtype;
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public String getType() {
    return mimetype;
  }

  /**
   * Duplicate code in BMTDTO.
   * 
   * @return true, if is image
   */
  public boolean isImage() {
    return mimetype != null && mimetype.equals("image");
  }

  /**
   * Duplicate code in BMTDTO.
   * 
   * @return true, if is pdf
   */
  public boolean isPdf() {
    return mimetype != null && mimesubtype != null && mimetype.equals("application")
        && mimesubtype.equals("pdf");
  }

  /**
   * Duplicate code in BMTDTO.
   * 
   * @return true, if is text
   */
  public boolean isText() {
    return mimetype != null && mimesubtype != null && mimetype.equals("text")
        && mimesubtype.equals("plain");
  }

  /**
   * Sets the subtype.
   * 
   * @param subtype
   *          the new subtype
   */
  public void setSubtype(final String subtype) {
    this.mimesubtype = subtype;
  }

  /**
   * Sets the type.
   * 
   * @param type
   *          the new type
   */
  public void setType(final String type) {
    this.mimetype = type;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return mimesubtype == null ? mimetype : mimetype + "/" + mimesubtype;
  }

}
