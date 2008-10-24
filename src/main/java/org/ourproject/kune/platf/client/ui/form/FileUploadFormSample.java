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
 */package org.ourproject.kune.platf.client.ui.form;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FileUploadFormSample extends FormPanel {
    private static final String MUSIC_UPLOAD_ACTION = "/services/fileupload";

    public FileUploadFormSample() {
        setAction(MUSIC_UPLOAD_ACTION); // set the action, must match with
        // the servlet URL pattern
        setEncoding(FormPanel.ENCODING_MULTIPART);
        setMethod(FormPanel.METHOD_POST);

        VerticalPanel panel = new VerticalPanel();
        setWidget(panel);

        panel.add(new Label("Title"));
        TextBox name = new TextBox();
        panel.add(name);

        panel.add(new Label("File"));
        FileUpload upload = new FileUpload();
        upload.setName("someFile");
        panel.add(upload);

        Button submit = new Button("Submit");
        submit.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                submit(); // submit the form
            }
        });
        panel.add(submit);
    }
}
