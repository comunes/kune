/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package cc.kune.core.shared.dto;

import cc.kune.core.shared.domain.TagCloudResult;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StateContainerDTO extends StateAbstractDTO implements IsSerializable {

    private I18nLanguageDTO language;
    private String typeId;
    private String toolName;
    private ContainerDTO container;
    private ContainerDTO rootContainer;
    private AccessRightsDTO containerRights;
    private LicenseDTO license;
    private TagCloudResult tagCloudResult;
    private AccessListsDTO accessLists;

    public StateContainerDTO() {
    }

    public AccessListsDTO getAccessLists() {
        return accessLists;
    }

    public ContainerDTO getContainer() {
        return container;
    }

    public AccessRightsDTO getContainerRights() {
        return containerRights;
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

    public TagCloudResult getTagCloudResult() {
        return tagCloudResult;
    }

    public String getToolName() {
        return toolName;
    }

    public String getTypeId() {
        return typeId;
    }

    public boolean isType(final String type) {
        return getTypeId().equals(type);
    }

    public void setAccessLists(final AccessListsDTO accessLists) {
        this.accessLists = accessLists;
    }

    public void setContainer(final ContainerDTO container) {
        this.container = container;
    }

    public void setContainerRights(final AccessRightsDTO containerRights) {
        this.containerRights = containerRights;
    }

    public void setLanguage(final I18nLanguageDTO language) {
        this.language = language;
    }

    public void setLicense(final LicenseDTO license) {
        this.license = license;
    }

    public void setRootContainer(final ContainerDTO rootContainer) {
        this.rootContainer = rootContainer;
    }

    public void setTagCloudResult(final TagCloudResult tagCloudResult) {
        this.tagCloudResult = tagCloudResult;
    }

    public void setToolName(final String toolName) {
        this.toolName = toolName;
    }

    public void setTypeId(final String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "StateDTO[" + getStateToken() + "/" + getTypeId() + "]";
    }

}
