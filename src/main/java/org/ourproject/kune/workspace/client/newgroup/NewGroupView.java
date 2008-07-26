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
package org.ourproject.kune.workspace.client.newgroup;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.workspace.client.newgroup.ui.SiteErrorType;

public interface NewGroupView extends View {

    void center();

    void clearData();

    LicenseDTO getLicense();

    String getLongName();

    String getPublicDesc();

    String getShortName();

    String getTags();

    void hide();

    void hideMessage();

    boolean isCommunity();

    boolean isFormValid();

    boolean isOrganization();

    boolean isOrphanedProject();

    boolean isProject();

    void maskProcessing();

    void setEnabledBackButton(boolean enabled);

    void setEnabledFinishButton(boolean enabled);

    void setEnabledNextButton(boolean enabled);

    void setMessage(String message, SiteErrorType type);

    void show();

    void showLicenseForm();

    void showNewGroupInitialDataForm();

    void unMask();

}
