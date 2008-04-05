
package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.platf.client.services.Kune;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

class GroupLogoPanel extends SimplePanel {
    private EntityTextLogo entityTextLogo;

    public GroupLogoPanel() {
    }

    private EntityTextLogo getEntityTextLogo() {
        if (entityTextLogo == null) {
            this.entityTextLogo = new EntityTextLogo();
        }
        return entityTextLogo;
    }

    public void setLogo(final String groupName) {
        clear();
        add(getEntityTextLogo());
        entityTextLogo.setDefaultText(groupName);
    }

    public void setLogo(final Image image) {
        clear();
        add(image);
    }

    public void setTextColor(final String color) {
        this.entityTextLogo.setTextColor(color);
    }

    class EntityTextLogo extends VerticalPanel {

        private static final int GROUP_NAME_LIMIT_SIZE = 90;
        private static final String LOGO_SMALL_FONT_SIZE = "108%";
        private static final String LOGO_DEFAULT_FONT_SIZE = "167%";
        private Label defTextLogoLabel = null;
        private Hyperlink defTextPutYourLogoHL = null;
        private final HorizontalPanel putYourLogoHP;

        public EntityTextLogo() {
            // Initialize
            super();
            defTextLogoLabel = new Label();
            HTML expandCell = new HTML("<b></b>");
            putYourLogoHP = new HorizontalPanel();
            defTextPutYourLogoHL = new Hyperlink();

            // Layout
            add(defTextLogoLabel);
            add(putYourLogoHP);
            putYourLogoHP.add(expandCell);
            putYourLogoHP.add(defTextPutYourLogoHL);

            // Set properties
            // TODO: Put your logo here functionality
            defTextPutYourLogoHL.setText(Kune.I18N.t("Put Your Logo Here"));
            expandCell.setWidth("100%");
            putYourLogoHP.setCellWidth(expandCell, "100%");
            // TODO: link to configure the logo
            addStyleName("kune-EntityTextLogo");
            setDefaultText("");
        }

        public void setDefaultText(final String text) {
            if (text.length() > GROUP_NAME_LIMIT_SIZE) {
                DOM.setStyleAttribute(defTextLogoLabel.getElement(), "fontSize", LOGO_SMALL_FONT_SIZE);
            } else {
                DOM.setStyleAttribute(defTextLogoLabel.getElement(), "fontSize", LOGO_DEFAULT_FONT_SIZE);
            }
            defTextLogoLabel.setText(text);

        }

        public void setTextColor(final String color) {
            DOM.setStyleAttribute(defTextLogoLabel.getElement(), "color", color);
        }

        public void setPutYourLogoVisible(final boolean visible) {
            putYourLogoHP.setVisible(visible);

        }
    }

    public void setPutYourLogoVisible(final boolean visible) {
        entityTextLogo.setPutYourLogoVisible(visible);

    }

}
