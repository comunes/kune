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
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.i18n.Resources;
import org.ourproject.kune.platf.client.shortcuts.Keyboard;
import org.ourproject.kune.platf.client.shortcuts.ShortcutDescriptor;

import cc.kune.core.client.i18n.I18nTranslationServiceMocked;

public class KeyStrokeTest {

    @Test
    public void altS() {
        final KeyStroke key = KeyStroke.getKeyStroke(Character.valueOf('S'), Keyboard.MODIFIER_ALT);
        assertEquals(" (Alt+S)", key.toString());
    }

    @Before
    public void before() {
        new Resources(new I18nTranslationServiceMocked());
    }

    @Test
    public void ctrlComa() {
        final ShortcutDescriptor shortcut = new ShortcutDescriptor(true, ',');
        assertEquals(" (Ctrl+,)", shortcut.toString());
        assertTrue(shortcut.is(',', Keyboard.MODIFIER_CTRL));
        assertTrue(!shortcut.is(',', Keyboard.MODIFIER_ALT));
        assertTrue(!shortcut.is(',', Keyboard.MODIFIER_SHIFT));
    }

    @Test
    public void ctrlS() {
        final ShortcutDescriptor shortcut = new ShortcutDescriptor(true, 's');
        assertEquals(" (Ctrl+S)", shortcut.toString());
        assertTrue(shortcut.is('s', Keyboard.MODIFIER_CTRL));
        assertTrue(!shortcut.is('s', Keyboard.MODIFIER_ALT));
        assertTrue(!shortcut.is('s', Keyboard.MODIFIER_SHIFT));
    }

    @Test
    public void ctrlSCapital() {
        final ShortcutDescriptor shortcut = new ShortcutDescriptor(true, 'S');
        assertEquals(" (Ctrl+S)", shortcut.toString());
        assertTrue(shortcut.is('S', Keyboard.MODIFIER_CTRL));
        assertTrue(!shortcut.is('S', Keyboard.MODIFIER_ALT));
        assertTrue(!shortcut.is('S', Keyboard.MODIFIER_SHIFT));
    }

    @Test
    public void ctrlShiftS() {
        final ShortcutDescriptor shortcut = new ShortcutDescriptor(true, false, true, 'S', null);
        assertEquals(" (Ctrl+Shift+S)", shortcut.toString());
        assertTrue(!shortcut.is('S', Keyboard.MODIFIER_ALT));
        assertTrue(shortcut.is('S', Keyboard.MODIFIER_SHIFT | Keyboard.MODIFIER_CTRL));
    }

    @Test
    public void equalsTest() {
        final KeyStroke key1 = KeyStroke.getKeyStroke(Character.valueOf('B'), Keyboard.MODIFIER_ALT);
        final KeyStroke key2 = KeyStroke.getKeyStroke(Character.valueOf('B'), Keyboard.MODIFIER_ALT);
        assertEquals(key1, key2);
    }

    @Test
    public void space() {
        final KeyStroke key = KeyStroke.getKeyStroke(Character.valueOf(' '), Keyboard.MODIFIER_ALT);
        assertEquals(" (Alt+Space)", key.toString());
    }
}