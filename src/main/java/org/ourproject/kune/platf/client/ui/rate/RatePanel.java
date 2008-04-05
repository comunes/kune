package org.ourproject.kune.platf.client.ui.rate;

import org.ourproject.kune.platf.client.services.Kune;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class RatePanel extends Composite implements RateView {
    private Grid rateGrid;
    private Image[] starImg;
    private Label rateDesc;

    public RatePanel(final Double rate, final Integer byUsers) {
        initialize();
        layout();
        setProperties();
        if (rate != null) {
            setRate(rate);
        }
        if (byUsers != null) {
            setByUsers(byUsers);
        }
    }

    public void setByUsers(final Integer byUsers) {
        if (byUsers.intValue() == 0) {
            rateDesc.setText(Kune.I18N.t("(Not rated)"));
        } else if (byUsers.intValue() == 1) {
            // i18n params pluralization
            rateDesc.setText(Kune.I18N.t("([%d] user)", byUsers));
        } else {
            rateDesc.setText(Kune.I18N.t("([%d] users)", byUsers));
        }
    }

    public void setRate(final Double rate) {
        setRate(Star.genStars(rate.doubleValue()));
    }

    private void initialize() {
        rateGrid = new Grid(1, 6);
        starImg = new Image[5];
        for (int i = 0; i < 5; i++) {
            starImg[i] = new Image();
            new Star(Star.GREY).getImage().applyTo(starImg[i]);
        }
        rateDesc = new Label();
    }

    private void layout() {
        initWidget(rateGrid);
        for (int i = 0; i < 5; i++) {
            rateGrid.setWidget(0, i, starImg[i]);
        }
        rateGrid.setWidget(0, 5, rateDesc);
    }

    private void setProperties() {
        rateGrid.setCellPadding(0);
        rateGrid.setCellSpacing(0);
        rateGrid.setBorderWidth(0);
        rateGrid.addStyleName("kune-RatePanel-Stars");
        rateDesc.addStyleName("kune-RatePanel-Label");
        rateDesc.addStyleName("kune-Margin-Medium-lr");
    }

    private void setRate(final Star stars[]) {
        for (int i = 0; i < 5; i++) {
            stars[i].getImage().applyTo(starImg[i]);
        }
    }

}
