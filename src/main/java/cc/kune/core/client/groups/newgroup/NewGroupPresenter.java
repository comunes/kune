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
package cc.kune.core.client.groups.newgroup;

import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.noti.NotifyLevel;
import cc.kune.common.client.noti.NotifyUser;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.errors.GroupNameInUseException;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.HistoryTokenCallback;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteCommonTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class NewGroupPresenter extends Presenter<NewGroupView, NewGroupPresenter.NewGroupProxy> implements NewGroup {

    @ProxyCodeSplit
    public interface NewGroupProxy extends Proxy<NewGroupPresenter> {
    }

    private final Provider<GroupServiceAsync> groupService;

    private final I18nTranslationService i18n;
    private boolean mustGoToPrevious;
    private final Session session;
    private final Provider<SignIn> signIn;
    private final StateManager stateManager;

    @Inject
    public NewGroupPresenter(final EventBus eventBus, final NewGroupView view, final NewGroupProxy proxy,
            final I18nTranslationService i18n, final Session session, final StateManager stateManager,
            final Provider<GroupServiceAsync> groupService, final Provider<SignIn> signIn) {
        super(eventBus, view, proxy);
        this.i18n = i18n;
        this.session = session;
        this.stateManager = stateManager;
        this.groupService = groupService;
        this.signIn = signIn;

        stateManager.addSiteToken(SiteCommonTokens.NEWGROUP, new HistoryTokenCallback() {

            @Override
            public void onHistoryToken() {
                doNewGroup();
            }
        });
        mustGoToPrevious = true;
    }

    @Override
    public void doNewGroup() {
        session.check(new AsyncCallbackSimple<Void>() {
            @Override
            public void onSuccess(final Void result) {
                if (session.isLogged()) {
                    NotifyUser.showProgressProcessing();
                    getView().show();
                    getView().focusOnShorName();
                    NotifyUser.hideProgress();
                } else {
                    // stateManager.restorePreviousToken();
                    NotifyUser.info(i18n.t(CoreMessages.REGISTER_TO_CREATE_A_GROUP));
                    signIn.get().showSignInDialog();
                }
            }
        });
    }

    private GroupType getTypeOfGroup() {
        if (getView().isProject()) {
            return GroupType.PROJECT;
        } else if (getView().isOrganization()) {
            return GroupType.ORGANIZATION;
        } else {
            return GroupType.COMMUNITY;
        }
    }

    @Override
    protected void onBind() {
        super.onBind();
        getView().getFirstBtn().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                onRegister();
            }
        });
        getView().getSecondBtn().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                onCancel();
            }
        });
        getView().getClose().addCloseHandler(new CloseHandler<PopupPanel>() {

            @Override
            public void onClose(final CloseEvent<PopupPanel> event) {
                Log.debug("Closing register presenter");
                NewGroupPresenter.this.onCancel();
            }
        });
    }

    public void onCancel() {
        getView().hide();
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
        if (getView().isFormValid()) {
            getView().maskProcessing();
            final String shortName = getView().getShortName();
            final String longName = getView().getLongName();
            final String publicDesc = getView().getPublicDesc();
            final LicenseDTO license = session.getDefLicense();
            final GroupDTO group = new GroupDTO(shortName, longName, getTypeOfGroup());
            group.setDefaultLicense(license);

            final AsyncCallback<StateToken> callback = new AsyncCallback<StateToken>() {
                @Override
                public void onFailure(final Throwable caught) {
                    if (caught instanceof GroupNameInUseException) {
                        getView().unMask();
                        setMessage(i18n.t(CoreMessages.NAME_IN_ALREADY_IN_USE), NotifyLevel.error);
                    } else {
                        getView().unMask();
                        setMessage(i18n.t("Error creating group"), NotifyLevel.error);
                        throw new UIException("Other kind of exception in group registration", caught);
                    }
                }

                @Override
                public void onSuccess(final StateToken token) {
                    mustGoToPrevious = false;
                    getView().hide();
                    stateManager.gotoToken(token);
                    reset();
                    getView().unMask();
                }
            };
            groupService.get().createNewGroup(session.getUserHash(), group, publicDesc, getView().getTags(), null,
                    callback);
        }
    }

    private void reset() {
        getView().clearData();
        mustGoToPrevious = true;
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    public void setMessage(final String message, final NotifyLevel level) {
        getView().setMessage(message, level);
    }
}
