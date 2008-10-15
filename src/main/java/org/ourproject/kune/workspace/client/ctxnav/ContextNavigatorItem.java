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
