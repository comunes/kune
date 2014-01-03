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
package cc.kune.core.client.sn;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.rpcservices.SocialNetServiceHelper;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class CollabsGroupSNDropController.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class CollabsGroupSNDropController extends GroupSNDropController {

  /**
   * Instantiates a new collabs group sn drop controller.
   * 
   * @param dragController
   *          the drag controller
   * @param contentService
   *          the content service
   * @param session
   *          the session
   * @param i18n
   *          the i18n
   * @param sNClientUtils
   *          the s n client utils
   */
  @Inject
  public CollabsGroupSNDropController(final KuneDragController dragController,
      final ContentServiceHelper contentService, final Session session,
      final I18nTranslationService i18n, final SocialNetServiceHelper sNClientUtils) {
    super(dragController, SocialNetworkSubGroup.COLLABS, contentService, session, i18n, sNClientUtils);
  }

}
