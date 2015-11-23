package org.ourproject.massmob.client;

import com.google.inject.Inject;
import com.thezukunft.wave.connector.Participant;
import com.thezukunft.wave.connector.Wave;

public class StateManager {
    private final Participant viewer;
    private final Wave wave;
    private final UserSelfPreferences userPrefs;
    private boolean enabled;

    @Inject
    public StateManager(final Wave wave, final UserSelfPreferences userPrefs) {
        this.wave = wave;
        this.userPrefs = userPrefs;
        viewer = wave.getViewer();
        enabled = true;
    }

    public String getAnswer(final Participant part) {
        return wave.getState().get(genKey(part, CustomConstants.ANSWER));
    }

    public boolean getAssistantOpen() {
        return Boolean.parseBoolean(userPrefs.get(CustomConstants.ASSISTOPEN, CustomConstants.DEFASSISTOPEN));
    }

    public boolean getDescOpen() {
        return Boolean.parseBoolean(userPrefs.get(CustomConstants.DESCOPEN, CustomConstants.DEFDESCOPEN));
    }

    public String getStatus(final Participant part) {
        return getValue(genKey(part, CustomConstants.STATUS));
    }

    public String getValue(final String key) {
        return wave.getState().get(key);
    }

    public void setAnswer(final String answer) {
        setValue(genKey(viewer, CustomConstants.ANSWER), answer);
    }

    public void setAssistanceOpen(final boolean open) {
        userPrefs.set(CustomConstants.ASSISTOPEN, Boolean.toString(open));
    }

    public void setDescriptionOpen(final boolean open) {
        userPrefs.set(CustomConstants.DESCOPEN, Boolean.toString(open));
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setStatus(final String status) {
        setValue(genKey(viewer, CustomConstants.STATUS), status);
    }

    public void setValue(final String key, final String value) {
        if (enabled) {
            wave.getState().submitValue(key, value);
        }
    }

    private String genKey(final Participant part, final String key) {
        return new StringBuffer().append(part.getId()).append(":").append(key).toString();
    }
}