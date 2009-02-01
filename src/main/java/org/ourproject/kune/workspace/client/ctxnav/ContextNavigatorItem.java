/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.ctxnav;

import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.dto.ContentStatusDTO;
import org.ourproject.kune.platf.client.dto.StateToken;

public class ContextNavigatorItem {
    private final String id;
    private final String parentId;
    private final String iconUrl;
    private final String text;
    private final String tooltip;
    private final ContentStatusDTO contentStatusDTO;
    private final StateToken token;
    private final ActionItemCollection<StateToken> actionCollection;
    private final boolean allowDrag;
    private final boolean allowDrop;

    public ContextNavigatorItem(final String id, final String parentId, final String iconUrl, final String text,
            final String tooltip, final ContentStatusDTO contentStatusDTO, final StateToken token,
            final boolean allowDrag, final boolean allowDrop, final ActionItemCollection<StateToken> actionCollection) {
        this.id = id;
        this.parentId = parentId;
        this.iconUrl = iconUrl;
        this.text = text;
        this.tooltip = tooltip;
        this.contentStatusDTO = contentStatusDTO;
        this.token = token;
        this.allowDrag = allowDrag;
        this.allowDrop = allowDrop;
        this.actionCollection = actionCollection;
    }

    public ActionItemCollection<StateToken> getActionCollection() {
        return actionCollection;
    }

    public ContentStatusDTO getContentStatus() {
        return contentStatusDTO;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public StateToken getStateToken() {
        return token;
    }

    public String getText() {
        return text;
    }

    public String getTooltip() {
        return tooltip;
    }

    public boolean isDraggable() {
        return allowDrag;
    }

    public boolean isDroppable() {
        return allowDrop;
    }

}
