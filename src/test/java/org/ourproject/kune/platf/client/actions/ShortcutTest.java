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
package org.ourproject.kune.platf.client.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.i18n.Resources;

import cc.kune.core.client.i18n.I18nTranslationServiceMocked;

public class ShortcutTest {
    @Test
    public void altS() {
        final KeyStroke shortcut = Shortcut.getShortcut(false, true, false, false, 'S');
        assertEquals(" (Alt+S)", shortcut.toString());
    }

    @Before
    public void before() {
        new Resources(new I18nTranslationServiceMocked());
    }

    @Test
    public void ctrlComa() {
        final KeyStroke shortcut = Shortcut.getShortcut(true, ',');
        assertEquals(" (Ctrl+,)", shortcut.toString());
    }

    @Test
    public void ctrlSCapital() {
        final KeyStroke shortcut = Shortcut.getShortcut(true, 'S');
        assertEquals(" (Ctrl+S)", shortcut.toString());
    }

    @Test
    public void ctrlShiftS() {
        final KeyStroke shortcut = Shortcut.getShortcut(true, false, true, false, 'S');
        assertEquals(" (Ctrl+Shift+S)", shortcut.toString());
    }
}
