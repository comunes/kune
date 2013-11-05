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
package cc.kune.common.client.shortcuts;

public class ShortcutDescriptor {

  private static final String NO_KEYNAME = "nokeyname";

  private static boolean has(final int modifiers, final int modifier) {
    return ((modifiers & modifier) == modifier);
  }

  private final boolean alt;
  private final boolean ctrl;
  private final boolean shift;
  private final int keycode;
  private final String keyName;

  public ShortcutDescriptor(final boolean ctrl, final boolean alt, final boolean shift, final int key) {
    this(ctrl, alt, shift, key, NO_KEYNAME);
  }

  public ShortcutDescriptor(final boolean ctrl, final boolean alt, final boolean shift, final int key,
      final String keyName) {
    this.alt = alt;
    this.ctrl = ctrl;
    this.shift = shift;
    this.keycode = key;
    if (keyName == null) {
      this.keyName = NO_KEYNAME;
    } else {
      this.keyName = keyName;
    }
  }

  public ShortcutDescriptor(final boolean ctrl, final boolean shift, final int key) {
    this(ctrl, false, shift, key, NO_KEYNAME);
  }

  public ShortcutDescriptor(final boolean ctrl, final boolean shift, final int key, final String keyName) {
    this(ctrl, false, shift, key, keyName);
  }

  public ShortcutDescriptor(final boolean ctrl, final int key) {
    this(ctrl, false, false, key, NO_KEYNAME);
  }

  public ShortcutDescriptor(final boolean ctrl, final int keycode, final String keyName) {
    this(ctrl, false, false, keycode, keyName);
  }

  public ShortcutDescriptor(final int keycode, final int modifiers) {
    this(has(modifiers, Keyboard.MODIFIER_CTRL), has(modifiers, Keyboard.MODIFIER_ALT), has(modifiers,
        Keyboard.MODIFIER_SHIFT), keycode, NO_KEYNAME);
  }

  public ShortcutDescriptor(final int keycode, final int modifiers, final String keyName) {
    this(has(modifiers, Keyboard.MODIFIER_CTRL), has(modifiers, Keyboard.MODIFIER_ALT), has(modifiers,
        Keyboard.MODIFIER_SHIFT), keycode, keyName);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final ShortcutDescriptor other = (ShortcutDescriptor) obj;
    if (alt != other.alt) {
      return false;
    }
    if (ctrl != other.ctrl) {
      return false;
    }
    if (keycode != other.keycode) {
      return false;
    }
    if (shift != other.shift) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (alt ? 1231 : 1237);
    result = prime * result + (ctrl ? 1231 : 1237);
    result = prime * result + keycode;
    result = prime * result + (shift ? 1231 : 1237);
    return result;
  }

  public boolean is(final char keyCode, final int modifiers) {
    return (this.keycode == keyCode && same(modifiers, Keyboard.MODIFIER_ALT, alt)
        && same(modifiers, Keyboard.MODIFIER_CTRL, ctrl) && same(modifiers, Keyboard.MODIFIER_SHIFT,
          shift));
  }

  public boolean same(final int modifiers, final int modifier, final boolean keyValue) {
    return (has(modifiers, modifier) == keyValue);
  }

  @Override
  public String toString() {
    String s = " (";
    s += sKey(alt, "Alt");
    s += sKey(ctrl, "Ctrl");
    s += sKey(shift, "Shift");
    s += !keyName.equals(NO_KEYNAME) ? translateKey(keyName) + ")" : ("" + (char) keycode).toUpperCase()
        + ")";
    return s;
  }

  protected String translateKey(final String keyNameToTranslate) {
    // return Resources.i18n.tWithNT(keyNameToTranslate, "The '" +
    // keyNameToTranslate + "' keyboard key");
    return keyNameToTranslate;
  }

  private String sKey(final boolean key, final String specialKeyName) {
    return key ? translateKey(specialKeyName) + "+" : "";
  }

}
