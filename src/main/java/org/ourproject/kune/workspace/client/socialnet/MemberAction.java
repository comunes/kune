package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.workspace.client.WorkspaceEvents;

public class MemberAction {
    public final static MemberAction GOTO_GROUP_COMMAND = new MemberAction("Visit this member homepage",
	    WorkspaceEvents.GOTO_GROUP);

    private final String text;
    private final String action;

    public MemberAction(final String text, final String action) {
	this.text = text;
	this.action = action;
    }

    public String getText() {
	return text;
    }

    public String getAction() {
	return action;
    }
}