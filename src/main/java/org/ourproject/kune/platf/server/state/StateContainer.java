package org.ourproject.kune.platf.server.state;

import java.util.List;

import org.ourproject.kune.platf.server.access.AccessRights;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.TagResult;

public class StateContainer extends StateAbstract {

    private I18nLanguage language;
    private String typeId;
    private String toolName;
    private Container container;
    private Container rootContainer;
    private AccessRights containerRights;
    private License license;
    private List<TagResult> groupTags;
    private AccessLists accessLists;

    public StateContainer() {
    }

    public Container getContainer() {
        return container;
    }

    public AccessRights getContainerRights() {
        return containerRights;
    }

    public List<TagResult> getGroupTags() {
        return groupTags;
    }

    public I18nLanguage getLanguage() {
        return language;
    }

    public License getLicense() {
        return license;
    }

    public Container getRootContainer() {
        return rootContainer;
    }

    public String getToolName() {
        return toolName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public void setContainerRights(AccessRights containerRights) {
        this.containerRights = containerRights;
    }

    public void setGroupTags(List<TagResult> groupTags) {
        this.groupTags = groupTags;
    }

    public void setLanguage(I18nLanguage language) {
        this.language = language;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public void setRootContainer(Container rootContainer) {
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
        return "State[" + getStateToken() + "/" + getTypeId() + "]";
    }

    public AccessLists getAccessLists() {
        return accessLists;
    }

    public void setAccessLists(AccessLists accessLists) {
        this.accessLists = accessLists;
    }
}
