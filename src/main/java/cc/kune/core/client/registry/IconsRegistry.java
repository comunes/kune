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
 \*/
package cc.kune.core.client.registry;

import java.util.HashMap;
import java.util.Map;

import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.dto.BasicMimeTypeDTO;

public class IconsRegistry {
  private final Map<String, Object> contentTypesIcons;

  public IconsRegistry() {
    contentTypesIcons = new HashMap<String, Object>();
  }

  public Object getContentTypeIcon(final String typeId) {
    return contentTypesIcons.get(typeId);
  }

  /**
   * If there is a specific icon for a type/subtype pair or a generic type icon
   * in defect
   * 
   * @param typeId
   *          the kune typeId (see *ClientTool)
   * @param mimeType
   * @return
   */
  public Object getContentTypeIcon(final String typeId, final BasicMimeTypeDTO mimeType) {
    Object icon = getContentTypeIcon(IdGenerator.generate(typeId,
        mimeType == null ? null : mimeType.toString()));
    if (icon == null) {
      if (mimeType == null) {
        return getContentTypeIcon(typeId);
      }
    } else {
      return icon;
    }
    final String subtype = mimeType.getSubtype();
    if (subtype != null && subtype.length() > 0) {
      icon = getContentTypeIcon(typeId, new BasicMimeTypeDTO(mimeType.getType()));
    }
    return icon == null ? getContentTypeIcon(typeId) : icon;
  }

  public Object getContentTypeIcon(final String typeId, final ContentStatus contentStatus) {
    final Object icon = getContentTypeIcon(IdGenerator.generate(typeId, contentStatus.toString()));
    return (icon == null ? getContentTypeIcon(typeId) : icon);
  }

  public void registerContentTypeIcon(final String typeId, final BasicMimeTypeDTO mimeType,
      final Object icon) {
    registerContentTypeIcon(IdGenerator.generate(typeId, mimeType.toString()), icon);
  }

  public void registerContentTypeIcon(final String typeId, final ContentStatus contentStatus,
      final Object icon) {
    registerContentTypeIcon(IdGenerator.generate(typeId, contentStatus.toString()), icon);
  }

  public void registerContentTypeIcon(final String contentTypeId, final Object icon) {
    contentTypesIcons.put(contentTypeId, icon);
  }
}
