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
package cc.kune.chat.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class ToggleShowChatDialogEvent extends GwtEvent<ToggleShowChatDialogEvent.ToggleShowChatDialogHandler> {

    public interface HasToggleShowChatDialogHandlers extends HasHandlers {
        HandlerRegistration addToggleShowChatDialogHandler(ToggleShowChatDialogHandler handler);
    }

    public interface ToggleShowChatDialogHandler extends EventHandler {
        public void onToggleShowChatDialog(ToggleShowChatDialogEvent event);
    }

    private static final Type<ToggleShowChatDialogHandler> TYPE = new Type<ToggleShowChatDialogHandler>();

    public static void fire(final HasHandlers source, final boolean show) {
        source.fireEvent(new ToggleShowChatDialogEvent());
    }

    public static Type<ToggleShowChatDialogHandler> getType() {
        return TYPE;
    }

    protected ToggleShowChatDialogEvent() {
        // Possibly for serialization.
    }

    @Override
    protected void dispatch(final ToggleShowChatDialogHandler handler) {
        handler.onToggleShowChatDialog(this);
    }

    @Override
    public Type<ToggleShowChatDialogHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return "ToggleShowChatDialogEvent";
    }
}
