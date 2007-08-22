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

package org.ourproject.kune.platf.server;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.mapper.Mapper;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class KuneServerService implements KuneService {
    private final Mapper mapper;
    private final GroupManager groupManager;
    private final UserSession session;
    private final LicenseManager licenseManager;
    private static final Log log = LogFactory.getLog(KuneServerService.class);

    @Inject
    public KuneServerService(final UserSession session, final GroupManager groupManager,
	    final LicenseManager licenseManager, final Mapper mapper) {
	this.session = session;
	this.groupManager = groupManager;
	this.licenseManager = licenseManager;
	this.mapper = mapper;
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void createNewGroup(final GroupDTO group) throws SerializableException {
	log.debug(group.getShortName() + group.getLongName() + group.getPublicDesc() + group.getDefaultLicense()
		+ group.getType());
	groupManager.createGroup(mapper.map(group, Group.class), session.getUser());
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List getAllLicenses() throws SerializableException {
	return mapper.mapList(licenseManager.getAll(), LicenseDTO.class);
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public List getNotCCLicenses() throws SerializableException {
	return mapper.mapList(licenseManager.getNotCC(), LicenseDTO.class);
    }

}
