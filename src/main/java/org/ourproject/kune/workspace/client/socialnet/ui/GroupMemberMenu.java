package org.ourproject.kune.workspace.client.socialnet.ui;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.MenuBar;

public class GroupMemberMenu extends MenuBar {

    private final MenuBar commands;

    public GroupMemberMenu(final AbstractImagePrototype image, final String text) {
	super(false);
	String label = image.getHTML() + text;
	commands = new MenuBar(true);
	addItem(label, true, commands);
	setAutoOpen(false);
	commands.setAutoOpen(true);
	setStyleName("kune-GroupMemberLabel");
	commands.setStyleName("kune-GroupMemberCommands");
    }

    public void addCmd(final AbstractImagePrototype image, final String text, final Command command) {
	String textHtml = image.getHTML() + text;
	commands.addItem(textHtml, true, command);
    }

    public void addCmd(final String text, final Command command) {
	commands.addItem(text, true, command);
    }
}
