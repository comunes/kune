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
package cc.kune.gspace.client.viewers;

import cc.kune.common.client.ui.EditableLabel;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.common.client.ui.UiUtils;
import cc.kune.core.client.registry.IconsRegistry;
import cc.kune.core.shared.dto.BasicMimeTypeDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;

public class ContentTitleWidget extends Composite {
  private final EditableLabel editableTitle;
  private final GSpaceArmor gsArmor;
  private final I18nTranslationService i18n;
  private final IconsRegistry iconRegistry;
  private final Image titleIcon;

  public ContentTitleWidget(final I18nTranslationService i18n, final GSpaceArmor gsArmor,
      final IconsRegistry iconRegistry) {
    this.i18n = i18n;
    this.gsArmor = gsArmor;
    this.iconRegistry = iconRegistry;
    final FlowPanel flow = new FlowPanel();
    titleIcon = new Image();
    editableTitle = new EditableLabel();
    flow.add(titleIcon);
    flow.add(editableTitle);
    initWidget(flow);
  }

  public HasEditHandler getEditableTitle() {
    return editableTitle;
  }

  public void setText(final String text) {
    editableTitle.setText(text);
  }

  public void setTitle(final String title, final String typeId, final BasicMimeTypeDTO mime,
      final boolean editable) {
    final ForIsWidget docHeader = gsArmor.getDocHeader();
    UiUtils.clear(docHeader);

    final ImageResource resource = (mime == null ? (ImageResource) iconRegistry.getContentTypeIcon(typeId)
        : (ImageResource) iconRegistry.getContentTypeIcon(typeId, mime));
    final boolean hasIcon = resource != null;
    if (hasIcon) {
      titleIcon.setResource(resource);
    }
    titleIcon.setVisible(hasIcon);
    editableTitle.setText(title);
    if (editable) {
      editableTitle.setTooltip(i18n.t("Click to edit"));
    } else {
      editableTitle.setTooltip("");
    }
    editableTitle.setEditable(editable);
    docHeader.add(this);
  }

  public void setTitle(final String title, final String typeId, final boolean editable) {
    setTitle(title, typeId, null, editable);
  }

}
