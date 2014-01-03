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
package cc.kune.common.client.shortcuts;

// TODO: Auto-generated Javadoc
/**
 * The Class ShortcutDescriptor.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ShortcutDescriptor {

  /** The Constant NO_KEYNAME. */
  private static final String NO_KEYNAME = "nokeyname";

  /**
   * Checks for.
   *
   * @param modifiers the modifiers
   * @param modifier the modifier
   * @return true, if successful
   */
  private static boolean has(final int modifiers, final int modifier) {
    return ((modifiers & modifier) == modifier);
  }

  /** The alt. */
  private final boolean alt;
  
  /** The ctrl. */
  private final boolean ctrl;
  
  /** The shift. */
  private final boolean shift;
  
  /** The keycode. */
  private final int keycode;
  
  /** The key name. */
  private final String keyName;

  /**
   * Instantiates a new shortcut descriptor.
   *
   * @param ctrl the ctrl
   * @param alt the alt
   * @param shift the shift
   * @param key the key
   */
  public ShortcutDescriptor(final boolean ctrl, final boolean alt, final boolean shift, final int key) {
    this(ctrl, alt, shift, key, NO_KEYNAME);
  }

  /**
   * Instantiates a new shortcut descriptor.
   *
   * @param ctrl the ctrl
   * @param alt the alt
   * @param shift the shift
   * @param key the key
   * @param keyName the key name
   */
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

  /**
   * Instantiates a new shortcut descriptor.
   *
   * @param ctrl the ctrl
   * @param shift the shift
   * @param key the key
   */
  public ShortcutDescriptor(final boolean ctrl, final boolean shift, final int key) {
    this(ctrl, false, shift, key, NO_KEYNAME);
  }

  /**
   * Instantiates a new shortcut descriptor.
   *
   * @param ctrl the ctrl
   * @param shift the shift
   * @param key the key
   * @param keyName the key name
   */
  public ShortcutDescriptor(final boolean ctrl, final boolean shift, final int key, final String keyName) {
    this(ctrl, false, shift, key, keyName);
  }

  /**
   * Instantiates a new shortcut descriptor.
   *
   * @param ctrl the ctrl
   * @param key the key
   */
  public ShortcutDescriptor(final boolean ctrl, final int key) {
    this(ctrl, false, false, key, NO_KEYNAME);
  }

  /**
   * Instantiates a new shortcut descriptor.
   *
   * @param ctrl the ctrl
   * @param keycode the keycode
   * @param keyName the key name
   */
  public ShortcutDescriptor(final boolean ctrl, final int keycode, final String keyName) {
    this(ctrl, false, false, keycode, keyName);
  }

  /**
   * Instantiates a new shortcut descriptor.
   *
   * @param keycode the keycode
   * @param modifiers the modifiers
   */
  public ShortcutDescriptor(final int keycode, final int modifiers) {
    this(has(modifiers, Keyboard.MODIFIER_CTRL), has(modifiers, Keyboard.MODIFIER_ALT), has(modifiers,
        Keyboard.MODIFIER_SHIFT), keycode, NO_KEYNAME);
  }

  /**
   * Instantiates a new shortcut descriptor.
   *
   * @param keycode the keycode
   * @param modifiers the modifiers
   * @param keyName the key name
   */
  public ShortcutDescriptor(final int keycode, final int modifiers, final String keyName) {
    this(has(modifiers, Keyboard.MODIFIER_CTRL), has(modifiers, Keyboard.MODIFIER_ALT), has(modifiers,
        Keyboard.MODIFIER_SHIFT), keycode, keyName);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
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

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
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

  /**
   * Checks if is.
   *
   * @param keyCode the key code
   * @param modifiers the modifiers
   * @return true, if successful
   */
  public boolean is(final char keyCode, final int modifiers) {
    return (this.keycode == keyCode && same(modifiers, Keyboard.MODIFIER_ALT, alt)
        && same(modifiers, Keyboard.MODIFIER_CTRL, ctrl) && same(modifiers, Keyboard.MODIFIER_SHIFT,
          shift));
  }

  /**
   * Same.
   *
   * @param modifiers the modifiers
   * @param modifier the modifier
   * @param keyValue the key value
   * @return true, if successful
   */
  public boolean same(final int modifiers, final int modifier, final boolean keyValue) {
    return (has(modifiers, modifier) == keyValue);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
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

  /**
   * Translate key.
   *
   * @param keyNameToTranslate the key name to translate
   * @return the string
   */
  protected String translateKey(final String keyNameToTranslate) {
    // return Resources.i18n.tWithNT(keyNameToTranslate, "The '" +
    // keyNameToTranslate + "' keyboard key");
    return keyNameToTranslate;
  }

  /**
   * S key.
   *
   * @param key the key
   * @param specialKeyName the special key name
   * @return the string
   */
  private String sKey(final boolean key, final String specialKeyName) {
    return key ? translateKey(specialKeyName) + "+" : "";
  }

}
