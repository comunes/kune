/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
 \*/
package org.ourproject.kune.workspace.client.rate;

import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;

import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.RateResult;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.calclab.suco.client.events.Listener;

public class RatePresenter {

    private RateView view;
    private final ContentCapabilitiesRegistry capabilitiesRegistry;

    public RatePresenter(final StateManager stateManager, final ContentCapabilitiesRegistry capabilitiesRegistry) {
        this.capabilitiesRegistry = capabilitiesRegistry;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                if (state instanceof StateContentDTO) {
                    setState((StateContentDTO) state);
                } else {
                    view.setVisible(false);
                }
            }
        });
    }

    public void init(final RateView view) {
        this.view = view;
    }

    public void setRate(final RateResult result) {
        setRate(result.getRateByUsers(), result.getRate());
    }

    public void setVisible(final boolean visible) {
        view.setVisible(visible);
    }

    private void setRate(final Integer rateByUsers, final Double rate) {
        view.setVisible(true);
        view.setRate(rate);
        view.setByUsers(rateByUsers);
    }

    private void setState(final StateContentDTO state) {
        final Integer rateByUsers = state.getRateByUsers();
        final Double rate = state.getRate();
        if (capabilitiesRegistry.isRateable(state.getTypeId())) {
            setRate(rateByUsers, rate);
        } else {
            view.setVisible(false);
        }
    }
}
