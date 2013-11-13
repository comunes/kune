/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.client;

import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * The KuneEntryPoint is used to start kune complete client
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneEmbedEntryPoint extends AbstractKuneEntryPoint {

  /**
   * On module load continue.
   */
  @Override
  protected void onContinueModuleLoad() {
    // FIXME: emite is loading (via EmiteBrowserEntryPoint) here! (maybe we
    // don't need chat)
    DelayedBindRegistry.bind(ginjector);
    ginjector.getSpinerPresenter();

    ginjector.getSessionExpirationManager();
    ginjector.getEventLogger();
    ginjector.getErrorsDialog();
    // ginjector.getCorePresenter().get().forceReveal();
    // ginjector.getOnAppStartFactory();
    // ginjector.getStateManager();
    ginjector.getGwtGuiProvider();
    // ginjector.getGroupMembersPresenter();

    /* Tools (order in GUI) */
    // ginjector.getDocsParts();
    // ginjector.getBlogsParts();
    // ginjector.getWikiParts();
    // ginjector.getEventsParts();
    // ginjector.getTasksParts();
    // ginjector.getListsParts();
    // ginjector.getChatParts();
    // ginjector.getBartersParts();
    // ginjector.getTrashParts();

    // ginjector.getSiteLogo();
    // ginjector.getChatClient();
    // ginjector.getCoreParts();
    // ginjector.getGSpaceParts();
    // ginjector.getPSpaceParts();
    // ginjector.getHSpaceParts();

    // ginjector.getXmlActionsParser();
    // ginjector.getContentViewerSelector().init();

    // ginjector.getGlobalShortcutRegister().enable();
  }

  /**
   * On start module load.
   */
  @Override
  protected void onStartModuleLoad() {

  }

}
