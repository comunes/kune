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

package org.ourproject.kune.workspace.client.presence.ui;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.stacks.StackedDropDownPanel;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.summary.GroupSummaryView;

public class GroupSummaryPanel extends StackedDropDownPanel implements GroupSummaryView {

    public GroupSummaryPanel(final AbstractPresenter presenter) {
        super(presenter, Kune.getInstance().theme.getSummaryDD(), Kune.I18N.t("Group Summary"), Kune.I18N
                .t("Some summarized information about current project" + Site.IN_DEVELOPMENT), false);
        setDropDownContentVisible(true);
        setVisible(true);
    }

    public void setComment(final String comment) {
        super.clear();
        super.addComment(comment);
    }

}
