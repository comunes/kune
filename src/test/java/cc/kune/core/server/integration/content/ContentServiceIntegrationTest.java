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
package cc.kune.core.server.integration.content;

import cc.kune.core.client.rpcservices.ContentService;
import cc.kune.core.client.rpcservices.UserService;
import cc.kune.core.server.integration.IntegrationTest;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentServiceIntegrationTest.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class ContentServiceIntegrationTest extends IntegrationTest {

  /** The content service. */
  @Inject
  protected ContentService contentService;

  /** The user service. */
  @Inject
  protected UserService userService;

  /**
   * Gets the site default content.
   * 
   * @return the site default content
   * @throws Exception
   *           the exception
   */
  protected StateContentDTO getSiteDefaultContent() throws Exception {
    final StateToken stateToken = new StateToken(getDefSiteShortName());
    final StateContentDTO content = (StateContentDTO) contentService.getContent(session.getHash(),
        stateToken);
    return content;
  }

}
