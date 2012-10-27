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
package cc.kune.core.client.sn;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.rpcservices.SocialNetServiceHelper;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;

import com.google.inject.Inject;

public class AllMembersGroupSNDropController extends GroupSNDropController {

  @Inject
  public AllMembersGroupSNDropController(final KuneDragController dragController,
      final ContentServiceHelper contentService, final Session session,
      final I18nTranslationService i18n, final SocialNetServiceHelper sNClientUtils) {
    super(dragController, SocialNetworkSubGroup.ALL_GROUP_MEMBERS, contentService, session, i18n,
        sNClientUtils);
  }

}
