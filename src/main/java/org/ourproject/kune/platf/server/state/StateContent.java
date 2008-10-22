package org.ourproject.kune.platf.server.state;

import org.ourproject.kune.platf.server.domain.I18nLanguage;

public abstract class StateContent extends StateAbstract {

    private String typeId;
    private I18nLanguage language;
    private String toolName;

    public I18nLanguage getLanguage() {
        return language;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setLanguage(final I18nLanguage language) {
        this.language = language;
    }

    public void setTypeId(final String typeId) {
        this.typeId = typeId;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(final String toolName) {
        this.toolName = toolName;
    }

}
