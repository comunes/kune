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
package cc.kune.common.shared.utils;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nBasicUtils. Some code from Apache Wave SessionLocale.java. FIXME use SessionLocale in the future
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class I18nBasicUtils {
    /**
     * RFC 3066 pattern for language (primary subtag).
     */
    private static final String LANG_PATTERN = "[a-zA-Z]{1,8}";

    /** The Constant DEFAULT_LANG. */
    private static final String DEFAULT_LANG = "en";

    /**
     * Calculate the language fragment from a RFC 3066 locale.
     *
     * @param localeString
     *            the locale string (en_US, en, es_AR, etc)
     * @return the language fragment (en, en, es)
     */
    public static String getLanguage(String localeString) {
        localeString = localeString == null || localeString.equals("default") ? "en" : localeString;
        String[] split = localeString.split("[_-]");
        if ((split.length > 0) && split[0].matches(LANG_PATTERN)) {
            return split[0];
        } else {
            return DEFAULT_LANG;
        }
    }

    /**
     * Java locale normalize (uses _ and uppercase countries).
     *
     * @param localeString the locale string
     * @return the string
     */
    public static String javaLocaleNormalize(String localeString) {
        String[] split = localeString.split("[_-]");
        String lang;
        if ((split.length > 0) && split[0].matches(LANG_PATTERN)) {
            lang = split[0];
        } else {
            lang = DEFAULT_LANG;
        }
        if (split.length > 1) {
            return lang + "_" + split[1].toUpperCase();
        } else {
            return lang;
        }
    }
}
