package org.ourproject.kune.platf.client.dto;

import org.ourproject.kune.platf.client.state.Session;

public class StateTokenUtils {

    private final Session session;

    public StateTokenUtils(final Session session) {
        this.session = session;
    }

    public String getPublicUrl(final StateToken token) {
        String publicUrl = "";
        String separator = ".";

        String group = token.getGroup();
        String tool = token.getTool();
        String folder = token.getFolder();
        String document = token.getDocument();

        publicUrl += session.getSiteUrl() + "/public";

        if (group != null) {
            publicUrl += "/" + group;
        }
        if (tool != null) {
            publicUrl += separator + tool;
        }
        if (folder != null) {
            publicUrl += separator + folder;
        }
        if (document != null) {
            publicUrl += separator + document;
        }

        return publicUrl;
    }
}
