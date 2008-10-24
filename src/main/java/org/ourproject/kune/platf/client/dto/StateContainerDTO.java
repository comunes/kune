package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StateContainerDTO extends StateAbstractDTO implements IsSerializable {

    private I18nLanguageDTO language;
    private String typeId;
    private String toolName;
    private ContainerDTO container;
    private ContainerDTO rootContainer;
    private AccessRightsDTO containerRights;
    private LicenseDTO license;
    private List<TagResultDTO> groupTags;
    private AccessListsDTO accessLists;

    public StateContainerDTO() {
    }

    public ContainerDTO getContainer() {
        return container;
    }

    public AccessRightsDTO getContainerRights() {
        return containerRights;
    }

    public List<TagResultDTO> getGroupTags() {
        return groupTags;
    }

    public I18nLanguageDTO getLanguage() {
        return language;
    }

    public LicenseDTO getLicense() {
        return license;
    }

    public ContainerDTO getRootContainer() {
        return rootContainer;
    }

    public String getToolName() {
        return toolName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setContainer(ContainerDTO container) {
        this.container = container;
    }

    public void setContainerRights(AccessRightsDTO containerRights) {
        this.containerRights = containerRights;
    }

    public void setGroupTags(List<TagResultDTO> groupTags) {
        this.groupTags = groupTags;
    }

    public void setLanguage(I18nLanguageDTO language) {
        this.language = language;
    }

    public void setLicense(LicenseDTO license) {
        this.license = license;
    }

    public void setRootContainer(ContainerDTO rootContainer) {
        this.rootContainer = rootContainer;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "StateDTO[" + getStateToken() + "/" + getTypeId() + "]";
    }

    public AccessListsDTO getAccessLists() {
        return accessLists;
    }

    public void setAccessLists(AccessListsDTO accessLists) {
        this.accessLists = accessLists;
    }

}
