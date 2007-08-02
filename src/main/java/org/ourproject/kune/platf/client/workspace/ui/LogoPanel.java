package org.ourproject.kune.platf.client.workspace.ui;

import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

class LogoPanel extends SimplePanel {
    private EntityTextLogo entityTextLogo;

    public LogoPanel() {
    }

    private EntityTextLogo getEntityTextLogo() {
        if (entityTextLogo == null)
            this.entityTextLogo = new EntityTextLogo();
        return entityTextLogo;
    }

    public void setLogo(String groupName) {
        clear();
        add(getEntityTextLogo());
        entityTextLogo.setDefaultText(groupName);
    }

    public void setLogo(Image image) {
        clear();
        add(image);
    }

    class EntityTextLogo extends VerticalPanel {

        private Label defTextLogoLabel = null;
        private Hyperlink defTextPutYourLogoHL = null;

        public EntityTextLogo() {
            // Initialize
            super();
            defTextLogoLabel = new Label();
            defTextPutYourLogoHL = new Hyperlink();

            // Layout
            add(defTextLogoLabel);
            add(defTextPutYourLogoHL);

            // Set properties
//            defTextPutYourLogoHL.setText(Kune.getInstance().t.PutYourLogoHere());
            defTextPutYourLogoHL.setText("mirar LogoPanel.java");

            // TODO: link to configure the logo
            addStyleName("kune-EntityTextLogo");
            setDefaultText("");
        }

        public void setDefaultText(String title) {
            defTextLogoLabel.setText(title);
        }
    }

}
