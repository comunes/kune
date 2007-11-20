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

package org.ourproject.kune.platf.server.rpc;

import java.util.List;

import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.rpc.SiteService;
import org.ourproject.kune.platf.server.InitData;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.properties.ChatProperties;
import org.ourproject.kune.platf.server.properties.KuneProperties;
import org.ourproject.kune.platf.server.users.UserInfoService;
import org.ourproject.kune.platf.server.users.UserManager;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class SiteRPC implements RPC, SiteService {
    private final Mapper mapper;
    private final UserSession session;
    private final LicenseManager licenseManager;
    private final UserManager userManager;
    private final ChatProperties chatProperties;
    private final UserInfoService userInfoService;
    private final KuneProperties kuneProperties;

    // TODO: refactor: too many parameters! refactor to Facade Pattern
    @Inject
    public SiteRPC(final UserSession session, final UserManager userManager, final UserInfoService userInfoService,
            final LicenseManager licenseManager, final Mapper mapper, final KuneProperties kuneProperties,
            final ChatProperties chatProperties) {
        this.session = session;
        this.userManager = userManager;
        this.userInfoService = userInfoService;
        this.licenseManager = licenseManager;
        this.mapper = mapper;
        this.kuneProperties = kuneProperties;
        this.chatProperties = chatProperties;
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public InitDataDTO getInitData(final String userHash) throws SerializableException {
        final InitData data = new InitData();

        data.setCCLicenses(licenseManager.getCC());
        data.setNotCCLicenses(licenseManager.getNotCC());
        data.setUserInfo(userInfoService.buildInfo(userManager.find(session.getUser().getId()), session.getHash()));
        data.setChatHttpBase(chatProperties.getHttpBase());
        data.setChatDomain(chatProperties.getDomain());
        data.setChatRoomHost(chatProperties.getRoomHost());
        data.setWsThemes(this.kuneProperties.get(KuneProperties.WS_THEMES).split(","));
        data.setDefaultWsTheme(this.kuneProperties.get(KuneProperties.WS_THEMES_DEF));
        return mapper.map(data, InitDataDTO.class);
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
