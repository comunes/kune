package org.ourproject.kune.workspace.client.license.ui;

import org.ourproject.kune.workspace.client.license.LicensePresenter;
import org.ourproject.kune.workspace.client.license.LicenseView;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LicensePanel extends HorizontalPanel implements LicenseView {
    private final Label copyright;
    private final Image image;

    public LicensePanel(final LicensePresenter presenter) {
	copyright = new Label();
	image = new Image();
	this.add(copyright);
	this.add(image);
	ClickListener clickListener = new ClickListener() {
	    public void onClick(Widget arg0) {
		presenter.onLicenseClick();
	    }
	};
	copyright.addClickListener(clickListener);
	image.addClickListener(clickListener);
    }

    public void showImage(final String imageUrl) {
	image.setUrl(imageUrl);
    }

    public void showName(final String groupName, final String licenseName) {
	copyright.setText("© " + groupName + ", under license " + licenseName);
    }
}
