
package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.workspace.ContentSubTitlePresenter;
import org.ourproject.kune.workspace.client.workspace.ContentSubTitleView;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ContentSubTitlePanel extends HorizontalPanel implements ContentSubTitleView {
    private final Label subTitleLeftLabel;
    private final Label subTitleRightLabel;

    public ContentSubTitlePanel(final ContentSubTitlePresenter presenter) {

        subTitleLeftLabel = new Label();
        HorizontalPanel rigthHP = new HorizontalPanel();
        subTitleRightLabel = new Label();

        add(subTitleLeftLabel);
        add(rigthHP);
        rigthHP.add(subTitleRightLabel);

        setWidth("100%");
        subTitleRightLabel.setText(Kune.I18N.t("Language:"));
        subTitleLeftLabel.addStyleName("kune-Margin-Large-l");
        subTitleLeftLabel.addStyleName("kune-ft15px");
        subTitleLeftLabel.addStyleName("kune-ContentSubTitleBar-l");
        subTitleRightLabel.addStyleName("kune-Margin-Large-r");
        subTitleRightLabel.addStyleName("kune-ft12px");
        rigthHP.addStyleName("kune-ContentSubTitleBar-r");
        setCellVerticalAlignment(rigthHP, VerticalPanel.ALIGN_MIDDLE);
        rigthHP.setCellVerticalAlignment(subTitleLeftLabel, VerticalPanel.ALIGN_MIDDLE);
        rigthHP.setCellVerticalAlignment(subTitleRightLabel, VerticalPanel.ALIGN_MIDDLE);
    }

    public void setContentSubTitleLeft(final String subTitle) {
        subTitleLeftLabel.setText(subTitle);
    }

    public void setContentSubTitleLeftVisible(final boolean visible) {
        subTitleLeftLabel.setVisible(visible);
    }

    public void setContentSubTitleRight(final String subTitle) {
        subTitleRightLabel.setText(subTitle);

    }

    public void setContentSubTitleRightVisible(final boolean visible) {
        subTitleRightLabel.setVisible(visible);
    }

    public void setColors(final String background, final String textColor) {
        DOM.setStyleAttribute(this.getElement(), "backgroundColor", background);
        DOM.setStyleAttribute(subTitleLeftLabel.getElement(), "color", textColor);
        DOM.setStyleAttribute(subTitleRightLabel.getElement(), "color", textColor);
    }

}
