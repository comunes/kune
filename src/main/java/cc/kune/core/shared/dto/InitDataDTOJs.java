/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

import com.google.gwt.core.client.JavaScriptObject;

// TODO: Auto-generated Javadoc
/**
 * The Class InitDataDTO.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class InitDataDTOJs extends JavaScriptObject {

  /**
   * Instantiates a new inits the data dto js.
   */
  protected InitDataDTOJs() {
  }

  /**
   * The avi embed object.
   * 
   * @return the string
   */
  public final native String getaviEmbedObject() /*-{
                                                 return this.aviEmbedObject;
                                                 }-*/;

  /**
   * The chat domain.
   * 
   * @return the string
   */
  public final native String getchatDomain() /*-{
                                             return this.chatDomain;
                                             }-*/;

  /**
   * The chat http base.
   * 
   * @return the string
   */
  public final native String getchatHttpBase() /*-{
                                               return this.chatHttpBase;
                                               }-*/;

  /**
   * The chat room host.
   * 
   * @return the string
   */
  public final native String getchatRoomHost() /*-{
                                               return this.chatRoomHost;
                                               }-*/;

  /**
   * The current c cversion.
   * 
   * @return the string
   */
  public final native String getcurrentCCversion() /*-{
                                                   return this.currentCCversion;
                                                   }-*/;

  /**
   * The default license.
   * 
   * @return the license dto
   */
  public final native LicenseDTO getdefaultLicense() /*-{
                                                     return this.defaultLicense;
                                                     }-*/;

  /**
   * The default ws theme.
   * 
   * @return the string
   */
  public final native String getdefaultWsTheme() /*-{
                                                 return this.defaultWsTheme;
                                                 }-*/;

  /**
   * The def tutorial language.
   * 
   * @return the string
   */
  public final native String getdefTutorialLanguage() /*-{
                                                      return this.defTutorialLanguage;
                                                      }-*/;

  /**
   * The feedback enabled.
   * 
   * @return true, if successful
   */
  public final native boolean getfeedbackEnabled() /*-{
                                                   return this.feedbackEnabled;
                                                   }-*/;

  /**
   * The flv embed object.
   * 
   * @return the string
   */
  public final native String getflvEmbedObject() /*-{
                                                 return this.flvEmbedObject;
                                                 }-*/;

  /**
   * The gallery permitted extensions.
   * 
   * @return the string
   */
  public final native String getgalleryPermittedExtensions() /*-{
                                                             return this.galleryPermittedExtensions;
                                                             }-*/;

  /**
   * The img cropsize.
   * 
   * @return the int
   */
  public final native int getimgCropsize() /*-{
                                           return this.imgCropsize;
                                           }-*/;

  /**
   * The img iconsize.
   * 
   * @return the int
   */
  public final native int getimgIconsize() /*-{
                                           return this.imgIconsize;
                                           }-*/;

  /**
   * The img resizewidth.
   * 
   * @return the int
   */
  public final native int getimgResizewidth() /*-{
                                              return this.imgResizewidth;
                                              }-*/;

  /**
   * The img thumbsize.
   * 
   * @return the int
   */
  public final native int getimgThumbsize() /*-{
                                            return this.imgThumbsize;
                                            }-*/;

  /**
   * The max file size in mb.
   * 
   * @return the string
   */
  public final native String getmaxFileSizeInMb() /*-{
                                                  return this.maxFileSizeInMb;
                                                  }-*/;

  /**
   * The mp3 embed object.
   * 
   * @return the string
   */
  public final native String getmp3EmbedObject() /*-{
                                                 return this.mp3EmbedObject;
                                                 }-*/;

  /**
   * The ogg embed object.
   * 
   * @return the string
   */
  public final native String getoggEmbedObject() /*-{
                                                 return this.oggEmbedObject;
                                                 }-*/;

  /**
   * The public space visible.
   * 
   * @return true, if successful
   */
  public final native boolean getpublicSpaceVisible() /*-{
                                                      return this.publicSpaceVisible;
                                                      }-*/;

  /**
   * The site logo url.
   * 
   * @return the string
   */
  public final native String getSiteLogoUrl() /*-{
                                              return this.siteLogoUrl;
                                              }-*/;

  /**
   * The site logo url on over.
   * 
   * @return the string
   */
  public final native String getsiteLogoUrlOnOver() /*-{
                                                    return this.siteLogoUrlOnOver;
                                                    }-*/;

  /**
   * The site short name.
   * 
   * @return the string
   */
  public final native String getsiteShortName() /*-{
                                                return this.siteShortName;
                                                }-*/;

  /**
   * The site url.
   * 
   * @return the string
   */
  public final native String getsiteUrl() /*-{
                                          return this.siteUrl;
                                          }-*/;

  /**
   * The store untranslated strings.
   * 
   * @return true, if successful
   */
  public final native boolean getStoreUntranslatedStrings() /*-{
                                                            return this.storeUntranslatedStrings;
                                                            }-*/;

  /**
   * The timezones.
   * 
   * @return the string[]
   */
  public final native String[] getTimezones() /*-{
                                              return this.timezones;
                                              }-*/;

  /**
   * The translator enabled.
   * 
   * @return true, if successful
   */
  public final native boolean getTranslatorEnabled() /*-{
                                                     return this.translatorEnabled;
                                                     }-*/;

  /**
   * The use client content cache.
   * 
   * @return true, if successful
   */
  public final native boolean getUseClientContentCache() /*-{
                                                         return this.useClientContentCache;
                                                         }-*/;

  /**
   * The user info.
   * 
   * @return the user info dto
   */
  public final native JavaScriptObject getUserInfo() /*-{
                                                     return this.userInfo;
                                                     }-*/;

}
