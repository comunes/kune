/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.newgroup;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser.Level;
import org.ourproject.kune.workspace.client.WorkspaceMessages;
import org.ourproject.kune.workspace.client.site.SiteToken;

import cc.kune.core.client.errors.GroupNameInUseException;
import cc.kune.core.client.errors.UIException;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class NewGroupPresenter implements NewGroup {

    private NewGroupView view;
    private final I18nTranslationService i18n;
    private final Session session;
    private final StateManager stateManager;
    private final Provider<GroupServiceAsync> groupService;
    private boolean mustGoToPrevious;

    public NewGroupPresenter(final I18nTranslationService i18n, final Session session, final StateManager stateManager,
            final Provider<GroupServiceAsync> groupService) {
        this.i18n = i18n;
        this.session = session;
        this.stateManager = stateManager;
        this.groupService = groupService;
        stateManager.addSiteToken(SiteToken.newgroup.toString(), new Listener0() {
            public void onEvent() {
                doNewGroup();
            }
        });
        mustGoToPrevious = true;
    }

    public void doNewGroup() {
        session.check(new AsyncCallbackSimple<Void>() {
            public void onSuccess(final Void result) {
                if (session.isLogged()) {
                    NotifyUser.showProgressProcessing();
                    view.show();
                    view.center();
                    view.setLicense(session.getDefLicense());
                    NotifyUser.hideProgress();
                } else {
                    stateManager.restorePreviousToken();
                    NotifyUser.info(i18n.t(WorkspaceMessages.REGISTER_TO_CREATE_A_GROUP));
                }
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(final NewGroupView view) {
        this.view = view;
    }

    public void onCancel() {
        view.hide();
        reset();
        stateManager.restorePreviousToken();
    }

    public void onClose() {
        if (mustGoToPrevious) {
            stateManager.restorePreviousToken();
        }
        reset();
    }

    public void onRegister() {
        if (view.isFormValid()) {
            view.maskProcessing();
            final String shortName = view.getShortName();
            final String longName = view.getLongName();
            final String publicDesc = view.getPublicDesc();
            final LicenseDTO license = view.getLicense();
            final GroupDTO group = new GroupDTO(shortName, longName, getTypeOfGroup());
            group.setDefaultLicense(license);

            final AsyncCallback<StateToken> callback = new AsyncCallback<StateToken>() {
                public void onFailure(final Throwable caught) {
                    if (caught instanceof GroupNameInUseException) {
                        view.unMask();
                        setMessage(i18n.t(WorkspaceMessages.NAME_IN_ALREADY_IN_USE), Level.error);
                    } else {
                        view.unMask();
                        setMessage(i18n.t("Error creating group"), Level.error);
                        throw new UIException("Other kind of exception in group registration", caught);
                    }
                }

                public void onSuccess(final StateToken token) {
                    mustGoToPrevious = false;
                    view.hide();
                    stateManager.gotoToken(token);
                    reset();
                    view.unMask();
                }
            };
            groupService.get().createNewGroup(session.getUserHash(), group, publicDesc, view.getTags(), null, callback);
        }
    }

    public void setMessage(final String message, final Level level) {
        view.setMessage(message, level);
    }

    private GroupType getTypeOfGroup() {
        if (view.isProject()) {
            return GroupType.PROJECT;
        } else if (view.isOrganization()) {
            return GroupType.ORGANIZATION;
        } else {
            return GroupType.COMMUNITY;
        }
    }

    private void reset() {
        view.clearData();
        mustGoToPrevious = true;
    }
}
