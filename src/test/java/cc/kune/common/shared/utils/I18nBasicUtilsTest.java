/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under 
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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cc.kune.common.shared.utils.I18nBasicUtils;

public class I18nBasicUtilsTest {

    @Test
    public void someBasicTests() {
        assertEquals("en", I18nBasicUtils.getLanguage("en_US"));
        assertEquals("en", I18nBasicUtils.getLanguage("en"));
        assertEquals("es", I18nBasicUtils.getLanguage("es_AR"));
    }

    @Test
    public void testJavaLocaleNormalize() {
        assertEquals("en_US", I18nBasicUtils.javaLocaleNormalize("en-US"));
        assertEquals("pt_BR", I18nBasicUtils.javaLocaleNormalize("pt-br"));
        assertEquals("pt_BR", I18nBasicUtils.javaLocaleNormalize("pt-BR"));
    }

    @Test
    public void shouldReturnDefault() {
        assertEquals("en", I18nBasicUtils.getLanguage("default"));
        assertEquals("en", I18nBasicUtils.getLanguage("someOtherThing"));
    }

    @Test
    public void shouldWorkWithNull() {
        assertEquals("en", I18nBasicUtils.getLanguage(null));
    }
}
