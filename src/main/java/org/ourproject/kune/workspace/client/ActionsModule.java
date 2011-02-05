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
package org.ourproject.kune.workspace.client;

import org.ourproject.kune.platf.client.services.AbstractExtendedModule;
import org.ourproject.kune.workspace.client.socialnet.ParticipateAction;
import org.ourproject.kune.workspace.client.socialnet.UnjoinAction;

import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.ioc.decorator.NoDecoration;
import com.calclab.suco.client.ioc.module.Factory;

public class ActionsModule extends AbstractExtendedModule {
    @Override
    protected void onInstall() {
        register(NoDecoration.class, new Factory<ParticipateAction>(ParticipateAction.class) {
            @Override
            public ParticipateAction create() {
                return new ParticipateAction(i(Session.class), p(SocialNetworkServiceAsync.class),
                        i(StateManager.class), i(AccessRightsClientManager.class), i(I18nTranslationService.class),
                        i(IconResources.class));
            }
        });

        register(NoDecoration.class, new Factory<UnjoinAction>(UnjoinAction.class) {
            @Override
            public UnjoinAction create() {
                return new UnjoinAction(i(Session.class), p(SocialNetworkServiceAsync.class), i(StateManager.class),
                        i(AccessRightsClientManager.class), i(I18nTranslationService.class), i(IconResources.class));
            }
        });

    }
}
