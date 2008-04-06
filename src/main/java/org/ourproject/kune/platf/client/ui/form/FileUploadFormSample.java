package org.ourproject.kune.platf.client.ui.form;

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
