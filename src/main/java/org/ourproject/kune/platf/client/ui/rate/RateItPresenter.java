/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.ui.rate;

import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.WorkspaceEvents;

public class RateItPresenter {

    private static final Double NOT_RATED = new Double(-1);

    private RateItView view;

    private Double currentRate;

    private boolean isRating;

    public void init(final RateItView view) {
        this.view = view;
        currentRate = NOT_RATED;
        isRating = false;
    }

    public void setRate(final Double value) {
        currentRate = value;
        if (currentRate == null) {
            currentRate = NOT_RATED;
        }
        isRating = false;
        setRatePanel(currentRate);
    }

    protected void revertCurrentRate() {
        if (!isRating) {
            setRatePanel(currentRate);
        }
    }

    protected void starClicked(final int starClicked) {
        isRating = true;
        Double newValue = new Double(starClicked + 1);
        if (Math.ceil(currentRate.doubleValue()) == newValue.doubleValue()) {
            // Same star, rest 1/2
            newValue = new Double(currentRate.doubleValue() - 0.5);
        }
        setRatePanel(newValue);
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.RATE_CONTENT, newValue);
    }

    protected void starOver(final int starMouseOver) {
        Double value = new Double(starMouseOver + 1);
        if (Math.ceil(currentRate.doubleValue()) == value.doubleValue()) {
            // use user already rated -> live same value when mouse is over
            value = currentRate;
        }
        if (!isRating) {
            // If we are not doing a rate rpc call
            setRatePanel(value);
        }
    }

    private void setRatePanel(final Double value) {
        if (value.equals(NOT_RATED)) {
            view.clearRate();
            view.setDesc("");
        } else {
            view.setStars(value);
            setDesc((int) Math.ceil(value.doubleValue()));
        }
    }

    private void setDesc(final int rateTruncated) {
        if (rateTruncated >= 0 && rateTruncated <= 1) {
            view.setDesc(Kune.I18N.t("Poor"));
        } else if (rateTruncated == 2) {
            view.setDesc(Kune.I18N.t("Below average"));
        } else if (rateTruncated == 3) {
            view.setDesc(Kune.I18N.t("Average"));
        } else if (rateTruncated == 4) {
            view.setDesc(Kune.I18N.t("Above average"));
        } else if (rateTruncated == 5) {
            view.setDesc(Kune.I18N.t("Excellent"));
        } else {
            view.setDesc("");
        }
    }
}
