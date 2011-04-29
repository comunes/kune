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
package cc.kune.gspace.client.options;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.common.client.ui.dialogs.tabbed.AbstractTabbedDialogPanel;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.ws.entheader.EntityHeader;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class GroupOptionsPanel extends AbstractTabbedDialogPanel implements GroupOptionsView {

  public static final String GROUP_OP_PANEL_ID = "k-gop-diagpan";
  public static final String GROUP_OPTIONS_ERROR_ID = "k-gop-err-mess";
  private final EntityHeader entityHeader;
  private final GroupOptionsCollection entityPreferencesGroup;

  @Inject
  public GroupOptionsPanel(final EntityHeader entityHeader, final I18nTranslationService i18n,
      final NotifyLevelImages images, final GroupOptionsCollection entityOptionsGroup) {
    super(GROUP_OP_PANEL_ID, "", WIDTH, HEIGHT + 80, WIDTH, HEIGHT + 80, false, images,
        GROUP_OPTIONS_ERROR_ID, i18n.t("Close"), null, null, null);
    this.entityHeader = entityHeader;
    this.entityPreferencesGroup = entityOptionsGroup;
    super.setIconCls("k-options-icon");
    super.setTitle(i18n.t(CoreMessages.GROUP_OPTIONS_DIALOG_TITLE) + TextUtils.IN_DEVELOPMENT_P);
  }

  @Override
  public void addAction(final GuiActionDescrip descriptor) {
    entityHeader.addAction(descriptor);
  }

  @Override
  public Widget asWidget() {
    return super.getWidget();
  }

  @Override
  public void createAndShow() {
    createAndShowItImpl();
  }

  private void createAndShowItImpl() {
    super.getFirstBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        hide();
      }
    });
    entityPreferencesGroup.createAll();
    super.createAndShow();
  }
}
