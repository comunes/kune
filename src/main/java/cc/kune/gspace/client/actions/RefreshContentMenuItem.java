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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class RefreshContentMenuItem extends MenuItemDescriptor {

    public static class GoParentContainerAction extends AbstractExtendedAction {

        private final StateManager stateManager;

        @Inject
        public GoParentContainerAction(final StateManager stateManager) {
            this.stateManager = stateManager;
        }

        @Override
        public void actionPerformed(final ActionEvent event) {
            stateManager.refreshCurrentGroupState();
        }

    }

    @Inject
    public RefreshContentMenuItem(final I18nTranslationService i18n, final GoParentContainerAction action,
            final ContentViewerOptionsMenu optionsMenu, final NavResources res) {
        super(action);
        this.withText(i18n.t("Reload current page")).withIcon(res.refresh()).withParent(optionsMenu);
    }

}
