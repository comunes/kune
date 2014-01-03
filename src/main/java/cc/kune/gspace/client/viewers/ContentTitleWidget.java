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

// TODO: Auto-generated Javadoc
/**
 * The Class ContentTitleWidget.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ContentTitleWidget extends Composite {

  /** The Constant ID. */
  public static final String ID = "k-cnt-title-id";

  /** The editable title. */
  private final EditableLabel editableTitle;

  /** The gs armor. */
  private final GSpaceArmor gsArmor;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The icon registry. */
  private final IconsRegistry iconRegistry;

  /** The title icon. */
  private final Label titleIcon;

  /**
   * Instantiates a new content title widget.
   * 
   * @param i18n
   *          the i18n
   * @param gsArmor
   *          the gs armor
   * @param iconRegistry
   *          the icon registry
   */
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

  /**
   * Edits the.
   */
  public void edit() {
    editableTitle.edit();
  }

  /**
   * Gets the editable title.
   * 
   * @return the editable title
   */
  public HasEditHandler getEditableTitle() {
    return editableTitle;
  }

  /**
   * Highlight title.
   */
  public void highlightTitle() {
    editableTitle.highlightTitle();
  }

  /**
   * Sets the text.
   * 
   * @param text
   *          the new text
   */
  public void setText(final String text) {
    editableTitle.setText(text);
  }

  /**
   * Sets the title.
   * 
   * @param title
   *          the title
   * @param typeId
   *          the type id
   * @param mime
   *          the mime
   * @param editable
   *          the editable
   */
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

  /**
   * Sets the title.
   * 
   * @param title
   *          the title
   * @param typeId
   *          the type id
   * @param editable
   *          the editable
   */
  public void setTitle(final String title, final String typeId, final boolean editable) {
    setTitle(title, typeId, null, editable);
  }

}
