package org.ourproject.kune.platf.client.dto;

import org.ourproject.kune.platf.client.ui.WindowUtils;

public class StateTokenUtils {

    public String getPublicUrl(final StateToken token) {
        String publicUrl = "";
        String separator = ".";

        String group = token.getGroup();
        String tool = token.getTool();
        String folder = token.getFolder();
        String document = token.getDocument();

        publicUrl += WindowUtils.getPublicHost() + "/public";

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
