/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
