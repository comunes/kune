package cc.kune.common.client.shortcuts;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.KeyStroke;

public interface GlobalShortcutRegister {

    public abstract void disable();

    public abstract void enable();

    public abstract void put(final KeyStroke keystroke, final AbstractAction action);

}
