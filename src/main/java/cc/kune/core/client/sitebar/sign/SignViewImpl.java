package cc.kune.core.client.sitebar.sign;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel;

public class SignViewImpl extends Composite {

    interface SignViewImplUiBinder extends UiBinder<Widget, SignViewImpl> {
    }

    private static SignViewImplUiBinder uiBinder = GWT.create(SignViewImplUiBinder.class);
    @UiField SimplePanel signInPanel;

    public SignViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

}
