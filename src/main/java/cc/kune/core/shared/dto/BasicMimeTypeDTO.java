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
package cc.kune.core.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class BasicMimeTypeDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BasicMimeTypeDTO implements IsSerializable {

  /** The Constant AUDIO. */
  public static final String AUDIO = "audio";

  /** The Constant IMAGE. */
  public static final String IMAGE = "image";

  /** The Constant VIDEO. */
  public static final String VIDEO = "video";

  /** The subtype. */
  private String subtype;

  /** The type. */
  private String type;

  /**
   * Instantiates a new basic mime type dto.
   */
  public BasicMimeTypeDTO() {
    this(null, null);
  }

  /**
   * Instantiates a new basic mime type dto.
   * 
   * @param mimetype
   *          the mimetype
   */
  public BasicMimeTypeDTO(final String mimetype) {
    if (mimetype != null) {
      final String[] split = mimetype.split("/", 2);
      type = split[0];
      if (split.length > 1 && split[1].length() > 0) {
        subtype = split[1];
      }
    }
  }

  /**
   * Instantiates a new basic mime type dto.
   * 
   * @param type
   *          the type
   * @param subtype
   *          the subtype
   */
  public BasicMimeTypeDTO(final String type, final String subtype) {
    this.type = type;
    this.subtype = subtype;
  }

  /**
   * Gets the subtype.
   * 
   * @return the subtype
   */
  public String getSubtype() {
    return subtype;
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Checks if is avi.
   * 
   * @return true, if is avi
   */
  public boolean isAvi() {
    return type != null && subtype != null && type.equals("video")
        && (subtype.equals("x-msvideo") || subtype.equals("msvideo"));
  }

  /**
   * Checks if is flv.
   * 
   * @return true, if is flv
   */
  public boolean isFlv() {
    return type != null && subtype != null && type.equals("video")
        && (subtype.equals("flv") || subtype.equals("x-flv"));
  }

  /**
   * Duplicate code in BMT.
   * 
   * @return true, if is image
   */
  public boolean isImage() {
    return type != null && type.equals(IMAGE);
  }

  /**
   * Checks if is mp3.
   * 
   * @return true, if is mp3
   */
  public boolean isMp3() {
    return type != null && subtype != null && type.equals("audio")
        && (subtype.equals("mp3") || subtype.equals("x-mp3") || (subtype.equals("mpeg")));
  }

  /**
   * Checks if is ogg.
   * 
   * @return true, if is ogg
   */
  public boolean isOgg() {
    // http://wiki.xiph.org/index.php/MIME_Types_and_File_Extensions
    return type != null && subtype != null && subtype.equals("ogg");
  }

  /**
   * Duplicate code in BMT.
   * 
   * @return true, if is pdf
   */
  public boolean isPdf() {
    return type != null && subtype != null && type.equals("application") && subtype.equals("pdf");
  }

  /**
   * Duplicate code in BMTDTO.
   * 
   * @return true, if is text
   */
  public boolean isText() {
    return type != null && subtype != null && type.equals("text") && subtype.equals("plain");
  }

  /**
   * Sets the subtype.
   * 
   * @param subtype
   *          the new subtype
   */
  public void setSubtype(final String subtype) {
    this.subtype = subtype;
  }

  /**
   * Sets the type.
   * 
   * @param type
   *          the new type
   */
  public void setType(final String type) {
    this.type = type;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return subtype == null ? type : type + "/" + subtype;
  }

}
