
package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.platf.client.ui.CustomPushButton;
import org.ourproject.kune.platf.client.ui.rate.RateItPanel;
import org.ourproject.kune.platf.client.ui.rate.RateItPresenter;
import org.ourproject.kune.platf.client.ui.rate.RatePanel;
import org.ourproject.kune.workspace.client.workspace.ContentBottomToolBarPresenter;
import org.ourproject.kune.workspace.client.workspace.ContentBottomToolBarView;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ContentBottomToolBarPanel extends HorizontalPanel implements ContentBottomToolBarView {

    private final RatePanel rate;
    private final RateItPresenter rateItPresenter;
    private final RateItPanel rateIt;
    private CustomPushButton btn;

    public ContentBottomToolBarPanel(final ContentBottomToolBarPresenter presenter) {
        rate = new RatePanel(null, null);
        rateItPresenter = new RateItPresenter();
        rateIt = new RateItPanel(rateItPresenter);
        rateItPresenter.init(rateIt);
        Label expand = new Label("");
        this.add(rateIt);
        this.add(expand);
        this.add(rate);
        this.setWidth("100%");
        expand.setWidth("100%");
        this.setCellWidth(expand, "100%");
        this.addStyleName("kune-ContentToolBarPanel");
        this.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        this.setCellVerticalAlignment(rate, VerticalPanel.ALIGN_MIDDLE);
        this.setCellVerticalAlignment(rateIt, VerticalPanel.ALIGN_MIDDLE);
        rate.setVisible(false);
        rateIt.setVisible(false);
        // TODO setEnabled false to RateIt
    }

    public void setRate(final Double value, final Integer rateByUsers) {
        rate.setRate(value);
        rate.setByUsers(rateByUsers);
    }

    public void setRateIt(final Double currentUserRate) {
        rateItPresenter.setRate(currentUserRate);
    }

    public void setRateVisible(final boolean visible) {
        rate.setVisible(visible);
    }

    public void setRateItVisible(final boolean visible) {
        rateIt.setVisible(visible);
    }

    public void addButton(final String caption, final ClickListener listener) {
        btn = new CustomPushButton(caption, listener);
        this.insert(btn, 0);
        btn.addStyleName("kune-Button-Large-lrSpace");
    }

    public void setButtonVisible(final boolean isEnabled) {
        btn.setVisible(isEnabled);
    }

}
