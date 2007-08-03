package org.ourproject.kune.platf.client.workspace.ui;

import org.gwm.client.impl.DefaultGFrame;
import org.ourproject.kune.platf.client.services.Images;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

class SiteMessagePanel extends VerticalPanel implements SiteMessageView {

    static final Images IMG = Images.App.getInstance();

    static final String COLORINFO = "#E5FF80";
    static final String COLORIMP = "#FFE6D5";
    static final String COLORVERYIMP = "#FFD4AA";
    static final String COLORERROR = "#FFB380";

    private String currentColor = COLORINFO; // Initial CSS value

    Label message = null;
    Image icon = null;
    private PushButton closeLink;
    private SiteMessagePresenter presenter;

    public SiteMessagePanel(final SiteMessagePresenter sitePresenter) {
        this.presenter = sitePresenter;
        HorizontalPanel messageHP = new HorizontalPanel();
        message = new Label();
        icon = new Image();
        HorizontalPanel closeHP = new HorizontalPanel();
        Label expandCell = new Label("");
        closeLink = new PushButton(IMG.cross().createImage(), IMG.crossDark().createImage());
        closeLink.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
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

        // //FIXME this.setVisible(false);
        this.message.setText("lalalala");
        this.adjustWidth(600);
        // this.setHeight("33");
        setStyleName("kune-SiteMessagePanel");
        message.setHeight("27");
        IMG.info().applyTo(icon);
        closeHP.setWidth("100%");
        expandCell.setWidth("100%");
        closeHP.setCellWidth(expandCell, "100%");
    }

    void setMessage(String text) {
        // FIXME: This mix different message levels:
        this.message.setText(text);
        // Put on the top of all windows/popup
        DOM.setIntStyleAttribute(getElement(), "zIndex", DefaultGFrame.getLayerOfTheTopWindow() + 10);
        this.setVisible(true);
    }

    private void setBackgroundColor(String color) {
        if (currentColor != color) {
            DOM.setStyleAttribute(getElement(), "backgroundColor", color);
            DOM.setStyleAttribute(message.getElement(), "backgroundColor", color);
            DOM.setStyleAttribute(closeLink.getElement(), "backgroundColor", color);
            currentColor = color;
        }
    }

    public void setMessageError(String message) {
        setBackgroundColor(COLORERROR);
        IMG.error().applyTo(icon);
        this.setMessage(message);
    }

    public void setMessageVeryImp(String message) {
        setBackgroundColor(COLORVERYIMP);
        IMG.important().applyTo(icon);
        this.setMessage(message);
    }

    public void setMessageImp(String message) {
        setBackgroundColor(COLORIMP);
        IMG.emblemImportant().applyTo(icon);
        this.setMessage(message);
    }

    public void setMessageInfo(String message) {
        setBackgroundColor(COLORINFO);
        IMG.info().applyTo(icon);
        this.setMessage(message);
    }

    public void adjustWidth(int windowWidth) {
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
