
package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.platf.client.ui.EditableIconLabel;
import org.ourproject.kune.platf.client.ui.EditableClickListener;
import org.ourproject.kune.workspace.client.workspace.ContentTitlePresenter;
import org.ourproject.kune.workspace.client.workspace.ContentTitleView;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ContentTitlePanel extends HorizontalPanel implements ContentTitleView {
    private final EditableIconLabel titleLabel;
    private final Label dateLabel;

    public ContentTitlePanel(final ContentTitlePresenter presenter) {
        titleLabel = new EditableIconLabel(new EditableClickListener() {
            public void onEdited(final String text) {
                presenter.onTitleRename(text);
            }
        });
        HorizontalPanel rigthHP = new HorizontalPanel();
        dateLabel = new Label();

        add(titleLabel);
        add(rigthHP);
        rigthHP.add(dateLabel);

        setWidth("100%");
        titleLabel.addStyleName("kune-Margin-Large-l");
        titleLabel.addStyleName("kune-ft17px");
        dateLabel.addStyleName("kune-Margin-Large-r");
        dateLabel.addStyleName("kune-ft12px");
        titleLabel.addStyleName("kune-ContentTitleBar-l");
        rigthHP.addStyleName("kune-ContentTitleBar-r");
        setCellVerticalAlignment(titleLabel, VerticalPanel.ALIGN_MIDDLE);
        setCellVerticalAlignment(rigthHP, VerticalPanel.ALIGN_MIDDLE);
        rigthHP.setCellVerticalAlignment(dateLabel, VerticalPanel.ALIGN_MIDDLE);
    }

    public void setContentTitle(final String title) {
        titleLabel.setText(title);
    }

    public void setContentTitleEditable(final boolean editable) {
        titleLabel.setEditable(editable);
    }

    public void setContentDate(final String date) {
        dateLabel.setText(date);
    }

    public void setColors(final String background, final String textColor) {
        DOM.setStyleAttribute(this.getElement(), "backgroundColor", background);
        DOM.setStyleAttribute(titleLabel.getElement(), "color", textColor);
        DOM.setStyleAttribute(dateLabel.getElement(), "color", textColor);
    }

    public void setDateVisible(final boolean visible) {
        dateLabel.setVisible(visible);

    }

    public void restoreOldTitle() {
        titleLabel.restoreOldText();
    }
}
