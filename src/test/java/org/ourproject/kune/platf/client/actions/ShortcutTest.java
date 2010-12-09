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
