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
package cc.kune.core.server.rpc;

import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.rpcservices.SiteService;
import cc.kune.core.server.InitData;
import cc.kune.core.server.UserSessionManager;
import cc.kune.core.server.manager.ExtMediaDescripManager;
import cc.kune.core.server.manager.I18nCountryManager;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.LicenseManager;
import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.mapper.Mapper;
import cc.kune.core.server.properties.ChatProperties;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.server.properties.ReservedWordsRegistry;
import cc.kune.core.server.tool.ServerToolRegistry;
import cc.kune.core.server.users.UserInfo;
import cc.kune.core.server.users.UserInfoService;
import cc.kune.core.shared.dto.GSpaceTheme;
import cc.kune.core.shared.dto.InitDataDTO;
import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class SiteRPC implements RPC, SiteService {
  private final ChatProperties chatProperties;
  private final I18nCountryManager countryManager;
  private InitData data;
  private final ExtMediaDescripManager extMediaDescManager;
  private final KuneProperties kuneProperties;
  private final I18nLanguageManager languageManager;
  private final LicenseManager licenseManager;
  private final Mapper mapper;
  private final ReservedWordsRegistryDTO reservedWords;
  private final ServerToolRegistry serverToolRegistry;
  private final HashMap<String, GSpaceTheme> siteThemes;
  private final UserInfoService userInfoService;
  private final UserSessionManager userSessionManager;

  @Inject
  public SiteRPC(final UserSessionManager userSessionManager, final UserManager userManager,
      final UserInfoService userInfoService, final LicenseManager licenseManager, final Mapper mapper,
      final KuneProperties kuneProperties, final ChatProperties chatProperties,
      final I18nLanguageManager languageManager, final I18nCountryManager countryManager,
      final ServerToolRegistry serverToolRegistry, final ExtMediaDescripManager extMediaDescManager) {
    this.userSessionManager = userSessionManager;
    this.userInfoService = userInfoService;
    this.licenseManager = licenseManager;
    this.mapper = mapper;
    this.kuneProperties = kuneProperties;
    this.chatProperties = chatProperties;
    this.languageManager = languageManager;
    this.countryManager = countryManager;
    this.serverToolRegistry = serverToolRegistry;
    this.extMediaDescManager = extMediaDescManager;
    data = loadInitData();
    siteThemes = getSiteThemes(this.kuneProperties.getList(KuneProperties.WS_THEMES));
    reservedWords = new ReservedWordsRegistryDTO(ReservedWordsRegistry.fromList(kuneProperties));
  }

  private String[] getColors(final String key) {
    return this.kuneProperties.getList(key).toArray(new String[0]);
  }

  @Override
  @Transactional
  public InitDataDTO getInitData(final String userHash) throws DefaultException {
    final InitDataDTO dataMapped = mapper.map(data, InitDataDTO.class);

    final UserInfo userInfo = userInfoService.buildInfo(userSessionManager.getUser(),
        userSessionManager.getHash());
    if (userInfo != null) {
      dataMapped.setUserInfo(mapper.map(userInfo, UserInfoDTO.class));
    }

    dataMapped.setgSpaceThemes(siteThemes);
    dataMapped.setReservedWords(reservedWords);
    return dataMapped;
  }

  private HashMap<String, GSpaceTheme> getSiteThemes(final List<String> themes) {
    final HashMap<String, GSpaceTheme> map = new HashMap<String, GSpaceTheme>();
    for (final String theme : themes) {
      map.put(theme, getThemeFromProperties(theme));
    }
    return map;
  }

  private GSpaceTheme getThemeFromProperties(final String themeName) {
    final GSpaceTheme theme = new GSpaceTheme(themeName);
    theme.setBackColors(getColors(KuneProperties.WS_THEMES + "." + theme.getName() + ".backgrounds"));
    theme.setColors(getColors(KuneProperties.WS_THEMES + "." + theme.getName() + ".colors"));
    return theme;
  }

  private InitData loadInitData() {
    data = new InitData();
    data.setSiteUrl(kuneProperties.get(KuneProperties.SITE_URL));
    data.setLicenses(licenseManager.getAll());
    data.setLanguages(languageManager.getAll());
    data.setCountries(countryManager.getAll());
    data.setTimezones(TimeZone.getAvailableIDs());
    data.setChatHttpBase(chatProperties.getHttpBase());
    data.setChatDomain(chatProperties.getDomain());
    data.setSiteDomain(kuneProperties.get(KuneProperties.SITE_DOMAIN));
    data.setChatRoomHost(chatProperties.getRoomHost());
    data.setDefaultLicense(licenseManager.getDefLicense());
    data.setCurrentCCversion(this.kuneProperties.get(KuneProperties.CURRENT_CC_VERSION));
    data.setDefaultWsTheme(this.kuneProperties.get(KuneProperties.WS_THEMES_DEF));
    data.setSiteLogoUrl(kuneProperties.get(KuneProperties.SITE_LOGO_URL));
    data.setGalleryPermittedExtensions(kuneProperties.get(KuneProperties.UPLOAD_GALLERY_PERMITTED_EXTS));
    data.setMaxFileSizeInMb(kuneProperties.get(KuneProperties.UPLOAD_MAX_FILE_SIZE));
    data.setUserTools(serverToolRegistry.getToolsAvailableForUsers());
    data.setGroupTools(serverToolRegistry.getToolsAvailableForGroups());
    data.setImgResizewidth(kuneProperties.getInteger(KuneProperties.IMAGES_RESIZEWIDTH));
    data.setImgThumbsize(kuneProperties.getInteger(KuneProperties.IMAGES_THUMBSIZE));
    data.setImgCropsize(kuneProperties.getInteger(KuneProperties.IMAGES_CROPSIZE));
    data.setImgIconsize(kuneProperties.getInteger(KuneProperties.IMAGES_ICONSIZE));
    data.setFlvEmbedObject(kuneProperties.get(KuneProperties.FLV_EMBEDED_OBJECT));
    data.setMp3EmbedObject(kuneProperties.get(KuneProperties.MP3_EMBEDED_OBJECT));
    data.setOggEmbedObject(kuneProperties.get(KuneProperties.OGG_EMBEDED_OBJECT));
    data.setAviEmbedObject(kuneProperties.get(KuneProperties.AVI_EMBEDED_OBJECT));
    data.setExtMediaDescrips(extMediaDescManager.getAll());
    data.setFeedbackEnabled(kuneProperties.getBoolean(KuneProperties.FEEDBACK_ENABLED));
    data.setSiteShortName(kuneProperties.get(KuneProperties.SITE_SHORTNAME));
    return data;
  }

}
