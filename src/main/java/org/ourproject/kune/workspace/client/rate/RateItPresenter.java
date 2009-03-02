/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.workspace.client.rate;

import org.ourproject.kune.platf.client.dto.RateResultDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;

public class RateItPresenter implements RateIt {

    private static final Double NOT_RATED = new Double(-1);
    private RateItView view;
    private Double currentRate;
    private boolean isRating;
    private final I18nTranslationService i18n;
    private final Provider<ContentServiceAsync> contentServiceProvider;
    private final Session session;
    private final Provider<RatePresenter> ratePresenterProv;

    public RateItPresenter(final I18nTranslationService i18n, final Session session,
            final Provider<ContentServiceAsync> contentServiceProvider, final StateManager stateManager,
            Provider<RatePresenter> ratePresenterProvider, final ContentCapabilitiesRegistry capabilitiesRegistry) {
        this.i18n = i18n;
        this.session = session;
        this.contentServiceProvider = contentServiceProvider;
        this.ratePresenterProv = ratePresenterProvider;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                if (state instanceof StateContentDTO) {
                    StateContentDTO stateContentDTO = (StateContentDTO) state;
                    setState(capabilitiesRegistry.isRateable(stateContentDTO.getTypeId()),
                            stateContentDTO.getCurrentUserRate());
                } else {
                    view.setVisible(false);
                }
            }
        });
    }

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

    public void setVisible(final boolean visible) {
        view.setVisible(visible);
    }

    protected void revertCurrentRate() {
        if (!isRating) {
            setRatePanel(currentRate);
        }
    }

    protected void starClicked(final int starClicked) {
        isRating = true;
        final Double newValue = starClicked + 1d == currentRate ? currentRate - 0.5d : starClicked + 1d;
        setRatePanel(newValue);
        NotifyUser.showProgressProcessing();
        final StateAbstractDTO currentState = session.getCurrentState();
        contentServiceProvider.get().rateContent(session.getUserHash(), currentState.getStateToken(), newValue,
                new AsyncCallbackSimple<RateResultDTO>() {
                    public void onSuccess(final RateResultDTO result) {
                        NotifyUser.hideProgress();
                        NotifyUser.info(i18n.t("Content rated"));
                        if (currentState.getStateToken().equals(session.getCurrentStateToken())) {
                            session.getContentState().setRate(result);
                            setState(true, result.getCurrentUserRate());
                            ratePresenterProv.get().setRate(result);
                        }
                    }
                });
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

    private void setDesc(final int rateTruncated) {
        if (rateTruncated >= 0 && rateTruncated <= 1) {
            view.setDesc(i18n.t("Poor"));
        } else if (rateTruncated == 2) {
            view.setDesc(i18n.t("Below average"));
        } else if (rateTruncated == 3) {
            view.setDesc(i18n.t("Average"));
        } else if (rateTruncated == 4) {
            view.setDesc(i18n.t("Above average"));
        } else if (rateTruncated == 5) {
            view.setDesc(i18n.t("Excellent"));
        } else {
            view.setDesc("");
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

    private void setState(boolean isRateable, Double currentUserRate) {
        if (isRateable) {
            if (session.isLogged()) {
                setRate(currentUserRate);
                view.setVisible(true);
            } else {
                view.setVisible(false);
            }
        } else {
            view.setVisible(false);
        }
    }
}
