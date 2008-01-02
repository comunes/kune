/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.newgroup;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;

public interface NewGroupView extends View {

    void clearData();

    String getPublicDesc();

    String getLongName();

    String getShortName();

    boolean isProject();

    boolean isOrphanedProject();

    boolean isOrganization();

    boolean isCommunity();

    void hide();

    void setEnabledNextButton(boolean enabled);

    void setEnabledFinishButton(boolean enabled);

    void setEnabledBackButton(boolean enabled);

    void showNewGroupInitialDataForm();

    void showLicenseForm();

    LicenseDTO getLicense();

    boolean isFormValid();

    void hideMessage();

    void setMessage(String message, int type);

}
