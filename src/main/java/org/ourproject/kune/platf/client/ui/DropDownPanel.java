package org.ourproject.kune.platf.client.ui;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
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
 * TODO: pagination (using DeckPanel)
 * 
 */
public class DropDownPanel extends Composite implements ClickListener {
    private VerticalPanel dropDownPanelVP;
    private HorizontalPanel titleHP;
    private Label titleLabel;
    private DeckPanel contentDeckP;
    private SimplePanel cleanPanel;
    private Images img;
    private Image arrowImage;
    private BorderDecorator outerBorder;

    public DropDownPanel() {
	initialize();
	layout();
	setProperties();
	this.setContentVisible(false);
	arrowImage.addClickListener(this);
	titleLabel.addClickListener(this);
    }

    public DropDownPanel(final boolean visible) {
	this();
	this.setContentVisible(visible);
    }

    public DropDownPanel(final String headerText, final boolean visible) {
	this();
	this.setContentVisible(visible);
	this.setHeaderText(headerText);
    }

    public boolean contentEmpty() {
	return (contentDeckP.getWidgetCount() == 1);
    }

    public void setContent(final Widget widget) {
	if (!contentEmpty()) {
	    contentDeckP.remove(1);
	}
	contentDeckP.add(widget);
	widget.setWidth("100%");
	// refresh panel
	setContentVisible(isContentVisible());
    }

    public void setContentVisible(final boolean visible) {
	if (visible) {
	    img.arrowDownWhite().applyTo(arrowImage);
	    contentDeckP.setVisible(true);
	    if (!contentEmpty()) {
		contentDeckP.showWidget(1);
	    }
	} else {
	    img.arrowRightWhite().applyTo(arrowImage);
	    contentDeckP.setVisible(false);
	    contentDeckP.showWidget(0);
	}
    }

    public boolean isContentVisible() {
	return (contentDeckP.isVisible());
    }

    public void setHeaderText(final String text) {
	titleLabel.setText(text);
    }

    public void setHeaderTitle(final String title) {
	titleLabel.setTitle(title);
	arrowImage.setTitle(title);
    }

    public void onClick(final Widget sender) {
	if ((sender == titleHP) | (sender == arrowImage) | (sender == titleLabel)) {
	    setContentVisible(!isVisible());
	}
    }

    public void setBorderColor(final String color) {
	outerBorder.setColor(color);
	DOM.setStyleAttribute(arrowImage.getElement(), "backgroundColor", color);
	DOM.setStyleAttribute(dropDownPanelVP.getElement(), "backgroundColor", color);
	DOM.setStyleAttribute(titleLabel.getElement(), "backgroundColor", color);
    }

    public void setBackgroundColor(final String color) {
	DOM.setStyleAttribute(contentDeckP.getElement(), "backgroundColor", color);
    }

    private void initialize() {
	dropDownPanelVP = new VerticalPanel();
	outerBorder = new BorderDecorator(dropDownPanelVP, BorderDecorator.ALL);
	titleHP = new HorizontalPanel();
	arrowImage = new Image();
	titleLabel = new Label();
	contentDeckP = new DeckPanel();
	cleanPanel = new SimplePanel();
    }

    private void layout() {
	initWidget(outerBorder);
	dropDownPanelVP.add(titleHP);
	dropDownPanelVP.add(contentDeckP);
	titleHP.add(arrowImage);
	titleHP.add(titleLabel);
	contentDeckP.add(cleanPanel);
    }

    private void setProperties() {
	outerBorder.setCornerStyleName("kune-DropDownOuter");

	dropDownPanelVP.addStyleName("kune-DropDownOuter");
	dropDownPanelVP.setWidth("100%");
	dropDownPanelVP.setCellWidth(contentDeckP, "100%");
	dropDownPanelVP.setCellWidth(titleHP, "100%");
	cleanPanel.setWidth("100%");

	titleHP.addStyleName("kune-DropDownLabel");

	img = Images.App.getInstance();
	img.arrowDownWhite().applyTo(arrowImage);

	titleLabel.setText(Kune.getInstance().t.Text());

	contentDeckP.addStyleName("kune-DropDownInner");
    }
}
