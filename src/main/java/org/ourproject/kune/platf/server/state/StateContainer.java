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
