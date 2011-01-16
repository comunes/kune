package cc.kune.core.client.ui.dialogs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasDirectionalText;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BasicDialog extends Composite implements BasicDialogView {

    private static BasicDialogUiBinder uiBinder = GWT.create(BasicDialogUiBinder.class);
    @UiField
    InlineLabel title;
    @UiField
    Button firstBtn;
    @UiField
    Button secondBtn;
    @UiField
    VerticalPanel vp;

    interface BasicDialogUiBinder extends UiBinder<Widget, BasicDialog> {
    }

    public BasicDialog() {
        initWidget(uiBinder.createAndBindUi(this));
        title.ensureDebugId("k-ucvi-title");
        firstBtn.ensureDebugId("k-ucvi-accept-btn");
        secondBtn.ensureDebugId("k-ucvi-cancel-btn");
    }

    public void setTitleId(String id) {
        title.ensureDebugId(id);
    }

    public void setFirstBtnId(String id) {
        firstBtn.ensureDebugId(id);
    }

    public void setSecondBtnId(String id) {
        secondBtn.ensureDebugId(id);
    }

    @Override
    public HasDirectionalText getTitleText() {
        return title;
    }

    @Override
    public HasClickHandlers getSecondBtn() {
        return secondBtn;
    }

    @Override
    public HasClickHandlers getFirstBtn() {
        return firstBtn;
    }

    @Override
    public ForIsWidget getInnerPanel() {
        return vp;
    }

    @Override
    public HasText getFirstBtnText() {
        return firstBtn;
    }

    @Override
    public HasText getSecondBtnText() {
        return secondBtn;
    }
}
