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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cc.kune.domain.utils.HasId;

@Entity
@Table(name = "content_translations")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContentTranslation implements HasId {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private I18nLanguage language;

  private Long contentId;

  public Long getContentId() {
    return contentId;
  }

  public Long getId() {
    return id;
  }

  public I18nLanguage getLanguage() {
    return language;
  }

  public void setContentId(final Long contentId) {
    this.contentId = contentId;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public void setLanguage(final I18nLanguage language) {
    this.language = language;
  }

}
