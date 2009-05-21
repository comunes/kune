package org.ourproject.kune.platf.client.dto;

import org.ourproject.kune.platf.client.state.Session;

public class StateTokenUtils {

    private final Session session;
    private static final String SEPARATOR = ".";

    public StateTokenUtils(final Session session) {
        this.session = session;
    }

    public String getPublicUrl(final StateToken token) {
        String publicUrl = "";

        final String group = token.getGroup();
        final String tool = token.getTool();
        final String folder = token.getFolder();
        final String document = token.getDocument();

        publicUrl += session.getSiteUrl() + "/public";

        if (group != null) {
            publicUrl += "/" + group;
        }
        if (tool != null) {
            publicUrl += SEPARATOR + tool;
        }
        if (folder != null) {
            publicUrl += SEPARATOR + folder;
        }
        if (document != null) {
            publicUrl += SEPARATOR + document;
        }

        return publicUrl;
    }
}
