package cc.kune.core.shared.dto;

import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.inject.Inject;

public class StateTokenUtils {

    private static final String SEPARATOR = ".";
    private final Session session;

    @Inject
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
