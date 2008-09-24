package org.ourproject.kune.platf.client.ui.dialogs.upload;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;

import com.calclab.suco.client.listener.Listener0;

public class FileUploaderPresenter implements FileUploader {

    private FileUploaderView view;
    private final Session session;
    private StateToken currentUploadStateToken;

    public FileUploaderPresenter(final Session session) {
	this.session = session;
    }

    public boolean checkFolderChange() {
	final StateToken currentStateToken = session.getCurrentStateToken();
	if (sameContainer()) {
	    view.setUploadParams(session.getUserHash(), currentStateToken.toString());
	    return true;
	}
	if (view.hasUploadingFiles()) {
	    return false;
	} else {
	    currentUploadStateToken = currentStateToken;
	    view.setUploadParams(session.getUserHash(), currentStateToken.toString());
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
