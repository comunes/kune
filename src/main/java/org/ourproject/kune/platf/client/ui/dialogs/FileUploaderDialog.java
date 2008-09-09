package org.ourproject.kune.platf.client.ui.dialogs;

import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtextux.client.widgets.upload.UploadDialog;
import com.gwtextux.client.widgets.upload.UploadDialogListenerAdapter;

public class FileUploaderDialog implements FileUploader {

    private static final String URL = "/services/fileupload";

    private final UploadDialog dialog;
    private ToolbarButton traybarButton;
    private final I18nUITranslationService i18n;
    private final WorkspaceSkeleton ws;

    public FileUploaderDialog(final I18nUITranslationService i18n, final WorkspaceSkeleton ws) {
	this.i18n = i18n;
	this.ws = ws;
	dialog = new UploadDialog(i18n.t("File uploader"), false, true);
	dialog.addListener(new UploadDialogListenerAdapter() {
	    @Override
	    public void onUploadComplete(final UploadDialog source) {
		Site.info(i18n.t("Upload completed"));
		// traybarButton.destroy();
		// traybarButton = null;
	    }
	});
	dialog.setUrl(URL);

	// final UrlParam param[] = new UrlParam[2];
	// param[0] = new UrlParam("name1", "value1");
	// param[1] = new UrlParam("name2", "value2");
	// dialog.setBaseParams(param);
	// dialog.setPostVarName("myvar");
    }

    public void hide() {
	dialog.hide();
    }

    public void setPermittedExtensions(final String[] extensions) {
	dialog.setPermittedExtensions(extensions);
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
	dialog.show();
    }
}
