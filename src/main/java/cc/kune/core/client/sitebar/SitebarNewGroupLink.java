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
package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class SitebarNewGroupLink extends ButtonDescriptor {

    public static class SitebarNewGroupAction extends AbstractExtendedAction {

        private final StateManager stateManager;

        @Inject
        public SitebarNewGroupAction(final StateManager stateManager, final I18nTranslationService i18n) {
            super();
            this.stateManager = stateManager;
            putValue(Action.NAME, i18n.t("Create New Group"));
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            stateManager.gotoHistoryTokenButRedirectToCurrent(SiteTokens.NEWGROUP);
        }

    }

    @Inject
    public SitebarNewGroupLink(final SitebarNewGroupAction action) {
        super(action);
        setStyles("k-no-backimage, k-btn-sitebar, k-fl, k-noborder, k-nobackcolor");
        setParent(SitebarActionsPresenter.RIGHT_TOOLBAR);
    }
}
