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
package cc.kune.core.client.actions.xml;

import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class AddGadgetAction.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AddGadgetAction extends RolAction {

  /** The content service. */
  private final Provider<ContentServiceAsync> contentService;

  /** The gadget ext name. */
  private final String gadgetExtName;

  /** The gadget name. */
  private final String gadgetName;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The session. */
  private final Session session;

  /**
   * Instantiates a new adds the gadget action.
   * 
   * @param contentService
   *          the content service
   * @param session
   *          the session
   * @param i18n
   *          the i18n
   * @param rol
   *          the rol
   * @param authNeeded
   *          the auth needed
   * @param gadgetExtName
   *          the gadget ext name
   * @param gadgetName
   *          the gadget name
   * @param iconUrl
   *          the icon url
   */
  public AddGadgetAction(final Provider<ContentServiceAsync> contentService, final Session session,
      final I18nTranslationService i18n, final AccessRolDTO rol, final boolean authNeeded,
      final String gadgetExtName, final String gadgetName, final String iconUrl) {
    super(rol, authNeeded);
    this.contentService = contentService;
    this.i18n = i18n;
    this.session = session;
    this.gadgetExtName = gadgetExtName;
    this.gadgetName = gadgetName;
    putValue(Action.SMALL_ICON, iconUrl);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ActionListener#actionPerformed(cc.kune.common
   * .client.actions.ActionEvent)
   */
  @Override
  public void actionPerformed(final ActionEvent event) {
    contentService.get().addGadgetToContent(session.getUserHash(), session.getCurrentStateToken(),
        gadgetExtName, new AsyncCallbackSimple<Void>() {
          @Override
          public void onSuccess(final Void result) {
            NotifyUser.info(i18n.t("'[%s]' created succesfully", i18n.t(gadgetName)),
                i18n.t(CoreMessages.GADGETS_EXPERIMENTAL));
          }
        });
  }

}
