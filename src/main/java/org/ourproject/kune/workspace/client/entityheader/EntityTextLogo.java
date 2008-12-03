package org.ourproject.kune.workspace.client.entityheader;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EntityTextLogo extends VerticalPanel {

    public static final String LOGO_NAME = "k-elogop-ln";
    public static final String LOGO_IMAGE = "k-elogop-image";

    private static final String _100 = "100%";
    private static final String LOGO_MEDIUM_FONT_STYLE = "k-elogo-l-m";
    private static final String LOGO_SMALL_FONT_STYLE = "k-elogo-l-s";
    private static final String LOGO_LARGE_FONT_STYLE = "k-elogo-l-l";
    private final Label logoLabel;
    private final Image logoImage;

    public EntityTextLogo() {
        // Initialize
        super();
        HorizontalPanel generalHP = new HorizontalPanel();
        VerticalPanel logoTextVP = new VerticalPanel();
        logoImage = new Image();
        logoLabel = new Label();
        final Label expandCell = new Label("");

        logoImage.ensureDebugId(LOGO_IMAGE);
        logoLabel.ensureDebugId(LOGO_NAME);

        // Layout
        add(generalHP);
        generalHP.add(logoImage);
        generalHP.add(logoTextVP);
        logoTextVP.add(logoLabel);

        // Set properties

        expandCell.setStyleName("k-elogop-expand");
        generalHP.setWidth(_100);
        generalHP.setHeight(_100);
        generalHP.setCellWidth(logoTextVP, _100);
        generalHP.setCellHeight(logoTextVP, _100);
        logoTextVP.setWidth(_100);
        logoTextVP.setCellWidth(logoLabel, _100);
        super.setVerticalAlignment(ALIGN_MIDDLE);
        logoTextVP.setVerticalAlignment(ALIGN_MIDDLE);
        generalHP.setVerticalAlignment(ALIGN_MIDDLE);
        setStylePrimaryName("k-entitytextlogo");
        addStyleName("k-entitytextlogo-no-border");
        logoImage.setVisible(false);
        setLogoText("");
    }

    public void setLargeFont() {
        resetFontSize();
        logoLabel.addStyleName(LOGO_LARGE_FONT_STYLE);
    }

    public void setLogoImage(AbstractImagePrototype imageProto) {
        imageProto.applyTo(logoImage);
    }

    public void setLogoImage(final String url) {
        Image.prefetch(url);
        logoImage.setUrl(url);
    }

    public void setLogoText(final String text) {
        logoLabel.setText(text);
    }

    public void setLogoVisible(final boolean visible) {
        logoImage.setVisible(visible);
    }

    public void setMediumFont() {
        resetFontSize();
        logoLabel.addStyleName(LOGO_MEDIUM_FONT_STYLE);
    }

    public void setSmallFont() {
        resetFontSize();
        logoLabel.addStyleName(LOGO_SMALL_FONT_STYLE);
    }

    private void resetFontSize() {
        logoLabel.removeStyleName(LOGO_LARGE_FONT_STYLE);
        logoLabel.removeStyleName(LOGO_SMALL_FONT_STYLE);
        logoLabel.removeStyleName(LOGO_MEDIUM_FONT_STYLE);
    }
}