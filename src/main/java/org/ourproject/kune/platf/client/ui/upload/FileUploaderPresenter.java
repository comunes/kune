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
package org.ourproject.kune.platf.client.ui.upload;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener0;

public class FileUploaderPresenter implements FileUploader {

    private FileUploaderView view;
    private final Session session;
    private StateToken currentUploadStateToken;
    private final Provider<ContextNavigator> navProvider;

    public FileUploaderPresenter(final Session session, final Provider<ContextNavigator> navProvider) {
        this.session = session;
        this.navProvider = navProvider;
    }

    public boolean checkFolderChange() {
        final StateToken currentFolderStateToken = session.getCurrentStateToken().clone().clearDocument();
        if (sameContainer()) {
            view.setUploadParams(session.getUserHash(), currentFolderStateToken.toString());
            return true;
        }
        if (view.hasUploadingFiles()) {
            return false;
        } else {
            currentUploadStateToken = currentFolderStateToken;
            view.setUploadParams(session.getUserHash(), currentFolderStateToken.toString());
            return true;
        }
    }

    public boolean hasUploadingFiles() {
        return view.hasUploadingFiles();
    }

    public void hide() {
        view.hide();
    }

    public void init(final FileUploaderView view) {
        this.view = view;
        session.onUserSignOut(new Listener0() {
            public void onEvent() {
                view.destroy();
            }
        });
    }

    public void onUploadComplete() {
        navProvider.get().refresh(currentUploadStateToken);
    }

    public void resetPermittedExtensions() {
        view.resetPermittedExtensions();
    }

    public void setPermittedExtensions(final String extensions) {
        view.setPermittedExtensions(extensions);
    }

    public void show() {
        view.show();
    }

    private boolean sameContainer() {
        final StateToken currentStateToken = session.getCurrentStateToken();
        currentUploadStateToken = currentUploadStateToken == null ? currentStateToken : currentUploadStateToken;
        return currentUploadStateToken.equals(currentStateToken);
    }
}
