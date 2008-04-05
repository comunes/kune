
package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateDTO;

public class ContentBottomToolBarPresenter implements ContentBottomToolBarComponent {

    private ContentBottomToolBarView view;

    public void init(final ContentBottomToolBarView view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setRate(final StateDTO state, boolean logged) {
        if (state.isRateable()) {
            if (!logged) {
                setRate(state.getRate(), state.getRateByUsers());
            } else {
                setRate(state.getRate(), state.getRateByUsers(), state.getCurrentUserRate());
            }
        } else {
            setRateVisible(false);
            setRateItVisible(false);
        }
    }

    private void setRate(final Double value, final Integer rateByUsers, final Double currentUserRate) {
        view.setRate(value, rateByUsers);
        view.setRateIt(currentUserRate);
        setRateVisible(true);
        setRateItVisible(true);
    }

    private void setRate(final Double value, final Integer rateByUsers) {
        view.setRate(value, rateByUsers);
        setRateVisible(true);
        setRateItVisible(false);
    }

    private void setRateVisible(final boolean visible) {
        view.setRateVisible(visible);
    }

    private void setRateItVisible(final boolean visible) {
        view.setRateItVisible(visible);
    }

    public void setEnabledRateIt(final boolean enabled) {
        view.setRateItVisible(enabled);
    }

}
