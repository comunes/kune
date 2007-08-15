package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StateToken implements IsSerializable {
    private static final String[] EMPTY = new String[0];
    private static final String DOT = ".";
    public final String group;
    public final String tool;
    public final String folder;
    public final String document;

    public StateToken(final String group, final String tool, final String folder, final String document) {
	this.group = group;
	this.tool = tool;
	this.folder = folder;
	this.document = document;
    }

    public StateToken() {
	this(null, null, null, null);
    }

    public StateToken(final String encoded) {
	String[] splitted;
	if (encoded != null && encoded.length() > 0) {
	    splitted = encoded.split("\\.");
	} else {
	    splitted = EMPTY;
	}
	group = conditionalAssign(0, splitted);
	tool = conditionalAssign(1, splitted);
	folder = conditionalAssign(2, splitted);
	document = conditionalAssign(3, splitted);
    }

    private String conditionalAssign(final int index, final String[] splitted) {
	if (splitted.length > index) {
	    return splitted[index];
	} else {
	    return null;
	}
    }

    public static String encode(final String group, final String tool, final String folder, final String document) {
	String encoded = "";
	if (group != null) {
	    encoded += group;
	}
	if (tool != null) {
	    encoded += DOT + tool;
	}
	if (folder != null) {
	    encoded += DOT + folder;
	}
	if (document != null) {
	    encoded += DOT + document;
	}
	return encoded;
    }

    public boolean isComplete() {
	return document != null;
    }

    public boolean hasAll() {
	return group != null && tool != null && folder != null && document != null;
    }

    public boolean hasGroupToolAndFolder() {
	return group != null && tool != null && folder != null;
    }

    public boolean hasGroupAndTool() {
	return group != null && tool != null;
    }

    public boolean hasGroup() {
	return group != null;
    }

    public boolean hasNothing() {
	return group == null && tool == null && folder == null && document == null;
    }
}
