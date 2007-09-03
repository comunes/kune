package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.BorderDecorator;

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
    private VerticalPanel dropDownPanelVP = null;
    private HorizontalPanel titleHP = null;
    private Label titleLabel = null;
    private DeckPanel contentDeckP = null;
    private SimplePanel cleanPanel = null;
    private Images img;
    private Image arrowImage;
    private BorderDecorator outerBorder;

    public DropDownPanel() {
	super();
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

    protected void initialize() {
	dropDownPanelVP = new VerticalPanel();
	outerBorder = new BorderDecorator(dropDownPanelVP, BorderDecorator.ALL);
	titleHP = new HorizontalPanel();
	arrowImage = new Image();
	titleLabel = new Label();
	contentDeckP = new DeckPanel();
	cleanPanel = new SimplePanel();
    }

    protected void layout() {
	initWidget(outerBorder);
	dropDownPanelVP.add(titleHP);
	dropDownPanelVP.add(contentDeckP);
	titleHP.add(arrowImage); // FIXME: , 0, 0, 0, 3));
	titleHP.add(titleLabel); // FIXME: 0, 0, 0, 3));
	contentDeckP.add(cleanPanel);
    }

    protected void setProperties() {
	outerBorder.setCornerStyleName("kune-DropDownOuter");

	dropDownPanelVP.setBorderWidth(0);
	dropDownPanelVP.setSpacing(0);
	dropDownPanelVP.addStyleName("kune-DropDownOuter");

	titleHP.setBorderWidth(0);
	titleHP.setSpacing(0);
	titleHP.addStyleName("kune-DropDownLabel");

	img = Images.App.getInstance();
	img.arrowDownBlack().applyTo(arrowImage);
	// arrowImage.setHeight("16");
	// arrowImage.setWidth("16");

	titleLabel.setText(Kune.getInstance().t.Text());
	titleLabel.setWidth("100%");

	contentDeckP.addStyleName("kune-DropDownInner");
	// contentDeckP.setWidth("100%");
    }

    public boolean contentEmpty() {
	return (contentDeckP.getWidgetCount() == 1);
    }

    public void setContent(final Widget widget) {
	if (!contentEmpty()) {
	    contentDeckP.remove(1);
	}
	contentDeckP.add(widget);
    }

    public void setContentVisible(final boolean visible) {
	if (visible) {
	    if (!contentEmpty()) {
		img.arrowDownBlack().applyTo(arrowImage);
		contentDeckP.showWidget(1);
		contentDeckP.setVisible(true);
	    }
	} else {
	    img.arrowRightBlack().applyTo(arrowImage);
	    contentDeckP.showWidget(0);
	    contentDeckP.setVisible(false);
	}
    }

    public boolean isVisible() {
	return (contentDeckP.getVisibleWidget() == 1);
    }

    public void setTitle(final String title) {
	titleLabel.setText(title);
    }

    public void onClick(final Widget sender) {
	if ((sender == titleHP) | (sender == arrowImage) | (sender == titleLabel)) {
	    setContentVisible(!isVisible());
	}
    }

    public void setColor(final String color) {
	outerBorder.setColor(color);
	DOM.setStyleAttribute(arrowImage.getElement(), "backgroundColor", color);
	DOM.setStyleAttribute(dropDownPanelVP.getElement(), "backgroundColor", color);
	DOM.setStyleAttribute(titleLabel.getElement(), "backgroundColor", color);
    }
}
