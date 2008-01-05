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

import java.util.TimeZone;

import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.rpc.SiteService;
import org.ourproject.kune.platf.server.InitData;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.manager.I18nCountryManager;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.properties.ChatProperties;
import org.ourproject.kune.platf.server.properties.KuneProperties;
import org.ourproject.kune.platf.server.users.UserInfoService;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

@Singleton
public class SiteRPC implements RPC, SiteService {
    private final Mapper mapper;
    private final Provider<UserSession> userSessionProvider;
    private final LicenseManager licenseManager;
    private final UserManager userManager;
    private final ChatProperties chatProperties;
    private final UserInfoService userInfoService;
    private final KuneProperties kuneProperties;
    private final I18nLanguageManager languageManager;
    private final I18nCountryManager countryManager;

    // TODO: refactor: too many parameters! refactor to Facade Pattern
    @Inject
    public SiteRPC(final Provider<UserSession> userSessionProvider, final UserManager userManager,
            final UserInfoService userInfoService, final LicenseManager licenseManager, final Mapper mapper,
            final KuneProperties kuneProperties, final ChatProperties chatProperties,
            final I18nLanguageManager languageManager, final I18nCountryManager countryManager) {
        this.userSessionProvider = userSessionProvider;
        this.userManager = userManager;
        this.userInfoService = userInfoService;
        this.licenseManager = licenseManager;
        this.mapper = mapper;
        this.kuneProperties = kuneProperties;
        this.chatProperties = chatProperties;
        this.languageManager = languageManager;
        this.countryManager = countryManager;
    }

    @Transactional(type = TransactionType.READ_ONLY)
    public InitDataDTO getInitData(final String userHash) throws SerializableException {
        final InitData data = new InitData();
        UserSession userSession = getUserSession();

        data.setLicenses(licenseManager.getAll());
        data.setLanguages(languageManager.getAll());
        data.setCountries(countryManager.getAll());
        data.setTimezones(TimeZone.getAvailableIDs());
        data.setUserInfo(userInfoService.buildInfo(userManager.find(userSession.getUser().getId()), userSession
                .getHash()));
        data.setChatHttpBase(chatProperties.getHttpBase());
        data.setChatDomain(chatProperties.getDomain());
        data.setSiteDomain(kuneProperties.get(KuneProperties.SITE_DOMAIN));
        data.setChatRoomHost(chatProperties.getRoomHost());
        data.setWsThemes(this.kuneProperties.get(KuneProperties.WS_THEMES).split(","));
        data.setDefaultWsTheme(this.kuneProperties.get(KuneProperties.WS_THEMES_DEF));
        return mapper.map(data, InitDataDTO.class);
    }

    private UserSession getUserSession() {
        return userSessionProvider.get();
    }
}
