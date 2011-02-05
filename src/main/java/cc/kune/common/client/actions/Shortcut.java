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
package cc.kune.common.client.actions;

import cc.kune.common.client.shortcuts.Keyboard;

public final class Shortcut {

    public static KeyStroke getShortcut(final boolean ctrl, final boolean alt, final boolean shift, final boolean meta,
            final Character character) {
        return KeyStroke.getKeyStroke(character, (ctrl ? Keyboard.MODIFIER_CTRL : 0)
                + (alt ? Keyboard.MODIFIER_ALT : 0) + (shift ? Keyboard.MODIFIER_SHIFT : 0)
                + (meta ? Keyboard.MODIFIER_META : 0));
    }

    public static KeyStroke getShortcut(final boolean ctrl, final boolean shift, final Character character) {
        return getShortcut(ctrl, false, shift, false, character);
    }

    public static KeyStroke getShortcut(final boolean ctrl, final Character character) {
        return getShortcut(ctrl, false, false, false, character);
    }

    private Shortcut() {
    }

}
