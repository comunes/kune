/* AWTKeyStroke.java -- an immutable key stroke
   Copyright (C) 2002, 2004, 2005, 2006, 2012 Free Software Foundation

This file is part of GNU Classpath.

GNU Classpath is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

GNU Classpath is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Classpath; see the file COPYING.  If not, write to the
Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
02110-1301 USA.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version. */

package cc.kune.common.client.actions;

import java.util.HashMap;
import java.util.Map;

import cc.kune.common.client.shortcuts.Keyboard;
import cc.kune.common.shared.i18n.I18n;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;

/**
 * This class mirrors KeyEvents, representing both low-level key presses and key
 * releases, and high level key typed inputs. However, this class forms
 * immutable strokes, and can be efficiently reused via the factory methods for
 * creating them.
 * 
 * <p>
 * For backwards compatibility with Swing, this supports a way to build
 * instances of a subclass, using reflection, provided the subclass has a no-arg
 * constructor (of any accessibility).
 * 
 * @author Eric Blake (ebb9@email.byu.edu)
 * @author Andrew John Hughes (gnu_andrew@member.fsf.org)
 * @author Adapted version for GWT (C) The kune development team
 * @see #getKeyStroke(char)
 * @since 1.4
 * @status updated to 1.4
 */
public class KeyStroke {

  /**
   * The cache of recently created keystrokes. This maps KeyStrokes to
   * KeyStrokes in a cache which removes the least recently accessed entry,
   * under the assumption that garbage collection of a new keystroke is easy
   * when we find the old one that it matches in the cache.
   */
  private static final Map<KeyStroke, KeyStroke> CACHE = new HashMap<KeyStroke, KeyStroke>();

  private static final char CHAR_UNDEFINED = '\uffff';

  /** The most recently generated keystroke, or null. */
  private static KeyStroke recent;

  private static final int VK_UNDEFINED = 0;
  /**
   * A table of keyCode names to values. This is package-private to avoid an
   * accessor method.
   * 
   * @see #getKeyStroke(String)
   */
  static final Map<String, Object> VKTABLE = new HashMap<String, Object>();

  public static int getKeyboardModifiers(final NativeEvent event) {
    return (event.getShiftKey() ? Keyboard.MODIFIER_SHIFT : 0)
        | (event.getMetaKey() ? Keyboard.MODIFIER_META : 0)
        | (event.getCtrlKey() ? Keyboard.MODIFIER_CTRL : 0)
        | (event.getAltKey() ? Keyboard.MODIFIER_ALT : 0);
  }

  /**
   * Returns a keystroke representing a typed character.
   * 
   * @param keyChar
   *          the typed character
   * @return the specified keystroke
   */
  public static KeyStroke getKeyStroke(final char keyChar) {
    return getKeyStroke(keyChar, VK_UNDEFINED, 0, false);
  }

  /**
   * Gets the appropriate keystroke, creating one if necessary.
   * 
   * @param keyChar
   *          the keyChar
   * @param keyCode
   *          the keyCode
   * @param modifiers
   *          the modifiers
   * @param release
   *          true for key release
   * @return the specified keystroke
   */
  private static KeyStroke getKeyStroke(final char keyChar, final int keyCode, final int modifiers,
      final boolean release) {
    // Check level 0 cache.
    KeyStroke stroke = recent; // Avoid thread races.
    if (stroke != null && stroke.keyChar == keyChar && stroke.keyCode == keyCode
        && stroke.modifiers == modifiers && stroke.onKeyRelease == release) {
      return stroke;
    }
    stroke = new KeyStroke(keyChar, keyCode, modifiers, release);
    // Check level 1 cache.
    final KeyStroke cached = CACHE.get(stroke);
    if (cached == null) {
      CACHE.put(stroke, stroke);
    } else {
      stroke = cached;
    }
    return recent = stroke;
  }

  /**
   * Returns a keystroke representing a typed character with the given
   * modifiers. Note that keyChar is a <code>Character</code> instead of a
   * <code>char</code> to avoid accidental ambiguity with
   * <code>getKeyStroke(int, int)</code>. The modifiers are the bitwise or of
   * the masks found in InputEvent; the new style (*_DOWN_MASK) is preferred,
   * but the old style will work.
   * 
   * @param keyChar
   *          the typed character
   * @param modifiers
   *          the modifiers, or 0
   * @return the specified keystroke
   * @throws IllegalArgumentException
   *           if keyChar is null
   */
  public static KeyStroke getKeyStroke(final Character keyChar, final int modifiers) {
    if (keyChar == null) {
      throw new IllegalArgumentException();
    }
    return getKeyStroke(keyChar.charValue(), VK_UNDEFINED, modifiers, false);
  }

  /**
   * Returns a keystroke representing a pressed key event, with the given
   * modifiers. The "virtual key" should be one of the VK_* constants in
   * {@link KeyEvent}. The modifiers are the bitwise or of the masks found in
   * InputEvent; the new style (*_DOWN_MASK) is preferred, but the old style
   * will work.
   * 
   * @param keyCode
   *          the virtual key
   * @param modifiers
   *          the modifiers, or 0
   * @return the specified keystroke
   */
  public static KeyStroke getKeyStroke(final int keyCode, final int modifiers) {
    return getKeyStroke(CHAR_UNDEFINED, keyCode, modifiers, false);
  }

  /**
   * Returns a keystroke representing a pressed or released key event, with the
   * given modifiers. The "virtual key" should be one of the VK_* constants in
   * {@link KeyEvent}. The modifiers are the bitwise or of the masks found in
   * InputEvent; the new style (*_DOWN_MASK) is preferred, but the old style
   * will work.
   * 
   * @param keyCode
   *          the virtual key
   * @param modifiers
   *          the modifiers, or 0
   * @param release
   *          true if this is a key release instead of a key press
   * @return the specified keystroke
   */
  public static KeyStroke getKeyStroke(final int keyCode, final int modifiers, final boolean release) {
    return getKeyStroke(CHAR_UNDEFINED, keyCode, modifiers, release);
  }

  /**
   * Returns a keystroke representing what caused the key event.
   * 
   * @param event
   *          the key event to convert
   * @return the specified keystroke, or null if the event is invalid
   * @throws NullPointerException
   *           if event is null
   */
  public static KeyStroke getKeyStrokeForEvent(final Event event) {
    final int eventModif = getKeyboardModifiers(event);
    final int eventKeyCode = event.getKeyCode();
    // Log.info("key: " + eventKeyCode + " modifier: " + eventModif);
    switch (DOM.eventGetType(event)) {
    case Event.ONKEYDOWN:
      return getKeyStroke(CHAR_UNDEFINED, eventKeyCode, eventModif, false);
    case Event.ONKEYPRESS:
      final char charac = (char) eventKeyCode;
      return getKeyStroke(Character.isLowerCase(charac) ? Character.toUpperCase(charac) : charac,
          VK_UNDEFINED, eventModif, false);
    case Event.ONKEYUP:
      return getKeyStroke(CHAR_UNDEFINED, eventKeyCode, eventModif, false);
    default:
      return null;
    }
  }

  /**
   * The typed character, or CHAR_UNDEFINED for key presses and releases.
   * 
   * @serial the keyChar
   */
  private final char keyChar;

  /**
   * The virtual key code, or VK_UNDEFINED for key typed. Package visible for
   * use by Component.
   * 
   * @serial the keyCode
   */
  int keyCode;

  /**
   * The modifiers in effect. To match Sun, this stores the old style masks for
   * shift, control, alt, meta, and alt-graph (but not button1); as well as the
   * new style of extended modifiers for all modifiers.
   * 
   * @serial bitwise or of the *_DOWN_MASK modifiers
   */
  private int modifiers;

  /**
   * True if this is a key release; should only be true if keyChar is
   * CHAR_UNDEFINED.
   * 
   * @serial true to distinguish key pressed from key released
   */
  private boolean onKeyRelease;

  /**
   * Construct a keystroke with default values: it will be interpreted as a key
   * typed event with an invalid character and no modifiers. Client code should
   * use the factory methods instead.
   * 
   * @see #getKeyStroke(char)
   * @see #getKeyStroke(Character, int)
   * @see #getKeyStroke(int, int, boolean)
   * @see #getKeyStroke(int, int)
   * @see #getKeyStrokeForEvent(KeyEvent)
   * @see #getKeyStroke(String)
   */
  protected KeyStroke() {
    keyChar = CHAR_UNDEFINED;
  }

  /**
   * Construct a keystroke with the given values. Client code should use the
   * factory methods instead.
   * 
   * @param keyChar
   *          the character entered, if this is a key typed
   * @param keyCode
   *          the key pressed or released, or VK_UNDEFINED for key typed
   * @param modifiers
   *          the modifier keys for the keystroke, in old or new style
   * @param onKeyRelease
   *          true if this is a key release instead of a press
   * @see #getKeyStroke(char)
   * @see #getKeyStroke(Character, int)
   * @see #getKeyStroke(int, int, boolean)
   * @see #getKeyStroke(int, int)
   * @see #getKeyStrokeForEvent(KeyEvent)
   * @see #getKeyStroke(String)
   */
  protected KeyStroke(final char keyChar, final int keyCode, final int modifiers,
      final boolean onKeyRelease) {
    this.keyChar = keyChar;
    this.keyCode = keyCode;
    // No need to call extend(), as only trusted code calls this
    // constructor.
    this.modifiers = modifiers;
    this.onKeyRelease = onKeyRelease;
  }

  /**
   * Tests two keystrokes for equality.
   * 
   * @param obj
   *          the object to test
   * @return true if it is equal
   */
  @Override
  public final boolean equals(final Object obj) {
    if (!(obj instanceof KeyStroke)) {
      return false;
    }
    final KeyStroke stroke = (KeyStroke) obj;
    return this == obj
        || (keyChar == stroke.keyChar && keyCode == stroke.keyCode && modifiers == stroke.modifiers && onKeyRelease == stroke.onKeyRelease);
  }

  /**
   * Returns the character of this keystroke, if it was typed.
   * 
   * @return the character value, or CHAR_UNDEFINED
   * @see #getKeyStroke(char)
   */
  public final char getKeyChar() {
    return keyChar;
  }

  /**
   * Returns the virtual key code of this keystroke, if it was pressed or
   * released. This will be a VK_* constant from KeyEvent.
   * 
   * @return the virtual key code value, or VK_UNDEFINED
   * @see #getKeyStroke(int, int)
   */
  public final int getKeyCode() {
    return keyCode;
  }

  /**
   * Returns the AWT event type of this keystroke. This is one of
   * {@link KeyEvent#KEY_TYPED}, {@link KeyEvent#KEY_PRESSED}, or
   * {@link KeyEvent#KEY_RELEASED}.
   * 
   * @return the key event type
   */
  public final int getKeyEventType() {
    return keyCode == VK_UNDEFINED ? Event.ONKEYDOWN : onKeyRelease ? Event.ONKEYUP : Event.ONKEYPRESS;
  }

  @SuppressWarnings("deprecation")
  public String getKeyText() {
    switch (keyCode) {
    case KeyCodes.KEY_BACKSPACE:
      return translateKey("Backspace");
      // case KeyCodes.KEY_DELETE:
      // return translateKey("Delete");
    case KeyCodes.KEY_DOWN:
      return translateKey("Down");
    case KeyCodes.KEY_END:
      return translateKey("End");
    case KeyCodes.KEY_ENTER:
      return translateKey("Enter");
    case KeyCodes.KEY_ESCAPE:
      return translateKey("Escape");
    case KeyCodes.KEY_HOME:
      return translateKey("Home");
    case KeyCodes.KEY_LEFT:
      return translateKey("Left");
    case KeyCodes.KEY_PAGEDOWN:
      return translateKey("Page Down");
    case KeyCodes.KEY_PAGEUP:
      return translateKey("Page Up");
    case KeyCodes.KEY_RIGHT:
      return translateKey("Right");
    case KeyCodes.KEY_TAB:
      return translateKey("Tab");
    case KeyCodes.KEY_UP:
      return translateKey("Up");
    case Keyboard.KEY_SPACE:
      return translateKey("Space");
    case Keyboard.KEY_F1:
    case Keyboard.KEY_F2:
    case Keyboard.KEY_F3:
    case Keyboard.KEY_F4:
    case Keyboard.KEY_F5:
    case Keyboard.KEY_F6:
    case Keyboard.KEY_F7:
    case Keyboard.KEY_F8:
    case Keyboard.KEY_F9:
    case Keyboard.KEY_F10:
    case Keyboard.KEY_F11:
    case Keyboard.KEY_F12:
      return translateKey("F" + (keyCode - (Keyboard.KEY_F1 - 1)));
    case Keyboard.KEY_COMMA:
    case Keyboard.KEY_PERIOD:
    case Keyboard.KEY_SLASH:
    case Keyboard.KEY_0:
    case Keyboard.KEY_1:
    case Keyboard.KEY_2:
    case Keyboard.KEY_3:
    case Keyboard.KEY_4:
    case Keyboard.KEY_5:
    case Keyboard.KEY_6:
    case Keyboard.KEY_7:
    case Keyboard.KEY_8:
    case Keyboard.KEY_9:
    case Keyboard.KEY_SEMICOLON:
    case Keyboard.KEY_EQUALS:
    case Keyboard.KEY_A:
    case Keyboard.KEY_B:
    case Keyboard.KEY_C:
    case Keyboard.KEY_D:
    case Keyboard.KEY_E:
    case Keyboard.KEY_F:
    case Keyboard.KEY_G:
    case Keyboard.KEY_H:
    case Keyboard.KEY_I:
    case Keyboard.KEY_J:
    case Keyboard.KEY_K:
    case Keyboard.KEY_L:
    case Keyboard.KEY_M:
    case Keyboard.KEY_N:
    case Keyboard.KEY_O:
    case Keyboard.KEY_P:
    case Keyboard.KEY_Q:
    case Keyboard.KEY_R:
    case Keyboard.KEY_S:
    case Keyboard.KEY_T:
    case Keyboard.KEY_U:
    case Keyboard.KEY_V:
    case Keyboard.KEY_W:
    case Keyboard.KEY_X:
    case Keyboard.KEY_Y:
    case Keyboard.KEY_Z:
    case Keyboard.KEY_OPEN_BRACKET:
    case Keyboard.KEY_BACK_SLASH:
    case Keyboard.KEY_CLOSE_BRACKET:
      return String.valueOf(keyCode).toUpperCase();
    default:
      final String charS = String.valueOf(keyChar);
      if (charS == null) {
        return "Unknown keyCode: 0x" + (keyCode < 0 ? "-" : "") + Integer.toHexString(Math.abs(keyCode));
      } else {
        if (" ".equals(charS)) {
          return translateKey("Space");
        } else {
          return charS;
        }
      }
    }
  }

  /**
   * Returns the modifiers for this keystroke. This will be a bitwise or of
   * constants from InputEvent; it includes the old style masks for shift,
   * control, alt, meta, and alt-graph (but not button1); as well as the new
   * style of extended modifiers for all modifiers.
   * 
   * @return the modifiers
   * @see #getKeyStroke(Character, int)
   * @see #getKeyStroke(int, int)
   */
  public final int getModifiers() {
    return modifiers;
  }

  /**
   * Returns a hashcode for this key event. It is not documented, but appears to
   * be: <code>(getKeyChar() + 1) * (getKeyCode() + 1)
   * * (getModifiers() + 1) * 2 + (isOnKeyRelease() ? 1 : 2)</code>.
   * 
   * @return the hashcode
   */
  @Override
  public int hashCode() {
    return (keyChar + 1) * (keyCode + 1) * (modifiers + 1) * 2 + (onKeyRelease ? 1 : 2);
  }

  /**
   * Tests if this keystroke is a key release.
   * 
   * @return true if this is a key release
   * @see #getKeyStroke(int, int, boolean)
   */
  public final boolean isOnKeyRelease() {
    return onKeyRelease;
  }

  /**
   * Returns a cached version of the deserialized keystroke, if available.
   * 
   * @return a cached replacement if something goes wrong
   */
  protected Object readResolve() {
    final KeyStroke s = CACHE.get(this);
    if (s != null) {
      return s;
    }
    CACHE.put(this, this);
    return this;
  }

  @Override
  public String toString() {
    String s = " (";
    if ((modifiers & Keyboard.MODIFIER_META) != 0) {
      s += translateKey("Meta") + "+";
    }
    if ((modifiers & Keyboard.MODIFIER_CTRL) != 0) {
      s += translateKey("Ctrl") + "+";
    }
    if ((modifiers & Keyboard.MODIFIER_ALT) != 0) {
      s += translateKey("Alt") + "+";
    }
    if ((modifiers & Keyboard.MODIFIER_SHIFT) != 0) {
      s += translateKey("Shift") + "+";
    }
    /*
     * if ((modifiers & Event.BUTTON_LEFT) != 0) { s += translateKey("Button1")
     * + "+"; } if ((modifiers & Event.BUTTON_RIGHT) != 0) { s +=
     * translateKey("Button2") + "+"; } if ((modifiers & Event.BUTTON_MIDDLE) !=
     * 0) { s += translateKey("Button3") + "+"; }
     */
    s += getKeyText() + ")";
    return s;
  }

  protected String translateKey(final String keyNameToTranslate) {
    return I18n.tWithNT(keyNameToTranslate, "The '" + keyNameToTranslate + "' keyboard key");
    // return keyNameToTranslate;
  }

} // class KeyStroke
