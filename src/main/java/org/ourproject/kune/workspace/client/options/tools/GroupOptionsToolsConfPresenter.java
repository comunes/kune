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
package org.ourproject.kune.workspace.client.options.tools;

import java.util.Collection;
import java.util.List;

import org.ourproject.kune.workspace.client.options.EntityOptions;

import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.ToolSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener2;
import com.calclab.suco.client.ioc.Provider;

public class GroupOptionsToolsConfPresenter extends EntityOptionsToolsConfPresenter implements GroupOptionsToolConf {

    public GroupOptionsToolsConfPresenter(final StateManager stateManager, final Session session,
            final I18nTranslationService i18n, final EntityOptions entityOptions,
            final Provider<GroupServiceAsync> groupService) {
        super(session, stateManager, i18n, entityOptions, groupService);
        stateManager.onGroupChanged(new Listener2<String, String>() {
            public void onEvent(final String group1, final String group2) {
                setState();
            }
        });
    }

    @Override
    protected boolean applicable() {
        return session.isCurrentStateAGroup();
    }

    @Override
    protected Collection<ToolSimpleDTO> getAllTools() {
        return session.getGroupTools();
    }

    @Override
    protected StateToken getDefContentToken() {
        final ContentSimpleDTO defaultContent = session.getCurrentState().getGroup().getDefaultContent();
        return defaultContent == null ? null : defaultContent.getStateToken();
    }

    @Override
    protected String getDefContentTooltip() {
        return i18n.t("You cannot disable this tool because it's where the current group home page is located. To do that you have to select other content as the default group home page but in another tool.");
    }

    @Override
    protected List<String> getEnabledTools() {
        return session.getCurrentState().getEnabledTools();
    }

    @Override
    protected StateToken getOperationToken() {
        return session.getCurrentStateToken();
    }

    @Override
    protected void gotoDifLocationIfNecessary(final String toolName) {
        if (session.getCurrentStateToken().getTool().equals(toolName)) {
            final ContentSimpleDTO defaultContent = session.getCurrentState().getGroup().getDefaultContent();
            if (defaultContent != null) {
                stateManager.gotoStateToken(defaultContent.getStateToken());
            }
        }
    }

}
