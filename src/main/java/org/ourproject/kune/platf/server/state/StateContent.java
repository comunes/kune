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
