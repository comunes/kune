package org.ourproject.kune.platf.client.workspace.ui;

import org.gwm.client.impl.DefaultGFrame;
import org.ourproject.kune.platf.client.services.Images;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

class SiteMessagePanel extends VerticalPanel implements SiteMessageView {

    static final Images IMG = Images.App.getInstance();

    Label message = null;
    Image icon = null;
    private final PushButton closeLink;
    private final SiteMessagePresenter presenter;

    public SiteMessagePanel(final SiteMessagePresenter sitePresenter) {
        this.presenter = sitePresenter;
        HorizontalPanel messageHP = new HorizontalPanel();
        message = new Label();
        icon = new Image();
        HorizontalPanel closeHP = new HorizontalPanel();
        Label expandCell = new Label("");
        closeLink = new PushButton(IMG.cross().createImage(), IMG.crossDark().createImage());
        closeLink.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                if (sender == closeLink) {
                    setVisible(false);
                    presenter.onClose();
                }
            }
        });

        // Layout
        // this.add(new BorderPanel(messageHP, 5, 0, 0, 0));
        add(messageHP);
        add(closeHP);
        closeHP.add(expandCell);
        closeHP.add(closeLink);
        // messageHP.add(new BorderPanel(icon, 0, 10, 0, 5));
        messageHP.add(icon);
        messageHP.add(message);

        // Set properties

        setVisible(false);
        // this.setHeight("33");
        addStyleName("kune-SiteMessagePanel");
        message.setHeight("27");
        IMG.info().applyTo(icon);
        closeHP.setWidth("100%");
        expandCell.setWidth("100%");
        closeHP.setCellWidth(expandCell, "100%");
    }

    public void setMessage(final String text, final AbstractImagePrototype type, final String oldStyle,
            final String newStyle) {
        type.applyTo(icon);
        removeStyleName(oldStyle);
        addStyleName(newStyle);
        setMessage(text);
    }

    public void setMessage(final String text) {
        this.message.setText(text);
        // Put on the top of all windows/popup
        DOM.setIntStyleAttribute(getElement(), "zIndex", DefaultGFrame.getLayerOfTheTopWindow() + 10);
    }

    public void adjustWidth(final int windowWidth) {
        int messageWidth = windowWidth * 60 / 100 - 3;
        this.setWidth("" + messageWidth);
        message.setWidth("" + (messageWidth - 16 - 40));
    }

    public void hide() {
        this.setVisible(false);
    }

    public void show() {
        this.setVisible(true);
    }

    public void reset() {
        message.setText("");
    }
}
