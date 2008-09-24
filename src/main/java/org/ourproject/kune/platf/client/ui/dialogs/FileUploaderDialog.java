package org.ourproject.kune.platf.client.ui.dialogs;

import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.KuneStringUtils;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtextux.client.widgets.upload.UploadDialog;
import com.gwtextux.client.widgets.upload.UploadDialogListenerAdapter;

public class FileUploaderDialog implements FileUploader {

    protected static final String SITE_FILE_UPLOADER = "k-site-file-uploader";

    private static final String URL = "/kune/servlets/FileUploadManager";

    private UploadDialog dialog;
    private ToolbarButton traybarButton;
    private final I18nUITranslationService i18n;
    private final WorkspaceSkeleton ws;
    private final Session session;

    public FileUploaderDialog(final I18nUITranslationService i18n, final WorkspaceSkeleton ws, final Session session) {
	this.i18n = i18n;
	this.ws = ws;
	this.session = session;
	createDialog(false);
    }

    public void hide() {
	dialog.hide();
    }

    public void setPermittedExtensions(final String extensions) {
	DeferredCommand.addCommand(new Command() {
	    public void execute() {
		Log.error("Debugging setPerm issue, current perms: " + dialog.getPermittedExtensions());
		final Object[] objs = KuneStringUtils.splitTags(extensions).toArray();
		final String[] exts = new String[objs.length];
		for (int i = 0; i < objs.length; i++) {
		    exts[i] = (String) objs[i];
		}
		dialog.setPermittedExtensions(exts);
	    }
	});
    }

    public void show() {
	if (traybarButton == null) {
	    traybarButton = new ToolbarButton();
	    traybarButton.setTooltip(i18n.t("Show/hide uploader window"));
	    traybarButton.setIcon("images/nav/upload.png");
	    traybarButton.addListener(new ButtonListenerAdapter() {
		@Override
		public void onClick(final Button button, final EventObject e) {
		    if (dialog.isVisible()) {
			dialog.hide();
		    } else {
			dialog.show();
		    }
		}
	    });
	    ws.getSiteTraybar().addButton(traybarButton);
	}
	if (dialog == null) {
	    createDialog(true);
	} else {
	    dialog.show();
	}
    }

    private void createDialog(final boolean show) {
	DeferredCommand.addCommand(new Command() {
	    public void execute() {
		dialog = new UploadDialog(i18n.t("File uploader"), false, true);
		dialog.setId(SITE_FILE_UPLOADER);
		// dialog.setUploadAutostart(true);
		dialog.setResetOnHide(false);
		dialog.addListener(new UploadDialogListenerAdapter() {
		    @Override
		    public void onFileUploadStart(final UploadDialog source, final String filename) {
			setUploadParams(session.getUserHash(), session.getCurrentState().getStateToken().toString(),
				filename);
		    }

		    @Override
		    public void onUploadComplete(final UploadDialog source) {
			if (!dialog.isVisible()) {
			    Site.info(i18n.t("Upload completed"));
			    traybarButton.destroy();
			    traybarButton = null;
			}
		    }

		    @Override
		    public void onUploadError(final UploadDialog source, final String filename,
			    final JavaScriptObject data) {
			showError();
		    }

		    @Override
		    public void onUploadFailed(final UploadDialog source, final String filename) {
			showError();
		    }

		    private void setUploadParams(final String userhash, final String currentStateToken,
			    final String filename) {
			Log.info("Setting upload params");
			final UrlParam param[] = new UrlParam[3];
			param[0] = new UrlParam(USERHASH, userhash);
			param[1] = new UrlParam(CURRENT_STATE_TOKEN, currentStateToken);
			param[2] = new UrlParam(FILENAME, filename);
			dialog.setBaseParams(param);
			dialog.setPostVarName(filename);
		    }

		    private void showError() {
			if (!dialog.isVisible()) {
			    Site.veryImportant(i18n.t("Error uploading"));
			}
		    }

		});
		dialog.setUrl(URL);
		if (show) {
		    dialog.show();
		}
	    }
	});
    }
}
