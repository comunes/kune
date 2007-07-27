package org.ourproject.kune.platf.client.workspace;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WorkspacePanel extends Composite implements WorkspaceView {

    public WorkspacePanel() {
        VerticalPanel panel = new VerticalPanel();
        initWidget(panel);
    }

    public void addTab(String name) {
    }


    /**
     *
     * This is the class for the logo of a group or a user. By default, we use a Label instead of image until the user configure the logo.
     *
     */
    class EntityLogo extends Composite {
        public final static String DEFFONTSIZE = "189%";
        public final static String MEDFONTSIZE = "152%";
        public final static String MINFONTSIZE = "122%";

        public final static int DEFLOGOWIDTH = 468;
        public final static int DEFLOGOHEIGHT = 60;

        private VerticalPanel defTextLogoVP = null;
        private Label defTextLogoLabel = null;
        private Hyperlink defTextPutYourLogoHL = null;
        private Image logoImage = null;

        public EntityLogo() {
            this("");
            this.addStyleName("kune-EntityLogo");
        }

        public EntityLogo(String title) {
            // Initialize
            defTextLogoVP = new VerticalPanel();
            defTextLogoLabel = new Label();
            defTextPutYourLogoHL = new Hyperlink();
            logoImage = new Image();
            initWidget(defTextLogoVP);

            // Layout
            defTextLogoVP.add(defTextLogoLabel);
            defTextLogoVP.add(defTextPutYourLogoHL);

            // Set properties
            defTextLogoVP.setHeight(""+ DEFLOGOHEIGHT);
            defTextLogoVP.setWidth("" + DEFLOGOWIDTH);
            defTextLogoVP.setBorderWidth(0);
            defTextLogoVP.setCellHorizontalAlignment(defTextPutYourLogoHL, HasHorizontalAlignment.ALIGN_RIGHT);
            defTextLogoVP.setSpacing(0);
            defTextPutYourLogoHL.setText("");

            // TODO: link to configure the logo
            defTextLogoVP.addStyleName("def-logo-panel");
            defTextLogoLabel.addStyleName("def-logo-text");
            defTextPutYourLogoHL.addStyleName("put-you-logo");
            setDefaultText(title);
        }

        public void setDefaultText(String title) {
            defTextLogoLabel.setText(title);
        }

        public void setLogo(String url) {
            logoImage.setUrl(url);
            // TODO: limit size
            initWidget(logoImage);
        }
    }
}
