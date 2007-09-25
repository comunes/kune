package org.ourproject.kune.workspace.client.socialnet.ui;

public class MemberAction {
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