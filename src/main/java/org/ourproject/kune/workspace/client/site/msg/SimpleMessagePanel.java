package org.ourproject.kune.workspace.client.site.msg;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SimpleMessagePanel extends HorizontalPanel implements SiteMessageView {
    HTML message = null;
    Image messageIcon = null;

    public SimpleMessagePanel() {
        message = new HTML();
        messageIcon = new Image();
        final Images images = Images.App.getInstance();
        add(messageIcon);
        add(message);
        setCellVerticalAlignment(messageIcon, VerticalPanel.ALIGN_MIDDLE);
        setVisible(false);
        setStyleName("kune-SiteMessagePanel");
        addStyleDependentName("info");
        images.info().applyTo(messageIcon);
        messageIcon.addStyleName("gwt-Image");
        message.setWidth("100%");
        this.setCellWidth(message, "100%");
    }

    public void adjustWidth(final int windowWidth) {
        final int messageWidth = windowWidth * 60 / 100 - 3;
        this.setWidth("" + messageWidth);
    }

    public void hide() {
        message.setText("");
        this.setVisible(false);
    }

    public void reset() {
        message.setText("");
    }

    public void setMessage(final String text) {
        this.message.setHTML(text);
    }

    public void setMessage(final String text, final SiteErrorType lastMessageType, final SiteErrorType type) {
        final Images images = Images.App.getInstance();
        AbstractImagePrototype imagePrototype = null;
        switch (type) {
        case error:
            imagePrototype = images.error();
            break;
        case veryimp:
            imagePrototype = images.important();
            break;
        case imp:
            imagePrototype = images.emblemImportant();
            break;
        case info:
            imagePrototype = images.info();
            break;
        }
        imagePrototype.applyTo(messageIcon);
        removeStyleDependentName(lastMessageType.toString());
        addStyleDependentName(type.toString());
        setMessage(text);
    }

    public void show() {
        this.setVisible(true);
    }
}
