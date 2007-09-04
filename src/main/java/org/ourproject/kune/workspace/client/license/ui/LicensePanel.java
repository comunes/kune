package org.ourproject.kune.workspace.client.license.ui;

import org.ourproject.kune.workspace.client.license.LicensePresenter;
import org.ourproject.kune.workspace.client.license.LicenseView;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LicensePanel extends HorizontalPanel implements LicenseView {
    private Label name;
    private Image image;

    public LicensePanel(final LicensePresenter presenter) {
	name = new Label();
	image = new Image();
	add(name);
	add(image);
	ClickListener clickListener = new ClickListener() {
	    public void onClick(Widget arg0) {
		presenter.onLicenseClick();
	    }
	};
	name.addClickListener(clickListener);
	image.addClickListener(clickListener);
    }

    public void showImage(String imageUrl) {
	image.setUrl(imageUrl);
    }

    public void showName(String longName) {
	name.setText(longName);
    }
}
