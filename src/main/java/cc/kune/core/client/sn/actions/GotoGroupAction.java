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
package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class GotoGroupAction extends AbstractExtendedAction {

    private final StateManager stateManager;

    @Inject
    public GotoGroupAction(final StateManager stateManager, final I18nTranslationService i18n, final CoreResources res) {
        this.stateManager = stateManager;
        putValue(NAME, i18n.t("Visit this group homepage"));
        putValue(Action.SMALL_ICON, res.groupHome());
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        StateToken token;
        final Object target = event.getTarget();
        if (target instanceof GroupDTO) {
            token = ((GroupDTO) target).getStateToken();
        } else {
            token = ((UserSimpleDTO) target).getStateToken();
        }
        stateManager.gotoStateToken(token);
    }

}
