package org.ourproject.kune.platf.client.dto;

public abstract class StateContentDTO extends StateAbstractDTO {

    private I18nLanguageDTO language;
    private String toolName;
    private String typeId;

    public I18nLanguageDTO getLanguage() {
        return language;
    }

    public String getToolName() {
        return toolName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setLanguage(final I18nLanguageDTO language) {
        this.language = language;
    }

    public void setToolName(final String toolName) {
        this.toolName = toolName;
    }

    public void setTypeId(final String typeId) {
        this.typeId = typeId;
    }
}
