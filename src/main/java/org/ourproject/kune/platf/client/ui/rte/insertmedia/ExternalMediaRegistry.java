/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.ui.rte.insertmedia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.shared.dto.ExtMediaDescripDTO;

public class ExternalMediaRegistry extends ArrayList<ExtMediaDescripDTO> {

    private static final long serialVersionUID = -9109609520119776917L;
    public static final ExtMediaDescripDTO NO_MEDIA = new ExtMediaDescripDTO(null, null, null, null, null, 0, 0);

    public ExternalMediaRegistry(final List<ExtMediaDescripDTO> extMediaDescrips) {
        super(extMediaDescrips);
    }

    public ExtMediaDescripDTO get(final String url) {
        for (ExtMediaDescripDTO media : this) {
            if (media.is(url)) {
                return media;
            }

        }
        return NO_MEDIA;
    }

    public String getNames() {
        String names = "";
        for (Iterator<ExtMediaDescripDTO> iterator = this.iterator(); iterator.hasNext();) {
            ExtMediaDescripDTO elem = iterator.next();
            names += TextUtils.generateHtmlLink(elem.getSiteurl(), elem.getName());
            if (iterator.hasNext()) {
                names += ", ";
            }
        }
        return names;
    }
}