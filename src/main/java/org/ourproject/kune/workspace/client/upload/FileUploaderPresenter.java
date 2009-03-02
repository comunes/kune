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
 \*/
package org.ourproject.kune.workspace.client.upload;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.tool.FoldableAbstractClientTool;

import com.calclab.suco.client.events.Event;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class FileUploaderPresenter implements FileUploader {

    private FileUploaderView view;
    private final Session session;
    private StateToken currentUploadStateToken;
    private final Event<StateToken> onUploadComplete;

    public FileUploaderPresenter(final Session session) {
        this.session = session;
        this.onUploadComplete = new Event<StateToken>("onUploadComplete");
    }

    public void addOnUploadCompleteListener(final Listener<StateToken> slot) {
        onUploadComplete.add(slot);
    }

    public boolean checkFolderChange() {
        final StateToken currentFolderStateToken = session.getCurrentStateToken().copy().clearDocument();
        if (sameContainer()) {
            view.setUploadParams(session.getUserHash(), currentFolderStateToken.toString(),
                    currentFolderStateToken.getTool() + "." + FoldableAbstractClientTool.UPLOADEDFILE_SUFFIX);
            return true;
        }
        if (view.hasUploadingFiles()) {
            return false;
        } else {
            currentUploadStateToken = currentFolderStateToken;
            view.setUploadParams(session.getUserHash(), currentFolderStateToken.toString(),
                    currentFolderStateToken.getTool() + "." + FoldableAbstractClientTool.UPLOADEDFILE_SUFFIX);
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
        onUploadComplete.fire(currentUploadStateToken);
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
