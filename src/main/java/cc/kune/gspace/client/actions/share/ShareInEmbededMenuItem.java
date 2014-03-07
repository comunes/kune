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

package cc.kune.gspace.client.actions.share;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.services.MediaUtils;
import cc.kune.core.client.state.SessionInstance;
import cc.kune.core.client.ui.dialogs.PromptTopDialog;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.Builder;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.OnEnter;
import cc.kune.gspace.client.actions.IsInDevelopmentCondition;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The Class ShareInEmbededMenuItem.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ShareInEmbededMenuItem extends MenuItemDescriptor {

  @Singleton
  static public class ShareInEmbededAction extends AbstractExtendedAction {

    public static final String EMBED_DIALOG_ID = "k-siea-dialog";
    public static final String OK_BUTTON_ID = "k-siea-ok-btn";
    private PromptTopDialog diag;
    private final MediaUtils mediaUtils;

    @Inject
    public ShareInEmbededAction(final MediaUtils mediaUtils) {
      this.mediaUtils = mediaUtils;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      final String embedCode = mediaUtils.getKuneDocEmbedCode(ShareInHelper.getCurrentUrl(
          SessionInstance.get(), true));
      final Builder builder = new PromptTopDialog.Builder(EMBED_DIALOG_ID,
          I18n.t("Copy this code to your website"), true, false, I18n.getDirection(), new OnEnter() {
            @Override
            public void onEnter() {
            }
          });
      builder.width("320px").height("120px").firstButtonTitle(I18n.t("Close")).firstButtonId(
          OK_BUTTON_ID);
      builder.promptLines(6).promptWidth(295);
      diag = builder.build();
      diag.showCentered();
      diag.setTextFieldValue(embedCode);
      diag.setTextFieldReadOnly(true);
      diag.addStyleToTextField("k-sharelink-box");
      diag.setTextFieldSelectOnFocus(true);
      diag.setTextFieldFocusOnClick();
      diag.focusOnTextBox();
      diag.getFirstBtn().addClickHandler(new ClickHandler() {
        @Override
        public void onClick(final ClickEvent event) {
          diag.hide();
        }
      });
    }
  }

  /**
   * Instantiates a new share embed in other websites menu item.
   * 
   * @param action
   *          the action
   * @param iconic
   *          the iconic
   * @param session
   *          the session
   * @param menu
   *          the menu
   * @param i18n
   *          the i18n
   */
  @Inject
  public ShareInEmbededMenuItem(final ShareInEmbededAction action, final IconicResources iconic,
      final ShareMenu menu, final IsInDevelopmentCondition isInDevAddCondition) {
    super(action);
    withText(I18n.t("Embed in other websites")).withIcon(iconic.embed()).withParent(menu, false);
    withAddCondition(isInDevAddCondition);
  }
}
