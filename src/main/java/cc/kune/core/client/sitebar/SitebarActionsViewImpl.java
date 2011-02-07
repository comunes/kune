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
package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.core.client.sitebar.SitebarActionsPresenter.SitebarActionsView;
import cc.kune.gspace.client.WsArmor;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class SitebarActionsViewImpl extends ViewImpl implements SitebarActionsView {

    private final ActionSimplePanel toolbarLeft;
    private final ActionSimplePanel toolbarRight;

    @Inject
    public SitebarActionsViewImpl(final WsArmor armor, final ActionSimplePanel toolbarRight,
            final ActionSimplePanel toolbarLeft) {
        this.toolbarRight = toolbarRight;
        this.toolbarLeft = toolbarLeft;
        toolbarRight.addStyleName("k-sitebar");
        toolbarRight.addStyleName("k-floatright");
        toolbarLeft.addStyleName("k-sitebar");
        toolbarLeft.addStyleName("k-floatleft");
        armor.getSitebar().add(toolbarLeft);
        armor.getSitebar().add(toolbarRight);
    }

    @Override
    public Widget asWidget() {
        return toolbarRight;
    }

    @Override
    public IsActionExtensible getLeftBar() {
        return toolbarLeft;
    }

    @Override
    public IsActionExtensible getRightBar() {
        return toolbarRight;
    }

}