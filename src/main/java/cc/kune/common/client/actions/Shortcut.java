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
package cc.kune.common.client.actions;

import cc.kune.common.client.shortcuts.Keyboard;

// TODO: Auto-generated Javadoc
/**
 * The Class Shortcut.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public final class Shortcut {

  /**
   * Gets the shortcut.
   *
   * @param ctrl the ctrl
   * @param alt the alt
   * @param shift the shift
   * @param meta the meta
   * @param character the character
   * @return the shortcut
   */
  public static KeyStroke getShortcut(final boolean ctrl, final boolean alt, final boolean shift,
      final boolean meta, final Character character) {
    return KeyStroke.getKeyStroke(character, (ctrl ? Keyboard.MODIFIER_CTRL : 0)
        + (alt ? Keyboard.MODIFIER_ALT : 0) + (shift ? Keyboard.MODIFIER_SHIFT : 0)
        + (meta ? Keyboard.MODIFIER_META : 0));
  }

  /**
   * Gets the shortcut.
   *
   * @param ctrl the ctrl
   * @param shift the shift
   * @param character the character
   * @return the shortcut
   */
  public static KeyStroke getShortcut(final boolean ctrl, final boolean shift, final Character character) {
    return getShortcut(ctrl, false, shift, false, character);
  }

  /**
   * Gets the shortcut.
   *
   * @param ctrl the ctrl
   * @param character the character
   * @return the shortcut
   */
  public static KeyStroke getShortcut(final boolean ctrl, final Character character) {
    return getShortcut(ctrl, false, false, false, character);
  }

  /**
   * Gets the shortcut using as parameter something like "Alt+A" "Ctrl+M" or
   * "Meta+Shift+R".
   *
   * @param keys the keys
   * @return the shortcut
   */
  public static KeyStroke getShortcut(final String keys) {
    final boolean hasCtrl = keys.contains("Ctrl");
    final boolean hasAlt = keys.contains("Alt");
    final boolean hasMeta = keys.contains("Meta");
    final boolean hasShift = keys.contains("Shift");
    final Character key = keys.charAt(keys.length() - 1);
    return Shortcut.getShortcut(hasCtrl, hasAlt, hasShift, hasMeta, key);
  }

  /**
   * Instantiates a new shortcut.
   */
  private Shortcut() {
  }

}
