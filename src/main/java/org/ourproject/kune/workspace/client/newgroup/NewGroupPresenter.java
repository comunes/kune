/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.GroupType;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.GroupNameInUseException;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.site.SiteToken;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class NewGroupPresenter implements NewGroup {
    private NewGroupView view;
    private final I18nTranslationService i18n;
    private StateToken previousToken;
    private final Session session;
    private final StateManager stateManager;
    private final Provider<GroupServiceAsync> groupServiceProvider;

    public NewGroupPresenter(final I18nTranslationService i18n, final Session session, final StateManager stateManager,
            final Provider<GroupServiceAsync> groupServiceProvider) {
        this.i18n = i18n;
        this.session = session;
        this.stateManager = stateManager;
        this.groupServiceProvider = groupServiceProvider;
        stateManager.addSiteToken(SiteToken.newgroup.toString(), new Listener<StateToken>() {
            public void onEvent(final StateToken previousStateToken) {
                doNewGroup(previousStateToken);
            }
        });
    }

    public void doNewGroup(final StateToken previousToken) {
        this.previousToken = previousToken;
        session.check(new AsyncCallbackSimple<Object>() {
            public void onSuccess(final Object result) {
                if (session.isLogged()) {
                    Site.showProgressProcessing();
                    view.show();
                    view.center();
                    Site.hideProgress();
                } else {
                    stateManager.gotoToken(previousToken);
                    Site.info(i18n.t("Sign in or register to create a group"));
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

    public void onBack() {
        view.setEnabledBackButton(false);
        view.setEnabledFinishButton(false);
        view.setEnabledNextButton(true);
        view.showNewGroupInitialDataForm();
    }

    public void onCancel() {
        view.hide();
        reset();
        stateManager.gotoToken(previousToken);
    }

    public void onClose() {
        reset();
        stateManager.gotoToken(previousToken);
    }

    public void onFinish() {
        view.maskProcessing();
        final String shortName = view.getShortName();
        final String longName = view.getLongName();
        final String publicDesc = view.getPublicDesc();
        final LicenseDTO license = view.getLicense();
        final GroupDTO group = new GroupDTO(shortName, longName, getTypeOfGroup());
        group.setDefaultLicense(license);

        final AsyncCallback<StateToken> callback = new AsyncCallback<StateToken>() {
            public void onFailure(final Throwable caught) {
                try {
                    throw caught;
                } catch (final GroupNameInUseException e) {
                    onBack();
                    view.unMask();
                    setMessage(i18n.t("This name in already in use, try with a different name."), SiteErrorType.error);
                } catch (final Throwable e) {
                    onBack(); // The messageP is in first page of wizard :-/
                    view.unMask();
                    setMessage(i18n.t("Error creating group"), SiteErrorType.error);
                    GWT.log("Other kind of exception in group registration", null);
                    throw new RuntimeException();
                }
            }

            public void onSuccess(final StateToken token) {
                stateManager.gotoToken(token);
                view.hide();
                reset();
                view.unMask();
            }
        };
        groupServiceProvider.get().createNewGroup(session.getUserHash(), group, publicDesc, view.getTags(), null,
                                                  callback);
    }

    public void onNext() {
        if (view.isFormValid()) {
            view.setEnabledBackButton(true);
            view.setEnabledFinishButton(true);
            view.setEnabledNextButton(false);
            view.showLicenseForm();
        }
    }

    public void setMessage(final String message, final SiteErrorType type) {
        view.setMessage(message, type);
    }

    private GroupType getTypeOfGroup() {
        if (view.isProject()) {
            return GroupType.PROJECT;
        } else if (view.isOrphanedProject()) {
            return GroupType.ORPHANED_PROJECT;
        } else if (view.isOrganization()) {
            return GroupType.ORGANIZATION;
        } else {
            return GroupType.COMMUNITY;
        }
    }

    private void reset() {
        view.clearData();
    }
}
