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
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class MembersModerationAction extends AbstractExtendedAction {

    private AdmissionType admissionType;
    private final Provider<GroupServiceAsync> groupServiceProvider;
    private final I18nTranslationService i18n;
    private final Session session;

    @Inject
    public MembersModerationAction(final Session session, final I18nTranslationService i18n,
            final Provider<GroupServiceAsync> groupServiceProvider) {
        this.session = session;
        this.i18n = i18n;
        this.groupServiceProvider = groupServiceProvider;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        groupServiceProvider.get().setGroupNewMembersJoiningPolicy(session.getUserHash(),
                session.getCurrentState().getGroup().getStateToken(), admissionType, new AsyncCallbackSimple<Void>() {
                    @Override
                    public void onSuccess(final Void result) {
                        NotifyUser.info(i18n.t("Members joining policy changed"));
                    }
                });
    }

    public void setAdmissionType(final AdmissionType admissionType) {
        this.admissionType = admissionType;
    }

}
