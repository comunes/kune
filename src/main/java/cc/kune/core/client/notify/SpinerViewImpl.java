package cc.kune.core.client.notify;

import cc.kune.core.client.notify.SpinerPresenter.SpinerView;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class SpinerViewImpl extends ViewImpl implements SpinerView {

    private final Label label;

    public SpinerViewImpl() {
        label = new Label();
    }

    @Override
    public Widget asWidget() {
        return label;
    }

    @Override
    public void fade() {
        // TODO Auto-generated method stub

    }

    @Override
    public void show(final String message) {
        label.setText(message);
    }

    @Override
    public void showLoading() {
        label.setText("FIXME");
    }

}
