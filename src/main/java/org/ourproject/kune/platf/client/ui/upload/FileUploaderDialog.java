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
 */package org.ourproject.kune.platf.client.ui.upload;

import org.ourproject.kune.platf.client.ui.TextUtils;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtextux.client.widgets.upload.UploadDialog;
import com.gwtextux.client.widgets.upload.UploadDialogListenerAdapter;

public class FileUploaderDialog extends AbstractUploader implements FileUploaderView {

    protected static final String SITE_FILE_UPLOADER = "k-site-file-uploader";

    private static final String URL = "/kune/servlets/FileUploadManager";

    private UploadDialog dialog;
    private ToolbarButton traybarButton;
    private final I18nUITranslationService i18n;
    private final WorkspaceSkeleton ws;
    private final FileUploaderPresenter presenter;

    public FileUploaderDialog(final FileUploaderPresenter presenter, final I18nUITranslationService i18n,
            final WorkspaceSkeleton ws) {
        this.presenter = presenter;
        this.i18n = i18n;
        this.ws = ws;
        createDialog(false);
    }

    public void destroy() {
        if (hasUploadingFiles()) {
            dialog.stopUpload();
            Site.important(i18n.t("Upload canceled after sign out"));
        }
        if (dialog != null) {
            dialog.destroy();
            dialog = null;
        }
        if (traybarButton != null) {
            traybarButton.destroy();
            traybarButton = null;
        }
    }

    public boolean hasUploadingFiles() {
        return dialog.hasUnuploadedFiles();
    }

    public void hide() {
        dialog.hide();
    }

    public void resetPermittedExtensions() {
        final String[] extensions = {};
        setPermittedExtensions(extensions);
    }

    public void setPermittedExtensions(final String extensions) {
        final Object[] objs = TextUtils.splitTags(extensions).toArray();
        final String[] exts = new String[objs.length];
        for (int i = 0; i < objs.length; i++) {
            exts[i] = (String) objs[i];
        }
        setPermittedExtensions(exts);
    }

    public void setUploadParams(final String userhash, final String currentStateToken) {
        dialog.setBaseParams(genUploadParams(userhash, currentStateToken));
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
        dialog = new UploadDialog(i18n.t("File uploader"), false, true);
        dialog.setId(SITE_FILE_UPLOADER);
        dialog.setAllowCloseOnUpload(true);
        dialog.setResetOnHide(false);
        dialog.setClosable(true);
        dialog.setCloseAction(Window.HIDE);
        dialog.setMinimizable(true);
        dialog.setUploadAutostart(false);

        dialog.addListener(new UploadDialogListenerAdapter() {
            @Override
            public boolean onBeforeAdd(final UploadDialog source, final String filename) {
                boolean mustAdd = presenter.checkFolderChange();
                if (!mustAdd) {
                    Site.important(i18n.t("Wait until current uploads finish to upload files in other location"));
                }
                return mustAdd;
            }

            @Override
            public void onMinimize(final Window source) {
                dialog.hide();
            }

            @Override
            public void onUploadComplete(final UploadDialog source) {
                if (!dialog.isVisible()) {
                    Site.info(i18n.t("Upload completed"));
                    traybarButton.destroy();
                    traybarButton = null;
                }
                presenter.onUploadComplete();
            }

            @Override
            public void onUploadError(final UploadDialog source, final String filename, final JavaScriptObject data) {
                showError();
            }

            @Override
            public void onUploadFailed(final UploadDialog source, final String filename) {
                showError();
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

    private void setPermittedExtensions(final String[] extensions) {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                if (dialog == null) {
                    createDialog(false);
                }
                Log.info("PermittedExtensions: " + extensions.toString() + " length: " + extensions.length);
                dialog.setPermittedExtensions(extensions);
            }
        });
    }
}
