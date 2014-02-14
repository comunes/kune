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

package cc.kune.core.client.invitation;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.sitebar.search.EntitySearchPanel;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The Class InviteUserSearchPanel is an EntitySearchPanel to invite users to a
 * list
 *
 * @author Antonio Tenorio Fornés <antoniotenorio@ucm.es>
 *
 */
@Singleton
public class ListInviteUserSearchPanel extends EntitySearchPanel {

  /**
   * Instantiates a new group invite user search panel
   *
   * @param img
   *          the image resource
   * @param i18n
   *          the i18n translation service
   */
  @Inject
  public ListInviteUserSearchPanel(final CoreResources img, final I18nTranslationService i18n) {
    super(img, i18n);
  }

}
