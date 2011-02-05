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
package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.actions.AbstractExtendedAction;
import org.ourproject.kune.platf.client.actions.ui.GuiActionDescrip;

import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener2;
import com.google.gwt.resources.client.ImageResource;

public abstract class RolAction extends AbstractExtendedAction {

    public static UIStatus refreshStatus(final AccessRolDTO rolRequired, final boolean authNeed,
            final boolean isLogged, final boolean visibleForMembers, final boolean visibleForNonMemb,
            final AccessRights newRights) {
        boolean newVisibility = false;
        boolean newEnabled = false;
        if (authNeed && !isLogged) {
            newVisibility = newEnabled = false;
        } else {
            // Auth ok
            newEnabled = RolComparator.isEnabled(rolRequired, newRights);
            if (newEnabled) {
                final boolean isMember = RolComparator.isMember(newRights);
                newEnabled = newVisibility = isMember && visibleForMembers || !isMember && visibleForNonMemb;
            } else {
                newVisibility = false;
            }
        }
        return new UIStatus(newVisibility, newEnabled);
    }

    protected final Session session;
    protected final StateManager stateManager;
    protected final I18nTranslationService i18n;
    private boolean visibleForNonMemb;
    private boolean visibleForMembers;
    private boolean authNeed;

    public RolAction(final Session session, final StateManager stateManager,
            final AccessRightsClientManager rightsManager, final I18nTranslationService i18n,
            final AccessRolDTO rolRequired, final String text, final String tooltip, final ImageResource icon) {
        super(text, tooltip, icon);
        this.session = session;
        this.stateManager = stateManager;
        this.i18n = i18n;
        this.visibleForMembers = true;
        this.visibleForNonMemb = true;
        this.authNeed = false;
        rightsManager.onRightsChanged(new Listener2<AccessRights, AccessRights>() {
            public void onEvent(final AccessRights prevRights, final AccessRights newRights) {
                setStatus(refreshStatus(rolRequired, authNeed, session.isLogged(), visibleForMembers,
                        visibleForNonMemb, newRights));
            }

        });
    }

    public void setMustBeAuthenticated(final boolean authNeed) {
        this.authNeed = authNeed;
    }

    public void setVisible(final boolean forMembers, final boolean forNonMembers) {
        this.visibleForMembers = forMembers;
        this.visibleForNonMemb = forNonMembers;
    }

    private void setStatus(final UIStatus refreshStatus) {
        setEnabled(refreshStatus.isEnabled());
        putValue(GuiActionDescrip.VISIBLE, refreshStatus.isVisible());
    }
}
