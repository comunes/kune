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
package cc.kune.gspace.client.viewers;

import cc.kune.common.client.ui.EditableLabel;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.common.client.ui.UiUtils;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.core.client.registry.IconsRegistry;
import cc.kune.core.shared.dto.BasicMimeTypeDTO;
import cc.kune.gspace.client.armor.GSpaceArmor;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.Label;

public class ContentTitleWidget extends Composite {
  public static final String ID = "k-cnt-title-id";
  private final EditableLabel editableTitle;
  private final GSpaceArmor gsArmor;
  private final I18nTranslationService i18n;
  private final IconsRegistry iconRegistry;
  private final Label titleIcon;

  public ContentTitleWidget(final I18nTranslationService i18n, final GSpaceArmor gsArmor,
      final IconsRegistry iconRegistry) {
    this.i18n = i18n;
    this.gsArmor = gsArmor;
    this.iconRegistry = iconRegistry;
    final FlowPanel flow = new FlowPanel();
    titleIcon = new Label();
    titleIcon.setStyleName("k-content-title-icon");
    editableTitle = new EditableLabel();
    flow.add(titleIcon);
    flow.add(editableTitle);
    initWidget(flow);
    ensureDebugId(ID);
  }

  public void edit() {
    editableTitle.edit();
  }

  public HasEditHandler getEditableTitle() {
    return editableTitle;
  }

  public void highlightTitle() {
    editableTitle.highlightTitle();
  }

  public void setText(final String text) {
    editableTitle.setText(text);
  }

  public void setTitle(final String title, final String typeId, final BasicMimeTypeDTO mime,
      final boolean editable) {
    final ForIsWidget docHeader = gsArmor.getDocHeader();
    UiUtils.clear(docHeader);

    final KuneIcon resource = (mime == null ? (KuneIcon) iconRegistry.getContentTypeIcon(typeId)
        : (KuneIcon) iconRegistry.getContentTypeIcon(typeId, mime));
    final boolean hasIcon = resource != null;
    if (hasIcon) {
      titleIcon.setText(resource.getCharacter().toString());
    }
    titleIcon.setVisible(hasIcon);
    editableTitle.setText(title);
    if (editable) {
      editableTitle.setTooltip(i18n.t("Click to rename"));
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
