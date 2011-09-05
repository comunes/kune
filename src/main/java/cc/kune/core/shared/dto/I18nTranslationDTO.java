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
package cc.kune.core.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.view.client.ProvidesKey;

public class I18nTranslationDTO implements IsSerializable, Comparable<I18nTranslationDTO> {

  /**
   * The key provider that provides the unique ID
   */
  public static final ProvidesKey<I18nTranslationDTO> KEY_PROVIDER = new ProvidesKey<I18nTranslationDTO>() {
    @Override
    public Object getKey(final I18nTranslationDTO item) {
      return item == null ? null : item.getId();
    }
  };
  private boolean dirty;
  private Long id;
  private String text;
  private String trKey;

  public I18nTranslationDTO() {
    this(null, null, null);
  }

  public I18nTranslationDTO(final Long id, final String trKey, final String text) {
    this.id = id;
    this.trKey = trKey;
    this.text = text;
    setDirty(false);
  }

  @Override
  public int compareTo(final I18nTranslationDTO o) {
    return (o == null || o.trKey == null) ? -1 : -o.trKey.compareTo(trKey);
  }

  public Long getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public String getTrKey() {
    return trKey;
  }

  public boolean isDirty() {
    return dirty;
  }

  public void setDirty(final boolean dirty) {
    this.dirty = dirty;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public void setText(final String text) {
    this.text = text;
  }

  public void setTrKey(final String trKey) {
    this.trKey = trKey;
  }

}
