package org.ourproject.kune.platf.client.ui;

import org.ourproject.kune.platf.client.services.Images;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * <p>
 * This panel opens when you click on the arrow or the title (gmail style)
 * </p>
 * 
 */
public class DropDownPanel extends Composite implements ClickListener, HasColor {
    private VerticalPanel vp;
    private HorizontalPanel titleHP;
    private Label titleLabel;
    private SimplePanel contentPanel;
    private Images img;
    private Image arrowImage;
    private RoundedBorderDecorator outerBorder;

    public DropDownPanel() {
        initialize();
        layout();
        setProperties();
        setContentVisible(false);
        arrowImage.addClickListener(this);
        titleLabel.addClickListener(this);
    }

    public DropDownPanel(final boolean visible) {
        this();
        setContentVisible(visible);
    }

    public DropDownPanel(final String headerText, final boolean visible) {
        this();
        setContentVisible(visible);
        setHeaderText(headerText);
    }

    public void setContent(final Widget widget) {
        contentPanel.setWidget(widget);
        // refresh panel
        setContentVisible(isContentVisible());
    }

    public void setContentVisible(final boolean visible) {
        if (visible) {
            img.arrowDownWhite().applyTo(arrowImage);
            contentPanel.setVisible(true);

        } else {
            img.arrowRightWhite().applyTo(arrowImage);
            contentPanel.setVisible(false);
        }
    }

    public boolean isContentVisible() {
        return contentPanel.isVisible();
    }

    public void setHeaderText(final String text) {
        titleLabel.setText(text);
    }

    public void setHeaderTitle(final String title) {
        // QuickTips size problems with images
        // KuneUiUtils.setQuickTip(arrowImage, title);
        KuneUiUtils.setQuickTip(titleLabel, title);
    }

    public void onClick(final Widget sender) {
        if (sender == titleHP | sender == arrowImage | sender == titleLabel) {
            setContentVisible(!isContentVisible());
        }
    }

    public void setColor(final String color) {
        outerBorder.setColor(color);
        DOM.setStyleAttribute(arrowImage.getElement(), "backgroundColor", color);
        DOM.setStyleAttribute(vp.getElement(), "backgroundColor", color);
        DOM.setStyleAttribute(titleLabel.getElement(), "backgroundColor", color);
    }

    public void setBackgroundColor(final String color) {
        DOM.setStyleAttribute(contentPanel.getElement(), "backgroundColor", color);
    }

    public String getColor() {
        return outerBorder.getColor();
    }

    public void setWidth(final String width) {
        super.setWidth(width);
        outerBorder.setWidth(width);
    }

    public void setHeight(final String height) {
        super.setHeight(height);
        outerBorder.setHeight(height);
    }

    private void initialize() {
        vp = new VerticalPanel();
        outerBorder = new RoundedBorderDecorator(vp, RoundedBorderDecorator.ALL);
        titleHP = new HorizontalPanel();
        arrowImage = new Image();
        titleLabel = new Label();
        contentPanel = new SimplePanel();
    }

    private void layout() {
        initWidget(outerBorder);
        vp.add(titleHP);
        vp.add(contentPanel);
        titleHP.add(arrowImage);
        titleHP.add(titleLabel);
    }

    private void setProperties() {
        outerBorder.setCornerStyleName("kune-DropDownOuter");

        vp.addStyleName("kune-DropDownOuter");
        vp.setWidth("100%");
        vp.setCellWidth(contentPanel, "100%");
        vp.setCellWidth(titleHP, "100%");

        titleHP.addStyleName("kune-DropDownLabel");

        img = Images.App.getInstance();
        img.arrowDownWhite().applyTo(arrowImage);

        titleLabel.setText("");

        contentPanel.addStyleName("kune-DropDownInner");
    }
}
