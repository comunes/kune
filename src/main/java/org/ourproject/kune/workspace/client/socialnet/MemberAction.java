package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.services.Kune;

public class MemberAction {

    public final static MemberAction GOTO_GROUP_COMMAND = new MemberAction(Kune.I18N.t("Visit this member homepage"),
            PlatformEvents.GOTO);

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