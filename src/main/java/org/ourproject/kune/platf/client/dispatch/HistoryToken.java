package org.ourproject.kune.platf.client.dispatch;

public class HistoryToken {
    private static final String DOT = ".";
    public final String group;
    public final String tool;
    public final String folder;
    public final String document;

    public HistoryToken(final String group, final String tool, final String folder, final String document) {
	this.group = group;
	this.tool = tool;
	this.folder = folder;
	this.document = document;
    }

    public HistoryToken(final String encoded) {
	String[] splitted = encoded.split("\\.");
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
	return group + DOT + tool + DOT + folder + DOT + document;
    }

    public boolean isComplete() {
	return document != null;
    }

}
