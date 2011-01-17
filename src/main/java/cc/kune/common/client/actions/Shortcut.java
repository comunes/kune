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
