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
import com.google.gwt.view.client.ProvidesKey;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nTranslationDTO.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class I18nTranslationDTO implements IsSerializable, Comparable<I18nTranslationDTO> {

  /** The key provider that provides the unique ID. */
  public static final ProvidesKey<I18nTranslationDTO> KEY_PROVIDER = new ProvidesKey<I18nTranslationDTO>() {
    @Override
    public Object getKey(final I18nTranslationDTO item) {
      return item == null ? null : item.getId();
    }
  };

  /** The dirty. */
  private boolean dirty;

  /** The id. */
  private Long id;

  /** The note for translators. */
  private String noteForTranslators;

  /** The parent id. */
  private Long parentId;

  /** The parent tr key. */
  private String parentTrKey;

  /** The text. */
  private String text;

  /** The tr key. */
  private String trKey;
  /*
   * trKey from is used when selecting a different language than English from
   * where we translate
   */
  /** The tr key from. */
  private String trKeyFrom;

  /**
   * Instantiates a new i18n translation dto.
   */
  public I18nTranslationDTO() {
    this(null, null, null, null, null, null, null);
  }

  /**
   * Instantiates a new i18n translation dto.
   * 
   * @param id
   *          the id
   * @param trKey
   *          the tr key
   * @param trKeyFrom
   *          the tr key from
   * @param text
   *          the text
   * @param parentId
   *          the parent id
   * @param parentTrKey
   *          the parent tr key
   * @param noteForTranslators
   *          the note for translators
   */
  public I18nTranslationDTO(final Long id, final String trKey, final String trKeyFrom,
      final String text, final Long parentId, final String parentTrKey, final String noteForTranslators) {
    this.id = id;
    this.trKey = trKey;
    this.trKeyFrom = trKeyFrom;
    this.text = text;
    this.parentId = parentId;
    this.parentTrKey = parentTrKey;
    this.noteForTranslators = noteForTranslators;
    setDirty(false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(final I18nTranslationDTO o) {
    return (o == null || o.id == null) ? -1 : -o.id.compareTo(id);
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
   * Gets the note for translators.
   * 
   * @return the note for translators
   */
  public String getNoteForTranslators() {
    return noteForTranslators;
  }

  /**
   * Gets the parent id.
   * 
   * @return the parent id
   */
  public Long getParentId() {
    return parentId;
  }

  /**
   * Gets the parent tr key.
   * 
   * @return the parent tr key
   */
  public String getParentTrKey() {
    return parentTrKey;
  }

  /**
   * Gets the text.
   * 
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * Gets the tr key.
   * 
   * @return the tr key
   */
  public String getTrKey() {
    return trKey;
  }

  /**
   * Gets the tr key from.
   * 
   * @return the tr key from
   */
  public String getTrKeyFrom() {
    return trKeyFrom;
  }

  /**
   * Checks if is dirty.
   * 
   * @return true, if is dirty
   */
  public boolean isDirty() {
    return dirty;
  }

  /**
   * Sets the dirty.
   * 
   * @param dirty
   *          the new dirty
   */
  public void setDirty(final boolean dirty) {
    this.dirty = dirty;
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
   * Sets the note for translators.
   * 
   * @param noteForTranslators
   *          the new note for translators
   */
  public void setNoteForTranslators(final String noteForTranslators) {
    this.noteForTranslators = noteForTranslators;
  }

  /**
   * Sets the parent id.
   * 
   * @param parentId
   *          the new parent id
   */
  public void setParentId(final Long parentId) {
    this.parentId = parentId;
  }

  /**
   * Sets the parent tr key.
   * 
   * @param parentTrKey
   *          the new parent tr key
   */
  public void setParentTrKey(final String parentTrKey) {
    this.parentTrKey = parentTrKey;
  }

  /**
   * Sets the text.
   * 
   * @param text
   *          the new text
   */
  public void setText(final String text) {
    this.text = text;
  }

  /**
   * Sets the tr key.
   * 
   * @param trKey
   *          the new tr key
   */
  public void setTrKey(final String trKey) {
    this.trKey = trKey;
  }

  /**
   * Sets the tr key from.
   * 
   * @param trKeyFrom
   *          the new tr key from
   */
  public void setTrKeyFrom(final String trKeyFrom) {
    this.trKeyFrom = trKeyFrom;
  }

}
