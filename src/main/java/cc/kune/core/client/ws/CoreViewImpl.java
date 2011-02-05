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
 */
package cc.kune.core.client.ws;

import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.StateManagerDefault;
import cc.kune.gspace.client.WsArmorImpl;
import cc.kune.gspace.client.resources.WsArmorResources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * The Class CoreView is where the general armor of Kune it created/attached.
 */
public class CoreViewImpl extends ViewImpl implements CorePresenter.CoreView {

    private final WsArmorImpl armor;

    /**
     * Instantiates a new core view.
     * 
     * @param armor
     *            the body
     */
    @Inject
    public CoreViewImpl(final WsArmorImpl armor, StateManagerDefault stateManager) {
        this.armor = armor;
        GWT.<CoreResources> create(CoreResources.class).css().ensureInjected();
        GWT.<WsArmorResources> create(WsArmorResources.class).style().ensureInjected();
        History.addValueChangeHandler(stateManager);
    }

    @Override
    public Widget asWidget() {
        return armor;
    }
}
